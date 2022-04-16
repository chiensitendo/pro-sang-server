package com.sang.prosangserver.integrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.prosangserver.constants.GeneralConstants;
import com.sang.prosangserver.dto.request.CreateAccountRequest;
import com.sang.prosangserver.dto.request.LoginRequest;
import com.sang.prosangserver.dto.request.RefreshTokenRequest;
import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.dto.response.LoginResponse;
import com.sang.prosangserver.dto.response.RefreshTokenResponse;
import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.enums.Roles;
import com.sang.prosangserver.repositories.AccountRepository;
import com.sang.prosangserver.services.AccountService;

@SpringBootTest
public class JWTSecurityTests {
		
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	private MockMvc mockMvc;
		
	private Account account = null;
	
	
	private void createAccount() throws Exception {
		// Create test account
		Long uuid = Instant.now().toEpochMilli();
		String username = "test_username_" + uuid;
		String email = uuid + "_test@gmail.com";
		Integer role = Roles.USER.getId();
		String password = "123456";
		String name = "test name";
		CreateAccountRequest request = 
			new CreateAccountRequest(username, email, role, password, name);
		accountService.createAccount(request);
		account = accountRepository.getOneByEmailOrUsernameAndIsDeletedIsFalse(email, username).orElseThrow();
	}
	
	@AfterEach
	public void rollback() throws Exception {
		if (account != null) {
			accountRepository.deleteById(account.getId());
			account = null;
		}
	}
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) throws Exception {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
		createAccount();
	}
	
	@Test
	public void callTest() throws Exception {
		this.mockMvc.perform(get("/api/account/1"))
		.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void jwtSecuritySuccessTest() throws Exception {
		if (account == null) {
			throw new Exception("user wasn't created");
		}
		
		// Login Test
		LoginRequest req = new LoginRequest(account.getUsername(), "123456");
		MvcResult result = this.mockMvc.perform(post("/account/login")
				.content(objectMapper.writeValueAsString(req))
				.contentType(GeneralConstants.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		LoginResponse loginResponse = getBody(result, LoginResponse.class);
		assertThat(loginResponse).isNotNull();
		
		// Verify Access Token
		MvcResult getAccountResult = this.mockMvc
				.perform(get("/account/" + loginResponse.getId())
				.with(userToken(loginResponse.getAccessToken())))
				.andExpect(status().isOk())
				.andDo(print()).andReturn();
		UserDetailResponse detailResponse = getBody(getAccountResult, UserDetailResponse.class);
		assertThat(detailResponse).isNotNull();
		
		// Verify Refresh Token
		RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
		refreshTokenRequest.setRefreshToken(loginResponse.getRefreshToken());
		refreshTokenRequest.setUsername(loginResponse.getUsername());
		MvcResult refreshTokenResult = this.mockMvc
				.perform(post("/account/refresh-token")
				.content(objectMapper.writeValueAsString(refreshTokenRequest))
				.contentType(GeneralConstants.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		RefreshTokenResponse refreshTokenResponse = getBody(refreshTokenResult, RefreshTokenResponse.class);
		assertThat(refreshTokenResponse).isNotNull();
		
		// Verify New Access Token
		MvcResult getNewAccountResult = this.mockMvc
						.perform(get("/account/" + loginResponse.getId())
						.with(userToken(refreshTokenResponse.getAccessToken())))
						.andExpect(status().isOk())
						.andDo(print()).andReturn();
		UserDetailResponse detail2Response = getBody(getNewAccountResult, UserDetailResponse.class);
		assertThat(detail2Response).isNotNull();		
		
	}
	
	protected <T> T getBody(MvcResult result, Class<T> clazz) throws Exception {
		
		String responseContent = result
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		GenericResponse genericResponse = objectMapper.readValue(responseContent, GenericResponse.class);
		T response = objectMapper.convertValue(genericResponse.getBody(), clazz);
		return response;
	}
	
	protected RequestPostProcessor userToken(String token) {
		return request -> {
			request.addHeader("Authorization", "Bearer " + token);
			return request;
		};
	}
}
