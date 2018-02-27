package com.alon.top.div1.level1;

import java.util.ArrayList;
import java.util.HashSet;

public class SkolemBinaryTree {

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 100; i++) {
            System.out.println("checking size " + i);
            SkolemBinaryTree tree = new SkolemBinaryTree();
            int[] edges = tree.construct(i);
            if (!tree.isSkolem(edges, i)) {
                throw new Exception("failed");
            }
        }
    }

    private int[] construct(int k) {
        ArrayList<Integer> edges = new ArrayList<>();
        edges.add(0);
        edges.add(1);

        if (k == 2) {
            edges.add(2);
            edges.add(0);

            edges.add(3);
            edges.add(0);
        } else {
        }


        for (int i = 1; i < k; i++) {
        }

        int result[] = new int[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            result[i] = edges.get(i);
        }
        return result;
    }

    private boolean isSkolem(int edges[], int n) throws Exception {
        for (int checkedNumber = 1; checkedNumber <= n; checkedNumber++) {
            int value1 = 2 * checkedNumber - 1;
            int value2 = value1 - 1;
            if (distance(edges, value1, value2) != checkedNumber) {
                return false;
            }
        }

        return true;
    }

    private int distance(int edges[], int from, int to) throws Exception {
        int distance = 0;
        HashSet<Integer> toVisit = new HashSet<>();
        toVisit.add(from);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(from);
        while (!toVisit.isEmpty()) {
            distance++;
            for (int i = 0; i < edges.length - 1; i += 2) {
                int edgeSide1 = edges[i];
                int edgeSide2 = edges[i + 1];
                if (visited.contains(edgeSide1)) {
                    if (!visited.contains(edgeSide2)) {
                        toVisit.add(edgeSide2);
                    }
                }
                if (visited.contains(edgeSide2)) {
                    if (!visited.contains(edgeSide1)) {
                        toVisit.add(edgeSide1);
                    }
                }
            }
            if (toVisit.contains(to)) {
                return distance;
            }
            if (toVisit.equals(visited)) {
                throw new Exception("failed");
            }
            visited.addAll(toVisit);
        }

        return -1;
    }
}