package GraphAdapter;

import gui.GraphicalUserInterface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import map.Countries;
import map.Country;
import map.Map;
import map.State;
import org.jgraph.graph.DefaultPort;

import EventHandlers.ShowGlobalLogListener;

/**
 * An instance of this class wraps around a JGraph object. This object
 * makes it easier for the user to access relevant information from the
 * JGraph object.
 */
public class GraphAdapter {

	/**
	 * Holds an instance of the GraphAdapter class. This ensures
	 * that everyone uses the same object, and that all the methods
	 * are properly synchronized.
	 */
	private static GraphAdapter instance = null;
	
    /**
     * This constructor does nothing.
     */
    private GraphAdapter() {
    }
    
    /**
     * Returns the singleton GraphAdapter.
     * @return The singleton GraphAdapter instance.
     */
    public static GraphAdapter getInstance()
    {
    	if (instance == null)
    	{
    		instance = new GraphAdapter();
    	}
    	return instance;
    }

    /**
     * This method returns the Country object that holds all of the
     * countries that make up the map.
     * @return The Countries object that holds all of the countries that
     * make up the map.
     */
    public synchronized Countries getCountries() {
        return ((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).getCountries();
    }
    
    /**
     * This method returns the cells that are selected.
     * @return Array of cells that are selected.
     */
    public synchronized Object[] getSelectedCells() {
    	return GraphicalUserInterface.getInstance().getGraph().getSelectionCells();
    }
    
    /**
     * Returns true if the cell contains a state.
     * @return
     */
    public synchronized boolean isVertex(Object o)
    {
    	DefaultGraphCell cell = (DefaultGraphCell) o;
    	return cell.getUserObject() instanceof State;
    }

    /**
     * This method returns the country whose turn it is.
     * @return The Country object whose turn it is.
     */
    public synchronized Country getTurn() {
        return ((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).getTurn();
    }

    /**
     * This method is used to set the country whose turn it is. It's important
     * to use this method immediately after the user pauses the simulation.
     * @param country The country whose turn it is.
     */
    public synchronized void setTurn(Country country) {
    	((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).setTurn( country );
    }

    /**
     * This method allows the user to retrieve all of the neighboring states of
     * state.
     * @param state The state the user wants to find the neighbors of.
     * @return An array of neighbors of state.
     */
	public synchronized State[] getNeighbors( State state ) {
        DefaultGraphCell stateCell = getCellFromState( state );
        if ( stateCell == null ) {
            return new State[ 0 ];
        }
        DefaultPort statePort = (DefaultPort) stateCell.getChildAt( 0 );
        Iterator<DefaultEdge> neighborEdgesIterator = (Iterator<DefaultEdge>) statePort.edges();
        HashSet<State> neighborStates = new HashSet<State>();
        DefaultPort source;
        DefaultPort target;
        DefaultEdge edge;
        while ( neighborEdgesIterator.hasNext() ) {
            edge = neighborEdgesIterator.next();
            source = (DefaultPort) edge.getSource();
            target = (DefaultPort) edge.getTarget();
            neighborStates.add( (State) ( (DefaultGraphCell) target.getParent() ).getUserObject() );
            neighborStates.add( (State) ( (DefaultGraphCell) source.getParent() ).getUserObject() );
        }
        neighborStates.remove( state );
        State[] output = new State[ neighborStates.size() ];
        neighborStates.toArray( output );
        return output;
    }
	
	/**
	 * This method returns the global log.
	 * @return
	 */
	public synchronized ArrayList<String> getGlobalLog()
	{
		return ((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).getGlobalLog();
	}
	
	/**
	 * This method allows the user to append a string to the global log.
	 * @param string The string to append to the global log.
	 */
	public synchronized void appendToGlobalLog(String string)
	{
		((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).appendToGlobalLog(string);
	}
	
	/**
	 * Returns true if the simulation is paused, and false otherwise.
	 * @return True if the simulation is paused, and false otherwise.
	 */
	public synchronized boolean isPaused()
	{
		return ((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).isPaused();
	}
	
	/**
	 * Allows the user to set the paused value.
	 * @param paused The value to set the paused variable to. Pass true if
	 * the simulation should pause, and false otherwise.
	 */
	public synchronized void setPaused(boolean paused)
	{
		((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).setPaused(paused);
	}
	
	/**
	 * Allows the user to toggle the paused value from true to false (or false to true)
	 * in one atomic step.
	 */
	public synchronized void togglePaused()
	{
		((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).togglePaused();
	}
	
	/**
	 * This method allows the user to indicate that the simulation has ended.
	 * @param finished False if the simulation has finished. True otherwise.
	 */
	public synchronized void setSimulationFinished(boolean finished)
	{
		((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).setSimulationFinished(finished);
	}
	
	/**
	 * Allows the user to tell if the simulation is finished.
	 * @return True if the simulation is finished, and false otherwise. 
	 */
	public synchronized boolean isSimulationFinished()
	{
		return ((Map)((DefaultGraphCell)GraphicalUserInterface.getInstance().getGraph().
        		getModel().getRootAt(0)).getUserObject()).isSimulationFinished();
	}
	
	/**
	 * Calling this method makes sure that the given state's country's color is
	 * the same as the given state's cell's color.
	 * @param state The state whose cell's color must be synchronized with its
	 * country's color.
	 */
	public synchronized void synchronizeColor(State state)
	{
		HashMap nested = new HashMap();
		HashMap newAttributes = new HashMap();
		GraphConstants.setBackground(newAttributes, state.getCountry().getColor());
		nested.put(getCellFromState(state), newAttributes);
		GraphicalUserInterface.getInstance().getGraph().getGraphLayoutCache().
		edit(nested, null, null, null);
	}

    /**
     * This is a helper method. It searches the {@see JGraph} object for the
     * provided state.
     * @param state The state that is in the {@see DefaultGraphCell} object that
     * will be returned.
     * @return The DefaultGraphCell object that contains the
     * provided state.
     */
    private DefaultGraphCell getCellFromState( State state ) {
        DefaultGraphCell root;
        GraphModel model = GraphicalUserInterface.getInstance().getGraph().getModel();
        for ( int i = 1 ; i < model.getRootCount() ; ++i ) {
            root = (DefaultGraphCell) model.getRootAt( i );
            if ( ( (State) root.getUserObject() ) == state ) {
                return root;
            }
        }
        return null;
    }
}
