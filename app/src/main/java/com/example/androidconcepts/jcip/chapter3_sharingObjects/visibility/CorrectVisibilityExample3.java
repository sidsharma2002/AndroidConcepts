package com.example.androidconcepts.jcip.chapter3_sharingObjects.visibility;

public class CorrectVisibilityExample3 {
    private boolean ready = false;
    private int value = 0;

    private void startLooping() {
        new Thread(() -> {
            while (true) {
                synchronized (this) {
                    // if ready and value are written prior to acquiring this lock then everything visible to previous-lock-acquiring-thread (here its main thread) will become visible to this thread.
                    // hence, here we will always see value = 40 with ready = true and not 0 and true.
                    if (!ready)
                        Thread.yield();
                    else
                        break;
                }
            }

            System.out.println("value " + value);
        }).start();
    }

    public void startExecution() {
        startLooping();

        System.out.println("changing data");

        synchronized (this) {
            // this can still be reorder but now it won't affect the correctness of our code.
            value = 40;
            ready = true;
        }
    }
}