package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryFeignClient {
    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    public Inventory createInventory(@RequestBody @Valid Inventory inventory);

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    public Inventory getInventory(@PathVariable("id") int id);

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public List<Inventory> getAllInventories();

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.PUT)
    public void updateInventory(@RequestBody @Valid Inventory inventory, @PathVariable int id);

    @RequestMapping(value = "/inventory{id}", method = RequestMethod.DELETE)
    public void deleteInventory(@PathVariable @Valid int id);
}
