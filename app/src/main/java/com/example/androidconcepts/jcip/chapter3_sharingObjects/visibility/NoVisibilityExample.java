package com.example.androidconcepts.jcip.chapter3_sharingObjects.visibility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class NoVisibilityExample {
    private boolean ready = false;
    private int value = 0;

    private void startLooping() {
        new Thread(() -> {
            while (!ready) { // loop may never break if updated value of ready is not visible.
                Thread.yield();
            }

            System.out.println("value " + value); // may print value : 0 here due to visibility reordering.
        }).start();
    }

    public void startExecution() {
        startLooping();

        System.out.println("changing data");

        // Reordering can happen in this :
        // though we are making data change to first value and then ready, it might be the case
        // that ready becomes true prior to the value because writes to value and read are independent to each other.
        value = 40;
        ready = true;

        // maybe rearranged to
        // ready = true;
        // value = 40;
    }
}
