package com.trilogyed.customerservice.service;


import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CustomerServiceLayer {

    @Autowired
    CustomerDao dao;

    public CustomerServiceLayer(CustomerDao dao) {
        this.dao = dao;
    }

    @Transactional
    public Customer newCustomer(Customer customer){
        return dao.createCustomer(customer);
    }

    @Transactional
    public Customer fetchCustomer(int id){
        return dao.getCustomer(id);
    }

    public List<Customer> fetchAllCustomers(){
        return dao.getAllCustomer();
    }

    public void updateCustomer(Customer customer, int id){
        dao.updateCustomer(customer, id);
    }

    public void deleteCustomer(int id){
        dao.deleteCustomer(id);
    }
}
