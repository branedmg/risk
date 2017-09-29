package utils;

import map.State;

/**
 * Stores the method that calculates a states military strength.
 */
public class Utils {
	/**
	 * The c is the formula military_strength = number_of_soldiers + c x number_of_tanks.
	 */
    public static final int c = 5;
    /**
     * Computes the military strength of the given state.
     * @param state The state for which the military strength needs to be calculated.
     * @return The military strength of the provided state.
     */
    public static int getMilitaryStrength(State state) {
        return state.getSoldiers() + c * state.getTanks();
    }
}
