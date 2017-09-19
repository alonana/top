package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnclearNotes implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new UnclearNotes()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\"(.*)\", \"(.*)\"\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String S = matcher.group(1);
        String T = matcher.group(2);
        String expected = matcher.group(3);
        String actual = isMatch(S, T);
        util.assertEquals(expected, actual);
    }

    private String isMatch(String S, String T) {
        HashMap<Character, Character> mix = new HashMap<>();
        mix.put('o', '0');
        mix.put('0', 'o');
        mix.put('1', 'l');
        mix.put('l', '1');
        mix.put('m', 'n');
        mix.put('n', 'm');

        for (int i = 0; i < S.length(); i++) {
            char c1 = S.charAt(i);
            char c2 = T.charAt(i);
            if (c1 == c2) {
                continue;
            }
            Character allowed = mix.get(c1);
            if (allowed == null || c2 != allowed) {
                return "Impossible";
            }
        }
        return "Possible";
    }
}
