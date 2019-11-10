package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoJdbcTemplateImplTest {

    @Autowired
    protected InvoiceDao invoiceDao;
    @Autowired
    protected InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        for(InvoiceItem invoice : invoiceItemList){
            invoiceItemDao.deleteInvoiceItem(invoice.getInvoiceItemId());
        }
        for(Invoice invoice : invoiceList){
            invoiceDao.deleteInvoice(invoice.getInvoiceId());
        }
    }

    @Test
    public void addGetDelete(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 01, 01));
        invoice = invoiceDao.submitInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(2);
        invoiceItem.setUnitPrice(new BigDecimal("5.99"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem1 = invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId());
        assertEquals(invoiceItem1, invoiceItem);

        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
        invoiceItem1 = invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId());
        assertNull(invoiceItem1);

    }

    @Test
    public void updateInvoiceItem(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 01, 01));
        invoice = invoiceDao.submitInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(2);
        invoiceItem.setUnitPrice(new BigDecimal("5.99"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitPrice(new BigDecimal("5.99"));
        invoiceItemDao.updateInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem1 = invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId());
        assertEquals(invoiceItem1, invoiceItem);
    }

    @Test
    public void getAllInvoices(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 01, 01));
        invoice = invoiceDao.submitInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(2);
        invoiceItem.setUnitPrice(new BigDecimal("5.99"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        assertEquals(invoiceItemList.size(), 3);
    }

    @Test
    public void getInvoiceItemByInvoice(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 01, 01));
        invoice = invoiceDao.submitInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(2);
        invoiceItem.setUnitPrice(new BigDecimal("5.99"));
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);
        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoiceItemByInvoice(invoice.getInvoiceId());
        assertEquals(invoiceItemList.size(), 3);
    }
}
