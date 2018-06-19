package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubarrayAverages implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new SubarrayAverages()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] arr = util.getInts(matcher.group(1));
        double[] expected = util.getDoubles(matcher.group(2));
        double[] actual = findBest(arr);
        util.assertEquals(expected, actual);
    }

    private double[] findBest(int[] arr) {
        double result[] = new double[arr.length];

        for (int i = 0; i < arr.length; i++) {

            Integer bestToIndex = null;
            double bestAvg = Double.MAX_VALUE;
            double sum = 0;
            int amount = 0;
            for (int j = i; j < arr.length; j++) {
                amount++;
                sum += arr[j];
                double currentAvg = sum / amount;
                if (currentAvg < bestAvg) {
                    bestAvg = currentAvg;
                    bestToIndex = j;
                }
            }
            for (int j = i; j <= bestToIndex; j++) {
                result[j] = bestAvg;
            }
            i = bestToIndex;
        }
        return result;
    }
}
