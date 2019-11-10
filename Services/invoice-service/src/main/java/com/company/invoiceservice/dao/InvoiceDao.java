package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;

import java.util.List;



public interface InvoiceDao {

    Invoice submitInvoice(Invoice invoice);

    Invoice getInvoiceById(int invoiceId);

    List<Invoice> getAllInvoices();

    void updateInvoice(Invoice invoice);

    void deleteInvoice(int invoiceId);

    List<Invoice> getInvoiceByCustomerId(int customerId);

}
