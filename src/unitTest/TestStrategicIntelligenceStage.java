/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package unitTest;

import map.Countries;
import map.Country;
import map.Map;
import map.State;
import gui.GraphicalUserInterface;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphLayoutCache;

import junit.framework.Assert;
import junit.framework.TestCase;
import stages.StrategicIntelligenceStage;
/**
 * Instances of this class are used by the test runner to test the static method
 * in the StrategicIntelligenceStage class. 
 * @author Aman Kandhola
 */
public class TestStrategicIntelligenceStage extends TestCase
{
    public TestStrategicIntelligenceStage(String name)
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
	 * 
	 */
    public void testrunStrategicIntelligenceStage()
    {

    	try {
        	// Import the map, and place it in the right place.
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("c:\\testintelligence_map"));
			GraphLayoutCache view = (GraphLayoutCache) decoder.readObject();
			GraphicalUserInterface.getInstance().getGraph().setGraphLayoutCache(view);
			
			// There are two countries in this map:
			// Africa and
			// Asia
			// I need to obtain references for these two countries to run the
			// intelligence stage.
			Country africa = null;
			Country asia = null;
			Map map = (Map) ( (DefaultGraphCell)view.getModel().getRootAt(0) ).getUserObject();
			Countries countries = map.getCountries();
			for (Country c : countries.getCountries()) {
				System.out.println(c.getName() + " has " + c.getNumberOfStates() + " states:");
				for (State s : c.getStates()) {
					System.out.println(s.getName());
				}
				if (c.getName().equals("Africa")) {
					africa = c;
				}
				else if (c.getName().equals("Asia")) {
					asia = c;
				}
				else {
					fail("Country other than Africa and Asia found.");
				}
			}
			
			// Now that the map is loaded, and we've obtained references to the
			// two countries to be tested, we can run the the intelligence stage for
			// africa and asia.
			StrategicIntelligenceStage.runStrategicIntelligenceStage(africa);
			
			// Africa has two states:
			// Egypt and
			// East Africa
			// Egypt should raise its reinforce flag, while East Africa, being the strongest
			// of the two states, will attack Middle East. To check this, I must get a reference
			// to the Middle East object
			State middleEast = null;
			for (State s : asia.getStates()) {
				if (s.getName().equals("Middle East")) {
					middleEast = s;
				}
			}
			ArrayList<State> states = africa.getStates();
			for (State s : states) {
				if (s.getName().equals("Egypt")) {
					Assert.assertEquals("Egypt's reinforce flag isn't properly set.", true, s.isReinforce());
					Assert.assertNull("Egypt's attack flag isn't properly set.", s.getAttack());
				}
				else if (s.getName().equals("East Africa")) {
					Assert.assertEquals("East Africa's reinforce flag isn't properly set.", false, s.isReinforce());
					Assert.assertEquals("East Africa's attack flag isn't properly set.", middleEast, s.getAttack());
				}
				else {
					fail("State other than Egypt and East Africa found in Africa.");
				}
			}
			
			StrategicIntelligenceStage.runStrategicIntelligenceStage(asia);
			
			// Asia has three states:
			// Afghanistan
			// Middle East and
			// India
			// India and Afghanistan should have no flags set, and Middle East should
			// have its attack flag set to Egypt.
			// So I need to obtain Egypt first to make sure the Middle East is indeed pointing
			// to Egypt.
			State egypt = null;
			for (State s : states) {
				if (s.getName().equals("Egypt")) {
					egypt = s;
				}
			}
			
			states = asia.getStates();
			
			for (State s : states) {
				if (s.getName().equals("Afghanistan")) {
					Assert.assertEquals("Afghanistan's reinforce flag isn't properly set.", false, s.isReinforce());
					Assert.assertNull("Afghanistan's attack flag isn't properly set.", s.getAttack());
				}
				else if (s.getName().equals("Middle East")) {
					Assert.assertEquals("Middle East's reinforce flag isn't properly set.", false, s.isReinforce());
					Assert.assertEquals("Middle East's attack flag isn't properly set.", egypt, s.getAttack());
				}
				else if (s.getName().equals("India")) {
					Assert.assertEquals("India's reinforce flag isn't properly set.", false, s.isReinforce());
					Assert.assertNull("India's attack flag isn't properly set.", s.getAttack());
				}
				else {
					fail("State other than Afghanistan, Middle East, and India found in Asia.");
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    

    }
    



}
