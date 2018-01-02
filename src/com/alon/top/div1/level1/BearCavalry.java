package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BearCavalry implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new BearCavalry()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, \\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int warriors[] = util.getInts(matcher.group(1));
        int horses[] = util.getInts(matcher.group(2));
        int expected = Integer.parseInt(matcher.group(3));
        int actual = countAssignments(warriors, horses);
        util.assertEquals(expected, actual);
    }

    private static final int MOD = (int) (1e9 + 7);

    private int countAssignments(int[] warriors, int[] horses) {
        int n = warriors.length;
        int otherWarriors[] = new int[n - 1];
        System.arraycopy(warriors, 1, otherWarriors, 0, n - 1);

        int result = 0;
        for (int firstWarriorHorseIndex = 0; firstWarriorHorseIndex < n; firstWarriorHorseIndex++) {
            int firstUnit = warriors[0] * horses[firstWarriorHorseIndex];

            int otherHorses[] = new int[n - 1];
            int j = 0;
            for (int i = 0; i < n; i++) {
                if (i == firstWarriorHorseIndex) {
                    continue;
                }
                otherHorses[j] = horses[i];
                j++;
            }

            result = (result + findWays(firstUnit, otherWarriors, otherHorses)) % MOD;
        }

        return result;
    }

    private int findWays(int firstUnit, int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);

        int n = a.length;
        long result = 1;

        int used = 0;
        for (int i = n - 1; i >= 0; i--) {
            int available = 0;
            for (int j = used; j < n; j++) {
                if (a[i] * b[j] < firstUnit) {
                    available++;
                }
            }
            if (available == 0) {
                return 0;
            }
            used++;
            result = (result * available) % MOD;
        }
        return (int) result;
    }
}