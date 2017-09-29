/**
 * 
 */
package unitTest;

import java.awt.Color;
import java.util.ArrayList;

import map.State;
import map.Country;
import junit.framework.TestCase;

/**
 * Instances of this class are used by the test runner to test the Country class.
 * @author Adm
 *
 */
public class TestCountry extends TestCase 
{

	/**
	 * @param name
	 */
	public TestCountry(String name) 
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
	 * This test gives a name to the country, and then checks if that name is properly returned.
	 * Test method for {@link map.Country#getName()}.
	 */
	public void testGetName() 
	{
		Country country = new Country();
		country.setName("USA");
		assertEquals("USA", country.getName());
		//fail("Not yet implemented");
	}

	/**
	 * This test checks if the method setName, properly sets the name for the country.
	 * Test method for {@link map.Country#setName(java.lang.String)}.
	 */
	public void testSetName() 
	{
		Country country = new Country();
		country.setName("USA");
		assertEquals("USA", country.getName());
		//fail("Not yet implemented");
	}

	/**
	 * This test checks if the returned color is the right one.
	 * Test method for {@link map.Country#getColor()}.
	 */
	public void testGetColor() 
	{
		Country country = new Country();
		Color c = new Color(255,0,0);
		country.setColor(c);
		assertEquals(c, country.getColor());
		//fail("Not yet implemented");
	}

	/**
	 * This test checks if the color is set properly.
	 * Test method for {@link map.Country#setColor(java.awt.Color)}.
	 */
	public void testSetColor() 
	{
		Country country = new Country();
		Color c = new Color(255,0,0);
		country.setColor(c);
		assertEquals(c, country.getColor());
		//fail("Not yet implemented");
	}

	/**
	 * This test makes sure that 
	 * Test method for {@link map.Country#getStates()}.
	 */
	public void testGetStates() 
	{
		Country country = new Country();
		State s1 = new State();
		State s2 = new State();
		State s3 = new State();
		State s4 = new State();
		State s5 = new State();		
		
		ArrayList<State> states = new ArrayList<State>();
		
		states.add(s1);
		states.add(s2);
		states.add(s3);
		states.add(s4);
		states.add(s5);
		
		country.setStates(states);
		
		assertEquals(states, country.getStates());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.Country#setStates(java.util.ArrayList)}.
	 */
	public void testSetStates() 
	{
		Country country = new Country();
		State s1 = new State();
		State s2 = new State();
		State s3 = new State();
		State s4 = new State();
		State s5 = new State();		
		
		ArrayList<State> states = new ArrayList<State>();
		
		states.add(s1);
		states.add(s2);
		states.add(s3);
		states.add(s4);
		states.add(s5);
		
		country.setStates(states);
		
		assertEquals(states, country.getStates());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.Country#addState(map.State)}.
	 */
	public void testAddState() 
	{
		Country country = new Country();
		State s = new State();
		State s2 = new State();
		State s3 = new State();
		State s4 = new State();
		State s5 = new State();
		
		country.addState(s);
		country.addState(s2);
		country.addState(s3);
		country.addState(s4);
		country.addState(s5);
		
		ArrayList<State> states = new ArrayList<State>(); 
		states = country.getStates();
		assertEquals(s, states.get(0));
		assertEquals(s2, states.get(1));
		assertEquals(s3, states.get(2));
		assertEquals(s4, states.get(3));
		assertEquals(s5, states.get(4));
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.Country#deleteState(map.State)}.
	 */
	public void testDeleteState() 
	{
		Country country = new Country();
		State s1 = new State();
		State s2 = new State();
		State s3 = new State();
		State s4 = new State();
		State s5 = new State();
		
		country.addState(s1);
		country.addState(s2);
		country.addState(s3);
		country.addState(s4);
		country.addState(s5);
		
		country.deleteState(s4);
		
		ArrayList<State> states = country.getStates();
		assertEquals(s1, states.get(0));
		assertEquals(s2, states.get(1));
		assertEquals(s3, states.get(2));
		assertEquals(s5, states.get(3));
		
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.Country#getNumberOfStates()}.
	 */
	public void testGetNumberOfStates() 
	{
		Country country = new Country();
		State s1 = new State();
		State s2 = new State();
		State s3 = new State();
		State s4 = new State();
		State s5 = new State();
		
		country.addState(s1);
		country.addState(s2);
		country.addState(s3);
		country.addState(s4);
		country.addState(s5);
		
		assertEquals(5, country.getNumberOfStates());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link map.Country#toString()}.
	 */
	public void testToString() 
	{
		Country country = new Country();
		country.setName("USA");
		
		assertEquals("USA", country.toString());
		//fail("Not yet implemented");
	}

}
