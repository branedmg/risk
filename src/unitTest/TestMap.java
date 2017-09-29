/**
 * 
 */
package unitTest;

import map.Countries;
import map.Country;
import map.Map;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Instances of this class are used by the test runner to test the Map class.
 * @author ManuelCN
 *
 */
public class TestMap extends TestCase {

	/**
	 * @param name
	 */
	public TestMap(String name) {
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
	 * This test makes sure that the right Countries object is being returned.
	 * Test method for {@link map.Map#getCountries()}.
	 */
	public void testGetCountries() {
		Countries countries = new Countries();
		Map map = new Map();
		map.setCountries(countries);
		Assert.assertEquals(countries, map.getCountries());
	}

	/**
	 * This test makes sure that the right Countries object is being set.
	 * Test method for {@link map.Map#setCountries(map.Countries)}.
	 */
	public void testSetCountries() {
		Countries countries = new Countries();
		Map map = new Map();
		map.setCountries(countries);
		Assert.assertEquals(countries, map.getCountries());
	}

	/**
	 * This test makes sure that the right Country object is returned.
	 * Test method for {@link map.Map#getTurn()}.
	 */
	public void testGetCountry() {
		Country country = new Country();
		Map map = new Map();
		map.setTurn(country);
		Assert.assertEquals(country, map.getTurn());
	}

	/**
	 * This test makes sure that the right Country object is being set.
	 * Test method for {@link map.Map#setTurn(map.Country)}.
	 */
	public void testSetCountry() {
		Country country = new Country();
		Map map = new Map();
		map.setTurn(country);
		Assert.assertEquals(country, map.getTurn());
	}

}
