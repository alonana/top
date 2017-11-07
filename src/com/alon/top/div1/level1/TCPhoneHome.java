package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCPhoneHome implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new TCPhoneHome()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), \\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int digits = Integer.parseInt(matcher.group(1));
        String[] specialPrefixes = util.getStrings(matcher.group(2));
        long expected = Long.parseLong(matcher.group(3));
        long actual = validNumbers(digits, specialPrefixes);
        util.assertEquals(expected, actual);
    }

    private long validNumbers(int digits, String[] specialPrefixes) {
        long result = (long) Math.pow(10, digits);
        List<String> prefixes = new LinkedList<>();
        prefixes.addAll(Arrays.asList(specialPrefixes));
        prefixes.sort(new LengthComparator());
        Set<String> visited = new HashSet<>();
        for (String p : prefixes) {
            if (prefixIncluded(p, visited)) {
                continue;
            }
            visited.add(p);
            result -= (long) Math.pow(10, digits - p.length());
        }
        return result;
    }

    private boolean prefixIncluded(String p, Set<String> visited) {
        for (String v : visited) {
            if (p.startsWith(v)) {
                return true;
            }
        }
        return false;
    }

    class LengthComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }
}