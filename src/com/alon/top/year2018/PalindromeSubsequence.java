package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PalindromeSubsequence implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new PalindromeSubsequence()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\"\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String s = matcher.group(1);
        int[] expected = util.getInts(matcher.group(2));
        int[] actual = optimalPartition(s);
        util.assertEquals(expected, actual);
    }

    private int[] optimalPartition(String s) {
        int[] optimal = null;
        for (int i = 0; i < s.length() - 1; i++) {
            int solution[] = optimalPartition(s, i);
            if (optimal == null || max(optimal) < max(solution)) {
                optimal = solution;
            }
        }
        return optimal;
    }

    private int max(int[] a) {
        int max = a[0];
        for (int i : a) {
            max = Math.max(max, i);
        }
        return max;
    }

    private int[] optimalPartition(String s, int firstGroupStart) {
        int currentAssign = 1;
        int assignments[] = new int[s.length()];
        Integer firstGroupStartUse = firstGroupStart;
        while (true) {
            boolean allAssigned = assignPalindrom(s, assignments, currentAssign, firstGroupStart);
            if (allAssigned) {
                return assignments;
            }
            currentAssign++;
            firstGroupStart = 0;
        }
    }

    private boolean assignPalindrom(String s, int assignments[], int currentAssign, int firstGroupStart) {
        boolean allAssigned = true;
        int left = firstGroupStart;
        int right = s.length() - 1;
        while (left <= right) {
            if (assignments[left] != 0) {
                left++;
            } else if (assignments[right] != 0) {
                right--;
            } else if (s.charAt(left) == s.charAt(right)) {
                assignments[left] = currentAssign;
                assignments[right] = currentAssign;
                left++;
                right--;
            } else {
                right--;
                allAssigned = false;
            }
        }
        return allAssigned;
    }
}
