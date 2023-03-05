package com.example.androidconcepts.jcip.chapter3_sharingObjects.visibility;

import org.junit.Test;

public class VisibilityExampleTest {

    @Test
    public void noVisibilityExampleTest() {
        NoVisibilityExample SUT = new NoVisibilityExample();
        SUT.startExecution();
    }

    @Test
    public void correctVisibilityExampleTest() {
        NoVisibilityExample2 SUT = new NoVisibilityExample2();
        SUT.startExecution();
    }
}