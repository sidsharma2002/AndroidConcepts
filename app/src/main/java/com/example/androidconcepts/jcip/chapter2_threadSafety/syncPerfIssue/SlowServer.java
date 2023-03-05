package com.example.androidconcepts.jcip.chapter2_threadSafety.syncPerfIssue;

import com.example.androidconcepts.jcip.common.ThreadContextSwitchTrigger;

public class SlowServer {

    private int hits;
    private int tenthHits;

    private final ThreadContextSwitchTrigger contextSwitchTrigger = new ThreadContextSwitchTrigger(100L);

    public synchronized void performCalculations() {
        hits++;
        int result = performFactorization();
        if (result % 10 == 0) {
            hits--;
        }
    }

    private int performFactorization() {
        contextSwitchTrigger.trigger();
        return 10;
    }
}
