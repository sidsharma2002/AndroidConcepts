package com.example.androidconcepts.advancedConcurrency.producerConsumer;

public class ProducerConsumerServer1 {
    private int hits = 0;

    public synchronized void incrementAndPut() {
        hits++;
        System.out.println("put hits = " + hits);
    }

    public synchronized int get() {
        System.out.println("get hits = " + hits);
        return hits;
    }
}
