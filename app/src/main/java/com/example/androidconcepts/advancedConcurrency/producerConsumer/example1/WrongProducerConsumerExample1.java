package com.example.androidconcepts.advancedConcurrency.producerConsumer.example1;

import com.example.androidconcepts.advancedConcurrency.producerConsumer.ProducerConsumerServer1;

public class WrongProducerConsumerExample1 {

    private final ProducerConsumerServer1 server = new ProducerConsumerServer1();

    private final Runnable producer = () -> {
        int hits = 0;

        while (true) {
            server.incrementAndPut();
            // to make it finite loop
            hits++;
            if (hits > 1000) {
                return;
            }
        }
    };

    private final Runnable consumer = () -> {
        int hits = 0;

        while (true) {
            server.get();
            // to make it finite loop
            hits++;
            if (hits > 1000) {
                return;
            }
        }
    };

    public void startExecution() throws InterruptedException {
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
