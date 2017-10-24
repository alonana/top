package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReverseAndIncrease implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new ReverseAndIncrease()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+)\\s+\"(\\S+)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        long s = Long.parseLong(matcher.group(1));
        long t = Long.parseLong(matcher.group(2));
        String expected = matcher.group(3);
        String actual = isPossible(s, t);
        util.assertEquals(expected, actual);
    }

    private String isPossible(long s, long t) {
        if (canConvert(s, t)) {
            return "Possible";
        }
        return "Impossible";
    }

    private boolean canConvert(long s, long t) {
        if (t >= s) {
            return true;
        }
        if (!isSameDigits(s, t)) {
            return false;
        }
        if (getDigits(s) == 1) {
            return false;
        }
        if (allDigitsNine(s)) {
            return false;
        }
        s = (long) (Math.pow(10, getDigits(s) - 1)) + 2;
        return s <= t;
    }

    private boolean allDigitsNine(long x) {
        while (x > 0) {
            long rem = x % 10;
            if (rem != 9) {
                return false;
            }
            x = x / 10;
        }
        return true;
    }

    private boolean isSameDigits(long s, long t) {
        return getDigits(s) == getDigits(t);
    }

    private int getDigits(long x) {
        int log = (int) Math.log10(x);
        return log + 1;
    }
}