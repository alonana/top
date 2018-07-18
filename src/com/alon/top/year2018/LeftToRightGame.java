package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeftToRightGame implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new LeftToRightGame()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+)\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int length = Integer.parseInt(matcher.group(1));
        int divsor = Integer.parseInt(matcher.group(2));
        String expected = matcher.group(3);
        String actual = whoWins(length, divsor);
        util.assertEquals(expected, actual);
    }

    String whoWins(int length, int divisor) {
        if (isDividable(length, divisor)) {
            return "Bob";
        }

        return "Alice";
    }

    // Alice is first
    boolean isDividable(int length, int divisor) {
        if (divisor == 1) {
            return true;
        }
        int digits = (int) Math.log(divisor);
        if (digits > length) {
            return false;
        }

        boolean bobLast = (length % 2 == 0);
        if (!bobLast) {
            return false;
        }
        return divisor <= 10;
    }
}