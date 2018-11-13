package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindThePerfectTriangle implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new FindThePerfectTriangle()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+)\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int area = Integer.parseInt(matcher.group(1));
        int perimeter = Integer.parseInt(matcher.group(2));
        int[] expected = util.getInts(matcher.group(3));
        int[] actual = constructTriangle(area, perimeter);
        util.assertEquals(expected.length, actual.length);
    }

    int[] constructTriangle(int area, int perimeter) {
        int findArea = area * 2;
        for (int x2 = 0; x2 < perimeter; x2++) {
            for (int y2 = x2; y2 < perimeter; y2++) {

                for (int x3 = 0; x3 < perimeter; x3++) {
                    for (int y3 = 0; y3 < perimeter; y3++) {

                        double actualArea = Math.abs(x2 * y3 - x3 * y2);
                        if (actualArea != findArea) {
                            continue;
                        }

                        double actualPerimeter = pointsLength(0, 0, x2, y2);
                        if (actualPerimeter > perimeter) {
                            continue;
                        }
                        actualPerimeter += pointsLength(0, 0, x3, y3);
                        if (actualPerimeter > perimeter) {
                            continue;
                        }
                        actualPerimeter += pointsLength(x2, y2, x3, y3);
                        if (actualPerimeter != perimeter) {
                            continue;
                        }

                        return new int[]{0, 0, x2, y2, x3, y3};
                    }
                }


            }
        }

        return new int[0];
    }

    private double pointsLength(int x1, int y1, int x2, int y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt(x * x + y * y);
    }

}
