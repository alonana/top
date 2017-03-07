package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MutaliskEasy {
    private static final int HIT1 = -9;
    private static final int HIT2 = -3;
    private static final int HIT3 = -1;

    private HashMap<String, Integer> cache;

    public static void main(String[] args) throws Exception {
        new MutaliskEasy().check();
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
        print("checking " + line);

        Pattern pattern = Pattern.compile("\\{(.*)}\\s+(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }

        int hp[] = getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));

        int actual = minimalAttacks(hp);
        if (actual != expected) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private int[] getInts(String a) {
        a = a.replace(" ", "");
        String weights[] = a.split(",");
        int[] aInts = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            aInts[i] = Integer.parseInt(weights[i]);
        }
        return aInts;
    }

    private void print(String s) {
        System.out.println(s);
        System.out.flush();
        System.err.flush();
    }

    private int minimalAttacks(int[] x) {
        cache = new HashMap<>();
        List<Integer> hp = new LinkedList<>();
        for (int i : x) {
            hp.add(i);
        }
        Collections.sort(hp);
        return findMinWithCache(hp);
    }

    private int findMinWithCache(List<Integer> hp) {
        if (hp.isEmpty()) {
            return 0;
        }
        String key = hp.toString();
        Integer cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        int min = findMin(hp);
        cache.put(key, min);
//        print("key " + key + " value " + min);
        return min;
    }

    private int findMin(List<Integer> hp) {
        if (hp.size() == 1) {
            hp = createList(hp, 0, HIT1);
            return 1 + findMinWithCache(hp);
        }
        if (hp.size() == 2) {
            List<Integer> hp1 = createList(hp, 0, HIT1, 1, HIT2);
            List<Integer> hp2 = createList(hp, 1, HIT1, 0, HIT2);
            return 1 + Math.min(findMinWithCache(hp1), findMinWithCache(hp2));
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < hp.size(); i++) {
            for (int j = 0; j < hp.size(); j++) {
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < hp.size(); k++) {
                    if (i == k || j == k) {
                        continue;
                    }
                    List<Integer> hp1 = createList(hp, i, HIT1, j, HIT2, k, HIT3);
                    min = Math.min(min, findMinWithCache(hp1));
                }
            }
        }
        return 1 + min;
    }

    private List<Integer> createList(List<Integer> hp, int... hits) {
        List<Integer> newHp = new LinkedList<>(hp);
        for (int i = 0; i < hits.length; i++) {
            int pos = hits[i];
            i++;
            int hit = hits[i];
            int points = newHp.get(pos);
            points += hit;
            newHp.remove(pos);
            newHp.add(pos, points);
        }

        newHp.removeIf(i -> i <= 0);
        Collections.sort(newHp);
        return newHp;
    }
}
