package com.sang.prosangserver.securities;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sang.prosangserver.constants.AuthConstants;
import com.sang.prosangserver.services.JwtService;
import com.sang.prosangserver.services.JwtUserDetailsService;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final JwtUserDetailsService jwtUserDetailsService;

	private final JwtService jwtService;
		
	public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, 
			JwtService jwtService) {
		this.jwtUserDetailsService = jwtUserDetailsService;
		this.jwtService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		if (!isPermitAll(request.getRequestURI(), request.getContextPath()) && requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			String username;
			String jwtToken;
			// JWT Token is in the form "Bearer token". Remove Bearer word and get
			// only the Token
			jwtToken = requestTokenHeader.substring(7);
			username = jwtService.getUsernameFromToken(jwtToken);

			// Once we get the token validate it.
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

				// if token is valid configure Spring Security to manually set
				// authentication
				if (jwtService.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		
		chain.doFilter(request, response);
		
	}
	
	private boolean isPermitAll(String uri, String contextPath) {
		return Stream.of(AuthConstants.IGNORE_AUTH_PATHS).anyMatch(path -> (contextPath + path).equals(uri));
	}
	

}
