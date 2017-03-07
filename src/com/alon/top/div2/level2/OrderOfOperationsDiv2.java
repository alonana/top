package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderOfOperationsDiv2 {
    private HashMap<String, Integer> cache;

    public static void main(String[] args) throws Exception {
        new OrderOfOperationsDiv2().check();
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
        cache = new HashMap<>();
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        System.out.println("checking " + line);
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\S+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("not matched");
        }
        String instructions = matcher.group(1);
        String expected = matcher.group(2);
        instructions = instructions.replace("\"", "").replace(" ", "");
        String s[] = instructions.split(",");
        int actual = minTime(s);
        if (Integer.parseInt(expected) != actual) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private int minTime(String[] s) {
        String key = Arrays.toString(s);
        Integer cached = cache.get(key);
        if (cached != null) {
            return cached;
        }
        Integer minCost = null;
        for (String value : s) {
            String[] updatedS = update(s, value);
            int cost = findCost(value) + minTime(updatedS);
            if (minCost == null || cost < minCost) {
                minCost = cost;
            }
        }
        if (minCost == null) {
            minCost = 0;
        }
        cache.put(key, minCost);
        return minCost;
    }

    private String[] update(String[] s, String executed) {
        ArrayList<String> result = new ArrayList<>();
        for (String instruction : s) {
            StringBuilder updatedInstruction = new StringBuilder();
            for (int i = 0; i < executed.length(); i++) {
                if (executed.charAt(i) == '0') {
                    updatedInstruction.append(instruction.charAt(i));
                }
            }
            String updatedString = updatedInstruction.toString();
            if (updatedString.contains("1")) {
                result.add(updatedString);
            }
        }
        return result.toArray(new String[]{});
    }

    private int findCost(String instruction) {
        int newCells = 0;
        for (int i = 0; i < instruction.length(); i++) {
            if (instruction.charAt(i) == '1') {
                newCells++;
            }
        }
        return (int) Math.pow(newCells, 2);
    }
}