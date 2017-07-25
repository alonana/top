package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CanidsSeesaw implements InterfaceTopTest {
    private static final int[] NOT_FOUND = new int[]{};

    private HashMap<String, int[]> cache;

    private int debugRun;
    private int[] wolf;
    private int[] fox;

    public static void main(String[] args) throws Exception {
        new TopUtil(new CanidsSeesaw()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)},\\s*\\{(.*)},\\s*(\\d+)\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] wolf = util.getInts(matcher.group(1));
        int[] fox = util.getInts(matcher.group(2));
        int k = Integer.parseInt(matcher.group(3));
        int[] expected = util.getInts(matcher.group(4));
        int[] actual = construct(wolf, fox, k);
        check(actual, k, expected);
    }

    private void check(int[] actual, int points, int[] expected) throws Exception {
        if (actual.length == 0 && expected.length == 0) {
            return;
        }
        int balance = 0;
        for (int i = 0; i < actual.length; i++) {
            int foxIndex = actual[i];
            int foxWeight = fox[foxIndex];
            int wolfWeight = wolf[i];
            balance += foxWeight;
            balance -= wolfWeight;
            if (balance > 0) {
                points--;
            }
        }
        if (points != 0) {
            throw new Exception("expected " + Arrays.toString(expected) + " actual " + Arrays.toString(actual));
        }
    }

    private int[] construct(int[] wolf, int[] fox, int k) {
        this.debugRun = 0;
        this.wolf = wolf;
        this.fox = fox;
        this.cache = new HashMap<>();
        int[] ints = recurseFind(0, 0, k);
        if (ints == null) {
            return new int[]{};
        }
        return ints;
    }

    private int[] recurseFind(int turn, int balance, int requiredPoints) {
        debugRun++;
        if (debugRun > 5000000) {
            debugRun = 0;
            System.out.println(cache.size() + " " + turn + " "+ Arrays.toString(fox));
        }

//        debug("starting turn " + turn + " balance " + balance + " required " + requiredPoints);
        if (turn == fox.length) {
            if (requiredPoints != 0) {
//                debug("FAILED");
                return null;
            }
//            debug("OK");
            return new int[]{};
        }

        String key = turn + " " + balance + " " + requiredPoints;
        int[] cached = cache.get(key);
        if (cached != null && cached != NOT_FOUND) {
            return cached;
        }

        int wolfWeight = wolf[turn];
        for (int i = 0; i < fox.length; i++) {
            int foxWeight = this.fox[i];
            if (foxWeight == 0) {
                continue;
            }
//            debug("turn " + turn + " wolf " + wolfWeight + " fox " + foxWeight);
            int newBalance = balance - wolfWeight + foxWeight;
            int newRequiredPoints = newBalance > 0 ? requiredPoints - 1 : requiredPoints;
            if (newRequiredPoints < 0) {
                continue;
            }
            this.fox[i] = 0;
            int[] result = recurseFind(turn + 1, newBalance, newRequiredPoints);
            this.fox[i] = foxWeight;
            if (result != null) {
                int[] updatedResult = new int[result.length + 1];
                updatedResult[0] = i;
                System.arraycopy(result, 0, updatedResult, 1, result.length);
                cache.put(key, updatedResult);
                return updatedResult;
            }
        }
        cache.put(key, NOT_FOUND);
        return null;
    }

    private void debug(String o) {
 //       System.out.println(o);
    }
}