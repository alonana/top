package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EllysTickets implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new EllysTickets()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, (\\d+)\\s+(\\d+.\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] prices = util.getInts(matcher.group(1));
        int fine = Integer.parseInt(matcher.group(2));
        double expected = Double.parseDouble(matcher.group(3));
        double actual = getPrice(prices, fine);
        util.assertEquals(expected, actual);
    }

    private double getPrice(int[] ticketPrice, int fine) {
        Double best = (double)fine;
        for (int i = 0; i < ticketPrice.length; i++) {
            double expected = (i * fine + (ticketPrice.length - i) * ticketPrice[i]) / (double) ticketPrice.length;
            //System.out.println("expected at location " + i + " is " + expected);
            if (expected < best) {
                best = expected;
            }
        }
        return best;
    }

}