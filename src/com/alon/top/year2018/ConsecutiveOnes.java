package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsecutiveOnes implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new ConsecutiveOnes()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        long n = Long.parseLong(matcher.group(1));
        int k = Integer.parseInt(matcher.group(2));
        long expected = Long.parseLong(matcher.group(3));
//        System.out.println("expected " + Long.toBinaryString(expected));
        long actual = get(n, k);
        util.assertEquals(expected, actual);
    }

    private long get(long n, int k) {
        long min = n;
        int i = 0;
        while (!hasConsecutive(n, k)) {
            n = turnBitOn(n, i);
            i++;
        }

        // clear trailing
        while (isBitSet(n, i) && i < 63) {
            i++;
        }
        i--;
        i -= k;
        while (i >= 0) {
            long temp = turnBitOff(n, i);
            if (temp >= min) {
                n = temp;
            }
            i--;
        }


//        System.out.println("actual   " + Long.toBinaryString(n));
//        System.out.println("min      " + Long.toBinaryString(min));
        return n;
    }

    private boolean hasConsecutive(long n, int k) {
        int consecutive = 0;
        for (int i = 0; i < 63; i++) {
            if (isBitSet(n, i)) {
                consecutive++;
                if (consecutive >= k) {
                    return true;
                }
            } else {
                consecutive = 0;
            }
        }
        return false;
    }

    private boolean isBitSet(long n, int i) {
        long mask = 1L << i;
        return (mask & n) != 0;
    }

    private long turnBitOn(long n, int i) {
        long mask = 1L << i;
        return mask | n;
    }

    private long turnBitOff(long n, int i) {
        long mask = 1L << i;
        return mask ^ n;
    }
}