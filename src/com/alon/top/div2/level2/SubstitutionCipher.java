package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubstitutionCipher {
    public static void main(String[] args) throws Exception {
        new SubstitutionCipher().check();
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
        Pattern pattern = Pattern.compile(".*\"(.*)\".*\"(.*)\".*\"(.*)\".*\"(.*)\"");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        String a = matcher.group(1);
        String b = matcher.group(2);
        String y = matcher.group(3);
        String expected = matcher.group(4);

        System.out.println("checking " + a + " " + b + " " + y);
        String actual = decode(a, b, y);
        if (!expected.equals(actual)) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private String decode(String a, String b, String y) {
        HashMap<Character, Character> sub = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            sub.put(b.charAt(i), a.charAt(i));
        }
        if (sub.size() == 'Z' - 'A') {
            char k = 'x';
            char v = 'x';
            for (char c = 'A'; c <= 'Z'; c++) {
                if (!sub.containsKey(c)) {
                    k = c;
                }
                if (!sub.containsValue(c)) {
                    v = c;
                }
            }
            sub.put(k, v);
        }

        StringBuilder clear = new StringBuilder();
        for (int i = 0; i < y.length(); i++) {
            Character c = y.charAt(i);
            Character replace = sub.get(c);
            if (replace == null) {
                System.out.println("not found: " + c);
                return "";
            }
            clear.append(replace);
        }
        return clear.toString();
    }
}