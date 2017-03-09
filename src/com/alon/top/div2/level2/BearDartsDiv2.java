package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BearDartsDiv2 {
    private static final int MAX = 1000000;

    public static void main(String args[]) throws Exception {
        new BearDartsDiv2().check();
    }

    private void check() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(getClass().getSimpleName() + ".txt");
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            checkLine(line);
        }
    }

    private void checkLine(String line) throws Exception {
        if (line.trim().isEmpty()) {
            return;
        }

        System.out.println("checking " + line);
        Pattern pattern = Pattern.compile("\\s*\\{(.*)\\}\\s*(\\S+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("not matched " + line);
        }
        String numbers = matcher.group(1);
        String expectedString = matcher.group(2);
        numbers = numbers.replaceAll(" ", "");
        String[] numbersSplit = numbers.split(",");
        int w[] = new int[numbersSplit.length];
        int i = 0;
        for (String number : numbersSplit) {
            w[i++] = Integer.parseInt(number);
        }
        long actual = count(w);
        if (actual != Long.parseLong(expectedString)) {
            throw new Exception("expected " + expectedString + " actual " + actual);
        }
    }

    private long count(int w[]) throws Exception {
        long result = 0;
        for (int a = 0; a < w.length - 3; a++) {
            for (int b = a + 1; b < w.length - 2; b++) {
                long factor2 = (long) w[a] * w[b] ;
                if (factor2>MAX){
                    continue;
                }
                for (int c = b + 1; c < w.length - 1; c++) {
                    long factor3 = factor2 * w[c];
                    if (factor3 > MAX) {
                        continue;
                    }
                    int factor = (int) factor3;
                    for (int d = c + 1; d < w.length; d++) {
                        if (factor == w[d]) {
                            result++;
                        }
                    }
                }
            }
        }
        return result;
    }
}