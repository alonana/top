package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CombiningSlimes {
    private int seq =0;
    private HashMap<String, Integer> cache = new HashMap<>();

    public static void main(String[] args) throws Exception {
        new CombiningSlimes().check();
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
        if (line.trim().isEmpty()) {
            return;
        }
        System.out.println(line);
        Pattern pattern = Pattern.compile("\\s*\\{(.*)}\\s+(\\S+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("not match for " + line);
        }
        String numbers = matcher.group(1);
        String expected = matcher.group(2);

        numbers = numbers.replaceAll(" ", "");
        String[] numbersArray = numbers.split(",");
        int a[] = new int[numbersArray.length];
        for (int i = 0; i < numbersArray.length; i++) {
            a[i] = Integer.parseInt(numbersArray[i]);
        }
        int actual = maxMascots(a);
        if (actual == Integer.parseInt(expected)) {
            return;
        }

        throw new Exception("expected " + expected + " actual " + actual);
    }

    private int maxMascots(int[] a) {
        List<Integer> all = new ArrayList<>();
        for (int n : a) {
            all.add(n);
        }
        return recursiveMax(all);
    }

    private int recursiveMax(List<Integer> a) {
        if (a.size() == 2) {
            return a.get(0) * a.get(1);
        }

        Collections.sort(a);
        String key = a.toString();
        Integer cachedScore = cache.get(key);
        if (cachedScore != null) {
            return cachedScore;
        }

        int maxScore = 0;
        for (int i = 0; i < a.size() - 1; i++) {
            for (int j = i + 1; j < a.size(); j++) {
                List<Integer> b = new LinkedList<>(a);
                Integer iElement = a.get(i);
                Integer jElement = a.get(j);
                int newSlime = iElement + jElement;
                int score = iElement * jElement;
                b.remove(i);
                b.remove(j - 1);
                b.add(newSlime);

                score += recursiveMax(b);
                maxScore = Math.max(maxScore, score);
            }
        }
        cache.put(key, maxScore);
        seq++;
        if (seq>10000){
            System.out.println(cache.size());
            seq=0;
        }
        return maxScore;
    }
}
