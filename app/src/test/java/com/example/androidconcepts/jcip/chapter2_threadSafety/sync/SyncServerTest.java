package com.example.androidconcepts.jcip.chapter2_threadSafety.sync;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SyncServerTest {

    @Test
    public void testingSync() throws Exception {
        SyncServer server = new SyncServer();
        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            server.perform1();
        }).start();

        // wait for perform1 thread to start
        Thread.sleep(100);

        new Thread(() -> {
            server.perform2();
            latch.countDown();
        }).start();

        new Thread(() -> {
            server.perform3();
            latch.countDown();
        }).start();

        if (latch.await(3, TimeUnit.SECONDS)) {
            throw new Exception("perform2 and perform3 executed!");
        }
    }
}