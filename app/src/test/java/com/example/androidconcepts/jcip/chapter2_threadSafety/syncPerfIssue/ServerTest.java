package com.example.androidconcepts.jcip.chapter2_threadSafety.syncPerfIssue;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ServerTest {

    @Test
    public void slowServerPerfTest() throws InterruptedException {
        SlowServer SUT = new SlowServer();
        CountDownLatch latch = new CountDownLatch(20);

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                SUT.performCalculations();
                latch.countDown();
            }).start();
        }

        latch.await(); // completes in 20 * 100 = 2 secs
    }

    @Test
    public void fastServerPerfTest() throws InterruptedException {
        FastServer SUT = new FastServer();
        CountDownLatch latch = new CountDownLatch(20);

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                SUT.performCalculations();
                latch.countDown();
            }).start();
        }

        latch.await(); // completes in 134 ms
    }
}