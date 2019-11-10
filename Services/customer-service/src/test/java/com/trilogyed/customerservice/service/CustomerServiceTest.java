package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.dao.CustomerDaoTempImpl;
import com.trilogyed.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    private CustomerDao dao;
    CustomerServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpCustomerDaoMock();
        service = new CustomerServiceLayer(dao);
    }

    private void setUpCustomerDaoMock(){
        dao = mock(CustomerDaoTempImpl.class);

        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Sally");
        customer.setLastName("Johnson");
        customer.setStreet("Main");
        customer.setCity("Americaville");
        customer.setZip("12345");
        customer.setEmail("email@aol.com");
        customer.setPhone("1234567890");

        Customer customer2 = new Customer();
        customer2.setFirstName("Sally");
        customer2.setLastName("Johnson");
        customer2.setStreet("Main");
        customer2.setCity("Americaville");
        customer2.setZip("12345");
        customer2.setEmail("email@aol.com");
        customer2.setPhone("1234567890");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        doReturn(customer).when(dao).createCustomer(customer2);
        doReturn(customer).when(dao).getCustomer(1);
        doReturn(customers).when(dao).getAllCustomer();
    }

    @Test
    public void saveCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("Sally");
        customer.setLastName("Johnson");
        customer.setStreet("Main");
        customer.setCity("Americaville");
        customer.setZip("12345");
        customer.setEmail("email@aol.com");
        customer.setPhone("1234567890");

        customer = service.newCustomer(customer);

        Customer fromService = service.fetchCustomer(1);
        assertEquals(customer, fromService);
    }

    @Test
    public void findAllCustomers(){
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Sally");
        customer.setLastName("Johnson");
        customer.setStreet("Main");
        customer.setCity("Americaville");
        customer.setZip("12345");
        customer.setEmail("email@aol.com");
        customer.setPhone("1234567890");

        List<Customer> customers = service.fetchAllCustomers();
        assertEquals(1, customers.size());
    }
}
