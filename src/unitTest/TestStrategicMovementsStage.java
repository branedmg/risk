/**
 * 
 */
package unitTest;

import gui.GraphicalUserInterface;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import stages.StrategicMovementsStage;

import map.Country;
import map.State;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Instances of this class are used by the test runner to test the StrategicMovementsStage
 * @author ManuelCN
 *
 */
public class TestStrategicMovementsStage extends TestCase {

	/**
	 * @param name
	 */
	public TestStrategicMovementsStage(String name) {
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
	 * Test method for {@link stages.StrategicMovementsStage#runStrategicMovementsStage(map.Country)}.
	 */
	public void testRunStrategicMovementsStage() {
		// There will be 4 friendly states {s1, s2, s3, s4}
		// and 1 enemy state {e1}
		// The graph will be as follows G = (V, E), where V = {s1, s2, s3, s4, e1}
		// and E = { {s1, e1} , {s1, s2} , {s1, s3} , {s1, s4} , {s2, e1}, {s3, s4} }
		// The flags will be set as follows
		// state  flag
		// s1     reinforce
		// s2     attack e1
		// s3     reinforce
		// s4     none
		// e1     none
		State s1, s2, s3, s4;
		State e1;
		s1 = new State();
		s2 = new State();
		s3 = new State();
		s4 = new State();
		e1 = new State();
		
		Country enemy = new Country();
		e1.setCountry(enemy);
		enemy.addState(e1);
		Country friendly = new Country();
		s1.setCountry(friendly);
		s2.setCountry(friendly);
		s3.setCountry(friendly);
		s4.setCountry(friendly);
		friendly.addState(s1);
		friendly.addState(s2);
		friendly.addState(s3);
		friendly.addState(s4);
		
		s1.setReinforce(true);
		s2.setAttack(e1);
		s3.setReinforce(true);
		
		// Now we must set the population, soldiers, and tanks for each state.
		int s1initialPopulation = 200;
		int s1initialSoldiers = 54;
		int s1initialTanks = 12;
		int s2initialPopulation = 500;
		int s2initialSoldiers = 64;
		int s2initialTanks = 23;
		int s3initialPopulation = 100;
		int s3initialSoldiers = 26;
		int s3initialTanks = 3;
		int s4initialPopulation = 89;
		int s4initialSoldiers = 12;
		int s4initialTanks = 8;
		int e1initialPopulation = 500;
		int e1initialSoldiers = 52;
		int e1initialTanks = 30;
		
		// We also need to computer the final values for these variables.
		// Given our scenario, the only states that should get altered are
		// s1, s3, and s4.
		int s1finalPopulation = s1initialPopulation + 2 + 2 + 2 + 2 + 2 + 2 ;
		int s1finalSoldiers = s1initialSoldiers + 2;
		int s1finalTanks = s1initialTanks + 2;
		int s2finalPopulation = s2initialPopulation;
		int s2finalSoldiers = s2initialSoldiers;
		int s2finalTanks = s2initialTanks;
		int s3finalPopulation = s3initialPopulation + 2 + 2 + 2 + 2 + 2 + 2;
		int s3finalSoldiers = s3initialSoldiers + 2;
		int s3finalTanks = s3initialTanks + 2;
		int s4finalPopulation = s4initialPopulation - 2 - 2 - 2 - 2 -16;
		int s4finalSoldiers = s4initialSoldiers - 2 - 2;
		int s4finalTanks = s4initialTanks - 2 - 2;
		int e1finalPopulation = e1initialPopulation;
		int e1finalSoldiers = e1initialSoldiers;
		int e1finalTanks = e1initialTanks;
		
		s1.setPopulation(s1initialPopulation);
		s1.setSoldiers(s1initialSoldiers);
		s1.setTanks(s1initialTanks);
		s2.setPopulation(s2initialPopulation);
		s2.setSoldiers(s2initialSoldiers);
		s2.setTanks(s2initialTanks);
		s3.setPopulation(s3initialPopulation);
		s3.setSoldiers(s3initialSoldiers);
		s3.setTanks(s3initialTanks);
		s4.setPopulation(s4initialPopulation);
		s4.setSoldiers(s4initialSoldiers);
		s4.setTanks(s4initialTanks);
		e1.setPopulation(e1initialPopulation);
		e1.setSoldiers(e1initialSoldiers);
		e1.setTanks(e1initialTanks);
		
		DefaultGraphCell cellS1, cellS2, cellS3, cellS4;
		DefaultGraphCell cellE1;
		cellS1 = new DefaultGraphCell(s1);
		cellS2 = new DefaultGraphCell(s2);
		cellS3 = new DefaultGraphCell(s3);
		cellS4 = new DefaultGraphCell(s4);
		cellE1 = new DefaultGraphCell(e1);
		
		DefaultPort portS1, portS2, portS3, portS4;
		DefaultPort portE1;
		portS1 = new DefaultPort();
		portS2 = new DefaultPort();
		portS3 = new DefaultPort();
		portS4 = new DefaultPort();
		portE1 = new DefaultPort();
		
		cellS1.add(portS1);
		cellS2.add(portS2);
		cellS3.add(portS3);
		cellS4.add(portS4);
		cellE1.add(portE1);
		
		DefaultEdge s1e1, s1s2, s1s3, s1s4, s2e1, s3s4;
		s1e1 = new DefaultEdge();
		s1s2 = new DefaultEdge();
		s1s3 = new DefaultEdge();
		s1s4 = new DefaultEdge();
		s2e1 = new DefaultEdge();
		s3s4 = new DefaultEdge();
		
		s1e1.setSource(portS1);
		s1e1.setTarget(portE1);
		s1s2.setSource(portS1);
		s1s2.setTarget(portS2);
		s1s3.setSource(portS1);
		s1s3.setTarget(portS3);
		s1s4.setSource(portS1);
		s1s4.setTarget(portS4);
		s2e1.setSource(portS1);
		s2e1.setTarget(portE1);
		s3s4.setSource(portS3);
		s3s4.setTarget(portS4);
		
		GraphicalUserInterface gui = GraphicalUserInterface.getInstance();
		JGraph controller = gui.getGraph();
		GraphLayoutCache view = controller.getGraphLayoutCache();
		
		DefaultGraphCell[] cells = {cellS1, cellS2, cellS3, cellS4, cellE1, s1s2, s1s3, s1s4, s1e1, s2e1, s3s4};
		view.insert(cells);
		
		StrategicMovementsStage.runStrategicMovementsStage(friendly);
		
		// Now, each state should have the new values for population, soldiers, and tanks.
		System.out.println(s4finalPopulation);
		System.out.println(s4.getPopulation());
		Assert.assertEquals(s1finalPopulation, s1.getPopulation());
		Assert.assertEquals(s1finalSoldiers, s1.getSoldiers());
		Assert.assertEquals(s1finalTanks, s1.getTanks());
		Assert.assertEquals(s2finalPopulation, s2.getPopulation());
		Assert.assertEquals(s2finalSoldiers, s2.getSoldiers());
		Assert.assertEquals(s2finalTanks, s2.getTanks());
		Assert.assertEquals(s3finalPopulation, s3.getPopulation());
		Assert.assertEquals(s3finalSoldiers, s3.getSoldiers());
		Assert.assertEquals(s3finalTanks, s3.getTanks());
		Assert.assertEquals(s4finalPopulation, s4.getPopulation());
		Assert.assertEquals(s4finalSoldiers, s4.getSoldiers());
		Assert.assertEquals(s4finalTanks, s4.getTanks());
		Assert.assertEquals(e1finalPopulation, e1.getPopulation());
		Assert.assertEquals(e1finalSoldiers, e1.getSoldiers());
		Assert.assertEquals(e1finalTanks, e1.getTanks());
	}

}
