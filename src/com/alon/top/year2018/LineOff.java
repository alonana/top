package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineOff implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new LineOff()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"([a-z]*)\"\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String points = matcher.group(1);
        int expected = Integer.parseInt(matcher.group(2));
        int actual = movesToDo(points);
        util.assertEquals(expected, actual);
    }

    private int movesToDo(String points) {
        List<Character> chars = new LinkedList<>();
        for (Character c : points.toCharArray()) {
            chars.add(c);
        }
        int moves = 0;
        while (true) {
            if (chars.size() <= 1) {
                return moves;
            }
            boolean moved = false;
            for (int i = 1; i < chars.size(); i++) {
                if (chars.get(i - 1).equals(chars.get(i))) {
                    chars.remove(i - 1);
                    chars.remove(i - 1);
                    moves++;
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                return moves;
            }
        }
    }
}