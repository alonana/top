package com.alon.top.year2018;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainForecast implements InterfaceTopTest {

    public static void main(String[] args) throws Exception {
        new TopUtil(new RainForecast()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("(\\d+), \\{(.*)}\\s+(\\S+)").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }

        int ilkoProb = Integer.parseInt(matcher.group(1));
        int[] deliverProbs = util.getInts(matcher.group(2));
        double expected = Double.parseDouble(matcher.group(3));
        double actual = predictRain(ilkoProb, deliverProbs);
        util.assertEquals(expected, actual);
    }

    private double predictRain(int ilkoProb, int[] deliverProbs) {
        if (ilkoProb == 50) {
            return 0.5;
        }
        if (ilkoProb < 50) {
            ilkoProb = 100 - ilkoProb;
        }
        double probabilityCorrect = (double) ilkoProb / 100;

        double keepProbability = 1;
        for (int deliver : deliverProbs) {
            double personKeepProbability = (double) deliver / 100;
            keepProbability =
                    keepProbability * personKeepProbability + (1 - keepProbability) * (1 - personKeepProbability);

        }

        keepProbability = Math.max(keepProbability, 1 - keepProbability);
        return probabilityCorrect * keepProbability + (1 - probabilityCorrect) * (1 - keepProbability);
    }
}