package com.alon.top.year2018;


import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomaticVacuumCleaner implements InterfaceTopTest {

    private long cols;

    public static void main(String[] args) throws Exception {
        new TopUtil(new AutomaticVacuumCleaner()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+), (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        long C = Long.parseLong(matcher.group(2));
        long A = Long.parseLong(matcher.group(3));
        long B = Long.parseLong(matcher.group(4));
        long expected = Long.parseLong(matcher.group(5));
        long actual = getDistance(C, A, B);
        util.assertEquals(expected, actual);
    }

    private long getDistance(long C, long A, long B) {
        this.cols = C;
        if (A == 0) {
            if (B == 0) {
                return 0;
            }
            B--;
        } else {
            A--;
        }
        Point start = new Point();
        Point point1 = findNext(start, A);
        Point point2 = findNext(point1, B);
        return findDistance(point1, point2);
    }

    Point findNext(Point p, long moves) {
        Point next = new Point();
        next.row = p.row;
        next.col = p.col;
        // move up to next row
        boolean moveRight = next.row % 2 == 0;
        if (moveRight) {
            if (next.col + moves < cols) {
                next.col += moves;
                return next;
            }
            moves -= (cols - next.col);
            next.row++;
        } else {
            if (next.col - moves >= 0) {
                next.col -= moves;
                return next;
            }
            moves -= (next.col + 1);
            next.row++;
        }
        long rowsAdvance = moves / cols;
        next.row += rowsAdvance;
        moves -= rowsAdvance * cols;

        moveRight = next.row % 2 == 0;
        if (moveRight) {
            next.col = moves;
        } else {
            next.col = cols - moves - 1;
        }
        return next;
    }


    long findDistance(Point p1, Point p2) {
        return Math.abs(p1.row - p2.row) + Math.abs(p1.col - p2.col);
    }

    class Point {
        long row;
        long col;
    }
}