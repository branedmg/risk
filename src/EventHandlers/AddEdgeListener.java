package EventHandlers;

import GraphAdapter.GraphAdapter;
import gui.GraphicalUserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.Port;

/**
 * Listens to presses of the "Add Edge" menu item in the "Edit" menu.
 */
public class AddEdgeListener implements ActionListener 
{
    /**
     * Reference to the user interface where the graph object that
     * represents the map is held.
     */
    private GraphicalUserInterface gui;

    /**
     * Constructor
     * @param gui The user interface where the graph object that
     * represents the map is held.
     */
    public AddEdgeListener()
    {
        gui = GraphicalUserInterface.getInstance();
    }
    /**
     * Tests if cell1 and cell2 are already linked or not.
     * @param cell1
     * @param cell2
     * @return boolean Returns true when cell1 and cell2 aren't linked yet.
     * Returns false otherwise.
     */
    private boolean notAlreadyLinked(Object cell1,Object cell2)
    {
    	JGraph graph = gui.getGraph();
    	GraphModel model = graph.getModel();
    	ArrayList<Object> listEdges = new ArrayList<Object>();
    	int numChildren = model.getChildCount(cell1);
    	
    	for (int i = 0; i < numChildren; i++) 
    	{
	    	Object port = model.getChild(cell1, i);
	    	if (model.isPort(port)) 
	    	{
		    	Iterator iter = model.edges(port);
		    	while (iter.hasNext()) 
		    	{
		    		Object thePort = iter.next();
		    		if( cell2.equals(model.getParent(model.getSource(thePort))) || cell2.equals(model.getParent(model.getTarget(thePort))) )
		    			return false;
		    	}
	    	}
    	}
    	return true;
    }
    /**
     * Obtains the selected cells of the map and links them if the
     * cells are two distinct vertices that aren't already linked.
     * Note: In the language of JGraph, a cell can be a vertex, an edge,
     * or a port (the thing used by JGraph to figure where the an edge and
     * vertex connect)
     * @param actionEvent Not used.
     */
    public void actionPerformed(ActionEvent actionEvent) 
    {
        if ( !GraphAdapter.getInstance().isPaused() )
        {
          return;
        }
        JGraph graph = gui.getGraph();
        GraphModel model = graph.getModel();
        Object[] selectedCells = graph.getSelectionCells();
        if (selectedCells.length == 2 && nodesAreVertices(selectedCells))
        {
        	//test if edge alreasy exist
        	if(notAlreadyLinked(((DefaultGraphCell)selectedCells[0]),((DefaultGraphCell)selectedCells[1])))
        	{
	            DefaultEdge edge = new DefaultEdge();
	            edge.setSource(((DefaultGraphCell)selectedCells[0]).getChildAt(0));
	            edge.setTarget(((DefaultGraphCell)selectedCells[1]).getChildAt(0));
	            graph.getGraphLayoutCache().insert(edge);
        	}
        }
    }
    /**
     * Determines if the selected cells are vertices.
     * @param selectedCells The selected cells.
     * @return Returns true if the cells are vertices, and false if
     * there is a cell that is a port or edge.
     */
    private boolean nodesAreVertices(Object[] selectedCells)
    {
    	JGraph graph = gui.getGraph();
    	GraphModel model = graph.getModel();
    	return(!model.isEdge(selectedCells[0]) && !model.isPort(selectedCells[0]) && !model.isEdge(selectedCells[1]) && !model.isPort(selectedCells[1]) );
    }
}