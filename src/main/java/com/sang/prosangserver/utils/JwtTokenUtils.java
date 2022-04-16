package com.sang.prosangserver.utils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1135695855174882924L;
		
	//retrieve username from jwt token
	public String getUsernameFromToken(String token, String secret) {
		return getClaimFromToken(token, Claims::getSubject, secret);
	}
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token, String secret) {
		return getClaimFromToken(token, Claims::getExpiration, secret);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String secret) {
		final Claims claims = getAllClaimsFromToken(token, secret);
		return claimsResolver.apply(claims);
	}
	
	//for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token, String secret) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//check if the token has expired
	public Boolean isTokenExpired(String token, String secret) {
		final Date expiration = getExpirationDateFromToken(token, secret);
		return expiration.before(new Date());
	}
	
	//generate token for user
	public String generateToken(String secret,  LocalDateTime expiredTime, String username) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, secret, expiredTime, username);
	}

	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String secret, LocalDateTime expiredTime, String subject) {
		Date convertedDatetime = Date.from(expiredTime.atZone(ZoneId.systemDefault()).toInstant());
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(convertedDatetime)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	//validate token
	public Boolean validateToken(String token, UserDetails userDetails, String secret) {
		final String username = getUsernameFromToken(token, secret);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secret));
	}
}
