package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EllysChocolates implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new EllysChocolates()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int P = Integer.parseInt(matcher.group(1));
        int K = Integer.parseInt(matcher.group(2));
        int N = Integer.parseInt(matcher.group(3));
        int expected = Integer.parseInt(matcher.group(4));
        int result = getCount(P, K, N);
        util.assertEquals(expected, result);
    }

    private int getCount(int P, int K, int N) {
        int total = 0;
        int wraps = 0;
        int chocolate = N / P;
        while (chocolate > 0) {
            total += chocolate;
            wraps += chocolate;
            chocolate = wraps / K;
            wraps = wraps % K;
        }
        return total;
    }
}