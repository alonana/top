package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArmorUp implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new ArmorUp()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+)\\s+(\\S+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int maxHP = Integer.parseInt(matcher.group(1));
        int currentHP = Integer.parseInt(matcher.group(2));
        int k = Integer.parseInt(matcher.group(3));
        double expected = Double.parseDouble(matcher.group(4));
        double actual = minimalDamage(maxHP, currentHP, k);
        util.assertEquals(expected, actual);
    }

    private double minimalDamage(int maxHP, int currentHP, int k) {
        double low = 0;
        double high = maxHP * 100.0;
        while (true) {
            double diff = Math.abs(high - low);
            if (diff < 10E-6) {
                return high;
            }
            double mid = (low + high) / 2;
            if (isKill(mid, maxHP, currentHP, k)) {
                high = mid;
            } else {
                low = mid;
            }
        }
    }

    private boolean isKill(double damage, int maxHP, int currentHP, int k) {
        double life = currentHP;
        for (int i = 0; i < k; i++) {
            life -= damage * (0.5 + life / (maxHP * 2));
            if (life <= 0) {
                return true;
            }
        }
        return false;
    }
}