package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaximumRange implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new MaximumRange()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String s = matcher.group(1);
        int expected = Integer.parseInt(matcher.group(2));
        int actual = findMax(s);
        util.assertEquals(expected, actual);
    }

    private int findMax(String s) {
        int plus = 0;
        int minus = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+') {
                plus++;
            } else {
                minus++;
            }
        }
        return Math.max(plus, minus);
    }
}