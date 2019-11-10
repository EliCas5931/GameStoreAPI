package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InventoryDaoTempImpl implements InventoryDao{

    public static final String INSERT_INVENTORY =
            "insert into inventory (product_id, quantity) value (?,?)";
    public static final String SELECT_INVENTORY =
            "select * from inventory where inventory_id = ?";
    public static final String SELECT_ALL_INVENTORY =
            "select * from inventory";
    public static final String UPDATE_INVENTORY =
            "update inventory set product_id = ?, quantity = ? where inventory_id = ?";
    public static final String DELETE_INVENTORY =
            "delete from inventory where inventory_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public InventoryDaoTempImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Inventory createInventory(Inventory inventory) {
        jdbcTemplate.update(INSERT_INVENTORY,
                inventory.getProductId(),
                inventory.getQuantity());
        inventory.setInventoryId(jdbcTemplate.queryForObject("select last_insert_id()", Integer.class));
        return inventory;
    }

    @Override
    public Inventory getInventory(int id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_INVENTORY, this::mapRowToInventory, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public List<Inventory> getAllInventories() {
        return jdbcTemplate.query(SELECT_ALL_INVENTORY, this::mapRowToInventory);
    }

    @Override
    public void updateInventory(Inventory inventory, int id) {
        jdbcTemplate.update(UPDATE_INVENTORY,
                inventory.getProductId(),
                inventory.getQuantity(),
                id);
    }

    @Override
    public void deleteInventory(int id) {
        jdbcTemplate.update(DELETE_INVENTORY, id);
    }

    private Inventory mapRowToInventory(ResultSet resultSet, int i) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(resultSet.getInt("inventory_id"));
        inventory.setProductId(resultSet.getInt("product_id"));
        inventory.setQuantity(resultSet.getInt("quantity"));
        return inventory;
    }
}
