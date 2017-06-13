package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PowerEquation implements InterfaceTopTest {
    private static final int MOD = (int) (10e9 + 7);
    private HashSet<Integer> visited;

    public static void main(String[] args) throws Exception {
        new TopUtil(new PowerEquation()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int n = Integer.parseInt(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = count(n);
        util.assertEquals(expected, actual);
    }

    private int count(int n) {
        visited = new HashSet<>();
        long result = (long) n * n % MOD;
        for (int i = 2; i <= n; i++) {
            result = (result + getPowers(i, n)) % MOD;
        }
        return (int) result;
    }

    private long getPowers(int i, int n) {
        long count = n;
        if (visited.contains(i)) {
            return count;
        }
        for (int power = 2; power < n; power++) {
            long splitAlternatives = 0;
            for (int sections = 1; sections < power; sections++) {
                if (power % sections != 0) {
                    continue;
                }
                int sectionSize = power / sections;
                int section = (int) Math.pow(i, sectionSize);
                if (section > n) {
                    continue;
                }
                System.out.println(i + "^" + power + " = " + section + "^" + sections);
                splitAlternatives++;
                visited.add(section);
            }
            long contribution = splitAlternatives * (splitAlternatives + 1);
            count += contribution;
            if (splitAlternatives > 0) {
                System.out.println("alternatives " + splitAlternatives + " contribution " + contribution);
            }
        }
        return count;
    }
}
