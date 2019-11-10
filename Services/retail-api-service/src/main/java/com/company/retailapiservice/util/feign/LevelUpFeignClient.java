package com.company.retailapiservice.util.feign;


import com.company.retailapiservice.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpFeignClient {

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)

    public List<LevelUp> getLevelUpByCustomerId(@PathVariable int id);

}
