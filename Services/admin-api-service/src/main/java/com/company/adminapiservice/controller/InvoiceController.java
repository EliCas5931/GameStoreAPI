package com.company.adminapiservice.controller;

import com.company.adminapiservice.exception.NotFoundException;
import com.company.adminapiservice.service.AdminServiceLayer;
import com.company.adminapiservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    AdminServiceLayer service;

    public InvoiceController(AdminServiceLayer service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public InvoiceViewModel addInvoice(@RequestBody InvoiceViewModel ivm, Principal principal) {
        return service.addInvoice(ivm);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices(Principal principal) {
        return service.getAllInvoices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel getInvoiceById(@PathVariable int id, Principal principal) {
        InvoiceViewModel ivm = service.getInvoiceById(id);
        if (ivm == null) {
            throw new NotFoundException("Invoice not found!");
        } else {
            return ivm;
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateInvoice(@RequestBody InvoiceViewModel ivm, @PathVariable int id, Principal principal) {
        if (ivm.getInvoiceId() == id) {
            service.updateInvoice(ivm, id);
        } else {
            throw new NotFoundException("Please try again, ID does not match!");
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteInvoice(@PathVariable int id, Principal principal) {
        service.deleteInvoice(id);
    }
}