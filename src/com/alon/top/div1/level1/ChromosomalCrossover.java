package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChromosomalCrossover implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new ChromosomalCrossover()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\", \"(.*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String A = matcher.group(1);
        String B = matcher.group(2);
        int expected = Integer.parseInt(matcher.group(3));
        int actual = maximalLength(A, B);
        util.assertEquals(expected, actual);
    }

    private int maximalLength(String A, String B) {
        return recurse("", "", A, B);
    }

    private int recurse(String aPart, String bPart, String A, String B) {
        if (A.isEmpty()) {
            return lcs(aPart, bPart);
        }

        char firstAChar = A.charAt(0);
        char firstBChar = B.charAt(0);
        String truncedA = A.substring(1);
        String truncedB = B.substring(1);

        if (firstAChar==firstBChar) {
            return recurse(aPart + firstAChar, bPart + firstBChar, truncedA, truncedB);
        }

        return Math.max(
                recurse(aPart + firstAChar, bPart + firstBChar, truncedA, truncedB),
                recurse(aPart + firstBChar, bPart + firstAChar, truncedA, truncedB)
        );
    }


    private int lcs(String A, String B) {
        int max = 0;
        for (int a = 0; a < A.length(); a++) {
            for (int b = 0; b < B.length(); b++) {
                int len = 0;
                while (a + len < A.length() && b + len < B.length() && A.charAt(a + len) == B.charAt(b + len)) {
                    len++;
                }
                max = Math.max(max, len);
            }
        }
        return max;
    }

}