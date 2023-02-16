package com.example.androidconcepts.jcip.chapter2_threadSafety.atomicSyncBug;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AtomicServerTest {

    @Test
    public void performConcurrencyTestOfWrongServer() throws Exception {
        // arrange
        WrongAtomicServer SUT = new WrongAtomicServer();
        CountDownLatch countDownLatch = new CountDownLatch(100);

        // act
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                SUT.performCalculation();
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await(10, TimeUnit.SECONDS);

        // assert
        Long hits = SUT.getHits();

        if (hits > 10) {
            throw new Exception("hits read are " + hits);
        }
    }

    @Test
    public void performConcurrencyTestOfCorrectServer() throws Exception {
        // arrange
        CorrectAtomicServer SUT = new CorrectAtomicServer();
        CountDownLatch countDownLatch = new CountDownLatch(100);

        // act
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                SUT.performCalculation();
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await(10, TimeUnit.SECONDS);

        // assert
        Long hits = SUT.getHits();

        if (hits > 10) {
            throw new Exception("hits read are " + hits);
        }
    }
}