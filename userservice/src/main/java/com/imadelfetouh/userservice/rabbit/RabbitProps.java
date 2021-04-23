package com.imadelfetouh.userservice.rabbit;

import com.rabbitmq.client.AMQP;

public class RabbitProps {

    private final static RabbitProps rabbitProps = new RabbitProps();
    private final String corrId;

    private RabbitProps() {
        corrId = "userservice";
    }

    public static RabbitProps getInstance() {
        return rabbitProps;
    }

    public AMQP.BasicProperties createProperties() {
        return new AMQP.BasicProperties()
                .builder()
                .correlationId(corrId)
                .build();
    }

    public String getCorrId() {
        return corrId;
    }
}
