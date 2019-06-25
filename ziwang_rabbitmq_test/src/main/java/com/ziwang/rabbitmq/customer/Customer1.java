package com.ziwang.rabbitmq.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "ziwang")
public class Customer1 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("直接模式接收到消息："+msg);
    }
}
