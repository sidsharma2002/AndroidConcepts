package com.example.androidconcepts.jcip.chapter3_sharingObjects.visibility;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class NoVisibilityExample2 {
    // these are synchronized but on different locks which doesn't help in hb guarantee.
    private final AtomicBoolean ready = new AtomicBoolean(false);
    private final AtomicInteger value = new AtomicInteger(0);

    private void startLooping() {
        new Thread(() -> {
            while (!ready.get()) {
                Thread.yield();
            }

            System.out.println("value " + value.get()); // may print value : 0 here due to reordering.
        }).start();
    }

    public void startExecution() {
        startLooping();

        System.out.println("changing data");

        // these values can still be reordered as there is no happens before guarantee.
        value.set(40);
        ready.set(true);
    }
}
