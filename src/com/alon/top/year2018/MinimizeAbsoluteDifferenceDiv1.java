package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinimizeAbsoluteDifferenceDiv1 implements InterfaceTopTest {
    private Double min;
    private int[] best;
    private int[] x;

    public static void main(String[] args) throws Exception {
        new TopUtil(new MinimizeAbsoluteDifferenceDiv1()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] x = util.getInts(matcher.group(1));
        int[] expected = util.getInts(matcher.group(2));
        int[] actual = findTuple(x);
        double minExpected = calculate(x[expected[0]], x[expected[1]], x[expected[2]], x[expected[3]]);
        double minActual = calculate(x[actual[0]], x[actual[1]], x[actual[2]], x[actual[3]]);
        util.assertEquals(minExpected, minActual);
    }

    private int[] findTuple(int[] x) {
        min = null;
        this.x = x;

        LinkedList<Integer> indexes = new LinkedList<>();
        for (int i = 0; i < x.length; i++) {
            indexes.add(i);
        }

        LinkedList<Integer> selected = new LinkedList<>();
        recursiveFind(indexes, selected);
        return best;
    }

    private void recursiveFind(LinkedList<Integer> indexes, LinkedList<Integer> selected) {
        if (indexes.size() == 1) {
            double x1 = x[selected.get(0)];
            double x2 = x[selected.get(1)];
            double x3 = x[selected.get(2)];
            double x4 = x[selected.get(3)];
            double result = calculate(x1, x2, x3, x4);
            if (min == null || result < min) {
                min = result;
                best = new int[4];
                best[0] = selected.get(0);
                best[1] = selected.get(1);
                best[2] = selected.get(2);
                best[3] = selected.get(3);
            }
            return;
        }

        for (int i = 0; i < indexes.size(); i++) {
            int selectedIndex = indexes.remove(i);
            selected.addLast(selectedIndex);
            recursiveFind(indexes, selected);
            selected.removeLast();
            indexes.add(i, selectedIndex);
        }
    }

    private double calculate(double x1, double x2, double x3, double x4) {
        return Math.abs((x1 / x2) - (x3 / x4));
    }
}