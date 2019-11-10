package com.trilogyed.productservice.controller;

import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.service.ProductServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductServiceLayer service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid Product product){
        return service.newProduct(product);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable("id") int id){
        return service.fetchProduct(id);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getAllProducts(){
        return service.fetchAllProduct();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateProduct(@RequestBody @Valid Product product, @PathVariable int id){
        service.updateProduct(product, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProduct(@PathVariable @Valid int id){
        service.removeProduct(id);
    }

}
