/**
 * 
 */
package unitTest;

import ArmyFactory.Army;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Instances of this class are used by the test runner to test the Army class.
 * @author ManuelCN
 *
 */
public class TestArmy extends TestCase {
	private Army army;

	/**
	 * @param name
	 */
	public TestArmy(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		army = new Army();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 
	 * Test method for {@link ArmyFactory.Army#getAddedPopulation()}.
	 */
	public void testGetAddedPopulation() {
		Assert.assertEquals(0, army.getAddedPopulation());
	}

	/**
	 * Test method for {@link ArmyFactory.Army#getAddedSoldiers()}.
	 */
	public void testGetAddedSoldiers() {
		Assert.assertEquals(0, army.getAddedSoldiers());
	}

	/**
	 * Test method for {@link ArmyFactory.Army#getAddedTanks()}.
	 */
	public void testGetAddedTanks() {
		Assert.assertEquals(0, army.getAddedTanks());
	}

}
