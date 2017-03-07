package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuckyCycle {
    public static void main(String args[]) throws Exception {
        new LuckyCycle().check();
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
        System.out.println("checking line " + line);
        Matcher matcher = Pattern.compile("\\{(.*)}, \\{(.*)}, \\{(.*)}\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("line not matched");
        }
        int[] edge1 = getInts(matcher.group(1));
        int[] edge2 = getInts(matcher.group(2));
        int[] weight = getInts(matcher.group(3));
        int[] expected = getInts(matcher.group(4));
        LinkedList<int[]> results = getEdge(edge1, edge2, weight);
        for (int[] actual : results) {
            System.out.println("actual result " + Arrays.toString(actual));
            if (Arrays.equals(expected, actual)) {
                return;
            }
        }
        throw new Exception("expected " + Arrays.toString(expected));
    }

    private int[] getInts(String s) {
        String ints[] = s.trim().replace(" ", "").split(",");
        if (ints.length == 1 && ints[0].isEmpty()) {
            return new int[]{};
        }
        int result[] = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            result[i] = Integer.parseInt(ints[i]);
        }
        return result;
    }

    private LinkedList<int[]> getEdge(int[] edge1, int[] edge2, int[] weight) throws Exception {
        HashMap<Edge, Integer> edges = new HashMap<>();
        for (int i = 0; i < edge1.length; i++) {
            int n1 = edge1[i];
            int n2 = edge2[i];
            int w = weight[i];
            edges.put(new Edge(n1, n2), w);
        }


        LinkedList<int[]> results = new LinkedList<>();
        HashMap<Edge, WeightCounter> counters = findCounters(edges);

        System.out.println("final counters");
        debugPrint(counters);


        for (Edge edge : counters.keySet()) {
            WeightCounter counter = counters.get(edge);
            int[] newEdge = counter.findEdge(edge);
            if (newEdge != null) {
                results.add(newEdge);
            }
        }

        if (results.isEmpty()) {
            results.add(new int[]{});
        }
        return results;
    }

    private void debugPrint(HashMap<Edge, WeightCounter> counters) {
        System.out.println("counters");
        LinkedList<String> x = new LinkedList<>();
        for (Edge edge : counters.keySet()) {
            WeightCounter counter = counters.get(edge);
            x.add(edge + " = " + counter);
        }
        Collections.sort(x);
        for (String s : x) {
            System.out.println(s);
        }
    }

    private HashMap<Edge, WeightCounter> findCounters(HashMap<Edge, Integer> edges) throws Exception {
        HashMap<Edge, WeightCounter> counters = new HashMap<>();
        for (Edge edge : edges.keySet()) {
            System.out.println("checking edge " + edge);
            int weight = edges.get(edge);

            addCounters(counters, edge, weight);
        }
        return counters;
    }

    private void addCounters(HashMap<Edge, WeightCounter> counters, Edge edge, int weight) throws CloneNotSupportedException {
        HashSet<Edge> toVisit = new HashSet<>();
        HashMap<Edge, WeightCounter> addedCounters = new HashMap<>();
        counters.put(edge, new WeightCounter(weight));
        toVisit.add(edge);
        while (!toVisit.isEmpty()) {
            Edge nextEdge = toVisit.iterator().next();
            System.out.println("scan edge " + nextEdge);
            toVisit.remove(nextEdge);
            WeightCounter nextWeight = counters.get(nextEdge);
            for (Edge existingEdge : counters.keySet()) {
                WeightCounter existingWeight = counters.get(existingEdge);
                Edge derivedEdge = existingEdge.deriveEdge(nextEdge);
                if (derivedEdge != null && !counters.containsKey(derivedEdge)) {
                    WeightCounter derivedWeight = existingWeight.deriveNew(nextWeight);
                    addedCounters.put(derivedEdge, derivedWeight);
                    toVisit.add(derivedEdge);
                }
            }
            counters.putAll(addedCounters);
            debugPrint(counters);
        }
    }

    class Edge {
        private int n1;
        private int n2;
        private String key;

        Edge(int node1, int node2) {
            n1 = Math.min(node1, node2);
            n2 = Math.max(node1, node2);
            key = n1 + "-" + n2;
        }

        int getNode1() {
            return n1;
        }

        int getNode2() {
            return n2;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return key;
        }

        @SuppressWarnings("all")
        @Override
        public boolean equals(Object other) {
            Edge otherEdge = (Edge) other;
            return this.key.equals(otherEdge.key);
        }

        Edge deriveEdge(Edge other) {
            if (other.equals(this)) {
                return null;
            }
            if (this.n1 == other.n1) {
                return new Edge(this.n2, other.n2);
            }
            if (this.n1 == other.n2) {
                return new Edge(this.n2, other.n1);
            }
            if (this.n2 == other.n1) {
                return new Edge(this.n1, other.n2);
            }
            if (this.n2 == other.n2) {
                return new Edge(this.n1, other.n1);
            }
            return null;
        }
    }

    class WeightCounter implements Cloneable {
        private int fours;
        private int sevens;

        WeightCounter(int w) {
            add(w);
        }

        @Override
        public String toString() {
            return "{" +
                    "four=" + fours +
                    ", seven=" + sevens +
                    '}';
        }

        void add(int w) {
            if (w == 4) {
                fours++;
            } else {
                sevens++;
            }
        }

        int[] findEdge(Edge edge) {
            if (fours + sevens < 3) {
                return null;
            }
            if (Math.abs(fours - sevens) != 1) {
                return null;
            }
            int newWeight;
            if (fours > sevens) {
                newWeight = 7;
            } else {
                newWeight = 4;
            }
            return new int[]{edge.getNode1(), edge.getNode2(), newWeight};
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        WeightCounter deriveNew(WeightCounter nextWeight) {
            WeightCounter c = new WeightCounter(4);
            c.fours = this.fours + nextWeight.fours;
            c.sevens = this.sevens + nextWeight.sevens;
            return c;
        }
    }
}
