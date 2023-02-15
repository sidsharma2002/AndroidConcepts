package com.example.androidconcepts.jcip.chapter2_threadSafety.intrinsicLocks;

/**
 * see HomeWidgetTest.java
 */
public class HomeWidget extends Widget {

    @Override
    synchronized void performTask() {
        System.out.println("HomeWidget performing task");
        super.performTask();
    }
}
