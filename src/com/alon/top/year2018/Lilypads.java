package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lilypads implements InterfaceTopTest {

    private boolean[][] pond;
    private int step;
    private int[] steps;

    public static void main(String[] args) throws Exception {
        new TopUtil(new Lilypads()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+), (\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int R = Integer.parseInt(matcher.group(1));
        int C = Integer.parseInt(matcher.group(2));
        int r0 = Integer.parseInt(matcher.group(3));
        int c0 = Integer.parseInt(matcher.group(4));
        int[] actual = traverse(R, C, r0, c0);
        System.out.println(Arrays.toString(actual));
    }

    private int[] traverse(int R, int C, int r0, int c0) throws Exception {
        pond = new boolean[R][C];
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                pond[r][c] = true;
            }
        }

        step = 0;
        steps = new int[R * C * 2];
        steps[step++] = r0;
        steps[step++] = c0;

        while (step < steps.length) {
            if (jumpRight()) {
                continue;
            }
            if (jumpLeft()) {
                continue;
            }
            if (jumpUp()) {
                continue;
            }
            if (jumpDown()) {
                continue;
            }
            throw new Exception("error");
        }

        return steps;
    }

    private boolean jumpRight() {
        int currentRow = steps[step - 2];
        int currentCol = steps[step - 1];
        for (int col = pond[currentRow].length - 1; col > currentCol; col--) {
            if (jumpIfPossible(currentRow, col)) {
                return true;
            }
        }
        return false;
    }

    private boolean jumpLeft() {
        int currentRow = steps[step - 2];
        int currentCol = steps[step - 1];
        for (int col = 0; col < currentCol; col++) {
            if (jumpIfPossible(currentRow, col)) {
                return true;
            }
        }
        return false;
    }

    private boolean jumpUp() {
        int currentRow = steps[step - 2];
        int currentCol = steps[step - 1];
        for (int row = 0; row < currentRow; row++) {
            if (jumpIfPossible(row, currentCol)) {
                return true;
            }
        }
        return false;
    }

    private boolean jumpDown() {
        int currentRow = steps[step - 2];
        int currentCol = steps[step - 1];
        for (int row = pond.length - 1; row > currentRow; row--) {
            if (jumpIfPossible(row, currentCol)) {
                return true;
            }
        }
        return false;
    }

    private boolean jumpIfPossible(int row, int col) {
        if (pond[row][col]) {
            pond[row][col] = false;
            steps[step++] = row;
            steps[step++] = col;
            return true;
        }
        return false;
    }
}