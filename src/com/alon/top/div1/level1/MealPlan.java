package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealPlan implements InterfaceTopTest {
    private HashSet<String> located;

    public static void main(String[] args) throws Exception {
        new TopUtil(new MealPlan()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, \\{(.*)}, \\{(.*)}, \\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] A = util.getInts(matcher.group(1));
        int[] B = util.getInts(matcher.group(2));
        int[] C = util.getInts(matcher.group(3));
        int[] D = util.getInts(matcher.group(4));

        long expected = Long.parseLong(matcher.group(5));
        long actual = countDistinct(A, B, C, D);
        util.assertEquals(expected, actual);
    }

    private long countDistinct(int[] A, int[] B, int[] C, int[] D) throws Exception {
        located = new HashSet<>();

        Arrays.sort(A);
        Arrays.sort(B);
        Arrays.sort(C);
        Arrays.sort(D);

        long amount = 0;
        for (int a : A) {
            for (int b : B) {

                if (isSkip(A, B, a, b)) continue;

                for (int c : C) {

                    if (isSkip(A, C, a, c)) continue;
                    if (isSkip(B, C, b, c)) continue;

                    for (int d : D) {

                        if (isSkip(A, D, a, d)) continue;
                        if (isSkip(B, D, b, d)) continue;
                        if (isSkip(C, D, c, d)) continue;

                        int[] l = new int[]{a, b, c, d};
                        println(Arrays.toString(l));
                        Arrays.sort(l);
                        String k = Arrays.toString(l);
                        if (located.contains(k)) throw new Exception("located " + k);
                        located.add(k);
                        amount++;
                    }
                }
            }
        }
        return amount;
    }

    private boolean isSkip(int[] A, int[] B, int a, int b) {
        if (b < a && exists(A, b) && exists(B, a)) {
            println("skipping " + a + "," + b);
            return true;
        }
        return false;
    }

    private boolean exists(int[] A, int b) {
        int i = Arrays.binarySearch(A, b);
        return i >= 0;
    }

    private void println(String x) {
        System.out.println(x);
    }
}