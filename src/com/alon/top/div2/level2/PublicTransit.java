package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicTransit {
    private int rows;
    private int cols;
    private int minDistance;
    private int port1Row;
    private int port1Col;
    private int port2Row;
    private int port2Col;

    public static void main(String[] args) throws Exception {
        new PublicTransit().check();
    }

    private void check() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(this.getClass().getSimpleName() + ".txt");
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            checkLine(line);
        }
    }

    private void checkLine(String line) throws Exception {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        print("checking " + line);

        Pattern pattern = Pattern.compile("(\\d+), (\\d+)\\s+(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        int R = Integer.parseInt(matcher.group(1));
        int C = Integer.parseInt(matcher.group(2));
        int expected = Integer.parseInt(matcher.group(3));

        int actual = minimumLongestDistance(R, C);
        if (actual != expected) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private void print(String s) {
        System.out.println(s);
        System.out.flush();
        System.err.flush();
    }

    private int minimumLongestDistance(int R, int C) {
        rows = R;
        cols = C;
        minDistance = rows + cols;
        for (port1Row = 0; port1Row < rows; port1Row++) {
            for (port1Col = 0; port1Col < cols; port1Col++) {
                for (port2Row = 0; port2Row < rows; port2Row++) {
                    for (port2Col = 0; port2Col < cols; port2Col++) {
                        checkDistanceWithPort();
                    }
                }
            }
        }
        return minDistance;
    }

    private void checkDistanceWithPort() {
        int currentMax = 0;
        for (int r1 = 0; r1 < rows; r1++) {
            for (int c1 = 0; c1 < cols; c1++) {
                for (int r2 = 0; r2 < rows; r2++) {
                    for (int c2 = 0; c2 < cols; c2++) {
                        int distance = findDistance(r1, c1, r2, c2);
                        currentMax = Math.max(currentMax, distance);
                    }
                }
            }
        }
        minDistance = Math.min(minDistance, currentMax);
    }

    private int findDistance(int r1, int c1, int r2, int c2) {
        int distanceWithoutPort = simpleDistance(r1, c1, r2, c2);
        int distanceWithPort1 = simpleDistance(r1, c1, port1Row, port1Col) + simpleDistance(port2Row, port2Col, r2, c2);
        int distanceWithPort2 = simpleDistance(r1, c1, port2Row, port2Col) + simpleDistance(port1Row, port1Col, r2, c2);
        return Math.min(distanceWithoutPort, Math.min(distanceWithPort1, distanceWithPort2));
    }

    private int simpleDistance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }
}