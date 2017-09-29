package map;

import java.util.ArrayList;
import java.awt.Color;
import java.io.Serializable;

/**
 * Class representing a country.
 */
public class Country implements Serializable 
{
	
    /**
     * The name of the country.
     */
     private volatile String name;
     
    /**
     * A list of the states that make up the country.
     */
    private volatile ArrayList<State> states;
    
    /**
     * The color assigned to the country.
     */
    private volatile Color color;

    /**
     * Constructor. Creates an empty list of states.
     */
    public Country()
    {
    	states = new ArrayList<State>();
    }
    
    /**
     * Returns the name of a country.
     * @return The name of the country.
     */
    public synchronized String getName()
    {
        return name;
    }

    /**
     * Sets the name of the country to name.
     * @param name The name to give to the country.
     */
    public synchronized void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the color of the country.
     * @return The color of the country.
     */
    public synchronized Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of the country.
     * @param c
     */
    public synchronized void setColor(Color c)
    {
        this.color = c;
    }

    /**
     * Returns the color of the country.
     * @return The color of the country.
     */
    public synchronized ArrayList<State> getStates()
    {
        return states;
    }

    /**
     * Sets the states of the country.
     * @param states The states of the country.
     */
    public synchronized void setStates(ArrayList<State> states)
    {
        this.states = states;
    }
    
    /**
    * Returns the states of a country as an array of states.
    * @return statesArray The array of states belonging to the country.
    */
    public synchronized State[] getStatesArray()
    {
       State[] stateArray = new State[states.size()];
       states.toArray( stateArray );
       return stateArray;
    }
    
    /**
     * Adds the specified state to the list of states owned by this country.
     * @param state The state to be added.
     */
    public synchronized void addState(State state)
    {
        states.add(state);
    }

    /**
     * Removes the specified state from the list of states owned by this country.
     * @param state State to be removed.
     */
    public synchronized void deleteState(State state)
    {
        states.remove(state);
    }

    /**
     * Returns the number of states that make up the country.
     * @return
     */
    public synchronized int getNumberOfStates()
    {
        return states.size();
    }


    /**
     * Returns the name of the country as a string. Returns the string "null" if the Country's
     * name hasn't been specified yet.
     */
    @Override
    public synchronized String toString()
    {
        if (this.name == null) return "null";
        return this.name;
    }

}
