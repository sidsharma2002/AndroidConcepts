package com.example.androidconcepts.advancedConcurrency.producerConsumer.example4;

public class Dish {
    private int id = 0;

    public Dish(int id) {
        this.id = id;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }
}
