package com.alon.top.div2.level2;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BearSlowlySorts implements InterfaceTopTest {
    private int moves;
    private int[] w;

    public static void main(String[] args) throws Exception {
        new TopUtil(new BearSlowlySorts()).check();
    }

    @Override
    public void checkLine(String line,TopUtil util) throws Exception {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        Pattern pattern = Pattern.compile("\\s*\\{(.*)}\\s*(\\S+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        String weightsArray = matcher.group(1);
        weightsArray = weightsArray.replace(" ", "");
        String weights[] = weightsArray.split(",");
        int[] w = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            w[i] = Integer.parseInt(weights[i]);
        }
        int expectedMoves = Integer.parseInt(matcher.group(2));

        System.out.println("checking " + Arrays.toString(w));
        int actualMoves = minMoves(w);
        if (actualMoves != expectedMoves) {
            throw new Exception("expected " + expectedMoves + " actual " + actualMoves);
        }
    }

    private int minMoves(int[] w) {
        moves = 0;
        this.w = w;

        if (this.w[0] > getMidMax(w.length)) {
            sortLeftIfRequired();
            sortRightIfRequired();
        } else {
            sortRightIfRequired();
            sortLeftIfRequired();
        }
        sortLeftIfRequired();

        for (int i = 1; i < w.length; i++) {
            if (w[i - 1] > w[i]) {
                moves++;
                break;
            }
        }

        return moves;
    }

    private void sortRightIfRequired() {
        int max = getMidMax(w.length - 1);
        if (w[w.length - 1] < max) {
            Arrays.sort(w, 1, w.length);
            moves++;
        }
    }

    private int getMidMax(int end) {
        int max = w[1];
        for (int i = 1 + 1; i < end; i++) {
            max = Math.max(max, w[i]);
        }
        return max;
    }

    private void sortLeftIfRequired() {
        int min = getMidMin(w.length - 1);
        if (w[0] > min) {
            Arrays.sort(w, 0, w.length - 1);
            moves++;
        }
    }

    private int getMidMin(int end) {
        int min = w[1];
        for (int i = 1 + 1; i < end; i++) {
            min = Math.min(min, w[i]);
        }
        return min;
    }
}