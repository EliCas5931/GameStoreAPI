package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerDaoTest {

    @Autowired
    CustomerDao dao;

    @Before
    public void setUp() throws Exception {
        List<Customer> customers = dao.getAllCustomer();
        customers.stream()
                .forEach(customer -> dao.deleteCustomer(customer.getCustomerId()));
    }

    @Test
    public void addGetDeleteCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("Sally");
        customer.setLastName("Johnson");
        customer.setStreet("Main");
        customer.setCity("Americaville");
        customer.setZip("12345");
        customer.setEmail("email@aol.com");
        customer.setPhone("1234567890");
        customer = dao.createCustomer(customer);
        Customer fromDao = dao.getCustomer(customer.getCustomerId());
        assertEquals(fromDao, customer);
        dao.deleteCustomer(customer.getCustomerId());
        fromDao = dao.getCustomer(customer.getCustomerId());
        assertNull(fromDao);
    }

    @Test
    public void getAllCustomers(){
        Customer customer = new Customer();
        customer.setFirstName("Sally");
        customer.setLastName("Johnson");
        customer.setStreet("Main");
        customer.setCity("Americaville");
        customer.setZip("12345");
        customer.setEmail("email@aol.com");
        customer.setPhone("1234567890");
        dao.createCustomer(customer);

        Customer customer2 = new Customer();
        customer2.setFirstName("Sally");
        customer2.setLastName("Johnson");
        customer2.setStreet("Main");
        customer2.setCity("Americaville");
        customer2.setZip("12345");
        customer2.setEmail("email@aol.com");
        customer2.setPhone("1234567890");
        dao.createCustomer(customer2);

        List<Customer> customers = dao.getAllCustomer();
        assertEquals(2, customers.size());
    }

    @Test
    public void updateCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("Sally");
        customer.setLastName("Johnson");
        customer.setStreet("Main");
        customer.setCity("Americaville");
        customer.setZip("12345");
        customer.setEmail("email@aol.com");
        customer.setPhone("1234567890");
        customer = dao.createCustomer(customer);
        customer.setFirstName("Sarah");
        dao.updateCustomer(customer, customer.getCustomerId());
        Customer fromDao = dao.getCustomer(customer.getCustomerId());
        assertEquals(customer, fromDao);
    }
}
