package com.trilogyed.productservice.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    int productId;
    String productName;
    String productDescription;
    BigDecimal listPrice;
    BigDecimal unitCost;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice.setScale(2);
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost.setScale(2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProductId() == product.getProductId() &&
                getProductName().equals(product.getProductName()) &&
                getProductDescription().equals(product.getProductDescription()) &&
                getListPrice().equals(product.getListPrice()) &&
                getUnitCost().equals(product.getUnitCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getListPrice(), getUnitCost());
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", listPrice=" + listPrice +
                ", unitCost=" + unitCost +
                '}';
    }
}
