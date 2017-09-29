package EventHandlers;

import GraphAdapter.GraphAdapter;
import gui.GraphicalUserInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jgraph.graph.DefaultGraphCell;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;

/**
 * An object of this class listens to presses of the "Delete" menu item
 * in the "Edit" menu of the user interface. When the "Delete" menu item
 * is pressed, all selected cells are removed from graph.
 * Note: In the JGraph terminology, a cell can be a vertex, edge, or port
 * (the things used by JGraph to figure where an edge and vertex meet).
 */
public class DeleteListener implements ActionListener 
{
    /**
     * Reference to the main user interface. This reference allows
     * this listener to obtain the graph to make changes to it.
     */
    private GraphicalUserInterface gui;
    /**
     * Constructor
     * @param gui The main user interface that holds the graph.
     */
    public DeleteListener() 
    {
        gui = GraphicalUserInterface.getInstance();
    }
    /**
     * Returns an ArrayList of all the edges connecting other cells to cell.
     * @param cell The cell we want all the edges to.
     * @return An ArrayList of all edges related to cell.
     */
    private ArrayList<Object> getEdges(Object cell)
    {
    	JGraph graph = gui.getGraph();
    	GraphModel model = graph.getModel();
    	ArrayList<Object> listEdges = new ArrayList<Object>();
    	int numChildren = model.getChildCount(cell);
    	for (int i = 0; i < numChildren; i++) 
    	{
	    	Object port = model.getChild(cell, i);
	    	if (model.isPort(port)) 
	    	{
		    	Iterator iter = model.edges(port);
		    	while (iter.hasNext()) 
		    	{
		    		listEdges.add(iter.next());
		    	}
	    	}
    	}
    	return listEdges;
    }
    /**
     * The method that is called by the "Delete" menu item when it is pressed.
     * This method deletes all of the selected cells from the graph.
     * @param e Not used.
     */
    public void actionPerformed(ActionEvent e)
    {
        if ( !GraphAdapter.getInstance().isPaused() )
        {
          return;
        }
        JGraph graph = gui.getGraph();
        GraphModel model = graph.getModel();
        Object[] selectedCells = graph.getSelectionCells();
        for(int i = 0 ; i < selectedCells.length ; i++)
        {
        	Object[] relatedEdges = getEdges(selectedCells[i]).toArray();
        	if (relatedEdges.length > 0)
        		graph.getGraphLayoutCache().remove(relatedEdges);
        }
        	
        if (selectedCells.length > 0) 
        {
            graph.getGraphLayoutCache().remove(selectedCells);
        	graph.repaint();
        }
    }
}
