package com.example.androidconcepts.jcr.collections.listInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListInterfaceExample1 {

    public List<String> getStringList_ArrayList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        return list;
    }

    public List<String> getStringList_UnmodifiableList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        return Collections.unmodifiableList(list);
    }
}
