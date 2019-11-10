package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductDaoTest {

    @Autowired
    ProductDao dao;

    @Before
    public void setUp() throws Exception{
        List<Product> products = dao.getAllProducts();
        products.stream()
                .forEach(product -> dao.deleteProduct(product.getProductId()));
    }

    @Test
    public void addGetDeleteProduct(){
        Product product = new Product();
        product.setProductName("Name");
        product.setProductDescription("Desc");
        product.setListPrice(BigDecimal.valueOf(10.50));
        product.setUnitCost(BigDecimal.valueOf(7.50));
        product = dao.createProduct(product);
        Product fromDao = dao.getProduct(product.getProductId());
        assertEquals(product, fromDao);
        dao.deleteProduct(product.getProductId());
        fromDao = dao.getProduct(product.getProductId());
        assertNull(fromDao);
    }

    @Test
    public void getAllProducts(){
        Product product = new Product();
        product.setProductName("Name");
        product.setProductDescription("Desc");
        product.setListPrice(BigDecimal.valueOf(10.50));
        product.setUnitCost(BigDecimal.valueOf(7.50));
        dao.createProduct(product);

        Product product2 = new Product();
        product2.setProductName("Name");
        product2.setProductDescription("Desc");
        product2.setListPrice(BigDecimal.valueOf(10.50));
        product2.setUnitCost(BigDecimal.valueOf(7.50));
        dao.createProduct(product2);

        List<Product> products = dao.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    public void updateProduct(){
        Product product = new Product();
        product.setProductName("Name");
        product.setProductDescription("Desc");
        product.setListPrice(BigDecimal.valueOf(10.50));
        product.setUnitCost(BigDecimal.valueOf(7.50));
        product = dao.createProduct(product);
        product.setProductName("Name2");
        dao.updateProduct(product, product.getProductId());
        Product fromDao = dao.getProduct(product.getProductId());
        assertEquals(product, fromDao);
    }
}
