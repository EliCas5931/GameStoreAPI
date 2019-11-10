package com.company.levelupservice.service;

import com.company.levelupservice.dao.LevelUpDao;
import com.company.levelupservice.dao.LevelUpDaoJdbcTemplateImpl;
import com.company.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTest {
    LevelUpServiceLayer serviceLayer;
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {
        setUpLevelUpDaoMock();

        serviceLayer = new LevelUpServiceLayer(levelUpDao);
    }

    @Test
    public void addFindLevelUp() {
        LevelUp lu = new LevelUp();
        lu.setCustomerId(7);
        lu.setPoints(10);
        lu.setMemberDate(LocalDate.now());

        lu = serviceLayer.createUpdateLevelUp(lu);
        LevelUp lu2 = serviceLayer.getLevelupById(7);

        assertEquals(lu, lu2);

    }

    @Test
    public void getAllLevelUps() {
        List<LevelUp> lul = new ArrayList<>();

        LevelUp lu2 = new LevelUp();
        lu2.setLevelUpId(4);
        lu2.setCustomerId(5);
        lu2.setPoints(6);
        lu2.setMemberDate(LocalDate.now());
        lul.add(lu2);

        LevelUp lu3 = new LevelUp();
        lu3.setLevelUpId(11);
        lu3.setCustomerId(12);
        lu3.setPoints(13);
        lu3.setMemberDate(LocalDate.now());
        lul.add(lu3);

        List<LevelUp> lupL = serviceLayer.getAllLevelups();

        assertEquals(lupL, lul);

    }

    public void setUpLevelUpDaoMock(){
        levelUpDao = mock(LevelUpDaoJdbcTemplateImpl.class);

        List<LevelUp> lul = new ArrayList<>();

        //add mock information here for tests
        LevelUp lu = new LevelUp();
        lu.setCustomerId(3);
        lu.setPoints(5);
        lu.setMemberDate(LocalDate.now());

        LevelUp lu2 = new LevelUp();
        lu2.setLevelUpId(4);
        lu2.setCustomerId(5);
        lu2.setPoints(6);
        lu2.setMemberDate(LocalDate.now());
        lul.add(lu2);

        LevelUp lu3 = new LevelUp();
        lu3.setLevelUpId(11);
        lu3.setCustomerId(12);
        lu3.setPoints(13);
        lu3.setMemberDate(LocalDate.now());
        lul.add(lu3);


//        doReturn(lu2).when(levelUpDao).addLevelUp(lu);
//        doReturn(lu2).when(levelUpDao).getLevelUpById(4);
        doReturn(lul).when(levelUpDao).getAllLevelUp();
//        doReturn(lu).when(levelUpDao).getLevelUpByCustomerId(5);


    }
}

