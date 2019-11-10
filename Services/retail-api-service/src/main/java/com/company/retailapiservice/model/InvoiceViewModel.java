package com.company.retailapiservice.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {

    private int invoiceId;
    private int customerId;
    private LocalDate purchaseDate;
    private List<InvoiceItem> invoiceItemList;
    private int points;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItem> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return invoiceId == that.invoiceId &&
                customerId == that.customerId &&
                points == that.points &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(invoiceItemList, that.invoiceItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItemList, points);
    }
}
