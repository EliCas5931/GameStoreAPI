package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.NotFoundException;
import com.company.adminapiservice.model.Customer;
import com.company.adminapiservice.model.Inventory;
import com.company.adminapiservice.model.LevelUp;
import com.company.adminapiservice.model.Product;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.InvoiceViewModel;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Component
public class AdminServiceLayer {

    @Autowired
    CustomerFeignClient customerClient;
    @Autowired
    LevelUpFeignClient levelupClient;
    @Autowired
    InvoiceFeignClient invoiceClient;
    @Autowired
    InventoryFeignClient inventoryClient;
    @Autowired
    ProductFeignClient productClient;

    public AdminServiceLayer(CustomerFeignClient customerClient, LevelUpFeignClient levelupClient, InvoiceFeignClient invoiceClient, InventoryFeignClient inventoryClient, ProductFeignClient productClient) {
        this.customerClient = customerClient;
        this.levelupClient = levelupClient;
        this.inventoryClient = inventoryClient;
        this.invoiceClient = invoiceClient;
        this.productClient = productClient;
    }

    // customer
    @Transactional
    public Customer addCustomer(Customer customer) {
        customer = customerClient.addCustomer(customer);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return customerClient.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        Customer customer;
        try {
            customer = customerClient.getCustomerById(id);
        } catch (FeignException e) {
            throw new NotFoundException("Invalid ID!");
        }
        return customer;
    }

    @Transactional
    public void updateCustomer(Customer customer, int id) {
        customerClient.updateCustomer(customer, id);
    }

    public void deleteCustomer(int id) {
        customerClient.deleteCustomer(id);
    }

    @Transactional
    public InvoiceViewModel addInvoice(InvoiceViewModel ivm) {
        return invoiceClient.addInvoice(ivm);
    }

    public List<InvoiceViewModel> getAllInvoices() {
        return invoiceClient.getAllInvoices();
    }

    public InvoiceViewModel getInvoiceById(int id) {
        InvoiceViewModel ivm;

        try {

            ivm = invoiceClient.getInvoiceById(id);

        } catch (FeignException e) {

            throw new NotFoundException("Invalid ID!");
        }
        return ivm;
    }
    @Transactional
    public void updateInvoice(InvoiceViewModel ivm, int id) {
        invoiceClient.updateInvoice(ivm, id);
    }

    public void deleteInvoice(int id) {
        invoiceClient.deleteInvoice(id);
    }

    @Transactional
    public Product addProduct(Product product){
        return productClient.addProduct(product);
    }

    public List<Product> getAllProducts(){
        return productClient.getAllProducts();
    }

    public Product getProductById(int id){
        Product product;

        try {

            product = productClient.getProductById(id);

        } catch (FeignException e) {

            throw new NotFoundException("Invalid ID!");

        }

        return product;
    }

    @Transactional
    public void updateProduct(Product product, int id){
        productClient.updateProduct(product,id);
    }

    public void deleteProduct(int id){
        productClient.deleteProduct(id);
    }

    @Transactional
    public LevelUp addLevelup(LevelUp levelup) {
        levelup = levelupClient.addLevelup(levelup);
        return levelup;
    }

    public List<LevelUp> getAllLevelups() {
        return levelupClient.getAllLevelups();
    }

    public LevelUp getLevelupById(int id) {
        LevelUp levelup;
        try {
            levelup = levelupClient.getLevelupById(id);
        } catch (FeignException e) {
            throw new NotFoundException("Invalid ID!");
        }
        return levelup;
    }

    @Transactional
    public void updateLevelup(LevelUp levelup, int id) {
        levelupClient.updateLevelup(levelup, id);
    }

    public void deleteLevelup(int id) {
        levelupClient.deleteLevelup(id);
    }

    @Transactional
    public Inventory addInventory(Inventory inventory){
        return inventoryClient.addInventory(inventory);
    }

    public List<Inventory> getAllInventory(){
        return inventoryClient.getAllInventory();
    }

    public Inventory getInventoryById(int id){
        Inventory inventory;

        try {

            inventory = inventoryClient.getInventoryById(id);

        } catch (FeignException e) {

            throw new NotFoundException("Invalid ID!");

        }

        return inventory;
    }

    @Transactional
    public void updateInventory( Inventory inventory,  int id) {
        inventoryClient.updateInventory(inventory,id);
    }

    public void deleteInventory( int id){
        inventoryClient.deleteInventory(id);
    }


}
