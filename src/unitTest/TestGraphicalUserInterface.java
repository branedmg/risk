/**
 * 
 */
package unitTest;

import map.Map;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;

import gui.GraphicalUserInterface;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class used to test the GraphicalUserInterface class.
 * @author ManuelCN
 *
 */
public class TestGraphicalUserInterface extends TestCase {

	/**
	 * @param name
	 */
	public TestGraphicalUserInterface(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Makes sure that two calls to getInstance doesn't return two different objects.
	 * Test method for {@link gui.GraphicalUserInterface#getInstance()}.
	 */
	public void testGetInstance() {
		GraphicalUserInterface gui1 = GraphicalUserInterface.getInstance();
		GraphicalUserInterface gui2 = GraphicalUserInterface.getInstance();
		Assert.assertEquals(gui1, gui2);
	}

	/**
	 * Makes sure that getGraph() returns an instance of JGraph, and that the
	 * first cell in the model has a map object.
	 * Test method for {@link gui.GraphicalUserInterface#getGraph()}.
	 */
	public void testGetGraph() {
		GraphicalUserInterface gui = GraphicalUserInterface.getInstance();
		Assert.assertTrue(gui.getGraph() instanceof JGraph);
		Assert.assertTrue( ( (DefaultGraphCell) gui.getGraph().getModel().getRootAt(0) ).getUserObject() instanceof Map);
	}

}
