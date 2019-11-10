package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InventoryServiceLayer {

    @Autowired
    InventoryDao dao;

    public InventoryServiceLayer(InventoryDao dao) {
        this.dao = dao;
    }

    @Transactional
    public Inventory newInventory(Inventory inventory){
        return dao.createInventory(inventory);
    }

    @Transactional
    public Inventory fetchInventory(int id){
        return dao.getInventory(id);
    }

    public List<Inventory> fetchAllInventories(){
        return dao.getAllInventories();
    }

    public void updateInventory(Inventory inventory, int id){
        dao.updateInventory(inventory, id);
    }

    public void deleteInventory(int id){
        dao.deleteInventory(id);
    }




}
