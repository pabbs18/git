package com.personalproject.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    public String createOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDto().stream()
                                           .map(orderLineItemDto -> getOrderLineItem(orderLineItemDto))
                                           .toList();
        order.setOrderLineItems(orderLineItems);
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
