package com.example.androidconcepts.advancedConcurrency.producerConsumer.example3;

import com.example.androidconcepts.advancedConcurrency.producerConsumer.ProducerConsumerServer1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BQProducerConsumerExample3 {
    private final ProducerConsumerServer1 server = new ProducerConsumerServer1();
    private final BlockingQueue<Integer> unConsumedValueBQ = new ArrayBlockingQueue<>(1);

    private final Runnable producer = () -> {
        int hits = 0;

        while (true) {
            int updatedValue = server.incrementAndPut();

            try {
                unConsumedValueBQ.put(updatedValue);
                System.out.println("put value = " + updatedValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // to make it finite loop
            hits++;
            if (hits > 10000) {
                return;
            }
        }
    };

    private final Runnable consumer = () -> {
        int hits = 0;

        while (true) {
            try {
                int value = unConsumedValueBQ.take();
                System.out.println("get value = " + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // to make it finite loop
            hits++;
            if (hits > 10000) {
                return;
            }
        }
    };

    public void startExecution() {
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
