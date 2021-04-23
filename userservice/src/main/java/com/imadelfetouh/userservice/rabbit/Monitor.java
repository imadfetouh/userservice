package com.imadelfetouh.userservice.rabbit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor {

    private static final Logger logger = Logger.getLogger(Monitor.class.getName());

    private final Object monitor;

    public Monitor() {
        monitor = new Object();
    }

    public void start() {
        while(true) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                    break;
                } catch (InterruptedException e) {
                    logger.log(Level.ALL, e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
