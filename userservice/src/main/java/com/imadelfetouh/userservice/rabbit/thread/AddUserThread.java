package com.imadelfetouh.userservice.rabbit.thread;

import com.imadelfetouh.userservice.rabbit.delivercallback.AddUserDeliverCallback;
import com.rabbitmq.client.DeliverCallback;

public class AddUserThread implements Runnable {

    private final String queue_name;
    private final String exchange_name;
    private final DeliverCallback deliverCallback;

    public AddUserThread() {
        queue_name = "userservice_adduserconsumer";
        exchange_name = "adduserexchange";
        deliverCallback = new AddUserDeliverCallback();
    }

    @Override
    public void run() {
        StartConsuming startConsuming = new StartConsuming(queue_name, exchange_name, deliverCallback);
        startConsuming.start();
    }
}
