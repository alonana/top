package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Halving implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new Halving()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] a = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = minSteps(a);
        util.assertEquals(expected, actual);
    }

    private int minSteps(int[] a) {
        Stick[] sticks = new Stick[a.length];
        for (int i = 0; i < a.length; i++) {
            sticks[i] = new Stick(a[i]);
        }

        print(sticks);
        int count = 0;
        while (true) {
            boolean updated = false;
            for (Stick s1 : sticks) {
                for (Stick s2 : sticks) {
                    if (s1.halfBy(s2)) {
                        count++;
                        print(sticks);
                        updated = true;
                    }
                }
            }
            if (!updated) {
                break;
            }
        }
        return count;
    }

    private void print(Stick[] sticks) {
        //System.out.println(Arrays.toString(sticks));
    }

    class Stick {
        private int max;
        private int min;

        public Stick(int i) {
            min = i;
            max = i;
        }

        public boolean halfBy(Stick other) {
            if (this.min > other.max) {
                this.max = (this.max + 1) / 2;
                this.min = this.min / 2;
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return min + "-" + max;
        }
    }
}