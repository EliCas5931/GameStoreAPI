package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;

import java.util.List;

public interface CustomerDao {

    Customer createCustomer(Customer customer);

    Customer getCustomer(int id);

    List<Customer> getAllCustomer();

    void updateCustomer(Customer customer, int id);

    void deleteCustomer(int id);

}
