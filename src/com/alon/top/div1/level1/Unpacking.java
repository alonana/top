package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unpacking implements InterfaceTopTest {
    private static int INF = (int) 1e9;

    public static void main(String[] args) throws Exception {
        new TopUtil(new Unpacking()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, \\{(.*)}, \\{(.*)}, (\\d+)\\s+(-?\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] a = util.getInts(matcher.group(1));
        int[] b = util.getInts(matcher.group(2));
        int[] cost = util.getInts(matcher.group(3));
        int K = Integer.parseInt(matcher.group(4));
        int expected = Integer.parseInt(matcher.group(5));
        int actual = getcost(a, b, cost, K);
        util.assertEquals(expected, actual);
    }

    private int getcost(int[] a, int[] b, int[] cost, int K) {
        int cheapest = INF;
        int[] tempAmount = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            tempAmount[i] = a[i] + b[i];
        }
        cheapest = Math.min(cheapest, findCheapest(tempAmount, cost, 2 * K - 1));

        for (int i = 0; i < a.length; i++) {
            tempAmount[i] = a[i] - 1;
        }
        cheapest = Math.min(cheapest, findCheapest(tempAmount, cost, K));

        for (int i = 0; i < a.length; i++) {
            tempAmount[i] = b[i] - 1;
        }
        cheapest = Math.min(cheapest, findCheapest(tempAmount, cost, K));

        if (cheapest == INF) {
            return -1;
        }

        return cheapest;
    }

    private int findCheapest(int[] a, int[] cost, int requiredAmount) {
        int costByAmount[] = new int[requiredAmount+1];
        Arrays.fill(costByAmount, INF);
        costByAmount[0] = 0;
        for (int i = 0; i < a.length; i++) {
            int addedAmount = a[i];
            int addedCost = cost[i];
            for (int amountIndex = costByAmount.length - 1; amountIndex >= 0; amountIndex--) {
                int costForAmount = costByAmount[amountIndex];
                if (costForAmount == INF) {
                    // cannot reach to this amount yet
                    continue;
                }
                int newAmount = amountIndex + addedAmount;
                if (newAmount > requiredAmount) {
                    // don't care if we get more than required
                    newAmount = requiredAmount ;
                }
                int newCostWithThisAmount = costForAmount + addedCost;
                costByAmount[newAmount] = Math.min(newCostWithThisAmount, costByAmount[newAmount]);
            }
        }

        return costByAmount[requiredAmount ];
    }

}
