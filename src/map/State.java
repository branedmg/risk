package map;

import java.io.Serializable;
import java.util.ArrayList;

import utils.Utils;

/**
 * Objects of this class represent the states of the map.
 */
public class State implements Serializable, Comparable<State>,Cloneable
{
	/**
	 * Allows this state to be cloned.
	 */
	@Override
	public Object clone()
    {
    try
       {
       return super.clone();
       }
    catch ( CloneNotSupportedException e )
       {
       return null;
       }
    }

	/**
	 * This variable references the state's log.
	 */
	private volatile StateLog state_log = new StateLog();
	
	/**
	 * Returns the state's log.
	 * @return The state's log.
	 */
	public synchronized StateLog getState_log() {
		return state_log;
	}

	/**
	 * Method that allows the state's log to be specified.
	 * @param state_log The state's log.
	 */
	public synchronized void setState_log(StateLog state_log) {
		this.state_log = state_log;
	}

	/**
	 * This method causes a clone of this state to be appended to the state's
	 * log. This allows for a future review of a state's state at the time the
	 * method was called.
	 */
	public synchronized void log_state()
	{
		state_log.add((State)this.clone());
	}

    /**
     * The allowed tech levels.
     */
    public static enum TechLevel {

        low, high
    };
    /**
     * A reference to the country the state belongs to.
     */
    private volatile Country country;
    /**
     * The name of the state.
     */
    private volatile String name;
    /**
     * The population of the state.
     */
    private volatile int population;
    /**
     * The number of soldiers that fight for the state.
     */
    private volatile int soldiers;
    /**
     * The number of tanks that fight for the state.
     */
    private volatile int tanks;
    /**
     * The technological level of a state.
     */
    private volatile TechLevel techLevel;
    /**
     * The attack flag.
     */
    private volatile State attack;
    /**
     * The reinforce flag.
     */
    public volatile boolean reinforce;
    
    /**
     * Constructor. Sets the state's tech level to low by default.
     */
    public State() {
        this.techLevel = TechLevel.low;
    }

    /**
     * Returns the country that owns this state.
     * @return Country that owns this state.
     */
    public synchronized Country getCountry() {
        return country;
    }

    /**
     * Sets the country that owns this state.
     * @param country The country that owns this state.
     */
    public synchronized void setCountry( Country country ) {
        this.country = country;
    }

    /**
     * Returns the name of the state.
     * @return The name of the state.
     */
    public synchronized String getName() {
        return name;
    }

    /**
     * Sets the name of the state.
     * @param name The name of the state.
     */
    public synchronized void setName( String name ) {
        this.name = name;
    }

    /**
     * Returns the population of this state.
     * @return The population of this state.
     */
    public synchronized int getPopulation() {
        return population;
    }

    /**
     * Sets the population of this state.
     * @param population The population of this state.
     */
    public synchronized void setPopulation( int population ) {
        this.population = population;
    }

    /**
     * Returns the number of soldiers for this state.
     * @return The number of soldiers for this state.
     */
    public synchronized int getSoldiers() {
        return soldiers;
    }

    /**
     * Sets the number of soldiers for this state.
     * @param soldiers The number of soldiers for this state.
     */
    public synchronized void setSoldiers( int soldiers ) {
        this.soldiers = soldiers;
    }

    /**
     * Returns the number of tanks for this state.
     * @return The number of tanks for this state.
     */
    public synchronized int getTanks() {
        return tanks;
    }

    /**
     * Sets the number of thanks for this state.
     * @param tanks The number of tanks for this state.
     */
    public synchronized void setTanks( int tanks ) {
        this.tanks = tanks;
    }

    /**
     * Returns the tech level of this state.
     * @return The tech level of this state.
     */
    public synchronized TechLevel getTechLevel() {
        return techLevel;
    }

    /**
     * Sets the tech level of this state.
     * @param techLevel The tech level of this state.
     */
    public synchronized void setTechLevel( TechLevel techLevel ) {
        this.techLevel = techLevel;
    }

    /**
     * Used by JGraph to display the state's information.
     */
    @Override
    public String toString() {
    	synchronized (this)
    	{
	        if ( name != null ) {
	            return name + " - " + population + " - " + soldiers + " - " + tanks;
	        }
	        return "null";
    	}
    }

    /**
     * Returns the state this state has been set to attack.
     * @return The state this state will or has attacked.
     */
    public synchronized State getAttack() {
        return attack;
    }

    /**
     * Sets the state this state will attack.
     * @param attack The state this state will attack.
     */
    public synchronized void setAttack( State attack ) {
        this.attack = attack;
    }

    /**
     * Returns whether this state requires reinforcements or not.
     * @return True is the state needs reinforcements, and false otherwise.
     */
    public synchronized boolean isReinforce() {
        return reinforce;
    }

    /**
     * Allows to set the reinforce flag.
     * @param reinforce True if the state needs reinforcements.
     */
    public synchronized void setReinforce( boolean reinforce ) {
        this.reinforce = reinforce;
    }

    /**
     * Returns the miltary strength of a state given by s + 5 * t, where
     * s is the number of soldiers of the state, t is the number of tanks of the state,
     * and c is some number greater than 1.
     * @return The military strength of the state.
     */
    public synchronized int getMilitaryStrength() {
        return Utils.getMilitaryStrength( this );
    }

    /**
     * Allows a list of states to be sorted.
     */
    @Override
    public int compareTo( State state ) {
        synchronized (this)
        {
        	return Utils.getMilitaryStrength( this ) - Utils.getMilitaryStrength( state );
        }
    }
}
