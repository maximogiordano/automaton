package com.example.automaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An instance of this class represents a nondeterministic finite automaton
 * state.
 */
public class NondeterministicFiniteAutomatonState {
    /**
     * The state name.
     */
    private String name;
    /**
     * true if and only if this state is a final or accepting state.
     */
    private boolean acceptingState;
    /**
     * Contains the set of states that can be accessed from this state for each
     * symbol.
     */
    private Map<Character, Set<NondeterministicFiniteAutomatonState>> transitions;
    /**
     * Contains the set of states that can be accessed from this state without
     * reading any symbol.
     */
    private Set<NondeterministicFiniteAutomatonState> lambdaTransitions;

    /**
     * Creates a new state for a nondeterministic finite automaton.
     *
     * @param name           The state name.
     * @param acceptingState true if and only if this state is a final or accepting state.
     */
    public NondeterministicFiniteAutomatonState(String name, boolean acceptingState) {
        this.name = name;
        this.acceptingState = acceptingState;
        this.transitions = new HashMap<>();
        this.lambdaTransitions = new HashSet<>();
    }

    /**
     * Adds the given state to the set of states that can be accessed from this
     * state by reading the given symbol.
     *
     * @param symbol The given symbol.
     * @param state  The given state.
     */
    public void addTransition(char symbol, NondeterministicFiniteAutomatonState state) {
        Set<NondeterministicFiniteAutomatonState> states = transitions.computeIfAbsent(symbol, k -> new HashSet<>());

        states.add(state);
    }

    /**
     * Adds the given state to the set of states that can be accessed from this
     * state without reading any symbol.
     *
     * @param state The given state.
     */
    public void addLambdaTransition(NondeterministicFiniteAutomatonState state) {
        lambdaTransitions.add(state);
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
     * @return The set of states that can be accessed from this state by reading
     * the input symbol (never null)
     */
    public Set<NondeterministicFiniteAutomatonState> getTransitions(char symbol) {
        Set<NondeterministicFiniteAutomatonState> states = transitions.get(symbol);

        return states == null ? new HashSet<>() : states;
    }

    /**
     * @return The set of states that can be accessed from this state without
     * reading any symbol (never null)
     */
    public Set<NondeterministicFiniteAutomatonState> getLambdaTransitions() {
        return lambdaTransitions;
    }
}
