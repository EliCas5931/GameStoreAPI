package com.trilogyed.customerservice.controller;

import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.service.CustomerServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    CustomerServiceLayer service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer){
        return service.newCustomer(customer);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable("id") int id){
        return service.fetchCustomer(id);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Customer> getAllCustomers(){
        return service.fetchAllCustomers();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCustomer(@RequestBody @Valid Customer customer, @PathVariable int id){
        service.updateCustomer(customer, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCustomer(@PathVariable @Valid int id){
        service.deleteCustomer(id);
    }

}
