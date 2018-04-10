package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileFlippingGame implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new TileFlippingGame()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), \\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int n = Integer.parseInt(matcher.group(1));
        int m = Integer.parseInt(matcher.group(2));
        String[] X = util.getStrings(matcher.group(3));
        int expected = Integer.parseInt(matcher.group(4));
        int actual = HowManyMoves(n, m, X);
        util.assertEquals(expected, actual);
    }

    private HashSet<String> visited = new HashSet<>();
    private int rows;
    private int cols;

    private int HowManyMoves(int n, int m, String[] X) {
        rows = n;
        cols = m;
        Node root = new Node(X);
        return root.findMinMoves();
    }

    class Node {
        private String[] board;
        private Collection<Node> children;

        Node(String[] board) {
            this.children = new LinkedList<>();
            this.board = board.clone();
        }

        int findMinMoves() {
            visited.add(this.key());
            spanTreeByLevels();
            //printTree("");
            return findMinHeight();
        }

//        private void printTree(String prefix) {
//            debug(format(prefix));
//            prefix += "   ";
//            for (Node n : children) {
//                n.printTree(prefix);
//            }
//        }
//
        private void spanTreeByLevels() {
            int level = 0;
            while (true) {
                //debug("spanning level " + level);
                boolean spanned = this.spanTree(level);
                if (!spanned) {
                    return;
                }
                level++;
            }
        }

        private boolean spanTree(int level) {
            if (level > 0) {
                level--;
                boolean spanned = false;
                for (Node n : children) {
                    if (n.spanTree(level)) {
                        spanned = true;
                    }
                }
                return spanned;
            }

            if (isAllSameColor()) {
                return false;
            }

            createChildren();
            return true;
        }

        private boolean isAllSameColor() {
            boolean white = false;
            boolean black = false;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    char tile = getTile(row, col);
                    if (tile == 'w') {
                        white = true;
                    } else if (tile == 'b') {
                        black = true;
                    }
                }
            }

            return !black || !white;
        }

        private void createChildren() {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    createChild(row, col);
                }
            }
        }

        private void createChild(int row, int col) {
            char color = board[row].charAt(col);
            if (color == 'e') {
                return;
            }

            Node child = new Node(board);
            child.reverseRecursive(row, col, color);
            String key = child.key();
            if (visited.contains(key)) {
                return;
            }

            visited.add(key);
            children.add(child);

//            debug("=== added for parent:\n" +
//                    format("  ") +
//                    "=== child:\n" +
//                    child.format("  "));
        }

        private char getTile(int row, int col) {
            return board[row].charAt(col);
        }

        private String key() {
            return Arrays.toString(board);
        }

        private void reverseRecursive(int row, int col, char color) {
            if (row < 0 || col < 0 || row >= rows || col >= cols) {
                return;
            }
            char existing = getTile(row, col);
            if (existing != color) {
                return;
            }

            reverse(row, col);
            reverseRecursive(row - 1, col, color);
            reverseRecursive(row + 1, col, color);
            reverseRecursive(row, col - 1, color);
            reverseRecursive(row, col + 1, color);
        }

        private void reverse(int row, int col) {
            char existingTile = getTile(row, col);
            String replaceRow = board[row];
            char reverse = existingTile == 'b' ? 'w' : 'b';
            board[row] = replaceRow.substring(0, col) + reverse + replaceRow.substring(col + 1);
        }

        private Integer findMinHeight() {
            if (children.isEmpty()) {
                if (isAllSameColor()) {
                    return 0;
                }
                return null;
            }

            Integer min = null;
            for (Node n : children) {
                Integer current = n.findMinHeight();
                if (current != null) {
                    if (min == null) {
                        min = current;
                    }
                    min = Math.min(min, current);
                }
            }
            if (min == null) {
                return null;
            }
            return 1 + min;
        }
//        public String format(String prefix) {
//            return prefix + String.join("\n" + prefix, board) + "\n";
//        }

    }

//    static void debug(String s) {
//        System.out.println(s);
//    }
}