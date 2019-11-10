package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.model.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceLayer {

    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;

    public InvoiceServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }

    public InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel invVM = new InvoiceViewModel();

        invVM.setInvoiceId(invoice.getInvoiceId());
        invVM.setCustomerId(invoice.getCustomerId());
        invVM.setPurchaseDate(invoice.getPurchaseDate());

        // Get invoice item by invoice ID
        // similar to book solution. do more research
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoiceItemByInvoice(invoice.getInvoiceId());
        invVM.setInvoiceItemList(invoiceItemList);

        return invVM;
    }

    public InvoiceViewModel submitInvoice(InvoiceViewModel invVM) {
        Invoice invoice = new Invoice();

        invoice.setCustomerId(invVM.getCustomerId());
        invoice.setPurchaseDate(invVM.getPurchaseDate());

        invoice = invoiceDao.submitInvoice(invoice);
        invVM.setInvoiceId(invoice.getInvoiceId());

        List<InvoiceItem> invoiceItemList = invVM.getInvoiceItemList();
        for (InvoiceItem it : invoiceItemList) {
            it = invoiceItemDao.addInvoiceItem(it);
        }

        return invVM;
    }

    public InvoiceViewModel getInvoiceById(int id) {

        Invoice invoice = invoiceDao.getInvoiceById(id);

        InvoiceViewModel invVM = buildInvoiceViewModel(invoice);

        return invVM;
    }

//    public InvoiceViewModel getInvoiceItemById(int id) {
//
//        InvoiceItem it = invoiceItemDao.getInvoiceItemById(id);
//
//        InvoiceViewModel invVM = buildInvoiceViewModel(it);
//
//        return invVM;
//    }

    public List<InvoiceViewModel> getAllInvoices() {

        List<Invoice> iL = invoiceDao.getAllInvoices();

        List<InvoiceViewModel> invVML = new ArrayList<>();

        for (Invoice invoice : iL) {

            invVML.add(buildInvoiceViewModel(invoice));

        }

        return invVML;
    }

    public void updateInvoice(InvoiceViewModel invVM) {

        Invoice invoice = new Invoice();

        invoice.setInvoiceId(invVM.getInvoiceId());
        invoice.setPurchaseDate(invVM.getPurchaseDate());
        invoice.setCustomerId(invVM.getCustomerId());

        invoiceDao.updateInvoice(invoice);

        List<InvoiceItem> invIL = invVM.getInvoiceItemList();

        for(InvoiceItem it : invIL) {

            invoiceItemDao.updateInvoiceItem(it);
        }
    }

    public void deleteInvoice(int id) {

        List<InvoiceItem> invIL = invoiceItemDao.getAllInvoiceItems();

        for(InvoiceItem it : invIL) {

            invoiceItemDao.deleteInvoiceItem(it.getInvoiceItemId());

        }

        invoiceDao.deleteInvoice(id);

    }

    public List<InvoiceViewModel> getInvoiceByCustomerId(int id) {

        List<Invoice> iL = invoiceDao.getInvoiceByCustomerId(id);

        List<InvoiceViewModel> invVML = new ArrayList<>();

        for (Invoice invoice : iL) {

            invVML.add(buildInvoiceViewModel(invoice));

        }

        return invVML;
    }

    // Invoice Items
    public List<InvoiceItem> getInvoiceItemByInvoiceId(int id) {

        return invoiceItemDao.getInvoiceItemByInvoice(id);

    }

    public void deleteInvoiceItemByInvoiceId(int id) {

        invoiceItemDao.deleteInvoiceItemByInvoiceId(id);

    }

    public void deleteInvoiceItemByInventoryId(int id) {

        invoiceItemDao.deleteInvoiceItemByInventoryId(id);

    }






}
