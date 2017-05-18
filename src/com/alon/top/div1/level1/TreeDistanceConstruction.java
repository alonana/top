package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeDistanceConstruction implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new TreeDistanceConstruction()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}.*\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }
        int[] d = util.getInts(matcher.group(1));
        int[] expected = util.getInts(matcher.group(1));
        int[] answer = construct(d);
        util.assertEquals(expected, answer);
    }

    private int[] construct(int[] d) {
        List<Integer> mainDiameter = populateMainDiameter(d);
        if (mainDiameter == null) {
            return new int[0];
        }
        debug("main diameter:" + mainDiameter);
        List<Integer> edges = new LinkedList<>();
        for (int i = 1; i < mainDiameter.size(); i++) {
            edges.add(mainDiameter.get(i - 1));
            edges.add(mainDiameter.get(i));
        }
        int minPossible = 1 + mainDiameter.size() / 2;
        for (int i = 0; i < d.length; i++) {
            if (mainDiameter.contains(i)) {
                continue;
            }
            if (d[i] < minPossible) {
                return new int[0];
            }
            int positionOnMainDiameter = mainDiameter.size() - d[i] + 1;
            edges.add(mainDiameter.get(positionOnMainDiameter));
            edges.add(i);
        }
        int[] result = new int[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            result[i] = edges.get(i);
        }
        return result;
    }

    private void debug(Object o) {
        System.out.println(o);
    }

    private List<Integer> populateMainDiameter(int d[]) {
        int maxDiameter = 0;
        for (int x : d) {
            maxDiameter = Math.max(maxDiameter, x);
        }
        LinkedList<Integer> mainDiameter = new LinkedList<>();
        for (int sequenceInMain = 0; sequenceInMain < maxDiameter + 1; sequenceInMain++) {
            int requiredDistance = Math.max(maxDiameter - sequenceInMain, sequenceInMain);
            boolean located = false;
            for (int i = 0; i < d.length; i++) {
                if (!mainDiameter.contains(i) && d[i] == requiredDistance) {
                    mainDiameter.add(i);
                    located = true;
                    break;
                }
            }
            if (!located) {
                return null;
            }
        }
        return mainDiameter;
    }
}