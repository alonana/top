package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovingCandies implements InterfaceTopTest {
    public static final int HIGH = 999999999;

    private int rows;
    private int cols;
    private String[] board;

    public static void main(String[] args) throws Exception {
        new TopUtil(new MovingCandies()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(-?\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }
        String[] board = util.getStrings(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = minMoved(board);
        util.assertEquals(expected, actual);
    }

    public int minMoved(String[] board) {
        int total = 0;
        this.board = board;
        for (String row : this.board) {
            total += row.length() - row.replace("#", "").length();
        }
        rows = board.length;
        cols = board[0].length();
        int[][] best = createHighBest(rows, cols);
        best[0][0] = hasCandy(0, 0) ? 0 : 1;
        print(best);

        for (int i = 1; i < total; i++) {
            best = iterate(best);
            print(best);
        }

        int result = best[rows - 1][cols - 1];
        if (result == HIGH) {
            return -1;
        }
        return result;
    }

    private void print(int[][] best) {
        if (true){
            return;
        }
        System.out.println("=====");
        for (int[] row : best) {
            for (int cost : row) {
                System.out.printf("%9d ", cost);
            }
            System.out.println();
        }
        System.out.println("=====");
    }

    private int[][] createHighBest(int rows, int cols) {
        int[][] best = new int[rows][cols];
        for (int[] row : best) {
            Arrays.fill(row, HIGH);
        }
        return best;
    }

    private int[][] iterate(int[][] prev) {
        int[][] best = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            System.arraycopy(prev[row], 0, best[row], 0, cols);
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int score = prev[row][col];
                update(best, score, row + 1, col);
                update(best, score, row - 1, col);
                update(best, score, row, col + 1);
                update(best, score, row, col - 1);
            }
        }
        return best;
    }

    private void update(int[][] best, int newScore, int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }
        if (!hasCandy(row, col)) {
            newScore++;
        }
        int currentScore = best[row][col];
        if (newScore < currentScore) {
            best[row][col] = newScore;
        }
    }

    private boolean hasCandy(int row, int col) {
        return board[row].charAt(col) == '#';
    }
}
