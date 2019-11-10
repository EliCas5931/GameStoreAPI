package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.model.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceLayerTest {

    InvoiceServiceLayer invoiceServiceLayer;
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception{
        setUpInvoiceDaoMock();
        setUpInvoiceItemMock();
        invoiceServiceLayer = new InvoiceServiceLayer(invoiceDao, invoiceItemDao);
    }

    public void setUpInvoiceDaoMock(){
        invoiceDao = mock(InvoiceDao.class);
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 01, 01));

        Invoice invoice2 = new Invoice();
        invoice2.setCustomerId(1);
        invoice2.setPurchaseDate(LocalDate.of(2019, 01, 01));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        doReturn(invoice).when(invoiceDao).submitInvoice(invoice2);
        doReturn(invoice).when(invoiceDao).getInvoiceById(1);
        doReturn(invoiceList).when(invoiceDao).getAllInvoices();
        doReturn(invoiceList).when(invoiceDao).getInvoiceByCustomerId(1);
    }

    public void setUpInvoiceItemMock(){
        invoiceItemDao = mock(InvoiceItemDao.class);
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(1);
        invoiceItem.setUnitPrice(new BigDecimal("5.99"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(1);
        invoiceItem2.setUnitPrice(new BigDecimal("5.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        doReturn(invoiceItem).when(invoiceItemDao).addInvoiceItem(invoiceItem2);
        doReturn(invoiceItemList).when(invoiceItemDao).getInvoiceItemByInvoice(1);
    }

    @Test
    public void submitInvoice(){
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(1);
        ivm.setPurchaseDate(LocalDate.of(2019, 01, 01));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(1);
        invoiceItem2.setUnitPrice(new BigDecimal("5.99"));
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem2);
        ivm.setInvoiceItemList(invoiceItemList);

        InvoiceViewModel ivmFromService = invoiceServiceLayer.submitInvoice(ivm);
        ivm.setInvoiceId(ivmFromService.getInvoiceId());

        assertEquals(ivm, ivmFromService);
    }

    @Test
    public void getInvoiceById(){
        InvoiceViewModel ivm = new InvoiceViewModel();

        ivm.setInvoiceId(1);
        ivm.setCustomerId(1);
        ivm.setPurchaseDate(LocalDate.of(2019, 01, 01));
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(1);
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(1);
        invoiceItem2.setUnitPrice(new BigDecimal("5.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        invoiceItemList.add(invoiceItem2);

        ivm.setInvoiceItemList(invoiceItemList);

        InvoiceViewModel ivm2 = invoiceServiceLayer.getInvoiceById(1);

        assertEquals(ivm, ivm2);
    }

    @Test
    public void getAllInvoiceViewModels(){

        List<InvoiceViewModel> invoiceViewModelList = invoiceServiceLayer.getAllInvoices();

        assertEquals(invoiceViewModelList.size(), 1);
    }

    @Test
    public void getInvoiceViewModelsByCustomer() {

        List<InvoiceViewModel> invoiceViewModelList = invoiceServiceLayer.getInvoiceByCustomerId(1);

        assertEquals(invoiceViewModelList.size(), 1);
    }
}
