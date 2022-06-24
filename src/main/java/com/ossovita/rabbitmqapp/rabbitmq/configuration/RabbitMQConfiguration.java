package com.ossovita.rabbitmqapp.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {

    //get values from properties place holder
    @Value("${rabbitmq.queues.user-queue.name}")
    private String userQueueName;

    @Value("${rabbitmq.queues.sale-advertisement-queue.name}")
    private String saleAdvertisementQueueName;


    @Value("${rabbitmq.exchanges.direct-exchange.name}")
    private String exchangeName;

    @Bean
    Queue userQueue() {
        return new Queue(userQueueName, false);
    }

    @Bean
    Queue saleAdvertisementQueue() {
        return new Queue(saleAdvertisementQueueName, false);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    //queue'yu direct exchange'a direkt olarak bağlar
    @Bean
    Binding userQueueBinding(@Qualifier("userQueue") Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("userRouting");
    }

    @Bean
    Binding saleAdvertisementQueueBinding(@Qualifier("saleAdvertisementQueue") Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("saleAdvertisementRouting");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
