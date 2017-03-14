package com.alon.top.div2.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robofactory implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new Robofactory()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}.*\\{(.*)}\\s+(\\S+)").matcher(line);
        if (!matcher.find()){
            throw new Exception("parse failed");
        }
        int[] query = util.getInts(matcher.group(1));
        String[] answer= util.getStrings(matcher.group(2));
        int expected = Integer.parseInt(matcher.group(3));
        int actual = reveal(query,answer);
        util.assertEquals(expected,actual);
    }

    private int reveal(int[] query, String[] answer) {
        List<Integer > candidates = new LinkedList<>();
        for (int i = 0; i < query.length; i++) {
            boolean odd = query[i] % 2 == 1;
            String expected = odd ? "Odd" : "Even";
            if (!answer[i].equals(expected)) {
                return i;
            }

            if (i == 0) {
                candidates.add(0);
                continue;
            }
            boolean prevOdd = query[i - 1] % 2 == 1;
            if (!prevOdd) {
                candidates.add(i);
            }
        }
        if (candidates.size()==1) {
            return candidates.get(0);
        }
        return -1;
    }
}
