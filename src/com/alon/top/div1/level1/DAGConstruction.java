package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DAGConstruction implements InterfaceTopTest {
    private ArrayList<Integer> edges = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new TopUtil(new DAGConstruction()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }
        int[] x = util.getInts(matcher.group(1));
        int[] expected = util.getInts(matcher.group(2));
        int[] answer = construct(x);
        System.out.println(Arrays.toString(expected));
        System.out.println(Arrays.toString(answer));
    }

    private int[] construct(int[] x) {
        edges = new ArrayList<>();
        if (!locate(x)) {
            return new int[]{-1};
        }

        int[] result = new int[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            result[i] = edges.get(i);
        }
        return result;
    }

    private boolean locate(int[] x) {
        while (true) {
            boolean empty = true;
            Integer reduced = null;
            for (int i = 0; i < x.length; i++) {
                if (x[i] > 0) {
                    empty = false;
                }
                if (x[i] == 1) {
                    reduced = i;
                    break;
                }
            }
            if (empty) {
                return true;
            }
            if (reduced == null) {
                return false;
            }
            recurseReduce(x, reduced);
        }
    }

    private void recurseReduce(int[] x, int reduced) {
        x[reduced] = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] > 1) {
                x[i]--;
                edges.add(i);
                edges.add(reduced);
            }
        }
    }
}