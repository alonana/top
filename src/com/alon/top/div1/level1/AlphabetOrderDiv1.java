package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphabetOrderDiv1 implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new AlphabetOrderDiv1()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String[] words = util.getStrings(matcher.group(1));
        String expected = matcher.group(2);
        String actual = isOrdered(words);
        util.assertEquals(expected, actual);
    }

    private boolean debug = false;

    private boolean[][] connection;

    private String isOrdered(String[] words) {
        connection = new boolean[26][26];
        for (String word : words) {
            if (word.length() < 2) {
                continue;
            }
            char prev = word.charAt(0);
            for (int i = 1; i < word.length(); i++) {
                char next = word.charAt(i);
                if (prev!= next) {
                    connection[prev - 'a'][next - 'a'] = true;
                }
                prev = next;
            }
        }

        if (isLoop()) {
            return "Impossible";
        }
        return "Possible";
    }

    private boolean isLoop() {
        for (int i = 0; i < connection.length; i++) {
            if (debug) {
                System.out.println("update " + i);
            }
            print();
            updateNexts();
        }

        print();
        for (int i = 0; i < connection.length; i++) {
            if (connection[i][i]) {
                return true;
            }
        }
        return false;
    }

    private void print() {
        if (!debug) {
            return;
        }
        System.out.println("  abcdefghijklmnopqrstuvwxyz");
        char c1 = 'a';
        for (boolean[] conn : connection) {
            System.out.print(c1 + " ");
            for (boolean b : conn) {
                if (b) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
            c1++;
        }

    }

    private void updateNexts() {
        for (int from = 0; from < connection.length; from++) {
            for (int to = 0; to < connection.length; to++) {
                if (connection[from][to]) {
                    for (int copy = 0; copy < connection.length; copy++) {
                        if (connection[to][copy]) {
                            connection[from][copy] = true;
                        }
                    }
                }
            }
        }
    }
}