/**
 * 
 */
package unitTest;

import map.State;

import map.Country;
import junit.framework.TestCase;

/**
 * @author Adm
 *
 */
public class TestState extends TestCase 
{

	/**
	 * @param name
	 */
	public TestState(String name) 
	{
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception 
	{
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	/**
	 * Test method for {@link map.State#getCountry()}.
	 */
	public void testGetCountry() 
	{
		Country country = new  Country();
		country.setName("USA");
		State state = new State();
		state.setCountry(country);
		assertEquals(country, state.getCountry());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#setCountry(map.Country)}.
	 */
	public void testSetCountry() 
	{
		Country country = new  Country();
		country.setName("USA");
		State state = new State();
		state.setCountry(country);
		assertEquals(country, state.getCountry());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#getName()}.
	 */
	public void testGetName() 
	{
		State state = new State();
		state.setName("Quebec");
		assertEquals("Quebec", state.getName());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#setName(java.lang.String)}.
	 */
	public void testSetName() 
	{
		State state = new State();
		state.setName("Quebec");
		assertEquals("Quebec", state.getName());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#getPopulation()}.
	 */
	public void testGetPopulation() 
	{
		State state = new State();
		state.setPopulation(250);
		assertEquals(250, state.getPopulation());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#setPopulation(int)}.
	 */
	public void testSetPopulation() 
	{
		State state = new State();
		state.setPopulation(250);
		assertEquals(250, state.getPopulation());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#getSoldiers()}.
	 */
	public void testGetSoldiers() 
	{
		State state = new State();
		state.setSoldiers(75);
		assertEquals(75, state.getSoldiers());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#setSoldiers(int)}.
	 */
	public void testSetSoldiers() 
	{
		State state = new State();
		state.setSoldiers(75);
		assertEquals(75, state.getSoldiers());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.State#toString()}.
	 */
	public void testToString() 
	{
		State state = new State();
		state.setName("Quebec");
		assertEquals("Quebec - 0 - 0 - 0", state.toString());
		//fail("Not yet implemented");
	}

}
