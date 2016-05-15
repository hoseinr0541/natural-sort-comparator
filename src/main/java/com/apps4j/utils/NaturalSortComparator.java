package com.apps4j.utils;

import java.util.Comparator;

public class NaturalSortComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return new AlphanumComparator().compare(o1, o2);
    }
}
