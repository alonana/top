package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongMansionDiv1 implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new AlphabetOrderDiv1()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, (\\d+), (\\d+), (\\d+), (\\d+)\\s+ (\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int t[] = util.getInts(matcher.group(1));
        int sX = Integer.parseInt(matcher.group(2));
        int sY = Integer.parseInt(matcher.group(3));
        int eX = Integer.parseInt(matcher.group(4));
        int eY = Integer.parseInt(matcher.group(5));
        long expected = Long.parseLong(matcher.group(6));
        long actual = minimalTime(t, sX, sY, eX, eY);
        util.assertEquals(expected, actual);
    }

    private long minimalTime(int[] t, int sX, int sY, int eX, int eY) {
        Long minimalCost = null;
        for (int slideRow = 0; slideRow < t.length; slideRow++) {
            long cost = getCost(t, sX, sY, eX, eY, slideRow);
            if (minimalCost == null || minimalCost > cost) {
                minimalCost = cost;
            }
        }
        return minimalCost;
    }

    private long getCost(int[] t, int sX, int sY, int eX, int eY, int slideRow) {
        long rowsChangeCost;
        if (sX == slideRow) {
            rowsChangeCost = t[sX];
        } else {
            int slideStart = Math.min(sX, slideRow);
            int slideEnd = Math.max(sX, slideRow);
            rowsChangeCost = 0;
            for (int i = slideStart; i < slideEnd; i++) {
                rowsChangeCost += t[i];
            }

            rowsChangeCost *= 2;
            if (sY == eY) {
                rowsChangeCost -= t[slideRow];
            }
        }

        long slideInRowCost;
        int moves = Math.abs(sY - eY);
        if (moves <= 1) {
            slideInRowCost = 0;
        } else {
            slideInRowCost = (long) (moves - 1) * t[slideRow];
        }

        return rowsChangeCost + slideInRowCost;
    }
}