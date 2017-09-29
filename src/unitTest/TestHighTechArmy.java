/**
 * 
 */
package unitTest;

import map.State;
import map.State.TechLevel;
import ArmyFactory.Army;
import ArmyFactory.HighTechArmy;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for HighTechArmy.
 * @author ManuelCN
 *
 */
public class TestHighTechArmy extends TestCase {
	private HighTechArmy highTechArmy;
	private State highTechState;
	int highTechStatePopulation;
	int highTechStateSoldiers;
	int highTechStateTanks;
	int highTechStateNewPopulation;
	int highTechStatePopulationToAdd;
	int highTechStateSoldiersToAdd;
	int highTechStateTanksToAdd;
	/**
	 * @param name
	 */
	public TestHighTechArmy(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		highTechState = new State();
		highTechState.setTechLevel(TechLevel.high);
		highTechStatePopulation = 300;
		highTechStateSoldiers = 10;
		highTechStateTanks = 12;
		highTechStatePopulationToAdd = ( (int) (Army.POPULATIONINCREASEFACTOR * highTechStatePopulation) );
		highTechStateNewPopulation =  highTechStatePopulationToAdd + highTechStatePopulation;
		highTechStateSoldiersToAdd =  ( (int) (Army.SOLDIERINCREASEFACTOR * highTechStateNewPopulation) );
		System.out.println((int)(Army.SOLDIERINCREASEFACTOR *highTechStateNewPopulation));
		highTechStateTanksToAdd = ( (int) (Army.TANKINCREASEFACTOR * highTechStateNewPopulation) );
		highTechState.setPopulation(highTechStatePopulation);
		highTechState.setSoldiers(highTechStateSoldiers);
		highTechState.setTanks(highTechStateTanks);
		highTechArmy = new HighTechArmy(highTechState);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link ArmyFactory.Army#getAddedPopulation()}.
	 */
	public void testGetAddedPopulation() {
		Assert.assertEquals(highTechStatePopulationToAdd, highTechArmy.getAddedPopulation());
	}

	/**
	 * Test method for {@link ArmyFactory.Army#getAddedSoldiers()}.
	 */
	public void testGetAddedSoldiers() {
		System.out.println(highTechStateSoldiersToAdd);
		System.out.println(highTechArmy.getAddedSoldiers());
		Assert.assertEquals(highTechStateSoldiersToAdd, highTechArmy.getAddedSoldiers());
	}

	/**
	 * Test method for {@link ArmyFactory.Army#getAddedTanks()}.
	 */
	public void testGetAddedTanks() {
		Assert.assertEquals(highTechStateTanksToAdd, highTechArmy.getAddedTanks());
	}

}
