package com.example.androidconcepts.advancedConcurrency.producerConsumer.example2;

import com.example.androidconcepts.advancedConcurrency.producerConsumer.ProducerConsumerServer1;

public class CorrectProducerConsumerExample2 {
    private final ProducerConsumerServer1 server = new ProducerConsumerServer1();

    private boolean isValueUnconsumed = false;

    private final Runnable producer = () -> {
        int hits = 0;

        while (true) {
            waitTillValueIsConsumed();

            int newValue = server.incrementAndPut();
            System.out.println("put : " + newValue);

            markValueUnconsumedAndNotify();

            // to make it finite loop
            hits++;
            if (hits > 10000) {
                return;
            }
        }
    };

    private void waitTillValueIsConsumed() {
        synchronized (this) {
            while (isValueUnconsumed) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void markValueUnconsumedAndNotify() {
        synchronized (this) {
            isValueUnconsumed = true;
            notifyAll();
        }
    }

    private final Runnable consumer = () -> {
        while (true) {
            waitTillValueBecomesUnconsumed();

            int value = server.get();
            System.out.println("get : " + value);

            markValueConsumedAndNotify();
        }
    };

    private void waitTillValueBecomesUnconsumed() {
        synchronized (this) {
            while (!isValueUnconsumed) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void markValueConsumedAndNotify() {
        synchronized (this) {
            isValueUnconsumed = false;
            notify();
        }
    }

    public void startExecution() {
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
