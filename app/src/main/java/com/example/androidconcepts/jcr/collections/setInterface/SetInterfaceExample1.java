package com.example.androidconcepts.jcr.collections.setInterface;

import java.util.HashSet;
import java.util.Set;

public class SetInterfaceExample1 {

    public Set<String> getStringSetNumeric() {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("1");
        stringSet.add("3");
        stringSet.add("2");
        stringSet.add("5");
        stringSet.add("4");
        return stringSet;
    }

    public Set<String> getStringSetAlphaNumeric() {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("1b");
        stringSet.add("3a");
        stringSet.add("2c");
        stringSet.add("5d");
        stringSet.add("4e");
        return stringSet;
    }
}
