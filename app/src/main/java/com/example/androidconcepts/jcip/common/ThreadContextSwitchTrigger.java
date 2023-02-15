package com.example.androidconcepts.jcip.common;

public class ThreadContextSwitchTrigger {
    private Long millis;

    public ThreadContextSwitchTrigger(Long millis) {
        this.millis = millis;
    }

    public void trigger() {
        long finalTime = System.currentTimeMillis() + 100;
        int count = 0;
        while (System.currentTimeMillis() < finalTime) {
            Thread.yield();
            count++;
        }
    }
}
