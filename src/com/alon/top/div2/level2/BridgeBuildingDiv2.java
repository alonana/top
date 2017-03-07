package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BridgeBuildingDiv2 {
    private Set<Integer> bridges = new HashSet<>();
    private int a[];
    private int b[];
    private int sideLength;

    public static void main(String[] args) throws Exception {
        new BridgeBuildingDiv2().check();
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
        System.out.println("checking " + line);

        Pattern pattern = Pattern.compile("\\{(.*)}, \\{(.*)}, (\\d+)\\s+(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        int[] aInts = getInts(matcher.group(1));
        int[] bInts = getInts(matcher.group(2));
        int K = Integer.parseInt(matcher.group(3));
        int expected = Integer.parseInt(matcher.group(4));

        int actual = minDiameter(aInts, bInts, K);
        if (actual != expected) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    @Override
    public String toString() {
        return "\nbridges=" + bridges +
                "\na=" + Arrays.toString(a) +
                "\nb=" + Arrays.toString(b);

    }

    private int[] getInts(String a) {
        a = a.replace(" ", "");
        String weights[] = a.split(",");
        int[] aInts = new int[weights.length];
        for (int i = 0; i < weights.length; i++) {
            aInts[i] = Integer.parseInt(weights[i]);
        }
        return aInts;
    }

    private int minDiameter(int[] a, int[] b, int K) throws Exception {
        this.a = a;
        this.b = b;
        this.sideLength=a.length+1;
        return recursiveCheck(K, 0);
    }

    private int recursiveCheck(int K, int checkIndex) throws Exception {
        if (K == 0) {
            return getDiameter();
        }

        bridges.add(checkIndex);
        int min = recursiveCheck(K - 1, checkIndex + 1);
        bridges.remove(checkIndex);

        if (checkIndex + K < sideLength) {
            min = Math.min(min, recursiveCheck(K, checkIndex + 1));
        }
        return min;
    }

    private int getDiameter() throws Exception {
        debug("getting diameter for " + toString());
        Integer max = -1;
        int nodes = sideLength * 2;
        for (int i = 0; i < nodes - 1; i++) {
            for (int j = i + 1; j < nodes; j++) {
                int distance = getDistance(i, j);
                max = Math.max(max, distance);
            }
        }
        debug("max diameter " + max + " for " + toString());
        return max;
    }

    private void debug(String s) {
//        System.out.println(s);
//        System.out.flush();
    }

    private int getDistance(int n1, int n2) throws Exception {
        debug("get distance " + n1 + " " + n2);
        HashSet<Integer> visited = new HashSet<>();
        PriorityQueue<TargetNode> toVisit = new PriorityQueue<>();
        toVisit.add(new TargetNode(n1, 0));
        while (!toVisit.isEmpty()) {
            TargetNode node = toVisit.poll();
            debug("visit " + node);
            if (node.getId() == n2) {
                debug("distance is " + node.getCost());
                return node.getCost();
            }
            if (visited.contains(node.getId())) {
                continue;
            }
            visited.add(node.getId());
            Collection<TargetNode> nexts = node.getNexts();
            debug("nexts: " + nexts);
            for (TargetNode next : nexts) {
                toVisit.add(next);
            }
        }
        throw new Exception("no path " + n1 + " " + n2);
    }

    class TargetNode implements Comparable<TargetNode> {
        private int id;
        private int cost;

        TargetNode(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", cost=" + cost +
                    '}';
        }

        int getId() {
            return id;
        }

        int getCost() {
            return cost;
        }

        @Override
        public int compareTo(TargetNode other) {
            return this.cost - other.cost;
        }

        Collection<TargetNode> getNexts() {
            if (id < sideLength) {
                return getNextsPerSide(a, id, sideLength);
            }
            return getNextsPerSide(b, id - sideLength, 0);
        }

        Collection<TargetNode> getNextsPerSide(int side[], int idInSide, int moveToOtherSide) {
            Collection<TargetNode> nexts = new LinkedList<>();
            if (idInSide > 0) {
                nexts.add(new TargetNode(id - 1, cost + side[idInSide - 1]));
            }
            if (idInSide < sideLength - 1) {
                nexts.add(new TargetNode(id + 1, cost + side[idInSide]));
            }
            if (bridges.contains(idInSide)) {
                nexts.add(new TargetNode(idInSide + moveToOtherSide, cost));
            }
            return nexts;
        }
    }
}
