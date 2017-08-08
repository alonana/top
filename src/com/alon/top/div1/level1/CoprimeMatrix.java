package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoprimeMatrix implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new CoprimeMatrix()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String[] coprime = util.getStrings(matcher.group(1));
        String expected = matcher.group(2);
        String actual = isPossible(coprime);
        util.assertEquals(expected, actual);
    }

    private String isPossible(String[] coprime) {
        for (int i = 0; i < coprime.length; i++) {
            for (int j = i; j < coprime.length; j++) {
                if (coprime[i].charAt(j) != coprime[j].charAt(i)) {
                    return "Impossible";
                }
            }
        }
        for (int i = 1; i < 100000000; i++) {
            if (isSequenceOk(coprime, i)) {
                System.out.println("located start index " + i);
                return "Possible";
            }
        }
        return "Impossible";
    }

    private boolean isSequenceOk(String[] coprime, int firstIndex) {
        for (int i = 0; i < coprime.length; i++) {
            if (!isRowOk(coprime[i], firstIndex, firstIndex + i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRowOk(String coprimeRow, int firstNumber, int currentNumber) {
        for (int i = 0; i < coprimeRow.length(); i++) {
            boolean coPrimeRequired = coprimeRow.charAt(i) == 'Y';
            int otherNumber = firstNumber + i;
            if (isCoPrime(currentNumber, otherNumber) != coPrimeRequired) {
                return false;
            }
        }
        return true;
    }

    private boolean isCoPrime(int a, int b) {
        while (b > 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a == 1;
    }
}