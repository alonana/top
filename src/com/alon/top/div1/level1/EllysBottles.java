package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EllysBottles implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new EllysBottles()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, (\\d+)\\s+(\\S+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] bottles = util.getInts(matcher.group(1));
        int k = Integer.parseInt(matcher.group(2));
        double expected = Double.parseDouble(matcher.group(3));
        double actual = getVolume(bottles, k);
        util.assertEquals(expected, actual);
    }

    private double getVolume(int[] b, int k) {
        double bottles[] = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            bottles[i] = b[i];
        }
        double last = 0;
        for (int i = 0; i < k; i++) {
            if (isAllIdentical(bottles)) {
                return bottles[0];
            }
            last = iterateAndChange(bottles);
        }
        return last;
    }

    private boolean isAllIdentical(double[] bottles) {
        for (int i = 1; i < bottles.length; i++) {
            if (bottles[0] != bottles[i]) {
                return false;
            }
        }
        return true;
    }

    private double iterateAndChange(double[] bottles) {
        int min = getMinIndex(bottles);
        int max = getMaxIndex(bottles);
        double average = (bottles[min] + bottles[max]) / 2;
        bottles[min] = average;
        bottles[max] = average;
        return average;
    }

    private int getMinIndex(double[] bottles) {
        int index = 0;
        double min = bottles[0];
        for (int i = 1; i < bottles.length; i++) {
            if (min > bottles[i]) {
                min = bottles[i];
                index = i;
            }
        }
        return index;
    }

    private int getMaxIndex(double[] bottles) {
        int index = 0;
        double max = bottles[0];
        for (int i = 1; i < bottles.length; i++) {
            if (max < bottles[i]) {
                max = bottles[i];
                index = i;
            }
        }
        return index;
    }

}
