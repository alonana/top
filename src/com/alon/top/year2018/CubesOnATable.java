package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CubesOnATable implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new CubesOnATable()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+)\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int surface = Integer.parseInt(matcher.group(1));
        String result = matcher.group(2);
        boolean expected = result.length() > 2;
        boolean actual = placeCubes(surface);
        util.assertEquals(expected, actual);
    }

    private boolean placeCubes(int surface) {
        return surface == 5 || surface > 7;
    }
}