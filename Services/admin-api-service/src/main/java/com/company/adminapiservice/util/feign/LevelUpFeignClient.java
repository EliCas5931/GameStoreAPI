package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="level-up-service")
public interface LevelUpFeignClient {

    @RequestMapping(value = "/levelup",method = RequestMethod.POST)
    public LevelUp addLevelup(@RequestBody LevelUp levelup);

    @RequestMapping(value = "/levelup",method = RequestMethod.GET)
    public List<LevelUp> getAllLevelups();

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.GET)
    public LevelUp getLevelupById(@PathVariable int id);

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.PUT)
    public void updateLevelup(@RequestBody LevelUp levelup, @PathVariable int id);

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.DELETE)
    public void deleteLevelup(@PathVariable int id);

}
