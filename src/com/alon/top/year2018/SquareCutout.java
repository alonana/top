package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SquareCutout implements InterfaceTopTest {

    private String[] cutout;
    private int rows;
    private int cols;
    private Integer startRow;
    private Integer startCol;
    private Integer endRow;
    private Integer endCol;
    private boolean invalid;

    public static void main(String[] args) throws Exception {
        new TopUtil(new SquareCutout()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String[] cutout = util.getStrings(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = verify(cutout);
        util.assertEquals(expected, actual);
    }

    private int verify(String[] cutout) {
        this.cutout = cutout;
        this.cols = cutout[0].length();
        this.rows = cutout.length;
        startRow = null;
        startCol = null;
        endRow = null;
        endCol = null;
        invalid = false;
        return verify();
    }

    private int verify() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                analyzeCell(row, col);
            }
        }

        return analyzeResult();
    }

    private int analyzeResult() {
        if (invalid) {
            return 0;
        }

        if (startRow == null) {
            return 1;
        }

        int minRows = endRow - startRow + 1;
        int minCols = endCol - startCol + 1;
        if (minRows == minCols) {
            return minRows;
        }
        if (minRows > minCols) {
            if (startCol == 0 || endCol == cols - 1) {
                return minRows;
            }
            return 0;
        } else {
            if (startRow == 0 || endRow == rows - 1) {
                return minCols;
            }
            return 0;
        }
    }

    private void analyzeCell(int row, int col) {
        if (invalid) {
            return;
        }

        boolean black = cutout[row].charAt(col) == '#';

        if (startRow == null) {
            if (black) {
                startRow = row;
                endRow = row;
                startCol = col;
                endCol = col;
            }
            return;
        }

        if (startRow == row) {
            if (black) {
                if (endCol == col - 1) {
                    endCol++;
                } else {
                    invalid = true;
                }
            }
            return;
        }

        if (col < startCol || col > endCol) {
            if (black) {
                invalid = true;
            }
            return;
        }

        if (col == startCol) {
            if (black) {
                endRow++;
            }
            return;
        }

        if (row == endRow) {
            if (!black) {
                invalid = true;
            }
            return;
        }

        if (black) {
            invalid = true;
        }
    }
}


