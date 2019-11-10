package com.company.retailapiservice.service;

import com.company.retailapiservice.exception.NotFoundException;
import com.company.retailapiservice.model.*;
import com.company.retailapiservice.util.feign.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import feign.FeignException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ServiceLayer {

    @Autowired
    CustomerFeignClient customerFeignClient;
    @Autowired
    InventoryFeignClient inventoryFeignClient;
    @Autowired
    InvoiceFeignClient invoiceFeignClient;
    @Autowired
    LevelUpFeignClient levelUpFeignClient;
    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String EXCHANGE = "level-up-exchange";
    public static final String ROUTING_KEY = "level-up.#";

    public ServiceLayer(CustomerFeignClient customerFeignClient, InventoryFeignClient inventoryFeignClient, InvoiceFeignClient invoiceFeignClient, LevelUpFeignClient levelUpFeignClient, ProductFeignClient productFeignClient, RabbitTemplate rabbitTemplate) {
        this.customerFeignClient = customerFeignClient;
        this.inventoryFeignClient = inventoryFeignClient;
        this.invoiceFeignClient = invoiceFeignClient;
        this.levelUpFeignClient = levelUpFeignClient;
        this.productFeignClient = productFeignClient;
    }

    @Transactional
    public Customer newCustomer(Customer customer){
        return customerFeignClient.createCustomer(customer);
    }

    @Transactional
    public Customer fetchCustomer(int id){
        return customerFeignClient.getCustomer(id);
    }

    public List<Customer> fetchAllCustomers(){
        return customerFeignClient.getAllCustomer();
    }

    public void updateCustomer(Customer customer, int id){
        customerFeignClient.updateCustomer(customer, id);
    }

    public void deleteCustomer(int id){
        customerFeignClient.deleteCustomer(id);
    }

    public InvoiceViewModel submitInvoice(InvoiceViewModel invVM) {

        // Make sure they're a customer. Business rule #1
        Customer customer;

        try {

            customer = customerFeignClient.getCustomer(invVM.getCustomerId());

        } catch (NotFoundException nfe) {

            throw new NotFoundException("Invalid ID. Please try again!");

        }


        // get inventory and product to set as invoice items
        List<InvoiceItem> invoiceItems = invVM.getInvoiceItemList();

        List<Inventory> inven = new ArrayList<>();

        // dry code up. implement stream and lambda
        invoiceItems.stream().forEach(invoiceItem -> {

            Inventory invenQuantity;

            // try and catch inven id not found
            try {

                invenQuantity = inventoryFeignClient.getInventory(invoiceItem.getInventoryId());

            } catch (NotFoundException nfe) {

                throw new NotFoundException("Id " + invoiceItem.getInventoryId() + " is invalid. Please try again!");
            }

            //Simplify from previous capstone
            if (invenQuantity.getQuantity() < invoiceItem.getQuantity()) {

                throw new NotFoundException("Not enough of this product is in stock!");
            }

            inven.add(invenQuantity);

            int productId = invenQuantity.getProductId();

            Product prod;

            // try and catch prod id not found
            try {

                prod = productFeignClient.getProduct(productId);

            } catch (NotFoundException e) {

                throw new NotFoundException("Id " + productId + " is invalid. Please try again!");
            }

            // set unit price per item
            // simplify code
            invoiceItem.setUnitPrice(prod.getListPrice());

        });

        for(InvoiceItem item: invVM.getInvoiceItemList()) {

            for (Inventory inventory : inventoryFeignClient.getAllInventories()) {

                if (inventory.getProductId() == item.getInvoiceItemId()){

                    if(item.getQuantity() > inventory.getQuantity()){  //verify order quantity and inventory quantity

                        throw new IllegalArgumentException("Not enough in stock!");

                    }

                    InvoiceItem invoiceItem = new InvoiceItem();

                    invoiceItem.setInventoryId(inventory.getInventoryId());

                    invoiceItem.setQuantity(item.getQuantity());

                    invoiceItem.setUnitPrice(productFeignClient.getProduct(item.getInvoiceItemId()).getListPrice());

                    invVM.setInvoiceItemList(new ArrayList<>());

                    invVM.getInvoiceItemList().add(invoiceItem);

                }
            }
        }


        // add invoice and return as an invoice view model
        LocalDate localDate = LocalDate.now();

        invVM.setPurchaseDate(localDate);

        invVM = invoiceFeignClient.createInvoice(invVM);

        // $50 = 10 points
        Integer totalPoints = new Integer(invVM.getPoints());

        invoiceItems.stream().forEach(invoiceItem -> {

            BigDecimal invoiceTotal =
                    invoiceItem.getUnitPrice()
                            .multiply(BigDecimal
                                    .valueOf(invoiceItem
                                            .getQuantity()));

            totalPoints(invoiceTotal.intValue() / 5);

        });

        invVM.setPoints(totalPoints.intValue());

        // setup message for rabbitmq
        LevelUp lu = new LevelUp();

        int rewardPoints = getLevelUpPointsByCustomerId(invVM.getCustomerId());

        if (rewardPoints == 0) {

            lu.setMemberDate(LocalDate.now());

        } else {

            List<LevelUp> lups = levelUpFeignClient.getLevelUpByCustomerId(invVM.getCustomerId());

            lu.setMemberDate(lups.get(0).getMemberDate());

        }

        lu.setPoints(invVM.getPoints());

        lu.setCustomerId(customer.getCustomerId());

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, lu);

        return invVM;
    }

    @Transactional
    public Inventory newInventory(Inventory inventory){
        return inventoryFeignClient.createInventory(inventory);
    }

    @Transactional
    public Inventory fetchInventory(int id){
        return inventoryFeignClient.getInventory(id);
    }

    public List<Inventory> fetchAllInventories(){
        return inventoryFeignClient.getAllInventories();
    }

    public void updateInventory(Inventory inventory, int id){
        inventoryFeignClient.updateInventory(inventory, id);
    }

    public void deleteInventory(int id){
        inventoryFeignClient.deleteInventory(id);
    }

    private void totalPoints(int i) {

    }

    private void sendPointsToQueue (Customer customer) {
        //send points to queue from here
        System.out.println("Sending message...");

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, customer);

        System.out.println("Message Sent");
    }

    public InvoiceViewModel getInvoiceById(int id) {

        InvoiceViewModel invVM = invoiceFeignClient.getInvoiceById(id);

        if (invVM == null) {

            throw new NotFoundException("No invoice available for that id!");
        }

//        BigDecimal rewardPoints = calculatePoints(invVM);
//
//        invVM.setPoints(rewardPoints);

        return invVM;

    }

    @Transactional
    public Product newProduct(Product product){
        return productFeignClient.createProduct(product);
    }

    @Transactional
    public Product fetchProduct(int id){
        return productFeignClient.getProduct(id);
    }

    public List<Product> fetchAllProduct(){
        return productFeignClient.getAllProducts();
    }

    public void updateProduct(Product product, int id){
        productFeignClient.updateProduct(product, id);
    }

    public void removeProduct(int id) {
        productFeignClient.deleteProduct(id);
    }

    public List<InvoiceViewModel> getAllInvoices() {

        List<InvoiceViewModel> IVM = invoiceFeignClient.getAllInvoices();

        if (IVM.isEmpty()) {

            throw new NotFoundException("No invoice is available!");

        }

        return IVM;
    }

    public List<InvoiceViewModel> getInvoicesByCustomerId(int id) {

        return invoiceFeignClient.getInvoiceByCustomerId(id);

    }

    public List<Product> getProductByInvoiceId(int id) {

        List<InvoiceItem> invoiceItems = invoiceFeignClient.getInvoiceById(id).getInvoiceItemList();

        if(invoiceItems.isEmpty()){

            throw new NotFoundException("Nothing found for id " + id);
        }
        List<Product> productList = new ArrayList<>();

        for(InvoiceItem item: invoiceItems){

            productList.add(
                    productFeignClient.getProduct(
                            inventoryFeignClient.getInventory(
                                    item.getInventoryId()
                            )
                                    .getProductId()
                    )
            );
        }
        return productList;

//        InvoiceViewModel invVM = invoiceFeignClient.getInvoiceById(id);

//        int invoiceId = invVM.getInvoiceId();

//        List<InvoiceItem> items = invoiceFeignClient.getInvoiceItemByInvoiceId(invoiceId);

//        List<Product> products = new ArrayList<>();

//        items.stream().forEach(item -> {

//            int tempInventoryId = item.getInventoryId();

//            int tempProductId = inventoryFeignClient
//            .getAllInventories()
//            .stream()
//            .filter(inventory -> inventory
//            .getInventoryId()==tempInventoryId)
//            .collect(Collectors.toList())
//                    .get(0).getProductId();

//            Product userProduct = fetchProduct(userProductId);

//            products.add(userProduct);
//        });

//        return products;
    }

    public List<Product> getProductsInInventory() {

        try {
            List<Inventory> inventoryList = inventoryFeignClient.getAllInventories();

            inventoryList = inventoryList.stream()
                    .filter(inventory -> inventory
                            .getQuantity() > 0)
                                    .collect(Collectors.toList());

            List<Product> productList = new ArrayList<>();

            for (Inventory inventory : inventoryList) {

                productList.add(productFeignClient.getProduct(inventory.getProductId()));

            }

            return productList;

        } catch (NotFoundException nfe) {

            throw new NotFoundException("Product not found!");

        }
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public int getLevelUpPointsByCustomerId(int id) {

        // Return a list

//        try {
//
//            LevelUp lu = levelUpFeignClient.getLevelUpByCustomerId(id);
//
//            return lu.getPoints();
//
//        } catch (NotFoundException nfe) {
//
//            throw new NotFoundException("Level up points could not be found!");
//
//        }

            List<LevelUp> lu = levelUpFeignClient.getLevelUpByCustomerId(id);

            int awardPoints = lu.stream().mapToInt(LevelUp::getPoints).sum();

            return awardPoints;

    }

    public String reliable() {

        return "Please wait while your points are being calculated!";

    }

    public Product getProductById(int id) {
        return productFeignClient.getProduct(id);
    }

    // send points to queue

    public int calculatePoints(BigDecimal invoiceTotal) {

        int rewardPoints = invoiceTotal.intValue() / 50;

        rewardPoints += 10;

        return rewardPoints;
    }
}
