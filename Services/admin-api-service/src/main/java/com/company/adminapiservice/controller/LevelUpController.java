package com.company.adminapiservice.controller;

import com.company.adminapiservice.exception.NotFoundException;
import com.company.adminapiservice.model.LevelUp;
import com.company.adminapiservice.service.AdminServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/levelup")
public class LevelUpController {

    @Autowired
    AdminServiceLayer service;

    public LevelUpController(AdminServiceLayer service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public LevelUp addLevelup(@RequestBody LevelUp levelup, Principal principal){
        return service.addLevelup(levelup);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<LevelUp> getAllLevelups(Principal principal) {
        return service.getAllLevelups();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUp getLevelupById(@PathVariable int id,Principal principal) {
        LevelUp  levelup= service.getLevelupById(id);
        if (levelup==null){
            throw new NotFoundException("Not found! Please try again!");
        } else {
            return levelup;
        }
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateLevelup(@RequestBody LevelUp levelup, @PathVariable int id,Principal principal) {
        if (levelup.getLevelupId()==id){
            service.updateLevelup(levelup,id);
        } else {
            throw new NotFoundException("Incorrect path, should be: "+ levelup.getLevelupId());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteLevelup(@PathVariable int id, Principal principal) {
        service.deleteLevelup(id);
    }
}
