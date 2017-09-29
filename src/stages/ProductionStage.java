package stages;

import ArmyFactory.Army;
import ArmyFactory.ArmyFactory;
import GraphAdapter.GraphAdapter;
import map.*;

/**
 * This class holds one static method. This method is responsible for updating
 * the population, number of soldiers, and number of tanks owned by each state of a country.
 */
public class ProductionStage {

	/**
	 * This method updates the population, soldiers, and tanks for every state belonging to country. 
	 * @param country The country to produce units for.
	 */
    public static void runProductionStage(Country country) 
    {
    	GraphAdapter adapter = GraphAdapter.getInstance();
    	adapter.appendToGlobalLog("Production For " + country.getName());
        for(State s : country.getStates())
        {
        	Army army = ArmyFactory.createArmy(s);
        	adapter.appendToGlobalLog("  State " + s.getName() + ": pop + " + army.getAddedPopulation() +
        			": sol + " + army.getAddedSoldiers() + ": tank + " + army.getAddedTanks());
            s.setPopulation( s.getPopulation() + army.getAddedPopulation() );
            s.setSoldiers( s.getSoldiers() + army.getAddedSoldiers() );
            s.setTanks( s.getTanks() + army.getAddedTanks() );
            s.log_state();
         }
    }
    
}
