package com.personalproject.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.personalproject.orderservice.dto.InventoryResponse;
import com.personalproject.orderservice.dto.InventoryResponseList;
import com.personalproject.orderservice.dto.OrderLineItemDto;
import com.personalproject.orderservice.dto.OrderRequest;
import com.personalproject.orderservice.model.Order;
import com.personalproject.orderservice.model.OrderLineItem;
import com.personalproject.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    
    @Transactional
    public String createOrder(OrderRequest orderRequest) throws IllegalAccessException{
        //check if the item is present in inventory before you create an order      
        
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDto().stream()
                                           .map(orderLineItemDto -> getOrderLineItem(orderLineItemDto))
                                           .toList();
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodeList = order.getOrderLineItems().stream()
                                 .map(orderLineItem -> orderLineItem.getSkuCode())
                                 .toList();

        InventoryResponseList inventoryResponseList = webClientBuilder.build().get()
                 .uri("http://inventory-service/api/inventory/getAll", 
                 uriBuilder -> uriBuilder.queryParam("skuCodeList", skuCodeList).build())
                 .retrieve()
                 .bodyToMono(InventoryResponseList.class)
                 .block();
        List<InventoryResponse> inventoryResponses = inventoryResponseList.getInventoryResponses();         
        
        boolean isInStock = false;
        if(!inventoryResponses.isEmpty()){
            isInStock = inventoryResponses.stream().allMatch(inventoryResponse -> (inventoryResponse.isInStock() == true));
        }         
        
        if(!isInStock){
            throw new IllegalArgumentException("Product not in stock");
        }      

        orderRepository.save(order);
        return "Order created successfully";
    }

    private OrderLineItem getOrderLineItem(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

        return orderLineItem;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
