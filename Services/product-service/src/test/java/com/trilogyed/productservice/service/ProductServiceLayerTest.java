package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.dao.ProductDaoTempImpl;
import com.trilogyed.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import sun.dc.pr.PRError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProductServiceLayerTest {

    private ProductDao dao;
    ProductServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpProductDaoMock();
        service = new ProductServiceLayer(dao);
    }

    private void setUpProductDaoMock(){
        dao = mock(ProductDaoTempImpl.class);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Banana");
        product.setProductDescription("Long yellow fruit");
        product.setListPrice(BigDecimal.valueOf(15));
        product.setUnitCost(BigDecimal.valueOf(10));

        Product product2 = new Product();
        product2.setProductId(1);
        product2.setProductName("Banana");
        product2.setProductDescription("Long yellow fruit");
        product2.setListPrice(BigDecimal.valueOf(15));
        product2.setUnitCost(BigDecimal.valueOf(10));

        List<Product> products = new ArrayList<>();
        products.add(product);

        doReturn(product).when(dao).createProduct(product2);
        doReturn(product).when(dao).getProduct(1);
        doReturn(products).when(dao).getAllProducts();
    }

    @Test
    public void saveProduct(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Banana");
        product.setProductDescription("Long yellow fruit");
        product.setListPrice(BigDecimal.valueOf(15));
        product.setUnitCost(BigDecimal.valueOf(10));

        product = service.newProduct(product);

        Product fromService = service.fetchProduct(product.getProductId());
        assertEquals(product, fromService);
    }

    @Test
    public void findAllProducts(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Banana");
        product.setProductDescription("Long yellow fruit");
        product.setListPrice(BigDecimal.valueOf(15));
        product.setUnitCost(BigDecimal.valueOf(10));

        product = service.newProduct(product);
        Product fromService = service.fetchProduct(product.getProductId());
        assertEquals(product, fromService);

        List<Product> products = service.fetchAllProduct();
        assertEquals(1, products.size());
    }
}
