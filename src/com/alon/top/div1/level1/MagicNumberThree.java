package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagicNumberThree implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new MagicNumberThree()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String s = matcher.group(1);
        int expected = Integer.parseInt(matcher.group(2));
        int actual = countSubsequences(s);
        util.assertEquals(expected, actual);
    }

    private int countSubsequences(String s) {
        int n[] = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            n[i] = s.charAt(i) - '0';
        }
        return countSubsequences(n);
    }

    private int countSubsequences(int n[]) {
        long reminder[] = new long[3];
        for (int currentDigit : n) {
            long newReminder[] = new long[3];
            System.arraycopy(reminder, 0, newReminder, 0, 3);
            newReminder[currentDigit % 3]++;
            for (int reminderIndex = 0; reminderIndex < 3; reminderIndex++) {
                if (reminder[reminderIndex] == 0) {
                    continue;
                }
                int addResult = (reminderIndex + currentDigit) % 3;
                newReminder[addResult] += reminder[reminderIndex];
                newReminder[addResult] %= 1000000007;
            }
            reminder = newReminder;
        }
        return (int) reminder[0];
    }
}