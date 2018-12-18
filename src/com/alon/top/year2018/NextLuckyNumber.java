package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NextLuckyNumber implements InterfaceTopTest {

    private int position;
    private long ticket;
    private int digit;

    public static void main(String[] args) throws Exception {
        new TopUtil(new NextLuckyNumber()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), (\\d+), (\\d+)\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        long lastTicket = Long.parseLong(matcher.group(1));
        int age = Integer.parseInt(matcher.group(2));
        int digit = Integer.parseInt(matcher.group(3));
        long expected = Long.parseLong(matcher.group(4));
        long actual = getTicket(lastTicket, age, digit);
        util.assertEquals(expected, actual);
    }

    private long getTicket(long lastTicket, int age, int digit) {
        this.digit = digit;
        ticket = lastTicket + 1;
        position = 0;

        while (true) {
            debug("ticket:" + ticket + " position:" + position);
            int amount = count();
            if (amount == age) {
                return ticket;
            }
            if (amount < age) {
                tryToAdd();
            } else {
                tryToRemove();
            }
        }
    }

    private void tryToRemove() {
        int positionDigit = getDigit(position);
        if (digit != positionDigit) {
            if (digit != 0) {
                setDigitZeroAtCurrentPosition();
            }
            position++;
            return;
        }
        addOneAtCurrentPosition();
    }

    private void setDigitZeroAtCurrentPosition() {
        int positionDigit = getDigit(position);
        long power = (long) Math.pow(10, position);
        ticket -= power * positionDigit;
    }

    private void debug(Object o) {
        //System.out.println(o);
    }

    private void tryToAdd() {
        int positionDigit = getDigit(position);
        if (digit == positionDigit) {
            position++;
            return;
        }
        addOneAtCurrentPosition();
    }

    private void addOneAtCurrentPosition() {
        long add = (long) Math.pow(10, position);
        ticket += add;
    }

    private int getDigit(int position) {
        long power = (long) Math.pow(10, position + 1);
        long l = ticket % power;
        power = power / 10;
        return (int) (l / power);
    }

    private int count() {
        int count = 0;
        long check = ticket;
        while (check > 0) {
            int firstDigit = (int) (check % 10);
            if (digit == firstDigit) {
                count++;
            }
            check = check / 10;
        }
        return count;
    }
}