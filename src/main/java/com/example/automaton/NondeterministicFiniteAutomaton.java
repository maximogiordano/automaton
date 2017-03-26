package com.example.automaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * An instance of this class represents a nondeterministic finite automaton.
 * </p>
 * <h1>Example</h1>
 * <p>
 * Strings of zeros and ones with an even number of zeros or an even number of
 * ones.
 * </p>
 * <p>
 * <pre>
 * NondeterministicFiniteAutomaton automaton = new NondeterministicFiniteAutomaton();
 *
 * automaton.addState("Initial State", false);
 * automaton.addState("Even Number of Zeros", true);
 * automaton.addState("Odd Number of Zeros", false);
 * automaton.addState("Even Number of Ones", true);
 * automaton.addState("Odd Number of Ones", false);
 *
 * automaton.addLambdaTransition("Initial State", "Even Number of Zeros");
 * automaton.addLambdaTransition("Initial State", "Even Number of Ones");
 *
 * automaton.addTransition("Even Number of Zeros", '0', "Odd Number of Zeros");
 * automaton.addTransition("Even Number of Zeros", '1', "Even Number of Zeros");
 * automaton.addTransition("Odd Number of Zeros", '0', "Even Number of Zeros");
 * automaton.addTransition("Odd Number of Zeros", '1', "Odd Number of Zeros");
 *
 * automaton.addTransition("Even Number of Ones", '0', "Even Number of Ones");
 * automaton.addTransition("Even Number of Ones", '1', "Odd Number of Ones");
 * automaton.addTransition("Odd Number of Ones", '0', "Odd Number of Ones");
 * automaton.addTransition("Odd Number of Ones", '1', "Even Number of Ones");
 *
 * automaton.setInitialState("Initial State");
 *
 * automaton.analyze(""); // returns true
 * automaton.analyze("10010101"); // returns true
 * automaton.analyze("1000101"); // returns true
 * automaton.analyze("1001101"); // returns true
 * automaton.analyze("101010"); // returns false
 * automaton.analyze("100102101"); // returns false
 * </pre>
 */
public class NondeterministicFiniteAutomaton {
    /**
     * Associates the state name to the state itself.
     */
    private Map<String, NondeterministicFiniteAutomatonState> states;
    /**
     * The initial state.
     */
    private NondeterministicFiniteAutomatonState initialState;

    /**
     * Creates a new nondeterministic finite automaton with no states.
     */
    public NondeterministicFiniteAutomaton() {
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

        states.put(stateName, new NondeterministicFiniteAutomatonState(stateName, acceptingState));
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
     * Adds a lambda transition to this automaton: the destination state can be
     * accessed from the source state without reading any symbol.
     *
     * @param stateNameFrom The source state name.
     * @param stateNameTo   The destination state name.
     * @throws NullPointerException     If the source state name is null.
     * @throws NullPointerException     If the destination state name is null.
     * @throws IllegalArgumentException If the source state name indicates a non-existent state.
     * @throws IllegalArgumentException If the destination state name indicates a non-existent state.
     * @throws IllegalArgumentException If the source and destination states are equals.
     * @throws IllegalArgumentException If a lambda transition for the given source state and
     *                                  destination state already exists.
     */
    public void addLambdaTransition(String stateNameFrom, String stateNameTo)
            throws NullPointerException, IllegalArgumentException {
        validateAddLambdaTransitionArguments(stateNameFrom, stateNameTo);

        NondeterministicFiniteAutomatonState stateFrom = states.get(stateNameFrom);
        NondeterministicFiniteAutomatonState stateTo = states.get(stateNameTo);

        stateFrom.addLambdaTransition(stateTo);
    }

    /**
     * Validates the {@link #addLambdaTransition(String, String)} arguments.
     *
     * @param stateNameFrom The source state name.
     * @param stateNameTo   The destination state name.
     * @throws NullPointerException     If the source state name is null.
     * @throws NullPointerException     If the destination state name is null.
     * @throws IllegalArgumentException If the source state name indicates a non-existent state.
     * @throws IllegalArgumentException If the destination state name indicates a non-existent state.
     * @throws IllegalArgumentException If the source and destination states are equals.
     * @throws IllegalArgumentException If a lambda transition for the given source state and
     *                                  destination state already exists.
     */
    private void validateAddLambdaTransitionArguments(String stateNameFrom, String stateNameTo)
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

        NondeterministicFiniteAutomatonState stateFrom = states.get(stateNameFrom);
        NondeterministicFiniteAutomatonState stateTo = states.get(stateNameTo);

        if (stateFrom.equals(stateTo)) {
            throw new IllegalArgumentException("The source and destination states are equals (" + stateNameFrom + ")");
        }

        if (stateFrom.getLambdaTransitions().contains(stateTo)) {
            throw new IllegalArgumentException("A lambda transition for the given source state (" + stateNameFrom
                    + ") and destination state (" + stateNameTo + ") already exists.");
        }
    }

    /**
     * Adds a transition to this automaton: the destination state can be
     * accessed from the source state by reading the input symbol.
     *
     * @param stateNameFrom The source state name.
     * @param symbol        The input symbol.
     * @param stateNameTo   The destination state name.
     * @throws NullPointerException     If the source state name is null.
     * @throws NullPointerException     If the destination state name is null.
     * @throws IllegalArgumentException If the source state name indicates a non-existent state.
     * @throws IllegalArgumentException If the destination state name indicates a non-existent state.
     * @throws IllegalArgumentException If a transition for the given source state, symbol and
     *                                  destination state already exists.
     */
    public void addTransition(String stateNameFrom, char symbol, String stateNameTo)
            throws NullPointerException, IllegalArgumentException {
        validateAddTransitionArguments(stateNameFrom, symbol, stateNameTo);

        NondeterministicFiniteAutomatonState stateFrom = states.get(stateNameFrom);
        NondeterministicFiniteAutomatonState stateTo = states.get(stateNameTo);

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
     * @throws IllegalArgumentException If a transition for the given source state, symbol and
     *                                  destination state already exists.
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

        NondeterministicFiniteAutomatonState stateFrom = states.get(stateNameFrom);
        NondeterministicFiniteAutomatonState stateTo = states.get(stateNameTo);

        if (stateFrom.getTransitions(symbol).contains(stateTo)) {
            throw new IllegalArgumentException("A transition for the given source state (" + stateNameFrom
                    + "), symbol (" + symbol + ") and destination state (" + stateNameTo + ") already exists.");
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

        Set<NondeterministicFiniteAutomatonState> currentStates = getClosure(initialState);

        for (char symbol : string.toCharArray()) {
            currentStates = getClosure(getNextStates(currentStates, symbol));
        }

        return containsAcceptingStates(currentStates);
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

    /**
     * Determines whether a given set of states contains accepting states.
     *
     * @param states Set of states.
     * @return true if the given set of states contains at least one accepting
     * state and false otherwise.
     */
    private boolean containsAcceptingStates(Set<NondeterministicFiniteAutomatonState> states) {
        for (NondeterministicFiniteAutomatonState state : states) {
            if (state.isAcceptingState()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines the set of states that can be accessed from a set of source
     * states by reading an input symbol.
     *
     * @param sourceStates Set of source states.
     * @param symbol       Input symbol.
     * @return The set of states that be accessed.
     */
    private Set<NondeterministicFiniteAutomatonState> getNextStates(
            Set<NondeterministicFiniteAutomatonState> sourceStates, char symbol) {
        Set<NondeterministicFiniteAutomatonState> nextStates = new HashSet<>();

        for (NondeterministicFiniteAutomatonState state : sourceStates) {
            nextStates.addAll(state.getTransitions(symbol));
        }

        return nextStates;
    }

    /**
     * Gets the lambda closure for a given set of states.
     *
     * @param states Input set of states.
     * @return A set that contains the lambda closure for the given set of
     * states.
     */
    private Set<NondeterministicFiniteAutomatonState> getClosure(Set<NondeterministicFiniteAutomatonState> states) {
        Set<NondeterministicFiniteAutomatonState> closure = new HashSet<>();

        for (NondeterministicFiniteAutomatonState state : states) {
            closure.addAll(getClosure(state));
        }

        return closure;
    }

    /**
     * Gets the lambda closure for a given state.
     *
     * @param state The given state.
     * @return A set that contains the lambda closure for the given state.
     */
    private Set<NondeterministicFiniteAutomatonState> getClosure(NondeterministicFiniteAutomatonState state) {
        Set<NondeterministicFiniteAutomatonState> closure = new HashSet<>();

        addLambdaAccessibleStates(closure, state);

        return closure;
    }

    /**
     * Adds to the closure all the lambda accessible states from a given source
     * state.
     *
     * @param closure     The closure.
     * @param sourceState The source state.
     */
    private void addLambdaAccessibleStates(Set<NondeterministicFiniteAutomatonState> closure,
                                           NondeterministicFiniteAutomatonState sourceState) {
        closure.add(sourceState);

        for (NondeterministicFiniteAutomatonState state : sourceState.getLambdaTransitions()) {
            if (!closure.contains(state)) {
                addLambdaAccessibleStates(closure, state);
            }
        }
    }
}
