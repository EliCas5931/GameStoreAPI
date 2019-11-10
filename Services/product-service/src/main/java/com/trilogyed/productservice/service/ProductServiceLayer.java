package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.dc.pr.PRError;

import java.util.List;

@Component
public class ProductServiceLayer {

    @Autowired
    ProductDao dao;

    public ProductServiceLayer(ProductDao dao) {
        this.dao = dao;
    }

    @Transactional
    public Product newProduct(Product product){
        return dao.createProduct(product);
    }

    @Transactional
    public Product fetchProduct(int id){
        return dao.getProduct(id);
    }

    public List<Product> fetchAllProduct(){
        return dao.getAllProducts();
    }

    public void updateProduct(Product product, int id){
        dao.updateProduct(product, id);
    }

    public void removeProduct(int id){
        dao.deleteProduct(id);
    }
}
