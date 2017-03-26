package com.example.test;

import com.example.automaton.NondeterministicFiniteAutomaton;

import javax.swing.JOptionPane;

public class NondeterministicFiniteAutomatonTestDrive {
    public static void main(String[] args) {
        NondeterministicFiniteAutomaton automaton = new NondeterministicFiniteAutomaton();
        String string;

        automaton.addState("Initial State", false);
        automaton.addState("Even Number of Zeros", true);
        automaton.addState("Odd Number of Zeros", false);
        automaton.addState("Even Number of Ones", true);
        automaton.addState("Odd Number of Ones", false);

        automaton.addLambdaTransition("Initial State", "Even Number of Zeros");
        automaton.addLambdaTransition("Initial State", "Even Number of Ones");

        automaton.addTransition("Even Number of Zeros", '0', "Odd Number of Zeros");
        automaton.addTransition("Even Number of Zeros", '1', "Even Number of Zeros");
        automaton.addTransition("Odd Number of Zeros", '0', "Even Number of Zeros");
        automaton.addTransition("Odd Number of Zeros", '1', "Odd Number of Zeros");

        automaton.addTransition("Even Number of Ones", '0', "Even Number of Ones");
        automaton.addTransition("Even Number of Ones", '1', "Odd Number of Ones");
        automaton.addTransition("Odd Number of Ones", '0', "Odd Number of Ones");
        automaton.addTransition("Odd Number of Ones", '1', "Even Number of Ones");

        automaton.setInitialState("Initial State");

        automaton.analyze(""); // returns true (even number of zeros and even
        // number
        // of ones)
        automaton.analyze("10010101"); // returns true (even number of zeros and
        // even
        // number of ones)
        automaton.analyze("1000101"); // returns true (even number of zeros)
        automaton.analyze("1001101"); // returns true (even number of ones)
        automaton.analyze("101010"); // returns false
        automaton.analyze("100102101"); // returns false (it must be a string of
        // zeros and ones)

        while ((string = JOptionPane.showInputDialog(null)) != null) {
            System.out.println(automaton.analyze(string));
        }
    }
}
