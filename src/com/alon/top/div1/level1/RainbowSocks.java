package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainbowSocks implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new RainbowSocks()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, (\\d+)\\s+(\\S+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] socks = util.getInts(matcher.group(1));
        int colorDiff = Integer.parseInt(matcher.group(2));
        double expected = Double.parseDouble(matcher.group(3));
        double actual = getPairProb(socks, colorDiff);
        util.assertEquals(expected, actual);
    }

    private double getPairProb(int[] socks, int colorDiff) {
        int total = 0;
        int accepted = 0;
        for (int i = 0; i < socks.length - 1; i++) {
            for (int j = i; j < socks.length; j++) {
                total++;
                if (Math.abs(socks[i] - socks[j]) <= colorDiff) {
                    accepted++;
                }
            }
        }
        return (double) accepted / total;
    }
}