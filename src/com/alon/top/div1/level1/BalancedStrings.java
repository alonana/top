package com.alon.top.div1.level1;

import java.util.Arrays;
import java.util.HashMap;

public class BalancedStrings {
    public static void main(String[] args) throws Exception {
        new BalancedStrings().check();
    }

    private void check() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println("checking for N=" + (i+1));
            String[] result = findAny(i + 1);
            System.out.println(Arrays.toString(result));
            System.out.println(getInstability(result));
            System.out.println(getSimilarity(result));
            if (getInstability(result) == getSimilarity(result)) {
                System.out.println("located length of " + result.length + ": " + Arrays.toString(result));
            } else {
                throw new Exception("failed");
            }
        }
    }

    private String[] findAny(int N) {
        if (N == 39) {
            return new String[]{"abababababababab",
                    "c",
                    "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s"};
        }
        String a[] = new String[N];
        if (N <= 26) {
            for (int i = 0; i < a.length; i++) {
                int c = 'a' + i;
                a[i] = "" + (char) c;
            }
        } else if (N <= 52) {
            int dup = N - 26;
            for (int i = 0; i < 26; i++) {
                int c = 'a' + i;
                a[i] = "" + (char) c;
                if (i < dup) {
                    a[i] += (char) c;
                }
            }
            for (int i = 26; i < a.length; i++) {
                int c = 'a' + dup + i - 26;
                a[i] = "" + (char) c + (char) c;
            }
        }
        return a;
    }

    private int getSimilarity(String s[]) {
        int count = 0;
        for (int i = 0; i < s.length - 1; i++) {
            for (int j = i + 1; j < s.length; j++) {
                count += getSimilarity(s[i], s[j]);
            }
        }
        return count;
    }

    private int getSimilarity(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private int getInstability(String a[]) {
        int count = 0;
        for (String s : a) {
            count += getInstability(s);
        }
        return count;
    }

    private int getInstability(String s) {
        HashMap<String, Boolean> pairs = new HashMap<>();
        for (int i = 0; i < s.length() - 1; i++) {
            String pair = s.charAt(i) + "" + s.charAt(i + 1);
            if (pairs.get(pair) == null) {
                pairs.put(pair, false);
            } else {
                pairs.put(pair, true);
            }
        }

        int count = 0;
        for (Boolean b : pairs.values()) {
            if (!b) {
                count++;
            }
        }
        return count;
    }
}
