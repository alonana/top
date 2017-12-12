package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrAndSum implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new OrAndSum()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, \\{(.*)}\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        long[] pairOr = util.getLongs(matcher.group(1));
        long[] pairSum = util.getLongs(matcher.group(2));
        String expected = matcher.group(3);
        String actual = isPossible(pairOr, pairSum);
        util.assertEquals(expected, actual);
    }

    private String isPossible(long[] pairOr, long[] pairSum) {
        if (check(pairOr, pairSum)) {
            return "Possible";
        }
        return "Impossible";
    }

    private boolean check(long[] pairOr, long[] pairSum) {
        boolean carry = false;
        for (int bit = 0; bit < 63; bit++) {
            long mask = 1L << bit;
            for (int element = 0; element < pairOr.length; element++) {
                boolean or = (pairOr[element] & mask) != 0;
                boolean sum = (pairSum[element] & mask) != 0;
                System.out.println("or " + or + " sum " + sum + " carry " + carry);
                if (!carry && sum && !or || carry && !sum && !or) {
                    return false;
                }
                carry = (!carry && !sum && or) || (carry && !sum && or) || (carry && sum && or);
            }
        }
        return true;
    }
}