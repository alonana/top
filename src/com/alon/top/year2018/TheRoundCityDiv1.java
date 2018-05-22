package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TheRoundCityDiv1 implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new TheRoundCityDiv1()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int r = Integer.parseInt(matcher.group(1));
        long expected = Long.parseLong(matcher.group(2));
        long actual = find(r);
        util.assertEquals(expected, actual);
    }

    private long find(int r) {
        long visible = 0;
        for (int col = 0; col <= r; col++) {
            for (int row = 1; row <= r; row++) {
                double distance = Math.sqrt((long) row * row + (long) col * col);
                if (distance > r) {
                    continue;
                }
                if (isVisible(row, col)) {
                    visible++;
                }
            }
        }
        return visible * 4;
    }

    private boolean isVisible(int row, int col) {
        int limit = Math.max(row, col);
        for (int i = 2; i <= limit; i++) {
            if (row % i == 0 && col % i == 0) {
//                System.out.println("NOT "+ row + ","+col);
                return false;
            }
        }
//        System.out.println("VIS "+ row + ","+col);
        return true;
    }
}