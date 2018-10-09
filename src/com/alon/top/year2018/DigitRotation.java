package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitRotation implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new DigitRotation()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String X = matcher.group(1);
        int expected = Integer.parseInt(matcher.group(2));
        int actual = sumRotations(X);
        util.assertEquals(expected, actual);
    }

    private int sumRotations(String X) {
        if (X.length() < 3) {
            return 0;
        }

        int n[] = new int[X.length()];
        for (int i = 0; i < X.length(); i++) {
            n[i] = X.charAt(i) - '0';
        }

        int mutate[] = new int[X.length()];
        int total[] = new int[X.length() + 20];

        for (int a = 0; a < n.length - 2; a++) {
            for (int b = a + 1; b < n.length - 1; b++) {
                for (int c = b + 1; c < n.length; c++) {
                    if (a == 0 && n[c] == 0) {
                        continue;
                    }
                    System.arraycopy(n, 0, mutate, 0, n.length);
                    mutate[a] = n[c];
                    mutate[b] = n[a];
                    mutate[c] = n[b];

                    addArray(total, mutate);
                }
            }
        }
        return modulo(total);
    }

    private int modulo(int[] total) {
        String s = "";
        for (int i : total) {
            s = s + i;
        }
        s = s.replaceAll("^0+", "");
        if (s.length() == 0) {
            return 0;
        }
        long l = Long.parseLong(s);
        l = l % 998244353;
        return (int) l;
    }


    private void addArray(int total[], int add[]) {
        int overflow = 0;
        int addIndex = add.length - 1;
        int totalIndex = total.length - 1;
        while (true) {
            int result = total[totalIndex] + add[addIndex] + overflow;
            total[totalIndex] = result % 10;
            overflow = result / 10;
            totalIndex--;
            addIndex--;
            if (addIndex < 0) {
                if (overflow != 0) {
                    total[totalIndex] += overflow;
                }
                return;
            }
        }
    }
}