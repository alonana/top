package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RememberWords implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new RememberWords()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+), (\\d+)\\s+\"(\\S+)\"").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int d1 = Integer.parseInt(matcher.group(1));
        int d2 = Integer.parseInt(matcher.group(2));
        int w1 = Integer.parseInt(matcher.group(3));
        int w2 = Integer.parseInt(matcher.group(4));
        String expected = matcher.group(5);
        String actual = isPossible(d1, d2, w1, w2);
        util.assertEquals(expected, actual);
    }

    private String isPossible(int d1, int d2, int w1, int w2) {
        if (isPossibleCheck(d1, d2, w1, w2)) {
            return "Possible";
        }
        return "Impossible";
    }

    private boolean isPossibleCheck(int d1, int d2, int w1, int w2) {
        Range r1 = getRange(d1, w1);
        Range r2 = getRange(d2, w2);
        return r1.near(r2) || r2.near(r1);
    }

    private Range getRange(int d, int w) {
        int equal = w / d;
        int min = equal - (d - 1) / 2;
        int max = equal + Math.min((d - 1) / 2, equal);

        if (min < 0) {
            min = 0;
        }
        int leftover = w % d;
        if (leftover > 0) {
            max++;
        }

//        if (min==0 && max==1){
//            long total =0;
//            int add=0;
//            while (total<w){
//                add++;
//                total+=add;
//            }
//
//            if (add<d){
//                max=add;
//            }
//        }

        Range range = new Range(min, max);
        System.out.println("range for " + d + " days " + w + " words is " + range);
        return range;
    }

    class Range {
        private int min;
        private int max;

        Range(int min, int max) {
            this.min = min;
            this.max = max;
        }

        boolean near(Range other) {
            return this.min >= other.min && this.min + 1 <= other.max ||
                    this.max + 1 >= other.min && this.max <= other.max ||
                    this.min < other.min && this.max > other.max;
        }

        @Override
        public String toString() {
            return min + "-" + max;
        }
    }
}