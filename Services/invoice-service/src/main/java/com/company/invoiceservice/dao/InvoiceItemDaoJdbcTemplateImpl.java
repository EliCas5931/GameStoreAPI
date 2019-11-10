package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemDaoJdbcTemplateImpl implements InvoiceItemDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public InvoiceItemDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String INSERT_INVOICE_ITEM_SQL =
            "insert into invoice_item (invoice_id, inventory_id, quantity, unit_price) values (?, ?, ?, ?)";
    public static final String SELECT_INVOICE_ITEM_BY_ID_SQL =
            "select * from invoice_item where invoice_item_id = ?";
    public static final String SELECT_ALL_INVOICE_ITEMS_SQL =
            "select * from invoice_item";
    public static final String UPDATE_INVOICE_ITEM_SQL =
            "update invoice_item set invoice_id = ?, inventory_id = ?, quantity = ?, unit_price = ? where invoice_item_id = ?";
    public static final String DELETE_INVOICE_ITEM_SQL =
            "delete from invoice_item where invoice_item_id = ?";
    public static final String SELECT_INVOICE_ITEMS_BY_INVOICE_ID =
            "select * from invoice_item where invoice_id = ?";

    @Override
    public InvoiceItem addInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                INSERT_INVOICE_ITEM_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        invoiceItem.setInvoiceItemId(id);

        return invoiceItem;
    }

    @Override
    public InvoiceItem getInvoiceItemById(int invoiceItemId) {

        try {

            return jdbcTemplate.queryForObject(SELECT_INVOICE_ITEM_BY_ID_SQL, this::mapRowToInvoiceItem, invoiceItemId);

        } catch (EmptyResultDataAccessException e) {

            return null;

        }
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {

        return jdbcTemplate.query(SELECT_ALL_INVOICE_ITEMS_SQL, this::mapRowToInvoiceItem);

    }

    @Override
    public void updateInvoiceItem(InvoiceItem invoiceItem) {

        jdbcTemplate.update(
                UPDATE_INVOICE_ITEM_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getInvoiceItemId());

    }

    @Override
    public void deleteInvoiceItem(int invoiceItemId) {

        jdbcTemplate.update(DELETE_INVOICE_ITEM_SQL, invoiceItemId);

    }

    @Override
    public List<InvoiceItem> getInvoiceItemByInvoice(int invoiceId) {
        try{
            return jdbcTemplate.query(SELECT_INVOICE_ITEMS_BY_INVOICE_ID, this::mapRowToInvoiceItem, invoiceId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteInvoiceItemByInvoiceId(int invoiceId) {

    }

    @Override
    public void deleteInvoiceItemByInventoryId(int inventoryId) {

    }

    private InvoiceItem mapRowToInvoiceItem (ResultSet rs, int rowNum) throws SQLException {
        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setInventoryId(rs.getInt("inventory_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitPrice(rs.getBigDecimal("unit_price"));

        return invoiceItem;
    }

}
