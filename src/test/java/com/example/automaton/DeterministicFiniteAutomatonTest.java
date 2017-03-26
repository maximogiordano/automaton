package com.example.automaton;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the following class: {@link DeterministicFiniteAutomaton}.
 * </p>
 * <p>
 * An instance of this class is created for each unit test performed.
 * </p>
 */
public class DeterministicFiniteAutomatonTest {
    /**
     * Automaton instance being tested.
     */
    private DeterministicFiniteAutomaton automaton;

    /**
     * Executes before each test, creating an automaton that accepts the strings
     * of zeros and ones with an even number of zeros. The initial state is not
     * set.
     */
    @Before
    public void before() {
        automaton = new DeterministicFiniteAutomaton();

        automaton.addState("Even", true);
        automaton.addState("Odd", false);

        automaton.addTransition("Even", '0', "Odd");
        automaton.addTransition("Even", '1', "Even");
        automaton.addTransition("Odd", '0', "Even");
        automaton.addTransition("Odd", '1', "Odd");
    }

    @Test(expected = NullPointerException.class)
    public void testAddStateWithNullArgument() {
        automaton.addState(null, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddStateWithIllegalArgument() {
        automaton.addState("Even", true);
    }

    @Test(expected = NullPointerException.class)
    public void testAddTransitionWithNullStateFrom() {
        automaton.addTransition(null, '0', "Odd");
    }

    @Test(expected = NullPointerException.class)
    public void testAddTransitionWithNullStateTo() {
        automaton.addTransition("Even", '0', null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTransitionWithIllegalStateFrom() {
        automaton.addTransition("even", '0', "Odd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTransitionWithIllegalStateTo() {
        automaton.addTransition("Even", '0', "odd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTransitionWithIllegalStateFromAndSymbol() {
        automaton.addTransition("Even", '0', "Odd");
    }

    @Test(expected = NullPointerException.class)
    public void testSetInitialStateWithNullArgument() {
        automaton.setInitialState(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInitialStateWithIllegalArgument() {
        automaton.setInitialState("even");
    }

    @Test(expected = IllegalStateException.class)
    public void testAnalyzeWithIllegalState() {
        automaton.analyze(""); // Oops! I forgot to set the initial state...
    }

    @Test
    public void testAnalyze() {
        automaton.setInitialState("Even");

        Assert.assertTrue(automaton.analyze(""));
        Assert.assertTrue(automaton.analyze("1010"));
        Assert.assertFalse(automaton.analyze("101"));
        Assert.assertFalse(automaton.analyze("10102"));
    }
}
