package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoJdbcTemplateImplTest {

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
        invoice.setPurchaseDate(LocalDate.of(2019, 02, 14));
        invoice = invoiceDao.submitInvoice(invoice);

        Invoice invoice2 = invoiceDao.getInvoiceById(invoice.getInvoiceId());
        assertEquals(invoice2, invoice);

        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        invoice2 = invoiceDao.getInvoiceById(invoice.getInvoiceId());
        assertNull(invoice2);
    }

    @Test
    public void updateInvoice(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 02, 14));
        invoice = invoiceDao.submitInvoice(invoice);

        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 02, 14));
        invoiceDao.updateInvoice(invoice);

        Invoice invoice1 = invoiceDao.getInvoiceById(invoice.getInvoiceId());
        assertEquals(invoice1, invoice);
    }

    @Test
    public void getAllInvoices(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 02, 14));
        invoice = invoiceDao.submitInvoice(invoice);

        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 02, 14));
        invoice = invoiceDao.submitInvoice(invoice);

        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 02, 14));
        invoice = invoiceDao.submitInvoice(invoice);

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        assertEquals(invoiceList.size(), 3);
    }

    @Test
    public void getInvoiceByCustomer(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 02, 14));
        invoice = invoiceDao.submitInvoice(invoice);

        List <Invoice> invoiceList = invoiceDao.getInvoiceByCustomerId(1);
        assertEquals(invoiceList.size(), 1);
    }

    @Test
    public void deleteInvoiceItemByInventoryId() {

    }
}
