package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;

import java.util.List;

public interface ProductDao {

    Product createProduct(Product product);

    Product getProduct(int id);

    List<Product> getAllProducts();

    void updateProduct(Product product, int id);

    void deleteProduct(int id);
}
