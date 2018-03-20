package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreesAndBrackets implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new TreesAndBrackets()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\",\\s+\"(.*)\"\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String t1 = matcher.group(1);
        String t2 = matcher.group(2);
        String expected = matcher.group(3);
        String actual = check(t1, t2);
        util.assertEquals(expected, actual);
    }

    private String check(String t1, String t2) {
        Node node1 = buildTree(t1);
        Node node2 = buildTree(t2);
        if (match(node1, node2)) {
            return "Possible";
        }
        return "Impossible";
    }

    private Node buildTree(String s) {
        Node node = recursiveBuildTree(s, 0, null);
        node.print("");
        return node;
    }

    private Node recursiveBuildTree(String s, int position, Node parent) {
        char c = s.charAt(position);
        position++;
        if (c == '(') {
            Node n = new Node(parent);
            recursiveBuildTree(s, position, n);
            return n;
        }
        if (position == s.length()) {
            return parent;
        }
        return recursiveBuildTree(s, position, parent.getParent());
    }

    private boolean match(Node n1, Node n2) {
        Iterator<Node> n1Iterator = n1.getChildren().iterator();
        Iterator<Node> n2Iterator = n2.getChildren().iterator();
        Node n2Current = null;
        while (true) {
            if (n2Current == null) {
                if (!n2Iterator.hasNext()) {
                    return true;
                }
                n2Current = n2Iterator.next();
            }
            if (!n1Iterator.hasNext()) {
                return false;
            }
            Node n1Current = n1Iterator.next();
            if (match(n1Current, n2Current)) {
                n2Current = null;
            }
        }
    }

    class Node {
        private Node parent;
        private List<Node> children = new LinkedList<>();

        Node(Node parent) {
            this.parent = parent;
            if (parent != null) {
                parent.children.add(this);
            }
        }

        List<Node> getChildren() {
            return children;
        }

        public void print(String prefix) {
            if (children.isEmpty()) {
                println(prefix + "()");
            } else {
                println(prefix + "(");
                for (Node child : children) {
                    child.print(prefix + "   ");
                }
                println(prefix + ")");
            }
        }

        public Node getParent() {
            return parent;
        }
    }

    private static void println(String s) {
        System.out.println(s);
        System.out.flush();
        System.err.flush();
    }
}