package com.example.automaton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the following class: {@link NondeterministicFiniteAutomaton}.
 * </p>
 * <p>
 * An instance of this class is created for each unit test performed.
 * </p>
 */
public class NondeterministicFiniteAutomatonTest {
    /**
     * Automaton instance being tested.
     */
    private NondeterministicFiniteAutomaton automaton;

    /**
     * Executes before each test, creating a default automaton. This automaton
     * accepts the strings of zeros and ones with an even number of zeros or an
     * even number of ones. The initial state is not set.
     */
    @Before
    public void before() {
        automaton = new NondeterministicFiniteAutomaton();

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
    }

    @Test(expected = NullPointerException.class)
    public void testAddStateWithNullArgument() {
        automaton.addState(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddStateWithIllegalArgument() {
        automaton.addState("Initial State", true);
    }

    @Test(expected = NullPointerException.class)
    public void testAddLambdaTransitionWithNullStateFrom() {
        automaton.addLambdaTransition(null, "Even Number of Zeros");
    }

    @Test(expected = NullPointerException.class)
    public void testAddLambdaTransitionWithNullStateTo() {
        automaton.addLambdaTransition("Initial State", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLambdaTransitionWithIllegalStateFrom() {
        automaton.addLambdaTransition("initial state", "Even Number of Zeros");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLambdaTransitionWithIllegalStateTo() {
        automaton.addLambdaTransition("Initial State", "even number of zeros");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLambdaTransitionWithAlreadyExistingTransition() {
        automaton.addLambdaTransition("Initial State", "Even Number of Zeros");
    }

    @Test(expected = NullPointerException.class)
    public void testAddTransitionWithNullStateFrom() {
        automaton.addTransition(null, '0', "Odd Number of Zeros");
    }

    @Test(expected = NullPointerException.class)
    public void testAddTransitionWithNullStateTo() {
        automaton.addTransition("Even Number of Zeros", '0', null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTransitionWithIllegalStateFrom() {
        automaton.addTransition("even number of zeros", '0', "Odd Number of Zeros");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTransitionWithIllegalStateTo() {
        automaton.addTransition("Even Number of Zeros", '0', "odd number of zeros");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTransitionWithAlreadyExistingTransition() {
        automaton.addTransition("Even Number of Zeros", '0', "Odd Number of Zeros");
    }

    @Test(expected = NullPointerException.class)
    public void testSetInitialStateWithNullArgument() {
        automaton.setInitialState(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInitialStateWithIllegalArgument() {
        automaton.setInitialState("initial state");
    }

    @Test(expected = IllegalStateException.class)
    public void testAnalyzeWithIllegalState() {
        automaton.analyze(""); // Oops! I forgot to set the initial state...
    }

    @Test
    public void testAnalyze() {
        automaton.setInitialState("Initial State");

        Assert.assertTrue(automaton.analyze(""));
        Assert.assertTrue(automaton.analyze("10010101"));
        Assert.assertTrue(automaton.analyze("1000101"));
        Assert.assertTrue(automaton.analyze("1001101"));
        Assert.assertFalse(automaton.analyze("101010"));
        Assert.assertFalse(automaton.analyze("100102101"));
    }
}
