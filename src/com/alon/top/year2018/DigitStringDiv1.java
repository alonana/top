package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitStringDiv1 implements InterfaceTopTest {

    private String S;
    private int X;
    private HashMap<String, Long> cache;

    public static void main(String[] args) throws Exception {
        new TopUtil(new DigitStringDiv1()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\", (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String S = matcher.group(1);
        int X = Integer.parseInt(matcher.group(2));
        long expected = Long.parseLong(matcher.group(3));
        long actual = count(S, X);
        util.assertEquals(expected, actual);
    }

    private long count(String S, int X) {
        this.S = S;
        this.X = X;
        this.cache = new HashMap<>();
        return recursiveCount(0, 0);
    }

    long recursiveCount(int nextIndex, long number) {
        if (nextIndex >= S.length()) {
            if (number > X) {
                return 1;
            }
            return 0;
        }

        String key = nextIndex + ":" + number;
        debug(key + " " + cache.size());
        Long cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        if (number > X) {
            debug("shortcut");
            int remaining = S.length() - nextIndex;
            return (long) Math.pow(2, remaining);
        }

        int digit = S.charAt(nextIndex) - '0';
        nextIndex++;
        long result = recursiveCount(nextIndex, number);
        if (number != 0 || digit != 0) {
            result += recursiveCount(nextIndex, number * 10 + digit);
        }

        cache.put(key, result);
        return result;
    }

    private void debug(String s) {
        System.out.println(s);
    }
}