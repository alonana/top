package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsItASquare {

    public static void main(String[] args) throws Exception {
        new IsItASquare().check();
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
        System.out.println("checking:" + line);
        Pattern pattern = Pattern.compile("\\{(.*)}\\s*,\\s*\\{(.*)}\\s*\"(.*)\"");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("line not matched " + line);
        }
        int x[] = parseInts(matcher.group(1));
        int y[] = parseInts(matcher.group(2));
        String expected = matcher.group(3);

        String actual = isSquare(x, y);
        if (!expected.equals(actual)) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private int[] parseInts(String ints) {
        ints = ints.trim().replace(" ", "");
        String[] numbers = ints.split(",");
        int result[] = new int[numbers.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(numbers[i]);
        }
        return result;
    }

    private String isSquare(int[] x, int[] y) {
        if (checkSquare(x, y)) {
            return "It's a square";
        }
        return "Not a square";
    }

    private boolean checkSquare(int[] x, int[] y) {
        double d01 = distance(x[0], y[0], x[1], y[1]);
        double d02 = distance(x[0], y[0], x[2], y[2]);
        double d03 = distance(x[0], y[0], x[3], y[3]);
        double d12 = distance(x[1], y[1], x[2], y[2]);
        double d13 = distance(x[1], y[1], x[3], y[3]);
        double d23 = distance(x[2], y[2], x[3], y[3]);

        if (d01 == d02) {
            return d23 == d01 && d13 == d01 &&
                    d03 == d12;
        }
        if (d01 == d03) {
            return d01 == d12 && d01 == d23 &&
                    d02 == d13;
        }
        return d02 == d03 && d02 == d12 && d02 == d13 && d01 == d23;
    }

    private double distance(int x1, int y1, int x2, int y2) {
        double sqrt = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        System.out.println(sqrt);
        return sqrt;
    }
}