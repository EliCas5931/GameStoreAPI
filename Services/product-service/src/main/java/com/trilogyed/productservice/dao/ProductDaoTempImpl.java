package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDaoTempImpl implements ProductDao {

    public static final String INSERT_PRODUCT =
            "insert into product(product_name, product_description, list_price, unit_cost) values (?,?,?,?)";
    public static final String SELECT_PRODUCT =
            "select * from product where product_id = ?";
    public static final String SELECT_ALL_PRODUCTS =
            "select * from product";
    public static final String UPDATE_PRODUCT =
            "update product set product_name = ?, product_description = ?, list_price = ?, unit_cost = ? where product_id = ?";
    public static final String DELETE_PRODUCT =
            "delete from product where product_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ProductDaoTempImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        jdbcTemplate.update(INSERT_PRODUCT,
                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost());
        product.setProductId(jdbcTemplate.queryForObject("select last_insert_id()", Integer.class));
        return product;
    }

    @Override
    public Product getProduct(int id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_PRODUCT, this::mapRowToProduct, id);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS, this::mapRowToProduct);
    }

    @Override
    public void updateProduct(Product product, int id) {
        jdbcTemplate.update(UPDATE_PRODUCT,
                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost(),
                id);
    }

    @Override
    public void deleteProduct(int id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }

    private Product mapRowToProduct(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setProductDescription(resultSet.getString("product_description"));
        product.setListPrice(resultSet.getBigDecimal("list_price"));
        product.setUnitCost(resultSet.getBigDecimal("unit_cost"));
        return product;
    }
}
