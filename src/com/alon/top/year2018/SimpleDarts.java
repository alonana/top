package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDarts implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new SimpleDarts()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int F = Integer.parseInt(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = highestScore(F);
        util.assertEquals(expected, actual);
    }

    private int highestScore(int F) {
        int bull1 = 25;
        int bull2 = 50;
        if (F == 1) {
            return 3 + bull1 + bull2;
        }
        if (F == 2) {
            return 3 * 2 + bull1 + bull2;
        }
        int hits[] = new int[]
                {
                        3 * F,
                        3 * (F - 1),
                        3 * (F - 2),
                        bull1,
                        bull2
                };

        int total = 0;
        for (int i = 0; i < 3; i++) {
            int hit = getMaxIndex(hits);
            total += hits[hit];
            hits[hit] = 0;
        }

        return total;
    }

    private int getMaxIndex(int a[]) {
        int max = a[0];
        int maxIndex = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
