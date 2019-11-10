package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.dao.InventoryDaoTempImpl;
import com.trilogyed.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class InventoryServiceLayerTest {

    private InventoryDao inventoryDao;
    InventoryServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpInventoryDaoMock();
        service = new InventoryServiceLayer(inventoryDao);
    }

    private void setUpInventoryDaoMock(){
        inventoryDao = mock(InventoryDaoTempImpl.class);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(2);
        inventory.setQuantity(10);

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1);
        inventory1.setProductId(2);
        inventory1.setQuantity(10);

        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventory);

        doReturn(inventory).when(inventoryDao).createInventory(inventory1);
        doReturn(inventory).when(inventoryDao).getInventory(1);
        doReturn(inventories).when(inventoryDao).getAllInventories();
    }

    @Test
    public void saveInventory(){
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(2);
        inventory.setQuantity(10);

        inventory = service.newInventory(inventory);

        Inventory fromService = service.fetchInventory(inventory.getInventoryId());
        assertEquals(inventory, fromService);
    }

    @Test
    public void findAllInventories(){
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(2);
        inventory.setQuantity(10);

        inventory = service.newInventory(inventory);
        Inventory fromService = service.fetchInventory(inventory.getInventoryId());
        assertEquals(inventory, fromService);

        List<Inventory> inventories = service.fetchAllInventories();
        assertEquals(1, inventories.size());
    }
}
