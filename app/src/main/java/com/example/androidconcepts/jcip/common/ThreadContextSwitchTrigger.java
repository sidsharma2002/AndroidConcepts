package com.example.androidconcepts.jcip.common;

import com.example.androidconcepts.common.BgThreadPoster;

public class ThreadContextSwitchTrigger {
    private Long millis;

    public ThreadContextSwitchTrigger(Long millis) {
        this.millis = millis;
    }

    public void trigger() {
        long finalTime = System.currentTimeMillis() + millis;
        int count = 0;
        while (System.currentTimeMillis() < finalTime) {
            Thread.yield();
            count++;
        }
    }

    public void trigger(Long time) {
        long finalTime = System.currentTimeMillis() + time;
        int count = 0;
        while (System.currentTimeMillis() < finalTime) {
            Thread.yield();
            count++;
        }
    }
}

