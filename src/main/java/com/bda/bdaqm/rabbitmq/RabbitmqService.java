package com.bda.bdaqm.rabbitmq;

import java.nio.channels.Channel;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {

    public void getMessage(Map message) {
        System.out.println("id：" + message.get("id"));
        System.out.println("data：" + message.get("data"));
        System.out.println("statusCode：" + message.get("statusCode"));
    }

}