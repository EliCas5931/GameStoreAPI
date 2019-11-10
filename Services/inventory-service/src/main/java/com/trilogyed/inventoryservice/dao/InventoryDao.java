package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryDao {

    Inventory createInventory(Inventory inventory);

    Inventory getInventory(int id);

    List<Inventory> getAllInventories();

    void updateInventory(Inventory inventory, int id);

    void deleteInventory(int id);
}
