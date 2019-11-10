package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerFeignClient {

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody @Valid Customer customer);

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("id") int id);

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public List<Customer> getAllCustomer();

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    public void updateCustomer(@RequestBody @Valid Customer customer, @PathVariable int id);

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable @Valid int id);

}
