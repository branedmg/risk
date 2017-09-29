/**
 * 
 */
package unitTest;

import java.util.ArrayList;

import map.Countries;
import map.Country;
import map.State;
import junit.framework.TestCase;

/**
 * Instances of this class are used by the test runner to test the Countries class.
 * @author Adm
 *
 */
public class TestCountries extends TestCase 
{

	/**
	 * @param name
	 */
	public TestCountries(String name) 
	{
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception 
	{
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	/**
	 * This test adds a few Country object to a Countries object, and makes sure
	 * the added objects are indeed the added objects.
	 * Test method for {@link map.Countries#setCountries(java.util.ArrayList)}.
	 */
	public void testSetCountries() 
	{
		Countries countries = new Countries();
		Country c1 = new Country();
		Country c2 = new Country();
		Country c3 = new Country();
		Country c4 = new Country();
				
		
		ArrayList<Country> lcountries = new ArrayList<Country>();
		
		lcountries.add(c1);
		lcountries.add(c2);
		lcountries.add(c3);
		lcountries.add(c4);
				
		countries.setCountries(lcountries);
		
		assertEquals(lcountries, countries.getCountries());
		//fail("Not yet implemented");
	}

	/**
	 * This test adds a few Country object to a Countries object, and makes sure
	 * the added objects are indeed the added objects.
	 * Test method for {@link map.Countries#getCountries()}.
	 */
	public void testGetCountries() 
	{
		Countries countries = new Countries();
		Country c1 = new Country();
		Country c2 = new Country();
		Country c3 = new Country();
		Country c4 = new Country();
				
		
		ArrayList<Country> lcountries = new ArrayList<Country>();
		
		lcountries.add(c1);
		lcountries.add(c2);
		lcountries.add(c3);
		lcountries.add(c4);
				
		countries.setCountries(lcountries);
		
		assertEquals(lcountries, countries.getCountries());
		//fail("Not yet implemented");
	}

	/**
	 * This test makes sure that Country objects are properly added to the Countries object.
	 * Test method for {@link map.Countries#addCountry(map.Country)}.
	 */
	public void testAddCountry() 
	{
		Countries countries = new Countries();
		Country c1 = new Country();
		Country c2 = new Country();
		Country c3 = new Country();
		Country c4 = new Country();
			
		countries.addCountry(c1);
		countries.addCountry(c2);
		countries.addCountry(c3);
		countries.addCountry(c4);
		
		ArrayList<Country> lcountries = new ArrayList<Country>(); 
		lcountries = countries.getCountries();
		
		assertEquals(c1, lcountries.get(0));
		assertEquals(c2, lcountries.get(1));
		assertEquals(c3, lcountries.get(2));
		assertEquals(c4, lcountries.get(3));
		//fail("Not yet implemented");
	}

	/**
	 * This test makes sure the Countries object properly removes Country objects.
	 * Test method for {@link map.Countries#deleteCountry(map.Country)}.
	 */
	public void testDeleteCountry() 
	{
		Countries countries = new Countries();
		Country c1 = new Country();
		Country c2 = new Country();
		Country c3 = new Country();
		Country c4 = new Country();
			
		countries.addCountry(c1);
		countries.addCountry(c2);
		countries.addCountry(c3);
		countries.addCountry(c4);
		
		countries.deleteCountry(c2);
		
		ArrayList<Country> lcountries = new ArrayList<Country>(); 
		lcountries = countries.getCountries();
		
		assertEquals(c1, lcountries.get(0));
		assertEquals(c3, lcountries.get(1));
		assertEquals(c4, lcountries.get(2));
		//fail("Not yet implemented");
	}

}
