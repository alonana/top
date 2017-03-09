package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoardEscapeDiv2 {
    private String s[];
    private HashMap<String, Boolean> cache;

    public static void main(String args[]) throws Exception {
        InputStream in = BoardEscapeDiv2.class.getClassLoader().getResourceAsStream(BoardEscapeDiv2.class.getSimpleName());
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
        ArrayList<String> input = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            input.add(line);
        }
        for (String check : input) {
            check(check);
        }
    }

    private static void check(String input) throws Exception {
        input = input.replace("Passed", "");
        Pattern pattern = Pattern.compile("\\{(.+)\\},\\s+(\\d+)\\s+\"(\\S+)\"");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new Exception("parse error");
        }
        String strings = matcher.group(1);
        String k = matcher.group(2);
        String winner = matcher.group(3);
        String board[] = strings.split(",");
        System.out.println("checking");
        for (int i = 0; i < board.length; i++) {
            board[i] = board[i].replace("\"", "").trim();
            System.out.println(board[i]);
        }
        System.out.println("k " + k + " " + winner);
        String actual = new BoardEscapeDiv2().findWinner(board, Integer.parseInt(k));
        if (!actual.equals(winner)) {
            throw new Exception("expected " + winner + " actual " + actual);
        }
    }

    private String findWinner(String s[], int k) {
        this.s = s;


        int tokRow = 0;
        int tokCol = 0;
        for (int row = 0; row < s.length; row++) {
            for (int col = 0; col < s[row].length(); col++) {
                if (s[row].charAt(col) == 'T') {
                    tokRow = row;
                    tokCol = col;
                }
            }
        }

        cache = new HashMap<>();
        s[tokRow] = s[tokRow].substring(0, tokCol) + '.' + s[tokRow].substring(tokCol + 1);
        if (canWin(tokRow, tokCol, k)) {
            return "Alice";
        }
        return "Bob";
    }

    private boolean canWin(int row, int col, int k) {
        if (k == 0) {
            return false;
        }
        String key = row + "_" + col + "_" + k;
        Boolean result = cache.get(key);
        if (result != null) {
            return result;
        }

        if (check(row - 1, col, k) || check(row + 1, col, k) || check(row, col + 1, k) || check(row, col - 1, k)) {
            cache.put(key, true);
            return true;
        }
        cache.put(key, false);
        return false;
    }

    private boolean check(int row, int col, int k) {
        if (row < 0 || row >= s.length) {
            return false;
        }
        if (col < 0 || col >= s[0].length()) {
            return false;
        }
        if (s[row].charAt(col) == '#') {
            return false;
        }
        if (s[row].charAt(col) == 'E') {
            return true;
        }
        return !canWin(row, col, k - 1);
    }
}
