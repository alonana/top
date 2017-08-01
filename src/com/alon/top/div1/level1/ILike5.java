package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ILike5 implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new ILike5()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] X = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = transformTheSequence(X);
        util.assertEquals(expected, actual);
    }

    private int transformTheSequence(int[] X) {
        int changes = 0;
        boolean fiveLocated = false;
        for (int i : X) {
            if (i % 2 == 0) {
                changes++;
                fiveLocated = true;
            } else if (i % 10 == 5) {
                fiveLocated = true;
            }
        }
        if (!fiveLocated) {
            changes++;
        }
        return changes;
    }
}