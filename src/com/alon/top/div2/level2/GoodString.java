package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoodString {
    public static void main(String args[]) throws Exception {
        new GoodString().check();
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
        System.out.println("checking line " + line);
        Matcher matcher = Pattern.compile("\"(.*)\"\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("line not matched");
        }
        String s = matcher.group(1);
        String expected = matcher.group(2);
        String actual = isGood(s);
        if (!expected.equals(actual)) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private String isGood(String s) throws Exception {
        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                balance++;
            } else {
                balance--;
            }
            if (balance < 0) {
                return "Bad";
            }
        }
        if (balance == 0) {
            return "Good";
        }
        return "Bad";
    }
}