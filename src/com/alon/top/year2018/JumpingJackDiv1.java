package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JumpingJackDiv1 implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new JumpingJackDiv1()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int dist = Integer.parseInt(matcher.group(1));
        int k = Integer.parseInt(matcher.group(2));
        int n = Integer.parseInt(matcher.group(3));
        long expected = Long.parseLong(matcher.group(4));
        long actual = getLocationOfJack(dist, k, n);
        util.assertEquals(expected, actual);
    }

    private int getLocationOfJack(int dist, int k, int n) {
        n++;
        int total = 0;
        for (int day = 0; day < n; day++) {
            if (day % k != 0) {
                total += dist;
            }
        }
        return total;
    }
}