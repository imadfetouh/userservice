package com.imadelfetouh.userservice.rabbit.thread;

import com.imadelfetouh.userservice.rabbit.delivercallback.DeleteUserDeliverCallback;
import com.rabbitmq.client.DeliverCallback;

public class DeleteUserThread implements Runnable {

    private final String queue_name;
    private final String exchange_name;
    private final DeliverCallback deliverCallback;

    public DeleteUserThread() {
        queue_name = "userservice_deleteuserconsumer";
        exchange_name = "deleteuserexchange";
        deliverCallback = new DeleteUserDeliverCallback();
    }

    @Override
    public void run() {
        StartConsuming startConsuming = new StartConsuming(queue_name, exchange_name, deliverCallback);
        startConsuming.start();
    }
}
