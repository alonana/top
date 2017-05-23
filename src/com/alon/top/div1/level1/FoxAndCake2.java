package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoxAndCake2 implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new FoxAndCake2()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+)\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int c = Integer.parseInt(matcher.group(1));
        int s = Integer.parseInt(matcher.group(2));
        String expected = matcher.group(3);
        String actual = isPossible(c, s);
        util.assertEquals(expected, actual);
    }

    private String isPossible(int c, int s) {
        return canSplit(c, s) ? "Possible" : "Impossible";
    }

    private boolean canSplit(int c, int s) {
        if (c == 1 || s == 1) {
            return false;
        }
        if (c%3 ==0 && s%3 ==0){
            return true;
        }
        boolean cEven = c % 2 == 0;
        boolean sEven = s % 2 == 0;
        if (cEven && sEven) {
            return true;
        }
        if (c > s) {
            if (c % s == 0) {
                return true;
            }
        } else {
            if (s % c == 0) {
                return true;
            }
        }
        if (!cEven && !sEven) {
            if (c>4 && s>4) {
                return true;
            }
        }

        if (cEven){
            if (s < 5 ) {
                return false;
            }
            if (c<8){
                return false;
            }
        }else {
            if (c < 5 ) {
                return false;
            }
            if (s < 8 ) {
                return false;
            }
        }
        return true;
    }
}