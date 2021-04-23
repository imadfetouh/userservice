package com.imadelfetouh.userservice.rabbit.consumer;

import com.imadelfetouh.userservice.rabbit.Monitor;
import com.imadelfetouh.userservice.rabbit.NonStopConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultConsumer implements NonStopConsumer {

    private static final Logger logger = Logger.getLogger(DefaultConsumer.class.getName());

    private final String queue_name;
    private final String exchange_name;
    private final DeliverCallback deliverCallback;

    public DefaultConsumer(String queue_name, String exchange_name, DeliverCallback deliverCallback) {
        this.queue_name = queue_name;
        this.exchange_name = exchange_name;
        this.deliverCallback = deliverCallback;
    }

    @Override
    public void consume(Channel channel) {
        try {
            channel.queueDeclare(queue_name, false, false, false, null);
            channel.exchangeDeclare(exchange_name, "direct", true);
            channel.queueBind(queue_name, exchange_name, "");

            channel.basicConsume(queue_name, true, deliverCallback, s -> {});

            Monitor monitor = new Monitor();
            monitor.start();
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
