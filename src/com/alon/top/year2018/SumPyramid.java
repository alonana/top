package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SumPyramid implements InterfaceTopTest {

    private static final int MOD = (int) (1e9 + 7);

    public static void main(String[] args) throws Exception {
        new TopUtil(new SumPyramid()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int levels = Integer.parseInt(matcher.group(1));
        int top = Integer.parseInt(matcher.group(2));
        int expected = Integer.parseInt(matcher.group(3));
        int actual = countPyramids(levels, top);
        util.assertEquals(expected, actual);
    }

    private int countPyramids(int levels, int top) {
        int[] upperLevel = new int[]{top};
        return recursiveCount(levels, upperLevel);
    }

    private int recursiveCount(int levels, int[] upperLevel) {
        if (levels == 1) {
            return 1;
        }

        long counter = 0;
        int[] nextLevel = new int[upperLevel.length + 1];

        for (int step = 0; step <= upperLevel[0]; step++) {
            boolean valid = true;
            nextLevel[0] = upperLevel[0] - step;
            for (int i = 0; i < upperLevel.length; i++) {
                nextLevel[i + 1] = upperLevel[i] - nextLevel[i];
                if (nextLevel[i + 1] < 0) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                counter = (counter + recursiveCount(levels - 1, nextLevel)) % MOD;
            }
        }

        return (int) counter;
    }
}