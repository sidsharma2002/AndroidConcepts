package com.example.androidconcepts.advancedConcurrency.producerConsumer.example5;

import com.example.androidconcepts.advancedConcurrency.producerConsumer.example4.Dish;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BQDishRackExample5 {

    private final BlockingQueue<Dish> washedDishesRack = new ArrayBlockingQueue<Dish>(3);
    private final BlockingQueue<Dish> cleanedDishesRack = new ArrayBlockingQueue<Dish>(3);

    private final Runnable dishWasher = () -> {
        int id = 0;

        while (true) {
            Dish dishWashed = new Dish(++id);

            try {
                washedDishesRack.put(dishWashed);
                System.out.println("dish washed : " + dishWashed.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (id > 10000) {
                return;
            }
        }
    };

    private final Runnable dishCleaner = () -> {
        while (true) {
            try {
                Dish dishCleaned = washedDishesRack.take();
                System.out.println("dish cleaned : " + dishCleaned.getId());
                cleanedDishesRack.put(dishCleaned);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private final Runnable dishDryer = () -> {
        while (true) {
            try {
                Dish dishDried = cleanedDishesRack.take();
                System.out.println("dish dried : " + dishDried.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void startExecution() {
        new Thread(dishWasher).start();
        new Thread(dishCleaner).start();
        new Thread(dishDryer).start();
    }
}
