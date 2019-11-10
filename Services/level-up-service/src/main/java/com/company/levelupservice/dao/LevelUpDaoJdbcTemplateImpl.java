package com.company.levelupservice.dao;

import com.company.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelUpDaoJdbcTemplateImpl implements LevelUpDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public LevelUpDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;

    }

    public static final String INSERT_LEVELUP_SQL =
            "insert into level_up (customer_id, points, member_date) values (?, ?, ?)";
    public static final String SELECT_LEVELUP_BY_ID_SQL =
            "select * from level_up where level_up_id = ?";
    public static final String SELECT_ALL_LEVELUPS_SQL =
            "select * from level_up";
    public static final String DELETE_LEVELUP_BY_ID_SQL =
            "delete from level_up where level_up_id = ?";
    public static final String UPDATE_LEVELUP_BY_ID_SQL =
            "update level_up set customer_id = ?, points = ?, member_date = ? where level_up_id = ?";
    public static final String SELECT_LEVELUPS_BY_CUSTOMER_ID_SQL =
            "select * from level_up where customer_id = ?";




    @Override
    public LevelUp addLevelUp(LevelUp levelUp) {

        jdbcTemplate.update(
                INSERT_LEVELUP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        levelUp.setLevelUpId(id);

        return levelUp;
    }

    @Override
    public LevelUp getLevelUpById(int levelUpId) {

        try{

            return jdbcTemplate.queryForObject(SELECT_LEVELUP_BY_ID_SQL, this::mapRowToLevelUp, levelUpId);

        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    @Override
    public List<LevelUp> getAllLevelUp() {

        return jdbcTemplate.query(SELECT_ALL_LEVELUPS_SQL, this::mapRowToLevelUp);

    }

    @Override
    public void updateLevelUp(LevelUp levelUp) {

        jdbcTemplate.update(UPDATE_LEVELUP_BY_ID_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate(),
                levelUp.getLevelUpId());

    }

    @Override
    public void deleteLevelUp(int levelUpId) {

        jdbcTemplate.update(DELETE_LEVELUP_BY_ID_SQL, levelUpId);

    }

    @Override
    public LevelUp getLevelUpByCustomerId(int customerId) {

        try{

            return jdbcTemplate.queryForObject(SELECT_LEVELUPS_BY_CUSTOMER_ID_SQL, this::mapRowToLevelUp, customerId);

        } catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    private LevelUp mapRowToLevelUp(ResultSet rs, int rowNum) throws SQLException {

        LevelUp levelUp = new LevelUp();

        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setPoints(rs.getInt("points"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());

        return levelUp;
    }
}
