package com.example.test;

import com.example.automaton.DeterministicFiniteAutomaton;
import com.example.automaton.NondeterministicFiniteAutomaton;

import java.util.Random;

public class FiniteAutomatonTestDrive {
    public static void main(String[] args) {
        NondeterministicFiniteAutomaton originalAutomaton = new NondeterministicFiniteAutomaton();

        originalAutomaton.addState("q0", false);
        originalAutomaton.addState("q1", false);
        originalAutomaton.addState("q2", false);
        originalAutomaton.addState("q3", false);
        originalAutomaton.addState("q4", false);
        originalAutomaton.addState("q5", false);
        originalAutomaton.addState("q6", false);
        originalAutomaton.addState("q7", false);
        originalAutomaton.addState("q8", false);
        originalAutomaton.addState("q9", false);
        originalAutomaton.addState("q10", true);
        originalAutomaton.addLambdaTransition("q0", "q1");
        originalAutomaton.addLambdaTransition("q0", "q7");
        originalAutomaton.addLambdaTransition("q1", "q2");
        originalAutomaton.addLambdaTransition("q1", "q4");
        originalAutomaton.addTransition("q2", 'a', "q3");
        originalAutomaton.addLambdaTransition("q3", "q6");
        originalAutomaton.addTransition("q4", 'b', "q5");
        originalAutomaton.addLambdaTransition("q5", "q6");
        originalAutomaton.addLambdaTransition("q6", "q1");
        originalAutomaton.addLambdaTransition("q6", "q7");
        originalAutomaton.addTransition("q7", 'a', "q8");
        originalAutomaton.addTransition("q8", 'b', "q9");
        originalAutomaton.addTransition("q9", 'b', "q10");
        originalAutomaton.setInitialState("q0");

        DeterministicFiniteAutomaton equivalentAutomaton = new DeterministicFiniteAutomaton();

        equivalentAutomaton.addState("A", false);
        equivalentAutomaton.addState("B", false);
        equivalentAutomaton.addState("C", false);
        equivalentAutomaton.addState("D", false);
        equivalentAutomaton.addState("E", true);
        equivalentAutomaton.addTransition("A", 'a', "B");
        equivalentAutomaton.addTransition("A", 'b', "C");
        equivalentAutomaton.addTransition("B", 'a', "B");
        equivalentAutomaton.addTransition("B", 'b', "D");
        equivalentAutomaton.addTransition("C", 'a', "B");
        equivalentAutomaton.addTransition("C", 'b', "C");
        equivalentAutomaton.addTransition("D", 'a', "B");
        equivalentAutomaton.addTransition("D", 'b', "E");
        equivalentAutomaton.addTransition("E", 'a', "B");
        equivalentAutomaton.addTransition("E", 'b', "C");
        equivalentAutomaton.setInitialState("A");

        Random random = new Random();

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            test(originalAutomaton, equivalentAutomaton, random);

            if (i % 1000 == 0) {
                System.out.println(i + " strings analyzed!");
            }
        }
    }

    private static void test(NondeterministicFiniteAutomaton originalAutomaton,
                             DeterministicFiniteAutomaton equivalentAutomaton, Random random) {
        String string = getRandomString(random);

        boolean originalAutomatonResult = originalAutomaton.analyze(string);
        boolean equivalentAutomatonResult = equivalentAutomaton.analyze(string);

        if (originalAutomatonResult != equivalentAutomatonResult) {
            throw new RuntimeException("Oops: " + string);
        }
    }

    private static final int MAX_STRING_LENGTH = 1024;

    private static String getRandomString(Random random) {
        int length = random.nextInt(MAX_STRING_LENGTH + 1);
        char[] characters = new char[length];

        for (int i = 0; i < length; i++) {
            characters[i] = random.nextBoolean() ? 'a' : 'b';
        }

        return new String(characters);
    }
}
