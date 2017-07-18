package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterleavingParenthesis implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new InterleavingParenthesis()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\", \"(.*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }
        String s1 = matcher.group(1);
        String s2 = matcher.group(2);
        int expected = Integer.parseInt(matcher.group(3));
        int actual = countWays(s1, s2);
        util.assertEquals(expected, actual);
    }

    private int countWays(String s1, String s2) {
        Element[][] dp = new Element[s1.length() + 1][s2.length() + 1];
        for (int row = 0; row < s1.length() + 1; row++) {
            for (int col = 0; col < s2.length() + 1; col++) {
                dp[row][col] = new Element(row, col);
                if (row > 0) {
                    dp[row][col].add(dp[row - 1][col], s1.charAt(row - 1));
                }
                if (col > 0) {
                    dp[row][col].add(dp[row][col - 1], s2.charAt(col - 1));
                }

//                System.out.println(row + "," + col + ": " + dp[row][col]);
//                System.out.flush();
            }
        }
        return dp[s1.length()][s2.length()].getWays();
    }

    class Element {
        static final long MOD = (long) (1E9 + 7);

        private int balance;
        private long ways;
        private boolean valid;

        Element(int row, int col) {
            if (row == 0 && col == 0) {
                ways = 1;
                valid = true;
            } else {
                valid = false;
            }
        }

        void add(Element prev, char c) {
            if (!prev.valid) {
                return;
            }
            int balanceTemp = prev.balance;
            if (c == '(') {
                balanceTemp++;
            } else {
                balanceTemp--;
            }
            if (balanceTemp < 0) {
                return;
            }
            valid = true;
            balance = balanceTemp;
            ways = (prev.ways + ways) % MOD;
        }

        int getWays() {
            if (balance == 0) {
                return (int) ways;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "{" +
                    "balance=" + balance +
                    ", ways=" + ways +
                    ", valid=" + valid +
                    '}';
        }
    }
}