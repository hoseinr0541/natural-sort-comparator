package com.apps4j.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(BlockJUnit4ClassRunner.class)
public class NaturalSortComparatorTest {

    @Test
    public void simpleSortTest() {
        List<String> sortedList = createAndSort("c", "b", "d");
        Assert.assertEquals(sortedList, createList("b", "c", "d"));
    }

    @Test
    public void sortBigAndSmallLettersOrderTest() {
        List<String> sortedList = createAndSort("a", "A");
        Assert.assertEquals(sortedList, createList("A", "a"));
    }

    @Test
    public void simpleOrderingOfNumbers1Test() {
        List<String> sortedList = createAndSort("a111", "a23");
        Assert.assertEquals(sortedList, createList("a23", "a111"));
    }

    @Test
    public void simpleOrderingOfNumbers2Test() {
        List<String> sortedList = createAndSort("a111", "A23");
        Assert.assertEquals(sortedList, createList("A23", "a111"));
    }

    @Test
    public void numberOrder1Test() {
        List<String> sortedList = createAndSort("11-112", "11-111");
        Assert.assertEquals(sortedList, createList("11-111", "11-112"));
    }

    @Test
    public void utfSymbolsTest() {
        List<String> sortedList = createAndSort("11Кир", "11Киа");
        Assert.assertEquals(sortedList, createList("11Киа", "11Кир"));
    }


    private List<String> createAndSort(String... values) {
        List<String> list = createList(values);
        Collections.sort(list, new NaturalSortComparator());
        return list;
    }

    private List<String> createList(String... values) {
        List<String> sortList = new ArrayList<String>(values.length);
        Collections.addAll(sortList, values);
        return sortList;
    }
}
