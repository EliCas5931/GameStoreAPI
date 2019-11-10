package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.border.EmptyBorder;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoTempImpl implements CustomerDao {

    public static final String INSERT_CUSTOMER =
            "insert into customer (first_name, last_name, street, city, zip, email, phone) values (?,?,?,?,?,?,?)";
    public static final String SELECT_CUSTOMER =
            "select * from customer where customer_id=?";
    public static final String SELECT_ALL_CUSTOMERS =
            "select * from customer";
    public static final String UPDATE_CUSTOMER =
            "update customer set first_name=?, last_name=?, street=?, city=?, zip=?, email=?, phone=? where customer_id=?";
    public static final String DELETE_CUSTOMER =
            "delete from customer where customer_id=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public CustomerDaoTempImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone());
        customer.setCustomerId(jdbcTemplate.queryForObject("select last_insert_id()", Integer.class));
        return customer;
    }

    @Override
    public Customer getCustomer(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER, this::mapRowToCustomer, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomer() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, this::mapRowToCustomer);
    }

    @Override
    public void updateCustomer(Customer customer, int id) {
        jdbcTemplate.update(UPDATE_CUSTOMER,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone(),
                id);
    }

    @Override
    public void deleteCustomer(int id) {
        jdbcTemplate.update(DELETE_CUSTOMER, id);
    }

    private Customer mapRowToCustomer(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getInt("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setStreet(resultSet.getString("street"));
        customer.setCity(resultSet.getString("city"));
        customer.setZip(resultSet.getString("zip"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhone(resultSet.getString("phone"));
        return customer;
    }

}
