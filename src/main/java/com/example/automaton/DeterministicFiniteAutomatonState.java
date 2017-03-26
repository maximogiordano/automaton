package com.example.automaton;

import java.util.HashMap;
import java.util.Map;

/**
 * An instance of this class represents a deterministic finite automaton state.
 */
public class DeterministicFiniteAutomatonState {
    /**
     * The state name.
     */
    private String name;
    /**
     * true if and only if this state is a final or accepting state.
     */
    private boolean acceptingState;
    /**
     * <p>
     * Transitions map.
     * </p>
     * <p>
     * If the automaton reads a symbol while being on this state, then it
     * changes to the corresponding state according to this map.
     * </p>
     */
    private Map<Character, DeterministicFiniteAutomatonState> transitions;

    /**
     * Creates a new state for a deterministic finite automaton.
     *
     * @param name           The state name.
     * @param acceptingState true if and only if this state is a final or accepting state.
     */
    public DeterministicFiniteAutomatonState(String name, boolean acceptingState) {
        this.name = name;
        this.acceptingState = acceptingState;
        this.transitions = new HashMap<>();
    }

    /**
     * <p>
     * Adds the given transition.
     * </p>
     * <p>
     * If the automaton reads the given symbol while being on this state, then
     * it will change to the given state.
     * </p>
     *
     * @param symbol The given symbol.
     * @param state  The given state.
     */
    public void addTransition(char symbol, DeterministicFiniteAutomatonState state) {
        transitions.put(symbol, state);
    }

    /**
     * @return The state name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return true if and only if this state is a final or accepting state.
     */
    public boolean isAcceptingState() {
        return acceptingState;
    }

    /**
     * @param symbol The input symbol.
     * @return The new automaton state when the input symbol is read, or null if
     * there's no transition specified for the input symbol.
     */
    public DeterministicFiniteAutomatonState getTransition(char symbol) {
        return transitions.get(symbol);
    }
}
