package com.example.androidconcepts.advancedConcurrency.producerConsumer.example4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BQDishRackExample4 {

    private  final BlockingQueue<Dish> dishRack = new ArrayBlockingQueue<>(3);

    private Runnable dishWasher = () -> {
        int id = 0;

        while (true) {
            Dish dishWashed = new Dish(++id);

            try {
                dishRack.put(dishWashed);
                System.out.println("dish washed : " + id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // for finite loop
            if (id > 10000) {
                return;
            }
        }
    };

    private Runnable dishCleaner = () -> {
        while (true) {
            try {
                Dish dishToBeCleaned = dishRack.take();
                System.out.println("dish cleaned : " + dishToBeCleaned.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void startExecution() {
        new Thread(dishWasher).start();
        new Thread(dishCleaner).start();
    }
}
