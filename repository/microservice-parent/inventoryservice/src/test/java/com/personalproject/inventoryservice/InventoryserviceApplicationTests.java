package com.personalproject.inventoryservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalproject.inventoryservice.dto.InventoryRequest;
import com.personalproject.inventoryservice.repository.InventoryRepository;
import com.personalproject.inventoryservice.service.InventoryService;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class InventoryserviceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	InventoryRepository inventoryRepository;

	@Container
	@ServiceConnection
	private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.28");

	static{
		mySQLContainer.withUrlParam("serverTimezone", "UTC")
					.withReuse(true)
					.start();
	}

	@Test
	void shouldCreateInventory() throws Exception{
		InventoryRequest inventoryRequest = getInventoryRequest();
		String inventoryString = objectMapper.writeValueAsString(inventoryRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/create")
												.contentType(MediaType.APPLICATION_JSON)
												.content(inventoryString))
			   .andExpect(MockMvcResultMatchers.status().isCreated());

		assertEquals(1, inventoryRepository.findAll().size());
		
	}
	

	@Test
	public void isInStock_success() throws Exception{
		//inventoryService.addInventory(getInventoryRequest());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/iPhone_13_red"))
						.andExpect(MockMvcResultMatchers.status().isOk());
	}

	public InventoryRequest getInventoryRequest(){
		return InventoryRequest.builder()
						.quantity(10)
						.skuCode("iPhone_13_red")
						.build();
	}
	

}
