package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BearPlaysDiv2 {
    public static void main(String[] args) throws Exception {
        new BearPlaysDiv2().check();
    }

    private void check() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(getClass().getSimpleName() + ".txt");
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
        if (line.trim().isEmpty()) {
            return;
        }

        System.out.println("checking " + line);
        Pattern pattern = Pattern.compile("(\\d+), (\\d+), (\\d+).*\"(.*)\"");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("not matched " + line);
        }
        String a = matcher.group(1);
        String b = matcher.group(2);
        String c = matcher.group(3);
        String expectedString = matcher.group(4);
        String actual = equalPiles(Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c));
        if (!actual.equals(expectedString)) {
            throw new Exception("expected " + expectedString + " actual " + actual);
        }
    }

    private String equalPiles(int A, int B, int C) {
        HashSet<Node> toVisit = new HashSet<>();
        HashSet<Node> visited = new HashSet<>();
        toVisit.add(new Node(A, B, C));
        while (!toVisit.isEmpty()) {
            Node node = toVisit.iterator().next();
            toVisit.remove(node);
            visited.add(node);
            if (node.pilesSame()) {
                return "possible";
            }
            for (Node next : node.nexts()) {
                if (!visited.contains(next)) {
                    toVisit.add(next);
                }
            }
        }
        return "impossible";
    }

    class Node {
        private List<Integer> piles = new LinkedList<>();
        private String key;

        Node(int A, int B, int C) {
            piles.add(A);
            piles.add(B);
            piles.add(C);
            Collections.sort(piles);
            key = piles.get(0) + "_" + piles.get(1) + "_" + piles.get(2);
        }

        boolean pilesSame() {
            return piles.get(0).equals(piles.get(1)) &&
                    piles.get(1).equals(piles.get(2));
        }

        @Override
        public boolean equals(Object other) {
            Node n = (Node) other;
            return this.key.equals(n.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        Set<Node> nexts() {
            Set<Node> result = new HashSet<>();
            addNodeIfPossible(result, 0, 1, 2);
            addNodeIfPossible(result, 0, 2, 1);
            addNodeIfPossible(result, 1, 0, 2);
            addNodeIfPossible(result, 1, 2, 0);
            addNodeIfPossible(result, 2, 0, 1);
            addNodeIfPossible(result, 2, 1, 0);
            return result;
        }

        private void addNodeIfPossible(Set<Node> result, int i1, int i2, int i3) {
            int n1 = piles.get(i1);
            int n2 = piles.get(i2);
            int n3 = piles.get(i3);
            if (n1 >= n2) {
                return;
            }
            n2 -= n1;
            n1 *= 2;
            result.add(new Node(n1, n2, n3));
        }
    }
}
