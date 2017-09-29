package EventHandlers;

import gui.GraphicalUserInterface;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JMenuItem;

import map.Countries;
import map.Country;
import map.State;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;

import stages.MilitaryTheatresStage;
import stages.ProductionStage;
import stages.StrategicIntelligenceStage;
import stages.StrategicMovementsStage;

import GraphAdapter.GraphAdapter;

/**
 * An object of this class listens to when the user starts a simulation. This controller
 * keeps track of which country's turn it is in the simulation, and what stage that country
 * is in.
 */
public class StageController implements ActionListener
{
	/**
	 * Constructor does nothing.
	 */
	public StageController()
	{
	}
	
	/**
	 * This method initiates a thread that loops the simulation until either the simulation
	 * is paused, or that a single country owns all of the states. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		/*
		 * The adapter is useful to everybody.
		 */
		GraphAdapter adapter = GraphAdapter.getInstance();
		/*
		 * If the simulation has finished. Then don't even start.
		 */
		if (adapter.isSimulationFinished() || adapter.getCountries().getCountries().size() == 0)
		{
			return;
		}
		/*
		 * If the pause MenuItem has been pressed, then the simulation
		 * must be paused. Otherwise, the start MenuItem was selected,
		 * and the simulation must run.
		 */
		String menuItemString = ( (JMenuItem) e.getSource() ).getText();
		
		if (menuItemString.equals("Pause"))
		{
			/*
			 * Set the global paused value to true.
			 */
			adapter.setPaused(true);
		}
		else
		{
			/*
			 * Start the thread that will run the simulation until the simulation
			 * is paused.
			 */
			adapter.setPaused(false);
			(new StageControllerThread()).start();
		}
	}
	
	/**
	 * An instance of this class takes care of running the simulation. The thread
	 * ends when the paused value is set to true.
	 */
	private class StageControllerThread extends Thread
	{
		
		/**
		 * The constructor doesn't do anything.
		 */
		public StageControllerThread()
		{
			
		}
		
		/**
		 * This is the method that calls the four stages for each country in a round robin
		 * fashion. After each round, it checks to see if the simulation has ended.
		 */
		@Override
		public void run()
		{
			/*
			 * Much of the information needed to run the simulation can be retrieved
			 * from an instance of GraphAdapter.
			 */
			GraphAdapter adapter = GraphAdapter.getInstance();
			/*
			 * To run the simulation, the countries must be retrieved.
			 */
			ArrayList<Country> countries = adapter.getCountries().getCountries();
			/*
			 * If there are no countries, then their shouldn't be any simulation.
			 */
			if (countries.size() == 0)
			{
				return;
			}
			/*
			 * The country's turn it is is.
			 */
			Country currentCountry = adapter.getTurn();
			/*
			 * If it's the first time the simulation is being run, then write that it
			 * starts, and log the status of all countries. Otherwise, write that it continues.
			 */
			if (currentCountry == null)
			{
				adapter.appendToGlobalLog("Simulation start.");
				ArrayList<map.State> states;
				for (Country c : countries)
				{
					states = c.getStates();
					adapter.appendToGlobalLog("Country " + c.getName());
					for (map.State s : c.getStates())
					{
						adapter.appendToGlobalLog("State " + s.getName() + ": (population, soldiers, tanks," +
								" attack, reinforce) = " + "(" + s.getPopulation() + ", " +
								s.getSoldiers() + ", " + s.getTanks() + ", " + (s.getAttack() == null ? "null" :
									s.getAttack().getName()) + ", " + s.isReinforce() + ")");
						s.log_state();
					}
				}
			}
			else
			{
				adapter.appendToGlobalLog("Simulation continuation.");
			}
			/*
			 * The index of the country whose turn it is is. 
			 */
			int currentCountryIndex = (currentCountry == null) ? 0 : countries.indexOf(currentCountry);
			currentCountry = countries.get(currentCountryIndex);
			/*
			 * The simulation will run until the paused value is set to true.
			 */
			GraphicalUserInterface gui = GraphicalUserInterface.getInstance();
			while (!adapter.isPaused())
			{
				ProductionStage.runProductionStage(currentCountry);
				try {
					sleep(1000L);
				} catch (Exception e) {}
				gui.update(gui.getGraphics());
				
				StrategicIntelligenceStage.runStrategicIntelligenceStage(currentCountry);
				try {
					sleep(1000L);
				} catch (Exception e) {}
				gui.update(gui.getGraphics());

				MilitaryTheatresStage.runMilitaryTheatresStage(currentCountry);
				try {
					sleep(1000L);
				} catch (Exception e) {}
				gui.update(gui.getGraphics());
				
				StrategicMovementsStage.runStrategicMovementsStage(currentCountry);
				try {
					sleep(1000L);
				} catch (Exception e) {}
				gui.update(gui.getGraphics());
				
				/*
				 * The next country to go has index.
				 */
				currentCountryIndex = (currentCountryIndex + 1)%countries.size();
				currentCountry = countries.get(currentCountryIndex);
				
				/*
				 * Once each stage has finished running, we must check to see
				 * if the simulation has ended (one of the countries own's all
				 * the states).
				 */
				Country possibleWinner = isSimulationComplete();
				if (possibleWinner != null)
				{
					adapter.setSimulationFinished(true);
					adapter.setPaused(true);
					adapter.appendToGlobalLog(possibleWinner.getName() + " Won The War");
					adapter.appendToGlobalLog("Simulation Finished.");
				}
			}
			/*
			 * The simulation has been paused. We must remember which country must go next.
			 */
			adapter.setTurn(countries.get(currentCountryIndex));
			if (!adapter.isSimulationFinished())
			{
				adapter.appendToGlobalLog("Simulation Paused.");
			}
		}
		
		/**
		 * This method returns the Country that won the war if the simulation has ended.
		 * The method returns null if there is no country that has conquered the entire map yet.
		 * @return Returns true if the simulation has finished. That is, is there
		 * a country that owns all of the states?
		 */
		private Country isSimulationComplete()
		{
			GraphAdapter adapter = GraphAdapter.getInstance();
			ArrayList<Country> countries = adapter.getCountries().getCountries();
			int numberOfCountriesWithNonZeroNumberOfStates = 0;
			Country potentialWinner = null;
			for (Country c : countries)
			{
				if (c.getNumberOfStates() != 0)
				{
					potentialWinner = c;
					++numberOfCountriesWithNonZeroNumberOfStates;
				}
				if (1 < numberOfCountriesWithNonZeroNumberOfStates)
				{
					return null;
				}
			}
			return potentialWinner;
		}
	}
    
}
