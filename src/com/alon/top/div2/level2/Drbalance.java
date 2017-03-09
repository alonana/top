package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Drbalance {
    public static void main(String args[]) throws Exception {
        new Drbalance().check();
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

        Pattern pattern = Pattern.compile("\"(.*)\", (\\S+)\\s+(\\S+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("no match for " + line);
        }
        String s = matcher.group(1);
        String k = matcher.group(2);
        String expected = matcher.group(3);

        System.out.println("checking " + s + " " + k);
        int actual = lesscng(s, Integer.parseInt(k));
        if (actual != Integer.parseInt(expected)) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private int lesscng(String s, int k) {
        int[] balances = new int[s.length()];
        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+') {
                balance++;
            } else {
                balance--;
            }
            balances[i] = balance;
        }

        int negatives = 0;
        for (int b : balances) {
            if (b < 0) {
                negatives++;
            }
        }

        int steps = 0;
        int checkIndex = 0;
        while (negatives > k) {
            if (s.charAt(checkIndex) == '-') {
                steps++;
                for (int i = checkIndex; i < balances.length; i++) {
                    int prev = balances[i];
                    balances[i] += 2;
                    if (prev < 0 && balances[i] >= 0) {
                        negatives--;
                    }
                }
            }
            checkIndex++;
        }
        return steps;
    }
}