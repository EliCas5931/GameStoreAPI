package com.company.levelupservice.controller;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.exception.NotFoundException;
import com.company.levelupservice.model.LevelUp;
import com.company.levelupservice.service.LevelUpServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/levelUp")
public class LevelUpController {

    @Autowired
    LevelUpServiceLayer serviceLayer;

    @Autowired
    LevelUpDao levelUpDao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp receiveLevelUp(@RequestBody LevelUp levelUp){
        return serviceLayer.createUpdateLevelUp(levelUp);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getById(@PathVariable int id){
        return levelUpDao.getLevelUpById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAll(){
        return levelUpDao.getAllLevelUp();
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateLevelup(@RequestBody LevelUp customer, @PathVariable int id) {
        if (customer.getLevelUpId()==id){
            serviceLayer.updateLevelUp(customer);
        } else {
            throw new NotFoundException("Id does not match: "+ customer.getLevelUpId());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteLevelUp(@PathVariable int id){
        levelUpDao.deleteLevelUp(id);
    }

    @GetMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getByCustomer(@PathVariable int customer_id){
        return levelUpDao.getLevelUpByCustomerId(customer_id);
    }

}
