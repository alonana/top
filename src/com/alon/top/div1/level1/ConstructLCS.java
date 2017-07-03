package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstructLCS implements InterfaceTopTest {

    private HashMap<String, Integer> cache;

    public static void main(String[] args) throws Exception {
        new TopUtil(new ConstructLCS()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int ab = Integer.parseInt(matcher.group(1));
        int bc = Integer.parseInt(matcher.group(2));
        int ca = Integer.parseInt(matcher.group(3));
        String result = construct(ab, bc, ca);
        System.out.println(result);
        validate(util, result, ab, bc, ca);
    }

    private void validate(TopUtil util, String result, int ab, int bc, int ca) throws Exception {
        String[] split = result.split(" ");
        String a = split[0];
        String b = split[1];
        String c = split[2];
        util.assertEquals(ab, lcs(a, b));
        util.assertEquals(ca, lcs(a, c));
        util.assertEquals(bc, lcs(c, b));
    }

    private int lcs(String a, String b) {
        System.out.println("check lcs of " + a + " " + b);
        cache = new HashMap<>();
        return recursiveLcs(a, b, "");
    }

    private int recursiveLcs(String a, String b, String identical) {
        if (a.isEmpty() || b.isEmpty()) {
            return identical.length();
        }
        String key = a + " " + b;
        Integer cached = cache.get(key);
        if (cached != null) {
            return identical.length() + cached;
        }
        int max = 0;
        max = Math.max(max, lcsUseFirstChar(a, b));
        max = Math.max(max, lcsUseFirstChar(b, a));
        max = Math.max(max, lcsSkipFirstChar(a, b));
        max = Math.max(max, lcsSkipFirstChar(b, a));
        cache.put(key, max);
        return max + identical.length();
    }

    private int lcsUseFirstChar(String a, String b) {
        char c = a.charAt(0);
        int position = b.indexOf(c);
        if (position == -1) {
            return 0;
        }
        b = b.substring(position + 1);
        return recursiveLcs(a.substring(1), b, "" + c);
    }

    private int lcsSkipFirstChar(String a, String b) {
        return recursiveLcs(a.substring(1), b, "");
    }

    private String construct(int ab, int bc, int ca) {
        if (ab <= bc) {
            if (bc <= ca) {
                String[] result = build(ab, bc, ca);
                return result[2] + " " + result[0] + " " + result[1];
            }
            if (ca <= ab) {
                String[] result = build(ca, ab, bc);
                return result[0] + " " + result[1] + " " + result[2];
            }
            String[] result = build(ab, ca, bc);
            return result[0] + " " + result[2] + " " + result[1];
        }
        if (ca <= bc) {
            String[] result = build(ca, bc, ab);
            return result[2] + " " + result[1] + " " + result[0];
        }
        if (ca <= ab) {
            String[] result = build(bc, ca, ab);
            return result[1] + " " + result[2] + " " + result[0];
        }
        String[] result = build(bc, ab, ca);
        return result[1] + " " + result[0] + " " + result[2];
    }

    private String[] build(int x, int y, int z) {
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        StringBuilder c = new StringBuilder();

        for (int i = 0; i < x; i++) {
            a.append(1);
        }
        for (int i = 0; i < y; i++) {
            a.append(0);
            b.append(0);
        }
        for (int i = 0; i < z; i++) {
            b.append(1);
            c.append(1);
        }
        return new String[]{a.toString(), b.toString(), c.toString()};
    }
}