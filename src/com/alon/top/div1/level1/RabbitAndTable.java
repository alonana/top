package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RabbitAndTable implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new RabbitAndTable()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int[] x = util.getInts(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = count(x);
        util.assertEquals(expected, actual);
    }

    private static final long MOD = 1000000007;

    private int count(int[] x) {
        long result = 0;
        int max = Arrays.stream(x).max().getAsInt();
        for (int i = 1; i <= max; i++) {
            long alternatives = findAlternatives(x, i);
            System.out.println("table size " + i + " alternatives " + alternatives);
            result += alternatives % MOD;
        }
        return (int) (result % MOD);
    }

    private long findAlternatives(int[] x, int tableSize) {
        int n = 0;
        for (int i : x) {
            if (i >= tableSize) {
                n++;
            }
        }
        if (n < tableSize) {
            return 0;
        }
        int tables = n / tableSize;
        int lastTableSize = n % tableSize;
        if (lastTableSize != 0) {
            tables++;
        }
        return factorial(n, tables, tableSize, lastTableSize);
    }

    private long factorial(int n, int tables, int tableSize, int lastTableSize) {
        int perTableOptions = 1;
        for (int i = 1; i <= tableSize; i++) {
            perTableOptions = perTableOptions * i;
        }

        long result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }
        if (lastTableSize == 0) {
            for (int i = 1; i <= tables; i++) {
                result = result / i;
            }
            if (perTableOptions>1) {
                result = result / (perTableOptions * tables);
            }
        }else {
            for (int i = 1; i <= tables-1; i++) {
                result = result / i;
            }
            result = result / (perTableOptions * (tables-1));
            for (int i = 1; i <= lastTableSize; i++) {
                result = result / i;
            }
        }
        return result;
    }
}