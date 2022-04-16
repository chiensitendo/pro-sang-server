package com.sang.prosangserver.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.sang.prosangserver.utils.JwtTokenUtils;

@SpringBootTest
public class JwtTokenUtilsTests {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	private static UserDetails USER_DETAILS;
	
	private static final Long EXPIRED_TIME = 1L;
	
	private static final String SECRET = "token_secret";
	
	@BeforeEach
	public void createUserDetails() {
		if (USER_DETAILS == null)
			USER_DETAILS = new User("test2", "123456", new ArrayList<>());
	}
	
	@Test
	public void injectionNotNullTest() {
		assertThat(jwtTokenUtils).isNotNull();
	}
	
	@Test
	public void expiredTimeTest() {
		LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(EXPIRED_TIME);
//		String token = jwtTokenUtils.generateToken(SECRET, expiredTime, USER_DETAILS);
//		Date dateFromToken = jwtTokenUtils.getExpirationDateFromToken(token, SECRET);
//		assertTrue(dateFromToken.after(new Date()));
	}
}
