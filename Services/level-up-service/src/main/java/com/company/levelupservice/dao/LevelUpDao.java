package com.company.levelupservice.dao;

import com.company.levelupservice.model.LevelUp;

import java.util.List;

public interface LevelUpDao {

   LevelUp addLevelUp(LevelUp levelUp);

   LevelUp getLevelUpById(int levelUpId);

   List<LevelUp> getAllLevelUp();

   void updateLevelUp(LevelUp levelUp);

   void deleteLevelUp(int levelUpId);

    LevelUp getLevelUpByCustomerId(int customerId);

}
