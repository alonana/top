package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RangeEncoding implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new RangeEncoding()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] arr = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        double actual = minRanges(arr);
        util.assertEquals(expected, actual);
    }

    private int minRanges(int[] arr) {
        int amount = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                amount++;
            }
        }
        return amount;
    }
}
