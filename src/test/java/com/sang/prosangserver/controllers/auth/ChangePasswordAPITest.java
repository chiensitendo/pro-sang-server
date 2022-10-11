package com.sang.prosangserver.controllers.auth;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.prosangserver.constants.GeneralConstants;
import com.sang.prosangserver.dto.request.ChangePasswordRequest;
import com.sang.prosangserver.dto.request.ChangePasswordResponse;
import com.sang.prosangserver.dto.request.CreateAccountRequest;
import com.sang.prosangserver.dto.request.LoginRequest;
import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.enums.Roles;
import com.sang.prosangserver.repositories.AccountRepository;
import com.sang.prosangserver.services.AccountService;
import com.sang.prosangserver.services.JwtService;

@SpringBootTest
public class ChangePasswordAPITest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtService jwtService;

	private MockMvc mockMvc;

	private Account account = null;

	private void createAccount() throws Exception {
		// Create test account
		Long uuid = Instant.now().toEpochMilli();
		String username = "test_username_change_password_" + uuid;
		String email = uuid + "_change_password_test@gmail.com";
		Integer role = Roles.USER.getId();
		String password = "123456";
		String firstName = "test";
		String lastName = "name";
		CreateAccountRequest request =
			new CreateAccountRequest(username, email, role, password, firstName, lastName, "");
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
	public void changePasswordSuccessTest() throws Exception {
		if (account == null) {
			throw new Exception("user wasn't created");
		}
		// Change password
		String newPassword = "111111";
		String token = jwtService.generateAccessToken(account.getUsername()).getToken();
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUsername(account.getUsername());
		request.setPassword("123456");
		request.setNewPassword(newPassword);
		MvcResult result = this.mockMvc
				.perform(post("/account/" + account.getId() + "/password-change")
				.with(userToken(token))
				.content(objectMapper.writeValueAsString(request))
				.contentType(GeneralConstants.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		ChangePasswordResponse response = getBody(result, ChangePasswordResponse.class);
		assertThat(response).isNotNull();

		// Verify New Password
		LoginRequest loginRequest = new LoginRequest(account.getUsername(), newPassword);
		this.mockMvc
		.perform(post("/account/login")
		.content(objectMapper.writeValueAsString(loginRequest))
		.contentType(GeneralConstants.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andDo(print());
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
