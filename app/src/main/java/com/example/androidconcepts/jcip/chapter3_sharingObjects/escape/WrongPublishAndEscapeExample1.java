package com.example.androidconcepts.jcip.chapter3_sharingObjects.escape;

import java.util.HashSet;
import java.util.Set;

public class WrongPublishAndEscapeExample1 {
    // thread-B might see this to be null even when initialize() is called on thread-A
    // due to visibility issues in discussed visibility package.
    public Set<Integer> secrets;

    public void initialize() { // acts as setter method
        secrets = new HashSet<>();
    }

    private final String[] states = new String[]{"1", "2"};

    // states array escaped and can be mutated by the caller.
    public String[] getStates() {
        return states;
    }
}
