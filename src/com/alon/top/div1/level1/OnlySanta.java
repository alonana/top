package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnlySanta implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new OnlySanta()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("^\\s*\"([A-Z]*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }
        String S = matcher.group(1);
        solve(S);
    }

    private void solve(String S) throws Exception {
        String result = convert(S);
        System.out.println("S: " + S + " result: " + result);
        if (!contains(result, "SANTA")) {
            throw new Exception("santa missing");
        }
        if (contains(result, "SATAN")) {
            throw new Exception("satan found");
        }
    }

    private boolean contains(String S, String subset) {
        int sIndex = 0;
        for (int subsetIndex = 0; subsetIndex < subset.length(); subsetIndex++) {
            char subsetChar = subset.charAt(subsetIndex);
            while (subsetChar != S.charAt(sIndex)) {
                sIndex++;
                if (sIndex == S.length()) {
                    return false;
                }
            }
        }
        return true;
    }

    private int indexOf(String S, String subset) {
        int sIndex = 0;
        for (int subsetIndex = 0; subsetIndex < subset.length(); subsetIndex++) {
            char subsetChar = subset.charAt(subsetIndex);
            while (subsetChar != S.charAt(sIndex)) {
                sIndex++;
                if (sIndex == S.length()) {
                    return -1;
                }
            }
        }
        return sIndex;
    }

    private String convert(String S) {
        if (contains(S, "SATA")) {
            int i = indexOf(S, "SA");
            return S.substring(0, i+1) + "N" + S.substring(i + 1);
        }
        if (contains(S, "SANTA")) {
            return S;
        }
        if (contains(S, "SANT")) {
            return S + "A";
        }
        if (contains(S, "SAN")) {
            return S + "TA";
        }
        if (contains(S, "SA")) {
            return S + "NTA";
        }
        if (contains(S, "S")) {
            return S + "ANTA";
        }
        return S + "SANTA";
    }
}