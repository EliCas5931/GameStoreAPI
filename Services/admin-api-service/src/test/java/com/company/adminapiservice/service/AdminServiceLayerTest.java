package com.company.adminapiservice.service;


import com.company.adminapiservice.model.*;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AdminServiceLayerTest {

    @InjectMocks
    AdminServiceLayer service;
    @Mock
    ProductFeignClient productClient;
    @Mock
    CustomerFeignClient customerClient;
    @Mock
    InventoryFeignClient inventoryClient;
    @Mock
    InvoiceFeignClient invoiceClient;
    @Mock
    LevelUpFeignClient levelUpClient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpProductClientMock();
        setUpCustomerClientMock();
        setUpInventoryClientMock();
        setUpInvoiceClientMock();
        setUpLevelUpClientMock();
    }

    @Test
    public void invoiceCRUD() {

        InvoiceViewModel invVM = new InvoiceViewModel();

        invVM.setInvoiceId(1);

        List<InvoiceViewModel> invList = new ArrayList<>();

        invList.add(invVM);

        when(invoiceClient.addInvoice(invVM)).thenReturn(invVM);
    }

    @Test
    public void customerCRUD() {
        //John said try when(client).add.thenreturn you learned in tutor session
        Customer customer = new Customer();

        customer.setCustomerId(1);

        List<Customer> customerList = new ArrayList<>();

        customerList.add(customer);

        when(customerClient.addCustomer(customer)).thenReturn(customer);

//        customer = service.addCustomer(customer);

    }

    @Test
    public void levelUpCRUD() {

        LevelUp levelUp = new LevelUp();

        levelUp.setLevelupId(1);

        List<LevelUp> levelUpList = new ArrayList<>();

        levelUpList.add(levelUp);

        when(levelUpClient.addLevelup(levelUp)).thenReturn(levelUp);
    }

    @Test
    public void inventoryCRUD() {

        Inventory inventory = new Inventory();

        inventory.setInventoryId(1);

        List<Inventory> inventoryList = new ArrayList<>();

        inventoryList.add(inventory);

        when(inventoryClient.addInventory(inventory)).thenReturn(inventory);
    }

    @Test
    public void productCRUD() {

        Product product = new Product();

        product.setProductId(1);

        List<Product> productList = new ArrayList<>();

        productList.add(product);

        when(productClient.addProduct(product)).thenReturn(product);
    }



    public void setUpLevelUpClientMock() {

    }

    public void setUpProductClientMock() {

    }

    public void setUpCustomerClientMock() {

    }

    public void setUpInventoryClientMock() {

    }

    public void setUpInvoiceClientMock() {
    }
}
