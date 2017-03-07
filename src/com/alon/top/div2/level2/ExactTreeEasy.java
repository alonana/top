package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExactTreeEasy {
    public static void main(String[] args) throws Exception {
        new ExactTreeEasy().check();
    }

    private void check() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(this.getClass().getSimpleName() + ".txt");
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            checkLine(line);
        }
    }

    private void checkLine(String line) throws Exception {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        System.out.println("checking " + line);
        System.out.flush();
        Pattern pattern = Pattern.compile("(\\d+), (\\d+).*\\{(.*)}");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        int n = Integer.parseInt(matcher.group(1));
        int m = Integer.parseInt(matcher.group(2));
        String expected = matcher.group(3);
        expected = expected.replace(" ", "");
        String[] split = expected.split(",");
        int expectedArray[] = new int[split.length];
        int i = 0;
        for (String s : split) {
            expectedArray[i++] = Integer.parseInt(s);
        }

        int[] actual = getTree(n, m);

        if (!Arrays.equals(expectedArray, actual)) {
            System.out.println("expected " + Arrays.toString(expectedArray) + " actual " + Arrays.toString(actual));
        }
    }

    private int[] getTree(int n, int m) {
        int edgesAmount = n - 1;
        int[] edges = new int[edgesAmount * 2];
        int limit = n - m + 1;
        System.out.println(limit);
        for (int edge = 0; edge < edgesAmount; edge++) {
            if (edge < limit) {
                edges[edge * 2] = edge;
                edges[edge * 2 + 1] = edge + 1;
            } else {
                edges[edge * 2] = 1;
                edges[edge * 2 + 1] = edge + 1;
            }
        }
        return edges;
    }
}