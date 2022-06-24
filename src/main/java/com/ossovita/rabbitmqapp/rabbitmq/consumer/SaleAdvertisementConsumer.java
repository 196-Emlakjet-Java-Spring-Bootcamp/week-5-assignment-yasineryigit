package com.ossovita.rabbitmqapp.rabbitmq.consumer;

import com.ossovita.rabbitmqapp.business.abstracts.SaleAdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleAdvertisementConsumer {

    SaleAdvertisementService saleAdvertisementService;

    public SaleAdvertisementConsumer(SaleAdvertisementService saleAdvertisementService) {
        this.saleAdvertisementService = saleAdvertisementService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.sale-advertisement-queue.name}")//get queue name from application.yml
    private void saveSaleAdvertisement(int piece) {
        log.info(piece + " sale advertisement will be created by consumer");
        try {
            //sleep for  2 mins
            Thread.sleep(5000);//5 sec for test
            saleAdvertisementService.createDummySaleAdvertisement(piece);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
