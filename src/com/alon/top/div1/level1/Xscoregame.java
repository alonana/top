package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xscoregame implements InterfaceTopTest {
    private HashMap<String,Integer> cache = new HashMap<>();

    public static void main(String[] args) throws Exception {
        new TopUtil(new Xscoregame()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\S+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }
        int[] A = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = getScore(A);
        util.assertEquals(expected, actual);
    }

    private int getScore(int[] A){
        List<Integer> list = new LinkedList<>();
        for (int i:A){
            list.add(i);
        }
        return getMaxScore(0,list);
    }

    private int getMaxScore(int X, List<Integer> list) {
        if (list.isEmpty()){
            return X;
        }

        String key = X + list.toString();
        Integer cached = cache.get(key);
        if (cached!= null){
            return cached;
        }

        int max = 0;
        for (int i=0; i<list.size(); i++) {
            List<Integer> remaining = new LinkedList<>(list);
            int Y = remaining.remove(i);
           int newX = X + (X^Y);
            int score = getMaxScore(newX, remaining);
            max = Math.max(max,score);
        }

        cache.put(key,max);

        return max;
    }
}