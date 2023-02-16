package com.example.androidconcepts.jcip.chapter2_threadSafety.atomicSyncBug;

import com.example.androidconcepts.jcip.common.ThreadContextSwitchTrigger;

import java.util.concurrent.atomic.AtomicLong;

/**
 * see AtomicServerTest.java
 */
public class WrongAtomicServer {

    private final AtomicLong hits = new AtomicLong(0);

    private ThreadContextSwitchTrigger contextSwitchTrigger = new ThreadContextSwitchTrigger(100L);

    void performCalculation() {
        if (hits.get() < 10) { // hits count should not get greater than 10.
            contextSwitchTrigger.trigger();
            hits.incrementAndGet();
        }
    }

    public Long getHits() {
        return hits.get();
    }
}
