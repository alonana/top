package com.alon.top.div2.level2;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ABBA implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new ABBA().check();
    }

    private void check() throws Exception {
        new TopUtil(this).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Pattern pattern = Pattern.compile("\"(\\S+)\", \"(\\S+)\"\\s+\"(.*)\"");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new Exception("not matched " + line);
        }
        String initial = matcher.group(1);
        String target = matcher.group(2);
        String expectedString = matcher.group(3);
        String actual = canObtain(initial, target);
        if (!actual.equals(expectedString)) {
            throw new Exception("expected " + expectedString + " actual " + actual);
        }
    }

    private String canObtain(String initial, String target) {
        HashSet<String> toVisit = new HashSet<>();
        toVisit.add(initial);
        HashSet<String> visited = new HashSet<>();
        while (!toVisit.isEmpty()) {
            String node = toVisit.iterator().next();
//            System.out.println("   " + node);
            if (node.equals(target)) {
                return "Possible";
            }
            toVisit.remove(node);
            visited.add(node);
            if (!target.contains(node) && !target.contains(reverse(node))) {
                continue;
            }
            String next = node + "A";
            if (!visited.contains(next)) {
                toVisit.add(next);
            }
            next = reverse(node) + "B";
            if (!visited.contains(next)) {
                toVisit.add(next);
            }
        }
        return "Impossible";
    }

    private String reverse(String node) {
        return new StringBuilder(node).reverse().toString();
    }
}