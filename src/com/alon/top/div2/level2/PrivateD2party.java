package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrivateD2party {
    public static void main(String[] args) throws Exception {
        new PrivateD2party().check();
    }

    private void check() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(this.getClass().getSimpleName() + ".txt");
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
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        print("checking " + line);

        Pattern pattern = Pattern.compile("\\{(.*)}\\s+(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        int[] a = getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));

        int actual = getsz(a);
        if (actual != expected) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private void print(String s) {
        System.out.println(s);
        System.out.flush();
        System.err.flush();
    }

    private int[] getInts(String a) {
        a = a.replace(" ", "");
        String weights[] = a.split(",");
        int[] aInts = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            aInts[i] = Integer.parseInt(weights[i]);
        }
        return aInts;
    }

    private int getsz(int[] a) {
        int max = a.length;
        for (int i = 0; i < a.length - 1; i++) {
            if (cutLoop(a, i)) {
                max--;
            }
        }
        return max;
    }

    private boolean cutLoop(int[] a, int i) {
        HashSet<Integer> visited = new HashSet<>();
        while (true) {
            int next = a[i];
            if (next == i) {
                return false;
            }
            if (visited.contains(next)) {
                a[i] = i;
                return true;
            }
            visited.add(i);
            i = next;
        }
    }
}