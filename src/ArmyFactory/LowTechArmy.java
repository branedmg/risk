package ArmyFactory;

import map.State;

/**
 * This class extends the Army class. An instance of this class gives the values
 * to add to a state's population, soldiers, and tanks values during the production stage.
 */
public class LowTechArmy extends Army {
	
	/**
	 * <p>Users should use the ArmyFactory's static method to get an army.</p>
	 * 
	 * <p>The constructor takes the state s, and initializes the values for addedPopulation,
	 * addedSoldiers, and addedTanks, which can be added to the population, soldiers, and tanks
	 * values respectively for state s during the production stage.</p>
	 * 
	 * <p>This class is for low tech armies only.</p>
	 * @param s The state for which the added values should be calculated for.
	 */
	public LowTechArmy( State s )
	{	
		addedPopulation = (int) ( POPULATIONINCREASEFACTOR * s.getPopulation() );
		if (addedPopulation == 0)
		{
			addedPopulation = 1;
		}
		int newPopulation = addedPopulation + s.getPopulation();
		addedSoldiers = (int) ( SOLDIERINCREASEFACTOR * newPopulation );
		if (addedSoldiers == 0)
		{
			addedSoldiers = 1;
		}
		addedTanks = 0;
	}
	
}
