package map;

import java.util.ArrayList;

/**
 * An object of this class holds a state's history.
 */
public class StateLog 
{
	/**
	 * The variable that holds the state's history.
	 */
	private ArrayList<State> state_history;
	
	/**
	 * Constructor. Sets the state's history to empty by default.
	 */
	public StateLog()
	{
		state_history = new ArrayList<State>();
	}
	
	/**
	 * Adds the state of a state.
	 * @param s The state that is a clone of the state the user wishes to log.
	 */
	public synchronized void add(State s)
	{
		synchronized (state_history)
		{
			this.state_history.add(s);
		}
	}
	
	/**
	 * Returns the history of the state.
	 * @return The history of the state.
	 */
	public synchronized ArrayList<State> getState_history()
	{
		synchronized(state_history)
		{
			return state_history;
		}
	}
	
	/**
	 * Sets the state's history.
	 * @param state_history The state's history.
	 */
	public synchronized void setState_history(ArrayList<State> state_history)
	{
		synchronized (state_history)
		{
			this.state_history = state_history;
		}
	}
	
}
