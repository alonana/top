package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArmySize implements InterfaceTopTest {

    private static HashMap<String, int[]> amounts = new LinkedHashMap<>();

    static {
        amounts.put("Few", new int[]{1, 4});
        amounts.put("Several", new int[]{5, 9});
        amounts.put("Pack", new int[]{10, 19});
        amounts.put("Lots", new int[]{20, 49});
        amounts.put("Horde", new int[]{50, 99});
        amounts.put("Throng", new int[]{100, 249});
        amounts.put("Swarm", new int[]{250, 499});
        amounts.put("Zounds", new int[]{500, 999});
        amounts.put("Legion", new int[]{1000, -1});
    }

    public static void main(String[] args) throws Exception {
        new TopUtil(new ArmySize()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }


        String[] units = util.getStrings(matcher.group(1));
        String[] expected = util.getStrings(matcher.group(2));
        String actual[] = getSum(units);
        util.assertEquals(expected, actual);
    }

    private String[] getSum(String[] units) {
        int min = 0;
        int max = 0;

        for (String unit : units) {
            min += amounts.get(unit)[0];
            int limit = amounts.get(unit)[1];
            if (limit == -1 || max == -1) {
                max = -1;
            } else {
                max += limit;
            }
        }

        ArrayList<String> range = new ArrayList<>();
        for (String name : amounts.keySet()) {
            int[] limits = amounts.get(name);
            int unitMin = limits[0];
            int unitMax = limits[1];
            if ((unitMax == -1 || min <= unitMax) && (max == -1 || max >= unitMin)) {
                range.add(name);
            }
        }
        return range.toArray(new String[0]);
    }


}
