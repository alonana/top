package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Permatchd2 {
    private HashMap<String, Integer> cache;

    public static void main(String[] args) throws Exception {
        new Permatchd2().check();
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

        Pattern pattern = Pattern.compile("\\{(.*)}\\s+(-?\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed: " + line);
        }
        String[] graph = getStrings(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));

        int actual = fix(graph);
        if (actual != expected) {
            throw new Exception("expected " + expected + " actual " + actual);
        }
    }

    private String[] getStrings(String s) {
        s = s.replace(" ", "");
        s = s.replace("\"", "");
        return s.split(",");
    }

    private void print(String s) {
        System.out.println(s);
        System.out.flush();
        System.err.flush();
    }

    private int fix(String[] graph) {
        cache = new HashMap<>();
        List<HashSet<Integer>> nodesGroups = new LinkedList<>();
        for (int i = 0; i < graph.length; i++) {
            if (!isIncluded(nodesGroups, i)) {
                nodesGroups.add(findConnected(i, graph));
            }
        }

        List<Group> groups = new LinkedList<>();
        for (HashSet<Integer> nodes : nodesGroups) {
            groups.add(calculateGroup(nodes, graph));
        }

        return recursiveFix(groups);
    }

    private boolean isIncluded(List<HashSet<Integer>> nodesGroups, int i) {
        for (HashSet<Integer> nodes : nodesGroups) {
            for (Integer node : nodes) {
                if (node == i) {
                    return true;
                }
            }
        }
        return false;
    }

    private HashSet<Integer> findConnected(int i, String[] graph) {
        HashSet<Integer> visited = new HashSet<>();
        HashSet<Integer> toVisit = new HashSet<>();
        toVisit.add(i);
        while (!toVisit.isEmpty()) {
            int node = toVisit.iterator().next();
            toVisit.remove(node);
            if (visited.contains(node)) {
                continue;
            }
            visited.add(node);
            String connections = graph[node];
            for (int next = 0; next < connections.length(); next++) {
                if (connections.charAt(next) == 'Y') {
                    toVisit.add(next);
                }
            }
        }
        return visited;
    }

    private Group calculateGroup(HashSet<Integer> nodes, String[] graph) {
        int edges = 0;
        for (Integer node : nodes) {
            String connections = graph[node];
            int count = connections.replace("N", "").length();
            edges += count;
        }

        return new Group(nodes.size(), edges / 2);
    }

    private int recursiveFix(List<Group> groups) {
        Collections.sort(groups);
        String key = groups.toString();
        Integer cached = cache.get(key);
        if (cached != null) {
            return cached;
        }

        Integer result = null;
        for (Group group : groups) {
            if (group.isOdd()) {
                result = changeGroup(groups, group);
                break;
            }
        }
        if (result == null) {
            result = 0;
        }

        cache.put(key, result);
        return result;
    }

    private int changeGroup(List<Group> groups, Group group) {
        int min = -1;
        if (group.canAdd()) {
            List<Group> newGroups = new LinkedList<>(groups);
            newGroups.remove(group);
            newGroups.add(group.cloneWithAddEdge());
            min = findNewMinIncrement(min, recursiveFix(newGroups));
        }
        for (Group merged : groups) {
            if (merged == group) {
                continue;
            }
            List<Group> newGroups = new LinkedList<>(groups);
            newGroups.remove(group);
            newGroups.remove(merged);
            newGroups.add(group.cloneMerge(merged));
            min = findNewMinIncrement(min, recursiveFix(newGroups));
        }
        return min;
    }

    private int findNewMinIncrement(int min, int newValue) {
        if (newValue == -1) {
            return min;
        }
        newValue++;
        if (min == -1) {
            return newValue;
        }

        return Math.min(min, newValue);
    }

    private class Group implements Comparable<Group> {
        private int nodes;
        private int edges;

        Group(int nodes, int edges) {
            this.nodes = nodes;
            this.edges = edges;
        }

        boolean canAdd() {
            int max = nodes * (nodes - 1) / 2;
            return max > edges;
        }

        boolean isOdd() {
            return edges % 2 == 1;
        }

        Group cloneWithAddEdge() {
            return new Group(nodes, edges + 1);
        }

        Group cloneMerge(Group merged) {
            return new Group(this.nodes + merged.nodes, this.edges + merged.edges + 1);
        }

        @Override
        public String toString() {
            return "Group{" +
                    "nodes=" + nodes +
                    ", edges=" + edges +
                    '}';
        }

        @Override
        public int compareTo(Group o) {
            int nodesDiff = this.nodes - o.nodes;
            if (nodesDiff != 0) {
                return nodesDiff;
            }
            return this.edges - o.edges;
        }
    }
}