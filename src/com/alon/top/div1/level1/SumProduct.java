package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SumProduct implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new SumProduct()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, (\\d+), (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] amount = util.getInts(matcher.group(1));
        int blank1 = Integer.parseInt(matcher.group(2));
        int blank2 = Integer.parseInt(matcher.group(3));
        int expected = Integer.parseInt(matcher.group(4));
        int actual = findSum(amount, blank1, blank2);
        util.assertEquals(expected, actual);
    }

    private static final int MOD = 1000_000_007;
    private int[] amount;
    private int counter = 0;

    private int findSum(int[] amount, int blank1, int blank2) {
        this.amount = amount;
        return (int) recurse(blank1, blank2, 0, 0);
    }

    private long recurse(int blank1, int blank2, long number1, long number2) {
        counter++;
        if (counter > 100_000_000) {
            System.out.println(number1 + "," + number2);
            counter = 0;
        }

        if (blank1 > 0) {
            long sum = 0;
            for (int i = 0; i < amount.length; i++) {
                if (amount[i] == 0) {
                    continue;
                }
                amount[i]--;
                sum = (sum + recurse(blank1 - 1, blank2, number1 * 10 + i, number2)) % MOD;
                amount[i]++;
            }
            return sum;
        }
        if (blank2 > 0) {
            long sum = 0;
            for (int i = 0; i < amount.length; i++) {
                if (amount[i] == 0) {
                    continue;
                }
                amount[i]--;
                sum = (sum + recurse(blank1, blank2 - 1, number1, number2 * 10 + i)) % MOD;
                amount[i]++;
            }
            return sum;
        }
        return (number1 * number2) % MOD;
    }
}