package com.personalproject.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.personalproject.inventoryservice.dto.InventoryRequest;
import com.personalproject.inventoryservice.dto.InventoryResponse;
import com.personalproject.inventoryservice.dto.InventoryResponseList;
import com.personalproject.inventoryservice.model.Inventory;
import com.personalproject.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public InventoryResponseList isInStock(List<String> skuCodesList) {
        List<InventoryResponse> list = inventoryRepository.findBySkuCodeIn(skuCodesList)
                           .stream()
                           .map(inventory -> 
                           InventoryResponse.builder()
                           .skuCode(inventory.getSkuCode())
                           .isInStock(inventory.getQuantity()>0)
                           .build()
                           ).toList();
        return new InventoryResponseList(list);
        

    }

    @Transactional
    public String addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();
        inventoryRepository.save(inventory);
        return "Inventory added";
    }
}
