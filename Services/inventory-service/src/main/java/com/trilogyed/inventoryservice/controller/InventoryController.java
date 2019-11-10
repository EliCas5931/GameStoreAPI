package com.trilogyed.inventoryservice.controller;


import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.model.Inventory;
import com.trilogyed.inventoryservice.service.InventoryServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    InventoryServiceLayer service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody @Valid Inventory inventory){
        return service.newInventory(inventory);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventory(@PathVariable("id") int id){
        return service.fetchInventory(id);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Inventory> getAllInventories(){
        return service.fetchAllInventories();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateInventory(@RequestBody @Valid Inventory inventory, @PathVariable int id){
        service.updateInventory(inventory, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInventory(@PathVariable @Valid int id){
        service.deleteInventory(id);
    }


}
