package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BichromePainting implements InterfaceTopTest {
    private String[] required;
    private char[][] current;
    private int fillSize;
    private int n;
    private char paintColor = 'B';

    public static void main(String[] args) throws Exception {
        new TopUtil(new BichromePainting()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, (\\d+)\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String[] board = util.getStrings(matcher.group(1));
        int k = Integer.parseInt(matcher.group(2));
        String expected = matcher.group(3);
        String actual = isThatPossible(board, k);
        util.assertEquals(expected, actual);
    }

    private String isThatPossible(String[] board, int k) {
        n = board.length;
        required = board;
        fillSize = k;
        current = new char[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                current[row][col] = 'W';
            }
        }

        if (canPaint()) {
            return "Possible";
        }
        return "Impossible";
    }

    private boolean canPaint() {
        for (int i = 0; i < n * n + 1; i++) {
            if (match()) {
                return true;
            }

            printBoard(current);

            fillSquares();

            if (paintColor == 'W') {
                paintColor = 'B';
            } else {
                paintColor = 'W';
            }
        }
        return false;
    }

    private void printBoard(char[][] b) {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                builder.append(b[row][col]);
            }
            builder.append("\n");
        }
        System.out.flush();
        System.err.flush();
        System.out.println("board:\n" + builder);
        System.out.flush();
        System.err.flush();
    }

    private boolean match() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char requiredColor = required[row].charAt(col);
                char currentColor = current[row][col];
                if (requiredColor != currentColor) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fillSquares() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char requiredColor = required[row].charAt(col);
                char currentColor = current[row][col];
                if (requiredColor != currentColor && requiredColor == paintColor) {
                    fillSquare(row, col);
                }
            }
        }
    }

    private void fillSquare(int row, int col) {
        int fromRow = row;
        int toRow = row + fillSize - 1;
        if (toRow >= n) {
            toRow = n - 1;
            fromRow = toRow - fillSize + 1;
        }

        int fromCol = col;
        int toCol = col + fillSize - 1;
        if (toCol >= n) {
            toCol = n - 1;
            fromCol = toCol - fillSize + 1;
        }

        for (int r = fromRow; r <= toRow; r++) {
            for (int c = fromCol; c <= toCol; c++) {
                current[r][c] = paintColor;
            }
        }
    }
}

