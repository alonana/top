package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopXorer implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new TopXorer()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] x = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = maximalRating(x);
        util.assertEquals(expected, actual);
    }

    private int maximalRating(int[] x) {
        Arrays.sort(x);
        int[] rates = new int[x.length];
        for (int bit = 0; bit < 32; bit++) {
            assignValues(x, rates, bit);
        }
        int result = 0;
        for (int rate : rates) {
            result = result ^ rate;
        }
        return result;
    }

    private void assignValues(int[] x, int[] rates, int bit) {
        for (int i = x.length - 1; i >= 0; i--) {
            if (canAssign(x[i], rates[i], bit)) {
                rates[i] = setBit(rates[i], bit);
                return;
            }
        }
    }

    private int setBit(int n, int bit) {
        int mask = 1 << (31 - bit);
        return n | mask;
    }

    private boolean canAssign(int limit, int rate, int bit) {
        int diff = limit - rate;
        int required = setBit(0, bit);
        int compare = Integer.compareUnsigned(diff, required);
        return compare >= 0;
    }
}