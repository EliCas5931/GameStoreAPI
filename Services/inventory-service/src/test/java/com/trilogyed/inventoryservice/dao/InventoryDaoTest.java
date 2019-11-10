package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryDaoTest {

    @Autowired
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        List<Inventory> inventories = inventoryDao.getAllInventories();
        inventories.stream()
                .forEach(inventory -> inventoryDao.deleteInventory(inventory.getInventoryId()));
    }

    @Test
    public void addGetDeleteInventory(){
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryDao.createInventory(inventory);
        Inventory fromDao = inventoryDao.getInventory(inventory.getInventoryId());
        assertEquals(fromDao, inventory);
        inventoryDao.deleteInventory(inventory.getInventoryId());
        fromDao = inventoryDao.getInventory(inventory.getInventoryId());
        assertNull(fromDao);
    }

    @Test
    public void getAllInventories(){
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventoryDao.createInventory(inventory);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(10);
        inventoryDao.createInventory(inventory1);

        List<Inventory> inventories = inventoryDao.getAllInventories();
        assertEquals(2, inventories.size());
    }

    @Test
    public void updateInventory(){
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = inventoryDao.createInventory(inventory);
        inventory.setQuantity(11);
        inventoryDao.updateInventory(inventory, inventory.getInventoryId());
        Inventory fromDao = inventoryDao.getInventory(inventory.getInventoryId());
        assertEquals(inventory, fromDao);
    }



}
