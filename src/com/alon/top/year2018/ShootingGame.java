package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShootingGame implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new ShootingGame()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+)\\s+(\\S+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int p = Integer.parseInt(matcher.group(1));
        double expected = Double.parseDouble(matcher.group(2));
        double actual = findProbability(p);
        util.assertEquals(expected, actual);
    }

    private double findProbability(int p) {
        double a = (double) p / 1E6;
        if (a > 0.5) {
            return -1;
        }
        if (a == 0.5) {
            return 1;
        }
        double bMax = 1;
        double bMin = 0;
        double bMid = 0.5;
        while (bMid - bMin > 1E-6) {
            double result = calculateAWin(a, bMid);
            if (result == 0.5) {
                return bMid;
            }
            if (result < 0.5) {
                // b is too big
                bMax = bMid;
            } else {
                // b is too small
                bMin = bMid;
            }
            bMid = (bMin + bMax) / 2;
        }
        return bMid;
    }

    private double calculateAWin(double a, double b) {
        double result = (1 - a) * (1 - b) * a;
        for (int i = 0; i < 100; i++) {
            result += a;
            result *= (1 - a) * (1 - b);
        }
        result += a;
        return result;
    }
}