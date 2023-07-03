package com.personalproject.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.personalproject.orderservice.dto.OrderRequest;
import com.personalproject.orderservice.model.Order;
import com.personalproject.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {   

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest orderRequest ){

        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderService.getOrders();
    }
}
