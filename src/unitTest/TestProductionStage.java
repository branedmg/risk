/**
 * 
 */
package unitTest;

import stages.ProductionStage;
import ArmyFactory.Army;
import map.Country;
import map.State;
import map.State.TechLevel;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * An instance of this class tests the production stage. It tests a particular case
 * to make sure that the values of population, soldiers, and tanks are updated properly.
 * @author ManuelCN
 *
 */
public class TestProductionStage extends TestCase {

	/**
	 * @param name
	 */
	public TestProductionStage(String name) {
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
	 * This code creates 2 states, sets their population, soldiers, and tanks values, computes
	 * the expected new population, soldiers, and tanks values, and compares these new values
	 * to the ones produced by the production stage.
	 * Test method for {@link stages.ProductionStage#runProductionStage(map.Country)}.
	 */
	public void testRunProductionStage() {
		Country country = new Country();
		
		State state1 = new State();
		int population1 = 1000;
		int postPopulation1 = population1 + (int) (Army.POPULATIONINCREASEFACTOR * population1);
		int soldiers1 = 200;
		int postSoldiers1 =  soldiers1 + ( (int) (Army.SOLDIERINCREASEFACTOR * postPopulation1) );
		int tanks1 = 20;
		int postTanks1 = tanks1 + ( (int) (Army.TANKINCREASEFACTOR * postPopulation1) );
		TechLevel techLevel1 = TechLevel.high;
		state1.setPopulation(population1);
		state1.setSoldiers(soldiers1);
		state1.setTanks(tanks1);
		state1.setTechLevel(techLevel1);
		
		State state2 = new State();
		int population2 = 555;
		int postPopulation2 = population2 + (int) (Army.POPULATIONINCREASEFACTOR * population2);
		int soldiers2 = 111;
		int postSoldiers2 = soldiers2 + ( (int) (Army.SOLDIERINCREASEFACTOR * postPopulation2) );
		int tanks2 = 0;
		int postTanks2 = 0;
		TechLevel techLevel2 = TechLevel.low;
		state2.setPopulation(population2);
		state2.setSoldiers(soldiers2);
		state2.setTanks(tanks2);
		state2.setTechLevel(techLevel2);
		
		country.addState(state1);
		country.addState(state2);
		
		ProductionStage.runProductionStage(country);
		
		System.out.println(postPopulation1);
		System.out.println(state1.getPopulation());
		Assert.assertEquals(postPopulation1, state1.getPopulation());
		
		Assert.assertEquals(postSoldiers1, state1.getSoldiers());
		Assert.assertEquals(postTanks1, state1.getTanks());
		
		Assert.assertEquals(postPopulation2, state2.getPopulation());
		Assert.assertEquals(postSoldiers2, state2.getSoldiers());
		Assert.assertEquals(postTanks2, state2.getTanks());
	}

}
