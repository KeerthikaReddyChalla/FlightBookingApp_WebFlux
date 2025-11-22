package com.flightapp.controller;

import com.flightapp.dto.AddInventoryRequest;
import com.flightapp.model.Inventory;
import com.flightapp.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight/airline/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Inventory> addInventory(@Valid @RequestBody AddInventoryRequest request) {
        return inventoryService.addInventory(request);
    }
}
