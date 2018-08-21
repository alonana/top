package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SymmetricImage implements InterfaceTopTest {

    private int rows;
    private int cols;
    private String[] image;

    public static void main(String[] args) throws Exception {
        new TopUtil(new SymmetricImage()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}\\s+(\\d+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        String[] image = util.getStrings(matcher.group(1));
        int expected = Integer.parseInt(matcher.group(2));
        int actual = countDirections(image);
        util.assertEquals(expected, actual);
    }

    private int countDirections(String[] image) {
        int result = 0;
        this.rows = image.length;
        this.cols = image[0].length();
        this.image = image;

        if (isRowsSymmetric()) {
            result++;
        }
        if (isColsSymmetric()) {
            result++;
        }
        return result;
    }

    private boolean isRowsSymmetric() {
        int start = 0;
        int end = rows - 1;
        while (start < end) {
            if (!image[start].equals(image[end])) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    private boolean isColsSymmetric() {
        int start = 0;
        int end = cols - 1;
        while (start < end) {
            for (int row = 0; row < rows; row++) {
                if (image[row].charAt(start) != image[row].charAt(end)) {
                    return false;
                }
            }
            start++;
            end--;
        }
        return true;
    }
}