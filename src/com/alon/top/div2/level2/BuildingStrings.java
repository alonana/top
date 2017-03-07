package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildingStrings {
    public static void main(String[] args) throws Exception {
        new BuildingStrings().check();
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

    private void print(String s) {
        System.out.println(s);
        System.out.flush();
        System.err.flush();
    }

    private void checkLine(String line) throws Exception {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        print("checking " + line);

        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        int K = Integer.parseInt(matcher.group(1));
        String[] strings = findAny(K);
        int actual = calc(strings);
        if (actual != K) {
            throw new Exception("expected " + K + " actual " + actual);
        }
    }

    private int calc(String[] strings) {
        print(Arrays.toString(strings));
        int sum = 0;
        for (String s : strings) {
            HashSet<Character> chars = new HashSet<>();
            for (Character c : s.toCharArray()) {
                chars.add(c);
            }
            sum += s.length() * chars.size();
        }
        return sum;
    }

    private String[] findAny(int K) throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        while (K >= 1300) {
            strings.add("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx");
            K -= 1300;
        }
        while (K != 0) {
//            print("current K=" + K);
            boolean divided = false;
            for (int i = 50; i > 0; i--) {
                if (K % i == 0) {
                    int j = getCharsAmount(K, i);
                    strings.add(createString(i, j));
                    K -= i * j;
                    divided = true;
                    break;
                }
            }
            if (!divided) {
                int i = 50;
                int j = getCharsAmount(K, i);
                strings.add(createString(i, j));
                K -= i * j;
            }
        }
        if (strings.size() > 50) {
            throw new Exception("Ahhh");
        }
        return strings.toArray(new String[strings.size()]);
    }

    private int getCharsAmount(int K, int i) {
        int j = K / i;
        if (j > 26) {
            j = 26;
        }
        if (j>i){
            j = i;
        }
        return j;
    }

    private String createString(int length, int chars) {
//        print("create " + length + "," + chars);
        String all = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx";
        String a = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        return all.substring(0, chars) + a.substring(0, length - chars);
    }
}
