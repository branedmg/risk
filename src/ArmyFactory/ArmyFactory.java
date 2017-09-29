package ArmyFactory;

import map.State;
import map.State.TechLevel;

/**
 * This class is used to create the units to be added to a state during that state's production stage.
 */
public class ArmyFactory 
{
	
	/**
	 * This method returns a LowTechArmy or a HighTechArmy Army. The low tech
	 * army doesn't have tanks, while the high tech army does.
	 * @param s The state the user wants to add units to.
	 * @return This object has three method: getAddedPopulation, getAddedSoldiers, and getAddedTanks.
	 * Each of these methods returns the respective amounts of population, soldiers, and tanks to be added
	 * to the state's values.
	 */
	public static Army createArmy (State s)
	{
		
		if ( s.getTechLevel() == TechLevel.low ) {
			return new LowTechArmy(s);
		}
		else {
			return new HighTechArmy(s);
		}
		
	}

}
