package com.personalproject.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.personalproject.inventoryservice.dto.InventoryRequest;
import com.personalproject.inventoryservice.dto.InventoryResponse;
import com.personalproject.inventoryservice.dto.InventoryResponseList;
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


    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public InventoryResponseList isInStock(@RequestParam List<String> skuCodeList){
      System.out.println("Call Made");
      return inventoryService.isInStock(skuCodeList);

    }
}
