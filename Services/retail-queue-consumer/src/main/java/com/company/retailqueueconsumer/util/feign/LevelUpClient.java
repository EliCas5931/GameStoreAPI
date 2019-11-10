package com.company.retailqueueconsumer.util.feign;

import com.company.retailqueueconsumer.model.LevelUp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="level-up-service")
public interface LevelUpClient {

    @RequestMapping(value = "/levelup", method = RequestMethod.POST)
    public LevelUp addLevelUp(@RequestBody LevelUp levelUp);

}
