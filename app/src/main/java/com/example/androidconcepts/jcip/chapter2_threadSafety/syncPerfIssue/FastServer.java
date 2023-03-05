package com.example.androidconcepts.jcip.chapter2_threadSafety.syncPerfIssue;

import com.example.androidconcepts.jcip.common.ThreadContextSwitchTrigger;

public class FastServer {
    private int hits;
    private int tenthHits;

    private final ThreadContextSwitchTrigger contextSwitchTrigger = new ThreadContextSwitchTrigger(100L);

    public void performCalculations() {
        synchronized (this) {
            hits++;
        }

        int result = performFactorization();

        if (result % 10 == 0) {
            synchronized (this) {
                hits--;
            }
        }
    }

    private int performFactorization() {
        contextSwitchTrigger.trigger();
        return 10;
    }
}
