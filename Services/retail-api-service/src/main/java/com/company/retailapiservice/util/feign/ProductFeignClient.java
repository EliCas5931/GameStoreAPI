package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public Product createProduct(@RequestBody @Valid Product product);

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("id") int id);

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public List<Product> getAllProducts();

    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public void updateProduct(@RequestBody @Valid Product product, @PathVariable int id);

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable @Valid int id);
}
