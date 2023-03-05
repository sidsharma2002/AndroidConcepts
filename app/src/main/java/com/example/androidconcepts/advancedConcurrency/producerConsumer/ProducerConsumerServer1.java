package com.example.androidconcepts.advancedConcurrency.producerConsumer;

public class ProducerConsumerServer1 {
    private int hits = 0;

    public synchronized int incrementAndPut() {
        hits++;
        return hits;
    }

    public synchronized int get() {
        return hits;
    }
}
