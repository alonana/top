package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmilesTheFriendshipUnicorn implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new SmilesTheFriendshipUnicorn()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), \\{(.*)}, \\{(.*)}\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] A = util.getInts(matcher.group(2));
        int[] B = util.getInts(matcher.group(3));
        String expected = matcher.group(4);
        String actual = hasFriendshipChain(A, B);
        util.assertEquals(expected, actual);
    }

    private String hasFriendshipChain( int[] A, int[] B) {
        Graph g = buildGraph(A, B);
        if (hasSimplePath(g)) {
            return "Yay!";
        }
        return ":(";
    }

    private boolean hasSimplePath(Graph g) {
        for (Node n : g.getNodes()) {
            debug("check node start "+n);
            if (hasSimplePathStartWith(n, new HashSet<>(), 4)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSimplePathStartWith(Node current, HashSet<Node> visited, int requiredLength) {
        if (requiredLength == 0) {
            return true;
        }

        HashSet<Node> nextVisited = new HashSet<>(visited);
        nextVisited.add(current);

        for (Node next : current.getNeighbors()) {
            if (visited.contains(next)) {
                continue;
            }
            if (hasSimplePathStartWith(next, nextVisited, requiredLength - 1)) {
                return true;
            }
        }

        return false;
    }

    private Graph buildGraph(int[] A, int[] B) {
        Graph g = new Graph();
        for (int i = 0; i < A.length; i++) {
            g.addEdge(A[i], B[i]);
        }
        return g;
    }

    class Graph {
        private HashMap<Integer, Node> nodes = new HashMap<>();

        void addEdge(int a, int b) {
            Node nodeA = getOrCreate(a);
            Node nodeB = getOrCreate(b);
            nodeA.addEdge(nodeB);
            nodeB.addEdge(nodeA);
        }

        Node getOrCreate(int id) {
            Node n = nodes.get(id);
            if (n == null) {
                n = new Node(id);
                nodes.put(id, n);
            }
            return n;
        }

        Collection<Node> getNodes() {
            return nodes.values();
        }
    }

    class Node {
        private Collection<Node> neighbors = new HashSet<>();
        private int id;

        Node(int id) {
            this.id = id;
        }

        void addEdge(Node n) {
            neighbors.add(n);
        }

        Collection<Node> getNeighbors() {
            return neighbors;
        }

        @Override
        public String toString() {
            return Integer.toString(id);
        }
    }

    static void debug(String m){
        if (false) {
            System.out.println(m);
        }
    }
}
