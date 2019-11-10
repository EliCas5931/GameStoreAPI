package com.company.levelupservice.service;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelUpServiceLayer {

    @Autowired
    LevelUpDao levelUpDao;

    @Autowired
    public LevelUpServiceLayer(LevelUpDao levelUpDao) {

        this.levelUpDao = levelUpDao;

    }

    //help
    public LevelUp createUpdateLevelUp(LevelUp levelUp){

        LevelUp lu = levelUpDao.getLevelUpByCustomerId(levelUp.getCustomerId());

        if(lu != null){

            lu.setPoints(lu.getPoints() + levelUp.getPoints());

            levelUpDao.updateLevelUp(lu);

        } else {

            lu = levelUpDao.addLevelUp(levelUp);

        }

        return lu;
    }

    public List<LevelUp> getAllLevelups() {
        return levelUpDao.getAllLevelUp();
    }

    public LevelUp getLevelupById(int id) {
        return levelUpDao.getLevelUpById(id);
    }

    public void updateLevelUp(LevelUp levelup) {
        levelUpDao.updateLevelUp(levelup);
    }

    public void deleteLevelup(int id) {
        levelUpDao.deleteLevelUp(id);
    }

    public LevelUp getLevelUpByCustomerId(int id){
        return levelUpDao.getLevelUpByCustomerId(id);
    }
}
