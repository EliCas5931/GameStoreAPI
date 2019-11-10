package com.company.invoiceservice.controller;

import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.model.InvoiceViewModel;
import com.company.invoiceservice.service.InvoiceServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceServiceLayer invoiceServiceLayer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invVM) {

        return invoiceServiceLayer.submitInvoice(invVM);

    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {

        return invoiceServiceLayer.getInvoiceById(id);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {

        return invoiceServiceLayer.getAllInvoices();

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateInvoice(@PathVariable int id, @RequestBody InvoiceViewModel invVM) {

        invoiceServiceLayer.updateInvoice(invVM);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvoice(@PathVariable int id) {

        invoiceServiceLayer.deleteInvoice(id);

    }

    @GetMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable int id) {

        return invoiceServiceLayer.getInvoiceByCustomerId(id);

    }

    // Help invoice for retail side
    @GetMapping("/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<InvoiceItem> getInvoiceItemByInvoiceId(@PathVariable int id) {

        return invoiceServiceLayer.getInvoiceItemByInvoiceId(id);

    }

    @DeleteMapping("/invoiceItem/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvoiceItemByInvoiceId(@PathVariable int id) {

        invoiceServiceLayer.deleteInvoiceItemByInvoiceId(id);

    }

    @DeleteMapping("/invoiceItem/inventory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvoiceItemByInventoryId(@PathVariable int id) {

        invoiceServiceLayer.deleteInvoiceItemByInventoryId(id);

    }


}
