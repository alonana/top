package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MazeConstruct implements InterfaceTopTest {
    public static final char EMPTY = '.';
    public static final char FULL = '#';

    public static void main(String[] args) throws Exception {
        new TopUtil(new MazeConstruct()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher m = Pattern.compile("(\\d+)").matcher(line);
        if (!m.find()) {
            throw new Exception("not found");
        }
        int k = Integer.parseInt(m.group(1));
        String[] board = construct(k);
        System.out.println("board " + board.length + "X" + board[0].length() + " :");
        for (String b : board) {
            System.out.println(b);
        }

        int actual = bfs(board);
        util.assertEquals(k, actual);
    }

    private int bfs(String[] board) throws Exception {
        int rows = board.length;
        int cols = board[0].length();
        HashSet<Point> visited = new HashSet<>();
        LinkedList<Point> toVisit = new LinkedList<>();
        toVisit.add(new Point(0, 0, 0));
        while (!toVisit.isEmpty()) {
            Point point = toVisit.removeFirst();
            if (point.getRow() == rows - 1 && point.getCol() == cols - 1) {
                return point.getCost();
            }
            if (visited.contains(point)) {
                continue;
            }
            visited.add(point);
            toVisit.addAll(point.getNexts(board));
        }
        throw new Exception("path not found");
    }

    class Point {
        private int row;
        private int col;
        private int cost;

        public Point(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
        }

        public Collection<Point> getNexts(String[] board) {
            Collection<Point> points = new LinkedList<>();
            Point up = new Point(this.row - 1, this.col, this.cost + 1);
            if (up.isValid(board)) {
                points.add(up);
            }
            Point down = new Point(this.row + 1, this.col, this.cost + 1);
            if (down.isValid(board)) {
                points.add(down);
            }
            Point left = new Point(this.row, this.col - 1, this.cost + 1);
            if (left.isValid(board)) {
                points.add(left);
            }
            Point right = new Point(this.row, this.col + 1, this.cost + 1);
            if (right.isValid(board)) {
                points.add(right);
            }
            return points;
        }

        @SuppressWarnings("all")
        private boolean isValid(String[] board) {
            int rows = board.length;
            int cols = board[0].length();
            if (this.row < 0 || this.row >= rows || this.col < 0 || this.col >= cols) {
                return false;
            }
            return board[this.row].charAt(this.col) == EMPTY;
        }
    }

    private String[] construct(int k) throws Exception {
        if (k <= 98) {
            return constructSimple(k);
        }
        for (int cols = 2; cols <= 50; cols++) {
            for (int rows = 5; rows <= 50; rows += 4) {
                int result = (rows + 1) * (cols - 1) / 2 + rows - 1;
                if (result == k) {
                    return constructMeasured(rows, cols, 0);
                }
                if (result > k) {
                    int diff = result - k;
                    if (diff % 2 == 0 && diff < cols) {
                        System.out.println("required " + k + " result " + result);
                        int shorten = diff / 2;
                        return constructMeasured(rows, cols, shorten);
                    }
                }
            }
        }
        throw new Exception("not located");
    }

    private String[] constructSimple(int k) {
        if (k < 50) {
            String line = repeat(EMPTY, k + 1);
            return new String[]{line};
        }
        int lines = k - 49 + 1;
        String[] result = new String[lines];
        for (int i = 0; i < lines; i++) {
            result[i] = repeat(EMPTY, 50);
        }
        return result;
    }

    private String repeat(char c, int amount) {
        char[] result = new char[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = c;
        }
        return new String(result);
    }

    private String[] constructMeasured(int rows, int cols, int shorten) {
        int result = (rows + 1) * (cols - 1) / 2 + rows - 1;
        System.out.println(rows + " X " + cols + " shorten " + shorten + " result " + result);
        String[] board = new String[rows];
        int step = 0;
        for (int row = 0; row < rows; row++) {
            String line;
            if (step == 0 || step == 2) {
                line = repeat(EMPTY, cols);
            } else if (step == 1) {
                line = repeat(FULL, cols - 1) + EMPTY;
            } else {
                line = EMPTY + repeat(FULL, cols - 1);
            }
            board[row] = line;
            step++;
            step = step % 4;
        }
        int row = rows - 2;
        int col = 1;
        int direction = +1;
        for (int i = 0; i < shorten; i++) {
            String original = board[row];
            board[row] = original.substring(0, col) + EMPTY + original.substring(col + 1);
            col += direction;
            if (col < 0 || col >= cols) {
                row -= 2;
                direction *= -1;
                col += direction;
                col += direction;
            }
        }
        return board;
    }
}
