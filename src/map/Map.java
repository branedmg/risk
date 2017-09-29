package map;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class' sole purpose is to save the information relating to the
 * simulation.
 */
public class Map implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
     * The object that contains all of the countries in the map.
     */
    private volatile Countries countries;
    
    /**
     * The country whose turn it is. The volatile modifier is used to make sure
     * that all parties see the changes made to country immediately after it is changed.
     * When the program starts the first time, the countries aren't known, and therefore
     * null implies that when the simulation is first initiated, the first country in the
     * list of countries should be used to run the simulation.
     */
    private volatile Country turn = null;
    
    /**
     * This variable is used to indicate if the simulation is paused or not.
     * The default value is true (the simulation is paused).
     */
    private volatile boolean paused = true;
    
    /**
     * The simulation's global log.
     */
    private volatile ArrayList<String> globalLog;
    
    /**
     * This variable is true if the simulation has ended (i.e. one country
     * owns all the states.) and false otherwise.
     */
    private volatile boolean simulationFinished = false;
    
    /**
     * Null constructor. All variables need to be set by the user.
     */
    public Map() {
    	countries = new Countries();
    	globalLog = new ArrayList<String>();
    }

    /**
     * Returns the countries that make up the map.
     * @return Countries that make up the map.
     */
    public synchronized Countries getCountries() {
        return countries;
    }

    /**
     * Sets the countries that make up the map.
     * @param countries Countries that make up the map.
     */
    public synchronized void setCountries( Countries countries ) {
        this.countries = countries;
    }

    /**
     * Returns the country whose turn it is.
     * @return The country whose turn it is.
     */
    public synchronized Country getTurn() {
        return turn;
    }

    /**
     * Sets the country whose turn it is.
     * @param country The country whose turn it is.
     */
    public synchronized void setTurn( Country country ) {
        this.turn = country;
    }

    /**
     * Returns true if the simulation is paused.
     * @return True if the simulation is paused.
     */
    public synchronized boolean isPaused()
    {
    	return paused;
    }
    
    /**
     * Allows users to pause the simulation.
     * @param paused True if the simulation should be paused.
     */
    public synchronized void setPaused(boolean paused)
    {
    	this.paused = paused;
    }
    
    /**
     * Allows the user to atomically change the paused variable. If the simulation
     * is paused, then the method unpauses the simulation. If the simulation isn't paused,
     * then the method pauses the simulation.
     */
    public synchronized void togglePaused()
    {
    	paused = !paused;
    }
    
    /**
     * Returns true if the simulation has finished (i.e. one country owns all of the states)
     * and false otherwise.
     * @return Returns true if the simulation has finished.
     */
    public synchronized boolean isSimulationFinished()
    {
    	return simulationFinished;
    }
    
    /**
     * This method allows the user to declare that the simulation has ended.
     * @param finished True if the simulation has ended, and false otherwise.
     */
    public synchronized void setSimulationFinished(boolean finished)
    {
    	simulationFinished = finished;
    }
    
    /**
     * Sets the map's global log.
     * @param globalLog The global log.
     */
    public synchronized void setGlobalLog(ArrayList<String> globalLog)
    {
    	this.globalLog = globalLog;
    }
    
    /**
     * Returns the map's global log.
     * @return The map's global log.
     */
    public synchronized ArrayList<String> getGlobalLog()
    {
    	return globalLog;
    }
    
    /**
     * This method allows the user to append a string to the global log.
     * @param string The string to append to the global log.
     */
    public synchronized void appendToGlobalLog(String string)
    {
    	globalLog.add(string);
    }
    
}
