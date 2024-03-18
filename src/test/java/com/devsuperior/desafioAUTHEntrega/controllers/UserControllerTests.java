package com.devsuperior.desafioAUTHEntrega.controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.desafioAUTHEntrega.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	private long existingId;
	private long nonExistingId;
	
	private String adminUsername;
	private String adminPassword;
	private String clientUsername;
	private String clientPassword;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 100000L;
		
		adminUsername = "bob@gmail.com";
		adminPassword = "123456";
		clientUsername = "ana@gmail.com";
		clientPassword = "123456";
	}
	
	@Test
	public void findByIdShouldReturnUnauthorizedWhenNoTokenGiven() throws Exception {

		ResultActions result =
				mockMvc.perform(get("/users/{id}", existingId)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void findByIdShouldReturnUserWhenAdminAuthenticated() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/users/{id}", existingId)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));
		result.andExpect(jsonPath("$.name").isNotEmpty());
		result.andExpect(jsonPath("$.email").isNotEmpty());
	}
	
	@Test
	public void findByIdShouldReturnForbiddenWhenClientAuthenticated() throws Exception {
	
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		
		ResultActions result =
				mockMvc.perform(get("/users/{id}", existingId)
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/users/{id}", nonExistingId)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}
	
}
