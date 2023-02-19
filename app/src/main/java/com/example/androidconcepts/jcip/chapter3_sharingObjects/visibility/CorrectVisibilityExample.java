package com.example.androidconcepts.jcip.chapter3_sharingObjects.visibility;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CorrectVisibilityExample {
    private final AtomicBoolean ready = new AtomicBoolean(false);
    private final AtomicInteger value = new AtomicInteger(0);

    private void startLooping() {
        new Thread(() -> {
            while (!ready.get()) { // loop may never break if updated value of ready is not visible.
                Thread.yield();
            }

            System.out.println("value " + value.get()); // may print value : 0 here due to visibility reordering.
        }).start();
    }

    public void startExecution() {
        startLooping();

        System.out.println("changing data");

        value.set(40);
        ready.set(true);
    }
}
