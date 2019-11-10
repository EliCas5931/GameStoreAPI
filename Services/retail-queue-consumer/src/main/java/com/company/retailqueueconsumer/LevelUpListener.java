package com.company.retailqueueconsumer;

import com.company.retailqueueconsumer.model.LevelUp;
import com.company.retailqueueconsumer.util.feign.LevelUpClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class LevelUpListener {

    @Autowired
    private final LevelUpClient client;

    public LevelUpListener(LevelUpClient client) {

        this.client = client;

    }

    @RabbitListener(queues = RetailQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(LevelUp msg) {
        System.out.println(msg.toString());

        LevelUp lu = new LevelUp();

        lu.setLevelUpId(msg.getLevelUpId());
        lu.setCustomerId(msg.getCustomerId());
        lu.setPoints(msg.getPoints());
        lu.setMemberDate(msg.getMemberDate());

        client.addLevelUp(lu);
    }

}