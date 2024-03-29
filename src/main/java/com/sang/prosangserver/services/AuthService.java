package com.sang.prosangserver.services;

import java.time.LocalDateTime;

import com.sang.prosangserver.models.AuthUser;
import com.sang.prosangserver.utils.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.dto.request.ChangePasswordRequest;
import com.sang.prosangserver.dto.request.ChangePasswordResponse;
import com.sang.prosangserver.dto.request.LoginRequest;
import com.sang.prosangserver.dto.request.RefreshTokenRequest;
import com.sang.prosangserver.dto.response.LoginResponse;
import com.sang.prosangserver.dto.response.RefreshTokenResponse;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UnauthorizedException;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.models.JWTToken;
import com.sang.prosangserver.repositories.AccountRepository;

import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {

	private final JwtService jwtService;

	private final AccountRepository accountRepository;

	private final AuthenticationManager authenticationManager;

	private final MessageService messageService;

	private final PasswordEncoder passwordEncoder;

	public AuthService(JwtService jwtService,
			AccountRepository accountRepository,
			AuthenticationManager authenticationManager,
			MessageService messageService,
			PasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.accountRepository = accountRepository;
		this.authenticationManager = authenticationManager;
		this.messageService = messageService;
		this.passwordEncoder = passwordEncoder;
	}

	public LoginResponse login(LoginRequest request) {
		Authentication auth;
		try {
			auth = authenticate(request.getUsername(), request.getPassword());
			if (!auth.isAuthenticated()) {
				throw new UnauthorizedException();
			}
		} catch (Exception e) {
			throw new UnauthorizedException();
		}

		Account acc = getValidAccountByUserName(request.getUsername());
		final JWTToken accessToken = jwtService.generateAccessToken(acc.getUsername());
		final JWTToken refreshToken = jwtService.generateRefreshToken(acc.getUsername());
		acc.getAuth().setToken(accessToken.getToken());
		acc.getAuth().setTokenExpiredTime(accessToken.getExpiredTime());
		acc.getAuth().setRefreshToken(refreshToken.getToken());
		acc.getAuth().setRefreshTokenExpiredTime(refreshToken.getExpiredTime());
		acc.setLastLoginTime(LocalDateTime.now());
		accountRepository.saveAndFlush(acc);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return new LoginResponse(
				acc.getId(),
				acc.getUsername(),
				acc.getEmail(),
				StringUtils.getUserName(acc.getDetail().getFirstName(), acc.getDetail().getLastName()).trim(),
				acc.getDetail().getAccountPhotoUrl(),
				acc.getAuth().getToken(), acc.getAuth().getTokenExpiredTime(),
				acc.getAuth().getRefreshToken(), acc.getAuth().getRefreshTokenExpiredTime());
	}

	public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

		Account acc = getValidAccountByUserName(request.getUsername());
		validateRefreshToken(acc, request.getRefreshToken());
		final JWTToken accessToken = jwtService.generateAccessToken(acc.getUsername());
		final JWTToken refreshToken = jwtService.generateRefreshToken(acc.getUsername());
		acc.getAuth().setToken(accessToken.getToken());
		acc.getAuth().setTokenExpiredTime(accessToken.getExpiredTime());
		acc.getAuth().setRefreshToken(refreshToken.getToken());
		acc.getAuth().setRefreshTokenExpiredTime(refreshToken.getExpiredTime());
		accountRepository.saveAndFlush(acc);
		return new RefreshTokenResponse(
				acc.getAuth().getToken(), acc.getAuth().getTokenExpiredTime(),
				acc.getAuth().getRefreshToken(), acc.getAuth().getRefreshTokenExpiredTime());
	}

	public ChangePasswordResponse changePassword(Long id, ChangePasswordRequest request) {

		Authentication auth = null;
		try {
			auth = authenticate(request.getUsername(), request.getPassword());
			if (!auth.isAuthenticated()) {
				throw new UnauthorizedException();
			}
		} catch (Exception e) {
			throw new UnauthorizedException();
		}

		Account acc = getValidAccountById(id);
		acc.getAuth().setPassword(passwordEncoder.encode(request.getNewPassword()));
		accountRepository.saveAndFlush(acc);
		return new ChangePasswordResponse(acc.getUsername());
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			AuthUser userDetails = (AuthUser) auth.getPrincipal();
			Account acc = getValidAccountById(userDetails.getId());
			acc.getAuth().setToken(null);
			acc.getAuth().setTokenExpiredTime(null);
			acc.getAuth().setRefreshToken(null);
			acc.getAuth().setRefreshTokenExpiredTime(null);
			accountRepository.saveAndFlush(acc);
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

	private void validateRefreshToken(Account acc, String refreshToken) {
		Boolean isValid;
		try {
			String username = jwtService.getUsernameFromRefreshToken(refreshToken);
			isValid = acc.getUsername().equals(username);
			isValid = isValid && !jwtService.isRefreshTokenExpired(refreshToken);
			isValid = isValid && (refreshToken.equals(acc.getAuth().getRefreshToken()));
		} catch (ExpiredJwtException ee) {
			// TODO: handle exception when refresh token has expired
			throw new UnauthorizedException();
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
		if (!isValid) {
			throw new UnauthorizedException();
		}
	}

	private Authentication authenticate(String username, String password) {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	public Account getValidAccountByUserName(String username) {
		return accountRepository.getOneByUsernameAndIsDeletedIsFalse(username)
				.orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND)));
	}

	public Account getValidAccountById(Long id) {
		return accountRepository.getOneByIdAndIsDeletedIsFalse(id)
				.orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND)));
	}

	public AuthUser getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if (!auth.isAuthenticated() ||  !(principal instanceof UserDetails)) {
			throw new UnauthorizedException();
		}
		return (AuthUser) principal;
	}

	public void checkCorrectAccountId(AuthUser user, Long accountId) {
		if (!user.getId().equals(accountId)) {
			throw new UnauthorizedException();
		}
	}

	public AuthUser getAuthUserButNotRequired() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if (!auth.isAuthenticated() ||  !(principal instanceof UserDetails)) {
			return null;
		}
		return (AuthUser) principal;
	}
}
