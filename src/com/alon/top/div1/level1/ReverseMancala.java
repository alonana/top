package com.alon.top.div1.level1;

import com.alon.top.InterfaceTopTest;
import com.alon.top.div2.TopUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReverseMancala implements InterfaceTopTest {
    public static void main(String[] args) throws Exception {
        new TopUtil(new ReverseMancala()).check();
    }

    @Override
    public void checkLine(String line, TopUtil util) throws Exception {
        Matcher matcher = Pattern.compile("\\{(.*)}.*\\{(.*)}.*\\{(.*)}").matcher(line);
        if (!matcher.find()) {
            throw new Exception("parse failed");
        }
        int[] start = util.getInts(matcher.group(1));
        int[] target = util.getInts(matcher.group(1));
        int[] expected = util.getInts(matcher.group(1));
        int[] answer = findMoves(start, target);
        util.assertEquals(expected, answer);
    }

    private int[] findMoves(int[] start, int[] target) {
        State destination = new State(target);
        HashSet<State> visited = new HashSet<>();
        HashSet<State> toVisit = new HashSet<>();
        toVisit.add(new State(start));
        while (!toVisit.isEmpty()) {
            State state = toVisit.iterator().next();
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);
            if (state.equals(destination)) {
                return state.getSteps();
            }
            for (State next : state.getNexts()) {
                toVisit.add(next);
            }
        }
        return null;
    }

    class State {
        private int[] slots;
        private List<Integer> steps;

        State(int[] slots) {
            this.slots = slots;
            this.steps = new LinkedList<>();
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof State && equals((State) other);
        }

        boolean equals(State other) {
            return Arrays.equals(this.slots, other.slots);
        }

        Collection<State> getNexts() {
            Collection<State> nexts = new HashSet<>();
            if (steps.size() == 2500) {
                return nexts;
            }
            for (int i = 0; i < slots.length; i++) {
                if (slots[i] == 0) {
                    continue;
                }
                nexts.add(typeA(i));
                nexts.add(typeB(i));
            }
            return nexts;
        }

        State typeA(int slot) {
            int newSlots[] = new int[slots.length];
            System.arraycopy(slots, 0, newSlots, 0, slots.length);
            State result = new State(newSlots);
            result.steps = new LinkedList<>(this.steps);
            result.steps.add(slot);
            int pick = newSlots[slot];
            newSlots[slot] = 0;
            while (pick > 0) {
                slot = (slot + 1) % newSlots.length;
                newSlots[slot]++;
                pick--;
            }
            return result;
        }

        State typeB(int slot) {
            int newSlots[] = new int[slots.length];
            System.arraycopy(slots, 0, newSlots, 0, slots.length);
            State result = new State(newSlots);
            result.steps = new LinkedList<>(this.steps);
            result.steps.add(slots.length + slot);
            int pick = 0;
            while (newSlots[slot] != 0) {
                pick++;
                newSlots[slot]--;
                slot = slot - 1;
                if (slot < 0) {
                    slot = newSlots.length - 1;
                }
            }
            newSlots[slot] = pick;
            return result;
        }

        int[] getSteps() {
            int result[] = new int[steps.size()];
            for (int i = 0; i < steps.size(); i++) {
                result[i] = steps.get(i);
            }
            return result;
        }
    }
}
