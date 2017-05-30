package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParenthesisRemoval implements InterfaceTopTest {
    private static final int MOD = 1000000007;

    private HashMap<String, Integer> cache;
    private int calls;

    public static void main(String[] args) throws Exception {
        new TopUtil(new ParenthesisRemoval()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String s = matcher.group(1);
        int expected = Integer.parseInt(matcher.group(2));
        int actual = countWaysMath(s);
        util.assertEquals(expected, actual);
    }

    private int countWaysMath(String s) throws Exception {
        long result = 1;
        int close = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            if (s.charAt(i) == ')') {
                close++;
                result = (result * close) % MOD;
            } else {
                close--;
            }
        }
        return (int) result;
    }

    private int countWays(String s) throws Exception {
        cache = new HashMap<>();
        calls = 0;
        return countWaysRecurse(s);
    }

    private int countWaysRecurse(String s) throws Exception {
        if (s.length() == 0) {
            return 1;
        }
        Integer cached = cache.get(s);
        if (cached != null) {
            return cached;
        }

        calls++;
        if (calls > 100000) {
            calls = 0;
            debug("size " + cache.size() + " checking " + s);
        }
        int count = 0;
        int balance = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                balance--;
                count = (count + countWaysRecurse(getNewString(s, i))) % MOD;
                if (balance == 0) {
                    cache.put(s, count);
                    return count;
                }
            } else {
                balance++;
            }
        }
        throw new Exception("unbalanced " + s);
    }

    private void debug(String s) {
        System.out.println(s);
    }

    private String getNewString(String s, int position) {
        return s.substring(1, position) + s.substring(position + 1);
    }
}