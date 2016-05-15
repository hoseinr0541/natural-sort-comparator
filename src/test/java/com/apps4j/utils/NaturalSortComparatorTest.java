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
    public void getChunkFromZeroTest() {
        String str = "String1111";
        String chunk = new AlphanumComparator().getChunk(str, str.length(), 0);
        Assert.assertEquals("String", chunk);
    }

    @Test
    public void getChunkFromMid1Test() {
        String str = "String1111";
        String chunk = new AlphanumComparator().getChunk(str, str.length(), 6);
        Assert.assertEquals("1111", chunk);
    }

    @Test
    public void getChunkFromMid2Test() {
        String str = "String1111";
        String chunk = new AlphanumComparator().getChunk(str, str.length(), 5);
        Assert.assertEquals("g", chunk);
    }

    @Test
    public void getChunkFromMid3Test() {
        String str = "1111String";
        String chunk = new AlphanumComparator().getChunk(str, str.length(), 4);
        Assert.assertEquals("String", chunk);
    }

    @Test
    public void getChunkFromEnd() {
        String str = "String1111";
        String chunk = new AlphanumComparator().getChunk(str, str.length(), 9);
        Assert.assertEquals("1", chunk);
    }

    @Test
    public void simpleSortTest() {
        List<String> sortedList = createAndSort("c", "b", "d");
        Assert.assertEquals(createList("b", "c", "d"), sortedList);
    }

    @Test
    public void sortBigAndSmallLettersOrderTest() {
        List<String> sortedList = createAndSort("a", "A");
        Assert.assertEquals(createList("A", "a"), sortedList);
    }

    @Test
    public void simpleOrderingOfNumbers1Test() {
        List<String> sortedList = createAndSort("a111", "a23");
        Assert.assertEquals(createList("a23", "a111"), sortedList);
    }

    @Test
    public void simpleOrderingOfNumbers2Test() {
        List<String> sortedList = createAndSort("a111", "A23");
        Assert.assertEquals(createList("A23", "a111"), sortedList);
    }

    @Test
    public void numberOrder1Test() {
        List<String> sortedList = createAndSort("11-112", "11-111");
        Assert.assertEquals(createList("11-111", "11-112"), sortedList);
    }

    @Test
    public void utfSymbolsTest() {
        List<String> sortedList = createAndSort("11Кир", "11Киа");
        Assert.assertEquals(createList("11Киа", "11Кир"), sortedList);
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
