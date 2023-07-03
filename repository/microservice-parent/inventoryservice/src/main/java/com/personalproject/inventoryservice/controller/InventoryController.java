package com.personalproject.inventoryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.personalproject.inventoryservice.dto.InventoryRequest;
import com.personalproject.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    
    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addInventory(@RequestBody InventoryRequest inventoryRequest){
        return inventoryService.addInventory(inventoryRequest);
    }


    @GetMapping("/{sku-code}")
    @ResponseStatus(code = HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode){
      return inventoryService.isInStock(skuCode);
    }
}
