package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    InvoiceItem addInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem getInvoiceItemById(int invoiceItemId);

    List<InvoiceItem> getAllInvoiceItems();

    void updateInvoiceItem(InvoiceItem invoiceItem);

    void deleteInvoiceItem(int invoiceItemId);

    // Get invoice item by invoice ID for list in VM
    List<InvoiceItem> getInvoiceItemByInvoice(int invoiceId);

    // invoice in retail
    void deleteInvoiceItemByInvoiceId(int id);

    void deleteInvoiceItemByInventoryId(int id);

}
