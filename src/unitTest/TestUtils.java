/**
 * 
 */
package unitTest;

import utils.Utils;
import map.State;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * This class tests the Utils class.
 * @author ManuelCN
 *
 */
public class TestUtils extends TestCase {

	/**
	 * @param name
	 */
	public TestUtils(String name) {
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
	 * Makes sure that the military strength is computed correctly.
	 * Test method for {@link utils.Utils#getMilitaryStrength(map.State)}.
	 */
	public void testGetMilitaryStrength() {
		State state = new State();
		state.setSoldiers(5);
		state.setTanks(3);
		Assert.assertEquals(3 * Utils.c + 5, Utils.getMilitaryStrength(state));
	}

}
