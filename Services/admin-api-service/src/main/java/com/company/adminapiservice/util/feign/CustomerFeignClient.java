package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="customer-service")
public interface CustomerFeignClient {

    @RequestMapping(value = "/customers",method = RequestMethod.POST)
    public Customer addCustomer(@RequestBody Customer customer);

    @RequestMapping(value = "/customers",method = RequestMethod.GET)
    public List<Customer> getAllCustomers();

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable int id);

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.PUT)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable int id);

    @RequestMapping(value = "/customers/{id}",method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable int id);

}
