package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForumPostEasy implements InterfaceTopTest {
    private static final int DAY = 24 * 3600;
    private int maxFrom;
    private int minTo;
    private boolean invalid;

    public static void main(String[] args) throws Exception {
        new TopUtil(new ForumPostEasy()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}, \\{(.*)}\\s+\"(.*)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String exactPostTime[] = util.getStrings(matcher.group(1));
        String showPostTime[] = util.getStrings(matcher.group(2));
        String expected = matcher.group(3);
        String actual = getCurrentTime(exactPostTime, showPostTime);
        util.assertEquals(expected, actual);
    }

    private String getCurrentTime(String[] exactPostTime, String[] showPostTime) {
        if (exactPostTime.length == 0) {
            return "00:00:00";
        }
        invalid = false;
        for (int i = 0; i < exactPostTime.length; i++) {
            checkTimes(exactPostTime[i], showPostTime[i], i == 0);
            if (invalid) {
                return "impossible";
            }
        }

        if (maxFrom < DAY && minTo > DAY) {
            return "00:00:00";
        }

        return toTime(maxFrom);
    }

    private String toTime(int time) {
        int hours = time / 3600;
        time -= 3600 * hours;
        int minutes = time / 60;
        int seconds = time - minutes * 60;
        if (hours > 23) {
            hours -= 24;
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void checkTimes(String exactPostTime, String showPostTime, boolean first) {
        int time = 0;
        for (String x : exactPostTime.split(":")) {
            time *= 60;
            time += Integer.parseInt(x);
        }

        int currentFrom;
        int currentTo;
        if (showPostTime.equals("few seconds ago")) {
            currentFrom = time;
            currentTo = currentFrom + 59;
        } else if (showPostTime.contains("minutes")) {
            int minutes = Integer.parseInt(showPostTime.substring(0, showPostTime.indexOf(" ")));
            currentFrom = time + minutes * 60;
            currentTo = currentFrom + 59;
        } else {
            int hours = Integer.parseInt(showPostTime.substring(0, showPostTime.indexOf(" ")));
            currentFrom = time + hours * 3600;
            currentTo = currentFrom + 3599;
        }

        if (currentFrom >= DAY) {
            currentFrom -= DAY;
        }
        if (currentTo >= DAY) {
            currentTo -= DAY;
        }
        System.out.println("current " + toTime(currentFrom) + " - " + toTime(currentTo) + " min/max " + toTime(maxFrom) + " - " + toTime(minTo));

        if (first) {
            maxFrom = currentFrom;
            minTo = currentTo;
        } else {
            if (!isParallel(currentFrom, currentTo, maxFrom, minTo)) {
                invalid = true;
                return;
            }
            maxFrom = Math.max(currentFrom, maxFrom);
            minTo = Math.min(currentTo, minTo);
        }
    }

    private boolean isParallel(int from1, int to1, int from2, int to2) {
        if (from1 > to1) {
            to1 += DAY;
        }
        if (from2 > to2) {
            to2 += DAY;
        }
        if (from1 <= to2 && to1 >= from2) {
            return true;
        }
        if (from1 <= to2 + DAY && to1 >= from2 + DAY) {
            return true;
        }
        if (from1 + DAY <= to2 && to1 + DAY >= from2) {
            return true;
        }
        return false;
    }
}
