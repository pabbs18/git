package com.personalproject.orderservice;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalproject.orderservice.dto.OrderLineItemDto;
import com.personalproject.orderservice.dto.OrderRequest;
import com.personalproject.orderservice.repository.OrderRepository;
import com.personalproject.orderservice.service.OrderService;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc

public class BaseClass {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	OrderService orderService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	OrderRepository orderRepository;

	@Container
	@ServiceConnection
	private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.28");

	static{
		mySQLContainer.withUrlParam("serverTimezone", "UTC")
					.withReuse(true)
					.start();
	}
	
	@Test
	void shouldCreateOrder() throws Exception{
		OrderRequest orderRequest = getOrderRequest();
		String orderString = objectMapper.writeValueAsString(orderRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order/create")
												.contentType(MediaType.APPLICATION_JSON)
												.content(orderString))
			   .andExpect(MockMvcResultMatchers.status().isCreated());

		assertEquals(1, orderRepository.findAll().size());
		
	}
	

	@Test
	public void getAllTests_success() throws Exception{
		orderService.createOrder(getOrderRequest());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/order/getAll"))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
						.andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(((long)1)));
	}

	public OrderRequest getOrderRequest(){
		OrderLineItemDto orderLineItemDto = new  OrderLineItemDto();
		orderLineItemDto.setPrice(new BigDecimal(1200));
		orderLineItemDto.setQuantity(10);
		orderLineItemDto.setSkuCode("iPhone_13");
		List<OrderLineItemDto> orderLineItemDtos = new ArrayList<>();
		orderLineItemDtos.add(orderLineItemDto);
		return new OrderRequest(orderLineItemDtos);	
	}
}
