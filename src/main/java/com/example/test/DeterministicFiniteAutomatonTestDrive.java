package com.example.test;

import com.example.automaton.DeterministicFiniteAutomaton;

import javax.swing.JOptionPane;

public class DeterministicFiniteAutomatonTestDrive {
    public static void main(String[] args) {
        DeterministicFiniteAutomaton automaton = new DeterministicFiniteAutomaton();
        String string;

        automaton.addState("Even", true);
        automaton.addState("Odd", false);

        automaton.addTransition("Even", '0', "Odd");
        automaton.addTransition("Even", '1', "Even");
        automaton.addTransition("Odd", '0', "Even");
        automaton.addTransition("Odd", '1', "Odd");

        automaton.setInitialState("Even");

        while ((string = JOptionPane.showInputDialog(null)) != null) {
            System.out.println(automaton.analyze(string));
        }
    }
}
