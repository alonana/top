package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EllysSquareWords implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new EllysSquareWords()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }


        String S = matcher.group(1);
        int expected = Integer.parseInt(matcher.group(2));
        int actual = getScore(S);
        util.assertEquals(expected, actual);
    }

    private int getScore(String S) {
        int max = calculateScore(S);
        for (char fromChar = 'A'; fromChar <= 'Z'; fromChar++) {
            for (char toChar = 'A'; toChar <= 'Z'; toChar++) {
                String updated = S.replace(fromChar + "", toChar + "");
                int score = calculateScore(updated);
                max = Math.max(score, max);
            }
        }
        return max;
    }

    private int calculateScore(String s) {
        int count[] = new int['Z' - 'A' + 1];
        for (char c : s.toCharArray()) {
            count[c - 'A']++;
        }

        int score = 0;
        for (int i : count) {
            score += i * i;
        }
        return score;
    }
}