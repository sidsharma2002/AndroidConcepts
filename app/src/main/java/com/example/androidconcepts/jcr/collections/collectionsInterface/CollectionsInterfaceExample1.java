package com.example.androidconcepts.jcr.collections.collectionsInterface;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionsInterfaceExample1 {

    private Collection<String> stringCollection = new ArrayList<>();

    public Collection<String> getStringCollection() {
        return stringCollection;
    }

    public void iterableExecute() {
        Iterable<String> stringIterable = stringCollection;

        // can only be looped but cannot access index using get()
        int counter = 0;
        for (String s : stringIterable) {
            System.out.println("iteration " + counter + " : " + s);
            counter++;
        }
    }
}
