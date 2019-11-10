package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.model.InvoiceItem;
import com.company.retailapiservice.model.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "invoice-service")

public interface InvoiceFeignClient {
    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invVM);

    @RequestMapping(value = "invoice/id/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id);

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.POST)
    public void updateInvoice(@PathVariable int id, @RequestBody InvoiceViewModel invVM);

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.DELETE)
    public void deleteInvoice(@PathVariable int id);

    @RequestMapping(value = "/invoice/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable int id);

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET)
    public List<InvoiceItem> getInvoiceItemByInvoiceId(@PathVariable int id);
}
