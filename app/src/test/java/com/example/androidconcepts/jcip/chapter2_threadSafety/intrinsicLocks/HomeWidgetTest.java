package com.example.androidconcepts.jcip.chapter2_threadSafety.intrinsicLocks;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class HomeWidgetTest {

    @Test
    public void performTaskTest() {
        // arrange
        CountDownLatch countDownLatch = new CountDownLatch(1);
        HomeWidget homeWidget = new HomeWidget();

        // act
        new Thread(() -> {
            try {
                // assert, that homeWidget doesn't run for more than 1 second.
                boolean hasFinished = countDownLatch.await(1, TimeUnit.SECONDS);
                assert hasFinished;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        homeWidget.performTask();
        countDownLatch.countDown();
    }
}