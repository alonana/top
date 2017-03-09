package com.alon.top.div2.level2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortestPathWithMagic {
    public static void main(String[] args) throws Exception {
        new ShortestPathWithMagic().check();
    }

    private void check() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                this.getClass().getSimpleName() + ".txt");
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()){
                continue;
            }
            Pattern pattern = Pattern.compile("\\{(.*)\\},\\s+(\\S+)\\s+(\\S+)");
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                throw new Exception("parse line failed " + line);
            }
            String dists = matcher.group(1);
            String k = matcher.group(2);
            String answer = matcher.group(3);

            dists = dists.replace(" ", "");
            dists = dists.replace("\"", "");


            double actual = getTime(dists.split(","), Integer.parseInt(k));
            if (actual != Double.parseDouble(answer)) {
                throw new Exception("expected " + answer + " actual " + actual);
            }
            System.out.println("answer "+ actual + " OK");
        }
    }

    private double getTime(String[] dist, int k) {
        System.out.println("checking " + k);
        for (String s:dist){
            System.out.println(s);
        }
        PriorityQueue<Target> toVisit = new PriorityQueue<>();
        HashSet<String> visited = new HashSet<>();
        toVisit.add(new Target(0, 0, k));
        while (!toVisit.isEmpty()) {
            Target target = toVisit.poll();
            if (target.isFinal()) {
                return target.getCost();
            }
            String key = target.getKey();
            if (visited.contains(key)) {
                continue;
            }
            visited.add(key);
            toVisit.addAll(target.getNexts(dist));
        }
        return -1;
    }

    class Target implements Comparable<Target> {
        private int city;
        private double cost;
        private int k;

        Target(int city, double cost, int k) {
            this.city = city;
            this.cost = cost;
            this.k = k;
        }

        boolean isFinal() {
            return city == 1;
        }

        double getCost() {
            return cost;
        }

        String getKey() {
            return city + "_" + k;
        }

        Collection<Target> getNexts(String[] dist) {
            LinkedList<Target> nexts = new LinkedList<>();
            String myDist = dist[city];
            for (int nextCity = 0; nextCity < dist.length; nextCity++) {
                if (nextCity == city) {
                    continue;
                }
                int nextCost = myDist.charAt(nextCity) - '0';
                nexts.add(new Target(nextCity, cost + nextCost, k));
                if (k > 0) {
                    nexts.add(new Target(nextCity, cost + (double) nextCost / 2, k - 1));
                }
            }
            return nexts;
        }

        @Override
        public int compareTo(Target o) {
            if (this.cost < o.cost) {
                return -1;
            }
            if (this.cost > o.cost) {
                return 1;
            }
            return 0;
        }
    }
}
