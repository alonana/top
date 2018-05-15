package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiplicationTable2 implements InterfaceTopTest {
    private Groups groups;
    private int[] table;
    private int N;

    public static void main(String[] args) throws Exception {
        new TopUtil(new MultiplicationTable2()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] table = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = minimalGoodSet(table);
        util.assertEquals(expected, actual);
    }

    private int minimalGoodSet(int[] table) {
        this.table = table;
        this.N = (int) Math.sqrt(table.length);
        this.groups = new Groups();

        printTable();

        for (int i = 0; i < N; i++) {
            addElement(i);
        }
        System.out.println(groups);
        return groups.getMinGroupSize();
    }

    private void printTable() {
        StringBuilder out = new StringBuilder();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                out.append(String.format("%2d ", table[r * N + c]));
            }
            out.append("\n");
        }
        System.out.println("table:\n" + out + "\n");
    }

    private void addElement(int addedElement) {
        HashSet<Integer> g = groups.addGroup();
        HashSet<Integer> pending = new HashSet<>();
        pending.add(addedElement);
        while (!pending.isEmpty()) {
            int current = pending.iterator().next();
            pending.remove(current);
            HashSet<Integer> nexts = findNexts(g, current);
            nexts.removeAll(g);
            pending.addAll(nexts);
            g.add(current);
        }
    }

    HashSet<Integer> findNexts(HashSet<Integer> g, int i) {
        HashSet<Integer> nexts = new HashSet<>();
        nexts.add(table[i * N + i]);
        for (int j : g) {
            nexts.add(table[i * N + j]);
            nexts.add(table[j * N + i]);
        }
        return nexts;
    }

    class Groups {
        private List<HashSet<Integer>> groups = new LinkedList<>();

        HashSet<Integer> addGroup() {
            HashSet<Integer> g = new HashSet<>();
            groups.add(g);
            return g;
        }

        Integer getMinGroupSize() {
            Integer min = null;
            for (HashSet<Integer> g : groups) {
                if (min == null || min > g.size()) {
                    min = g.size();
                }
            }
            return min;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("Groups\n");
            for (HashSet<Integer> group : groups) {
                result.append(group);
                result.append("\n");
            }

            return result.toString();
        }
    }
}
