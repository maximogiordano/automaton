package com.example.automaton;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * An instance of this class represents a deterministic finite automaton.
 * </p>
 * <h1>Example</h1>
 * <p>
 * Strings of zeros and ones with an even number of zeros.
 * </p>
 * <p>
 * <pre>
 * DeterministicFiniteAutomaton automaton = new DeterministicFiniteAutomaton();
 *
 * automaton.addState("Even", true);
 * automaton.addState("Odd", false);
 *
 * automaton.addTransition("Even", '0', "Odd");
 * automaton.addTransition("Even", '1', "Even");
 * automaton.addTransition("Odd", '0', "Even");
 * automaton.addTransition("Odd", '1', "Odd");
 *
 * automaton.setInitialState("Even");
 *
 * automaton.analyze(""); // returns true
 * automaton.analyze("1010"); // returns true
 * automaton.analyze("101"); // returns false
 * automaton.analyze("10102"); // returns false
 * </pre>
 */
public class DeterministicFiniteAutomaton {
    /**
     * <p>
     * States map.
     * </p>
     * <p>
     * It associates the state name to the state object.
     * </p>
     */
    private Map<String, DeterministicFiniteAutomatonState> states;
    /**
     * The initial state.
     */
    private DeterministicFiniteAutomatonState initialState;

    /**
     * Creates a new deterministic finite automaton with no states.
     */
    public DeterministicFiniteAutomaton() {
        states = new HashMap<>();
        initialState = null;
    }

    /**
     * Adds the given state.
     *
     * @param stateName      The state name.
     * @param acceptingState true if and only if this state is a final or accepting state.
     * @throws NullPointerException     If the state name is null.
     * @throws IllegalArgumentException If the state name is already used.
     */
    public void addState(String stateName, boolean acceptingState)
            throws NullPointerException, IllegalArgumentException {
        validateAddStateArguments(stateName);

        states.put(stateName, new DeterministicFiniteAutomatonState(stateName, acceptingState));
    }

    /**
     * Validates the {@link #addState(String, boolean)} arguments.
     *
     * @param stateName The state name.
     * @throws NullPointerException     If the state name is null.
     * @throws IllegalArgumentException If the state name is already used.
     */
    private void validateAddStateArguments(String stateName) throws NullPointerException, IllegalArgumentException {
        if (stateName == null) {
            throw new NullPointerException("The state name is null.");
        }

        if (states.containsKey(stateName)) {
            throw new IllegalArgumentException("The state name (" + stateName + ") is already used.");
        }
    }

    /**
     * <p>
     * Adds a transition to this automaton.
     * </p>
     * <p>
     * If this automaton is on the source state and the given symbol is read,
     * then this automaton will change to the destination state.
     * </p>
     *
     * @param stateNameFrom The source state name.
     * @param symbol        The input symbol.
     * @param stateNameTo   The destination state name.
     * @throws NullPointerException     If the source state name is null.
     * @throws NullPointerException     If the destination state name is null.
     * @throws IllegalArgumentException If the source state name indicates a non-existent state.
     * @throws IllegalArgumentException If the destination state name indicates a non-existent state.
     * @throws IllegalArgumentException If a transition for the given source state and symbol already
     *                                  exists.
     */
    public void addTransition(String stateNameFrom, char symbol, String stateNameTo)
            throws NullPointerException, IllegalArgumentException {
        validateAddTransitionArguments(stateNameFrom, symbol, stateNameTo);

        DeterministicFiniteAutomatonState stateFrom = states.get(stateNameFrom);
        DeterministicFiniteAutomatonState stateTo = states.get(stateNameTo);

        stateFrom.addTransition(symbol, stateTo);
    }

    /**
     * Validates the {@link #addTransition(String, char, String)} arguments.
     *
     * @param stateNameFrom The source state name.
     * @param symbol        The input symbol.
     * @param stateNameTo   The destination state name.
     * @throws NullPointerException     If the source state name is null.
     * @throws NullPointerException     If the destination state name is null.
     * @throws IllegalArgumentException If the source state name indicates a non-existent state.
     * @throws IllegalArgumentException If the destination state name indicates a non-existent state.
     * @throws IllegalArgumentException If a transition for the given source state and symbol already
     *                                  exists.
     */
    private void validateAddTransitionArguments(String stateNameFrom, char symbol, String stateNameTo)
            throws NullPointerException, IllegalArgumentException {
        if (stateNameFrom == null) {
            throw new NullPointerException("The source state name is null.");
        }

        if (stateNameTo == null) {
            throw new NullPointerException("The destination state name is null.");
        }

        if (!states.containsKey(stateNameFrom)) {
            throw new IllegalArgumentException(
                    "The source state name (" + stateNameFrom + ") indicates a non-existent state.");
        }

        if (!states.containsKey(stateNameTo)) {
            throw new IllegalArgumentException(
                    "The destination state name (" + stateNameTo + ") indicates a non-existent state.");
        }

        if (states.get(stateNameFrom).getTransition(symbol) != null) {
            throw new IllegalArgumentException("A transition for the given source state (" + stateNameFrom
                    + ") and symbol (" + symbol + ") already exists.");
        }
    }

    /**
     * Sets this automaton initial state.
     *
     * @param initialStateName The initial state name.
     * @throws NullPointerException     If the initial state name is null.
     * @throws IllegalArgumentException If the initial state name indicates a non-existent state.
     */
    public void setInitialState(String initialStateName) throws NullPointerException, IllegalArgumentException {
        validateSetInitialStateArguments(initialStateName);

        initialState = states.get(initialStateName);
    }

    /**
     * Validates the {@link #setInitialState(String)} arguments.
     *
     * @param initialStateName The initial state name.
     * @throws NullPointerException     If the initial state name is null.
     * @throws IllegalArgumentException If the initial state name indicates a non-existent state.
     */
    private void validateSetInitialStateArguments(String initialStateName)
            throws NullPointerException, IllegalArgumentException {
        if (initialStateName == null) {
            throw new NullPointerException("The initial state name is null.");
        }

        if (!states.containsKey(initialStateName)) {
            throw new IllegalArgumentException(
                    "The initial state name (" + initialStateName + ") indicates a non-existent state.");
        }
    }

    /**
     * Analyzes the given string.
     *
     * @param string The analyzed string.
     * @return true if and only if this automaton accepts the given string.
     * @throws NullPointerException  If the given string is null.
     * @throws IllegalStateException If this automaton isn't on a proper state (i.e., the initial
     *                               state has not been set)
     */
    public boolean analyze(String string) throws NullPointerException, IllegalStateException {
        validateAnalyzeArguments(string);
        validateAutomatonState();

        DeterministicFiniteAutomatonState currentState = initialState;

        for (char symbol : string.toCharArray()) {
            currentState = currentState.getTransition(symbol);

            if (currentState == null) {
                return false; // halting state
            }
        }

        return currentState.isAcceptingState();
    }

    /**
     * Validates the {@link #analyze(String)} arguments.
     *
     * @param string The analyzed string.
     * @throws NullPointerException If the given string is null.
     */
    private void validateAnalyzeArguments(String string) throws NullPointerException {
        if (string == null) {
            throw new NullPointerException("The given string is null.");
        }
    }

    /**
     * Validates this automaton state.
     *
     * @throws IllegalStateException If this automaton isn't on a proper state (i.e., the initial
     *                               state has not been set)
     */
    private void validateAutomatonState() throws IllegalStateException {
        if (initialState == null) {
            throw new IllegalStateException("The initial state has not been set.");
        }
    }
}
