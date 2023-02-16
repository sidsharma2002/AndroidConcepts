package com.example.androidconcepts.jcip.chapter2_threadSafety.sync;

public class SyncServer {
    public synchronized void perform1() {
        while (true) {
            // no op
        }
    }

    public synchronized void perform2() {
        System.out.println("perform2");
    }

    public synchronized void perform3() {
        System.out.println("perform3");
    }
}
