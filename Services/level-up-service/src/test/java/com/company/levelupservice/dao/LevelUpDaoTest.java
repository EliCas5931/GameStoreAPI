package com.company.levelupservice.dao;

import com.company.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LevelUpDaoTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {
        List<LevelUp> levelUpsList = levelUpDao.getAllLevelUp();

        for(LevelUp level : levelUpsList){
            levelUpDao.deleteLevelUp(level.getLevelUpId());
        }
    }

    @Test
    public void addGetDelete(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(15);
        levelUp.setMemberDate(LocalDate.of(2018, 02, 14));
        levelUp = levelUpDao.addLevelUp(levelUp);

        LevelUp levelUp2 = levelUpDao.getLevelUpById(levelUp.getLevelUpId());
        assertEquals(levelUp2, levelUp);

        levelUpDao.deleteLevelUp(levelUp.getLevelUpId());
        levelUp2 = levelUpDao.getLevelUpById(levelUp.getLevelUpId());
        assertNull(levelUp2);
    }

    @Test
    public void updateLevelUp(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(15);
        levelUp.setMemberDate(LocalDate.of(2018, 02, 14));
        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUp.setCustomerId(1);
        levelUp.setPoints(100);
        levelUp.setMemberDate(LocalDate.of(2018, 02, 14));
        levelUpDao.updateLevelUp(levelUp);

        LevelUp levelUp1 = levelUpDao.getLevelUpById(levelUp.getLevelUpId());
        assertEquals(levelUp1, levelUp);
    }

    @Test
    public void getAllLevelUps(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(15);
        levelUp.setMemberDate(LocalDate.of(2018, 02, 14));
        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUp.setCustomerId(1);
        levelUp.setPoints(30);
        levelUp.setMemberDate(LocalDate.of(2018, 02, 14));
        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUp.setCustomerId(1);
        levelUp.setPoints(45);
        levelUp.setMemberDate(LocalDate.of(2018, 02, 14));
        levelUp = levelUpDao.addLevelUp(levelUp);

        List<LevelUp> levelUpList = levelUpDao.getAllLevelUp();
        assertEquals(levelUpList.size(), 3);
    }

    @Test
    public void getLevelUpByCustomer(){
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(15);
        levelUp.setMemberDate(LocalDate.of(2018, 02, 14));
        levelUp = levelUpDao.addLevelUp(levelUp);

        LevelUp levelUp1 = levelUpDao.getLevelUpByCustomerId(1);
        assertEquals(levelUp1, levelUp);
    }

}
