package com.alon.top.div2;

import com.alon.top.InterfaceTopTest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class TopUtil {
    private InterfaceTopTest testClass;

    public TopUtil(InterfaceTopTest testClass) {
        this.testClass = testClass;
    }

    public void check() throws Exception {
        String resourceName = testClass.getClass().getName().replace("com.alon.top.", "").replace(".", "/") + ".txt";
        InputStream in = testClass.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (in == null) {
            throw new Exception("resource not found " + resourceName);
        }
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            print("checking line " + reader.getLineNumber() + ":" + line);
            testClass.checkLine(line, this);
        }
    }

    private static void print(Object o) {
        System.out.println(o);
        flush();
    }

    public int[] getInts(String s) {
        s = s.replace(" ", "");
        String numbers[] = s.split(",");
        if (numbers.length == 1 && numbers[0].isEmpty()) {
            return new int[]{};
        }
        int[] ints = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            ints[i] = Integer.parseInt(numbers[i]);
        }
        return ints;
    }

    public double[] getDoubles(String s) {
        s = s.replace(" ", "");
        String numbers[] = s.split(",");
        if (numbers.length == 1 && numbers[0].isEmpty()) {
            return new double[]{};
        }
        double[] doubles = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            doubles[i] = Double.parseDouble(numbers[i]);
        }
        return doubles;
    }

    public long[] getLongs(String s) {
        s = s.replace(" ", "");
        String numbers[] = s.split(",");
        if (numbers.length == 1 && numbers[0].isEmpty()) {
            return new long[]{};
        }
        long[] longs = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            longs[i] = Integer.parseInt(numbers[i]);
        }
        return longs;
    }

    public String[] getStrings(String s) {
        if (s.isEmpty()) {
            return new String[0];
        }
        s = s.trim();
        String strings[] = s.split(",");
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
            strings[i] = strings[i].substring(1, strings[i].length() - 1);
        }
        return strings;
    }

    public void assertEquals(int expected, int actual) throws Exception {
        flush();
        if (expected != actual) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    public void assertEquals(long expected, long actual) throws Exception {
        flush();
        if (expected != actual) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    public void assertEquals(double expected, double actual) throws Exception {
        flush();
        if (Math.abs(expected - actual) > 0.0001) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    public void assertEquals(boolean expected, boolean actual) throws Exception {
        flush();
        if (expected != actual) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    public void assertEquals(String expected, String actual) throws Exception {
        flush();
        if (!expected.equals(actual)) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private static void flush() {
        System.out.flush();
        System.err.flush();
    }

    public void assertEquals(int[] expected, int[] actual) throws Exception {
        flush();
        if (!Arrays.equals(expected, actual)) {
            throw new Exception("expected " + Arrays.toString(expected) + " actual " + Arrays.toString(actual));
        }
    }

    public void assertEquals(double[] expected, double[] actual) throws Exception {
        flush();
        if (!Arrays.equals(expected, actual)) {
            throw new Exception("expected " + Arrays.toString(expected) + " actual " + Arrays.toString(actual));
        }
    }

    public void assertEquals(String[] expected, String[] actual) throws Exception {
        flush();
        if (!Arrays.equals(expected, actual)) {
            throw new Exception("expected " + Arrays.toString(expected) + " actual " + Arrays.toString(actual));
        }
    }

    public void assertEqualsPairs(int[] expected, int[] actual) throws Exception {
        flush();

        LinkedList<String> expectedPairs = createPairs(expected);
        LinkedList<String> actualPairs = createPairs(actual);

        if (!expectedPairs.equals(actualPairs)) {
            throw new Exception("expected " + expectedPairs + " actual " + actualPairs);
        }
    }

    private LinkedList<String> createPairs(int[] ints) {
        LinkedList<String> pairs = new LinkedList<>();
        for (int i = 0; i < ints.length; i++) {
            pairs.add(ints[i] + "-" + ints[++i]);
        }
        pairs.sort(Comparator.naturalOrder());
        return pairs;
    }
}
