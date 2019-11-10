package com.company.retailapiservice.service;

import com.company.retailapiservice.model.*;
import com.company.retailapiservice.util.feign.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RetailApiServiceTest {

    public static final String EXCHANGE = "level-up-exchange";
    public static final String ROUTING_KEY = "level-up.#";

    @Mock
    InvoiceFeignClient invoiceClient;
    @Mock
    LevelUpFeignClient levelupClient;
    @Mock
    ProductFeignClient productClient;
    @Mock
    CustomerFeignClient customerClient;
    @Mock
    InventoryFeignClient inventoryClient;
    @Mock
    RabbitTemplate rabbit;
    @InjectMocks
    ServiceLayer service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpCustomerClientMock();
        setUpLevelUpClientMock();
        setUpInvoiceClientMock();
        setUpProductClientMock();
        setUpInventoryClientMock();
        setupRabbitTemplateMock();
    }

    @Test
    public void submitInvoice() {

        List<InvoiceItem> invIL  = new ArrayList<>();

        Inventory i = new Inventory();
        Product p = new Product();

        p.setProductId(1);
        p.setListPrice(new BigDecimal("10.00"));
        i.setQuantity(5);
        // need like an order items to mix product with inventory
//        invIL.add(p);
//        invIL.add(i);

    }

    @Test
    public void getAllInvoices() {
        List<InvoiceViewModel> invVM = service.getAllInvoices();

        // why isn't assertEquals not working! google how to mock client
        verify(invoiceClient, times(2)).getAllInvoices();
    }

    private void setUpCustomerClientMock() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Joe");
        customer.setLastName("Smith");
        customer.setStreet("1 Private Lane");
        customer.setCity("Charlotte");
        customer.setZip("28054");
        customer.setEmail("jsmith");
        customer.setPhone("7945551234");

        Customer customer1 = new Customer();
        customer1.setFirstName("Karen");
        customer1.setLastName("Turner");
        customer1.setStreet("2 Main Street");
        customer1.setCity("Charlotte");
        customer1.setZip("28062");
        customer1.setEmail("kturner");
        customer1.setPhone("7045554321");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        doReturn(customer).when(customerClient).createCustomer(customer1);
        doReturn(customer).when(customerClient).getCustomer(1);
        doReturn(customerList).when(customerClient).getAllCustomer();
        doReturn(null).when(customerClient).getCustomer(5);
    }

    private void setUpInventoryClientMock() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(20);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(20);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(2);
        inventory2.setProductId(2);
        inventory2.setQuantity(40);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);
        inventoryList.add(inventory2);

        doReturn(inventory).when(inventoryClient).createInventory(inventory1);
        doReturn(inventory).when(inventoryClient).getInventory(1);
        doReturn(inventoryList).when(inventoryClient).getAllInventories();
        doReturn(null).when(inventoryClient).getInventory(4);
    }

    public void setUpInvoiceClientMock() {

        List<InvoiceViewModel> iL = new ArrayList<>();
        List<InvoiceItem> invIt = new ArrayList<>();

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(8);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(15);
        invoiceItem.setUnitPrice(new BigDecimal("8.00").setScale(2, RoundingMode.HALF_UP));
        invIt.add(invoiceItem);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(9);
        invoiceItem2.setInventoryId(2);
        invoiceItem2.setQuantity(20);
        invoiceItem2.setUnitPrice(new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP));

        InvoiceViewModel invVM1 = new InvoiceViewModel();
        invVM1.setInvoiceId(1);
        invVM1.setCustomerId(13);
        invVM1.setPurchaseDate(LocalDate.of(2019, 8,26));
        invVM1.setInvoiceItemList(invIt);

        InvoiceViewModel invVM2 = new InvoiceViewModel();
        invVM2.setCustomerId(12);
        invVM2.setPurchaseDate(LocalDate.of(2019, 8, 26));
        invVM2.setInvoiceItemList(invIt);

        iL.add(invVM1);

        doReturn(invVM1).when(invoiceClient).createInvoice(invVM2);
        doReturn(invVM1).when(invoiceClient).getInvoiceById(1);
        doReturn(iL).when(invoiceClient).getAllInvoices();
        doReturn(iL).when(invoiceClient).getInvoiceByCustomerId(13);
    }

    public void setupRabbitTemplateMock() {

    }

    public void setUpLevelUpClientMock() {

    }

    public void setUpProductClientMock() {

    }


}
