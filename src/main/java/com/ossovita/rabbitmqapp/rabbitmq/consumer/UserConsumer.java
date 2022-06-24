package com.ossovita.rabbitmqapp.rabbitmq.consumer;

import com.ossovita.rabbitmqapp.business.abstracts.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserConsumer {

    UserService userService;

    public UserConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.user-queue.name}")//get queue name from application.yml
    private void saveUser(int piece) {
        log.info(piece + " random user will be created by consumer");
        try {
            //sleep for  2 mins
            Thread.sleep(5000);//5 sec for test
            userService.createDummyUser(piece);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
