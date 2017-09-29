/**
 * 
 */
package unitTest;

import map.State;
import map.State.TechLevel;
import ArmyFactory.Army;
import ArmyFactory.HighTechArmy;
import ArmyFactory.LowTechArmy;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test class for LowTechArmy.
 * @author ManuelCN
 *
 */
public class TestLowTechArmy extends TestCase {
	private LowTechArmy lowTechArmy;
	private State lowTechState;
	int lowTechStatePopulation;
	int lowTechStateSoldiers;
	int lowTechStateTanks;
	int lowTechStateNewPopulation;
	int lowTechStatePopulationToAdd;
	int lowTechStateSoldiersToAdd;
	int lowTechStateTanksToAdd;
	/**
	 * @param name
	 */
	public TestLowTechArmy(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		lowTechState = new State();
		lowTechState.setTechLevel(TechLevel.high);
		lowTechStatePopulation = 300;
		lowTechStateSoldiers = 10;
		lowTechStateTanks = 12;
		lowTechStateNewPopulation = lowTechStatePopulation + ( (int) (Army.POPULATIONINCREASEFACTOR * lowTechStatePopulation) );
		lowTechStatePopulationToAdd =  lowTechStateNewPopulation - lowTechStatePopulation;
		lowTechStateSoldiersToAdd =  ( (int) (Army.SOLDIERINCREASEFACTOR * lowTechStateNewPopulation) );
		lowTechStateTanksToAdd = 0;
		lowTechState.setPopulation(lowTechStatePopulation);
		lowTechState.setSoldiers(lowTechStateSoldiers);
		lowTechState.setTanks(lowTechStateTanks);
		lowTechArmy = new LowTechArmy(lowTechState);
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
		Assert.assertEquals(lowTechStatePopulationToAdd, lowTechArmy.getAddedPopulation());
	}

	/**
	 * Test method for {@link ArmyFactory.Army#getAddedSoldiers()}.
	 */
	public void testGetAddedSoldiers() {
		Assert.assertEquals(lowTechStateSoldiersToAdd, lowTechArmy.getAddedSoldiers());
	}

	/**
	 * Test method for {@link ArmyFactory.Army#getAddedTanks()}.
	 */
	public void testGetAddedTanks() {
		Assert.assertEquals(lowTechStateTanksToAdd, lowTechArmy.getAddedTanks());
	}

}
