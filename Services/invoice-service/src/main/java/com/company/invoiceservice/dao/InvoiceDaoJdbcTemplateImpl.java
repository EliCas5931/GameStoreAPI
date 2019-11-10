package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public InvoiceDaoJdbcTemplateImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String INSERT_VOICE_SQL =
            "insert into invoice (customer_id, purchase_date) values (?, ?)";
    public static final String SELECT_INVOICE_BY_ID_SQL =
            "select * from invoice where invoice_id = ?";
    public static final String SELECT_ALL_INVOICES_SQL =
            "select * from invoice";
    public static final String DELETE_INVOICE_SQL =
            "delete from invoice where invoice_id = ?";
    public static final String UPDATE_INVOICE_SQL =
            "update invoice set customer_id = ?, purchase_date = ? where invoice_id = ?";
    public static final String SELECT_INVOICES_BY_CUSTOMER_ID_SQL =
            "select * from invoice where customer_id = ?";

    @Override
    public Invoice submitInvoice(Invoice invoice) {
        jdbcTemplate.update(
                INSERT_VOICE_SQL,
                invoice.getCustomerId(),
                invoice.getPurchaseDate());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        invoice.setInvoiceId(id);

        return invoice;

    }

    @Override
    public Invoice getInvoiceById(int invoiceId) {

        try {

            return jdbcTemplate.queryForObject(SELECT_INVOICE_BY_ID_SQL, this::mapRowToInvoice, invoiceId);

        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {

        return jdbcTemplate.query(SELECT_ALL_INVOICES_SQL, this::mapRowToInvoice);

    }

    @Override
    public void updateInvoice(Invoice invoice) {

        jdbcTemplate.update(

                UPDATE_INVOICE_SQL,
                invoice.getCustomerId(),
                invoice.getPurchaseDate(),
                invoice.getInvoiceId());

    }

    @Override
    public void deleteInvoice(int invoiceId) {

        jdbcTemplate.update(DELETE_INVOICE_SQL, invoiceId);

    }

    @Override
    public List<Invoice> getInvoiceByCustomerId(int customerId) {

        try {

            return jdbcTemplate.query(SELECT_INVOICES_BY_CUSTOMER_ID_SQL, this::mapRowToInvoice, customerId);

        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    private Invoice mapRowToInvoice (ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();

        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
        return invoice;
    }
}
