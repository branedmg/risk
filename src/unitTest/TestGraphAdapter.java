/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package unitTest;

import junit.framework.TestCase;
import GraphAdapter.GraphAdapter;
import gui.GraphicalUserInterface;
import map.Countries;
import map.Country;
import map.Map;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphModel;
import junit.framework.Assert;
import map.State;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultPort;

/**
 * 
 * @author ManuelCN
 */
public class TestGraphAdapter extends TestCase {
	private Map map;
	private GraphModel model;
	private JGraph graph;
	private org.jgraph.graph.GraphLayoutCache view;
	private GraphicalUserInterface gui;

	public TestGraphAdapter(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gui = GraphicalUserInterface.getInstance();
		graph = gui.getGraph();
		model = graph.getModel();
		view = graph.getGraphLayoutCache();
		map = (Map) ((DefaultGraphCell) model.getRootAt(0)).getUserObject();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCountries() {
		System.out.println("testing getCountries");
		Country country1 = new Country();
		Country country2 = new Country();
		Countries countries = map.getCountries();
		countries.addCountry(country1);
		countries.addCountry(country2);
		map.setCountries(countries);
		GraphAdapter graphAdapter = GraphAdapter.getInstance();
		Assert.assertEquals(countries, graphAdapter.getCountries());
	}

	public void testGetTurn() {
		System.out.println("testing getTurn");
		Country country = new Country();
		map.setTurn(country);
		GraphAdapter graphAdapter = GraphAdapter.getInstance();
		Assert.assertEquals(country, graphAdapter.getTurn());
	}

	public void testSetTurn() {
		System.out.println("testing  setTurn");
		Country country = new Country();
		GraphAdapter graphAdapter = GraphAdapter.getInstance();
		graphAdapter.setTurn(country);
		Assert.assertEquals(country, map.getTurn());
	}

	public void testGetNeighbors() {
		System.out.println("testing getNeighbors");
		// I will create 2 States, 2 DefaultGraphCells, 2 DefaultPorts, and
		// 1 edge that will connect the two ports.
		State state1 = new State();
		State state2 = new State();
		DefaultGraphCell cell1 = new DefaultGraphCell(state1);
		DefaultGraphCell cell2 = new DefaultGraphCell(state2);
		DefaultPort port1 = new DefaultPort();
		DefaultPort port2 = new DefaultPort();
		cell1.add(port1);
		cell2.add(port2);
		DefaultEdge edge = new DefaultEdge();
		edge.setSource(port1);
		edge.setTarget(port2);
		DefaultGraphCell[] cells = { cell1, cell2, edge };
		view.insert(cells);
		GraphAdapter graphAdapter = GraphAdapter.getInstance();

		System.out.println("neighbors of state2");
		Assert.assertEquals(1, graphAdapter.getNeighbors(state2).length);
		Assert.assertEquals(state1, graphAdapter.getNeighbors(state2)[0]);

		System.out.println("neighbors of state1");
		Assert.assertEquals(1, graphAdapter.getNeighbors(state1).length);
		Assert.assertEquals(state2, graphAdapter.getNeighbors(state1)[0]);
	}

}
