package com.example.androidconcepts.jcip.chapter2_threadSafety.intrinsicLocks;


public class Widget {
    synchronized void performTask() {
        System.out.println("Widget performing task");
    }
}
