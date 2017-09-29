/**
 * 
 */
package unitTest;

import ArmyFactory.Army;
import ArmyFactory.ArmyFactory;
import ArmyFactory.HighTechArmy;
import ArmyFactory.LowTechArmy;
import map.State;
import map.State.TechLevel;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Instances of this class are used by the test runner to test the static method in
 * ArmyFactory.
 * @author ManuelCN
 *
 */
public class TestArmyFactory extends TestCase {

	/**
	 * @param name
	 */
	public TestArmyFactory(String name) {
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
	 * This method tests that the createArmy method properly creates armies.
	 * Test method for {@link ArmyFactory.ArmyFactory#createArmy(map.State)}.
	 */
	public void testCreateArmy() {
		State lowTechState = new State();
		lowTechState.setTechLevel(TechLevel.low);
		int lowTechStatePopulation = 200;
		int lowTechStateSoldiers = 20;
		int lowTechStateTanks = 15;
		int lowTechStateNewPopulation = lowTechStatePopulation + ( (int) (Army.POPULATIONINCREASEFACTOR * lowTechStatePopulation) );
		int lowTechStatePopulationToAdd =  lowTechStateNewPopulation - lowTechStatePopulation;
		int lowTechStateSoldiersToAdd = ( (int) (Army.SOLDIERINCREASEFACTOR * lowTechStateNewPopulation) );
		int lowTechStateTanksToAdd = 0;
		lowTechState.setPopulation(lowTechStatePopulation);
		lowTechState.setSoldiers(lowTechStateSoldiers);
		lowTechState.setTanks(lowTechStateTanks);
		// =================
		State highTechState = new State();
		highTechState.setTechLevel(TechLevel.high);
		int highTechStatePopulation = 300;
		int highTechStateSoldiers = 10;
		int highTechStateTanks = 12;
		int highTechStateNewPopulation = highTechStatePopulation + ( (int) (Army.POPULATIONINCREASEFACTOR * highTechStatePopulation) );
		int highTechStatePopulationToAdd =  highTechStateNewPopulation - highTechStatePopulation;
		int highTechStateSoldiersToAdd =  ( (int) (Army.SOLDIERINCREASEFACTOR * highTechStateNewPopulation) );
		int highTechStateTanksToAdd = ( (int) (Army.TANKINCREASEFACTOR * highTechStateNewPopulation) );
		highTechState.setPopulation(highTechStatePopulation);
		highTechState.setSoldiers(highTechStateSoldiers);
		highTechState.setTanks(highTechStateTanks);
		
		Army army = ArmyFactory.createArmy(lowTechState);
		Assert.assertTrue(army instanceof LowTechArmy);
		Assert.assertEquals(lowTechStatePopulationToAdd, army.getAddedPopulation());
		Assert.assertEquals(lowTechStateSoldiersToAdd, army.getAddedSoldiers());
		Assert.assertEquals(lowTechStateTanksToAdd, army.getAddedTanks());
		
		army = ArmyFactory.createArmy(highTechState);
		Assert.assertTrue(army instanceof HighTechArmy);
		Assert.assertEquals(highTechStatePopulationToAdd, army.getAddedPopulation());
		Assert.assertEquals(highTechStateSoldiersToAdd, army.getAddedSoldiers());
		Assert.assertEquals(highTechStateTanksToAdd, army.getAddedTanks());
	}

}
