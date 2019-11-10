package com.company.retailapiservice.controller;

import com.company.retailapiservice.model.Inventory;
import com.company.retailapiservice.model.InvoiceViewModel;
import com.company.retailapiservice.model.Product;
import com.company.retailapiservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class RetailController {

    @Autowired
    ServiceLayer service;

    public RetailController(ServiceLayer service) {

        this.service = service;

    }

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public InvoiceViewModel submitInvoice(@RequestBody InvoiceViewModel ivm) {
        return service.submitInvoice(ivm);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        return service.getInvoiceById(id);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices() {
        return service.getAllInvoices();
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return service.getInvoicesByCustomerId(id);
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(@PathVariable int id) {
        return service.getLevelUpPointsByCustomerId(id);
    }

    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    public List<Product> getProductsInInventory() {
        return service.getProductsInInventory();
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)
    public List<Product> getProductByInvoiceId(@PathVariable int id) {
        return service.getProductByInvoiceId(id);
    }

}
