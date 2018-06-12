package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArithmeticSequenceDiv1 implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new ArithmeticSequenceDiv1()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] n = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = findMinCost(n);
        util.assertEquals(expected, actual);
    }


    private int findMinCost(int n[]) {
        if (n.length < 3) {
            return 0;
        }

        int diffs = 0;
        for (int i = 1; i < n.length; i++) {
            diffs += n[i] - n[i - 1];
        }

        diffs = diffs / (n.length - 1);

        System.out.println("avg diff "+diffs);

        int best = check(n, diffs - 1);
        best = Math.min(best, check(n, diffs));
        best = Math.min(best, check(n, diffs + 1));
        return best;
    }

    private int check(int n[], int diff) {
        int[] m = new int[n.length];
        m[0] = n[0];
        for (int i = 1; i < n.length; i++) {
            m[i] = m[i - 1] + diff;
        }

        int cost = 0;

        for (int i = 0; i < n.length; i++) {
            cost += Math.abs(n[i] - m[i]);
        }

        return cost;
    }
}