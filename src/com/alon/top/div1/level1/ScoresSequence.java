package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ScoresSequence implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new ScoresSequence()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] s = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        double actual = count(s);
        util.assertEquals(expected, actual);
    }


    private int count(int[] s) throws Exception {
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i : s) {
            ints.add(i);
        }
        ints.sort(Collections.reverseOrder());
        Node nodes[] = new Node[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            nodes[i] = new Node();
            nodes[i].id = i;
        }
        for (int i = 0; i < ints.size(); i++) {
            Node node = nodes[i];
            for (int step = 0; step < ints.get(i); step++) {
                int nextNode = (i + step + 1) % nodes.length;
                node.to.add(nodes[nextNode]);
            }
        }
        int amount = 0;
        for (Node node : nodes) {
            amount += node.bfs();
        }
        return amount;
    }

    class Node {
        int id;
        LinkedList<Node> to = new LinkedList<>();

        int bfs() {
            HashSet<Node> toVisit = new HashSet<>();
            HashSet<Node> visited = new HashSet<>();
            toVisit.add(this);
            while (!toVisit.isEmpty()) {
                Node node = toVisit.iterator().next();
                toVisit.remove(node);
                visited.add(node);
                for (Node next : node.to) {
                    if (!visited.contains(next)) {
                        toVisit.add(next);
                    }
                }
            }
            return visited.size();
        }
    }
}

