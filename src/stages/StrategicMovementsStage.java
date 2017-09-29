package stages;

import utils.Utils;
import GraphAdapter.GraphAdapter;
import map.Country;
import map.State;

/**
 * This class has a static method, which is responsible for moving military
 * units to states that need them the most.
 */
public class StrategicMovementsStage {

    /**
     * This method moves military units from safe states to states that have
     * a reinforce flag.
     * @param country The country whose turn it is.
     */
    public static void runStrategicMovementsStage( Country country )
    {
        /*
         * This stage will transfer soldiers and tanks to states that have
         * a reinforce flag set.
         */
        GraphAdapter adapter = GraphAdapter.getInstance();
        adapter.appendToGlobalLog("Strategic Movements Stage For " + country.getName());
        State[] neighbors = null;
        for ( State s : country.getStates() )
        {
            /*
             * For each state s, if the reinforce flag is set, then every
             * friendly neighbor that doesn't have a flag should give it
             * some military units.
             */
            if ( s.isReinforce() )
            {
            	adapter.appendToGlobalLog("  " + s.getName() + " Needs Reinforcements ");
                neighbors = adapter.getNeighbors( s );
                for ( int i = 0 ; i < neighbors.length ; ++i )
                {
                    if ( neighbors[i].getCountry() == country && !neighbors[i].isReinforce() &&
                    		neighbors[i].getAttack() == null )
                    {
                        transferMilitaryUnits( neighbors[i] , s );
                    }
                }
            }
        }
    }

    /**
     * This method transfers military units from the source state to the
     * recipient state.
     * @param source The state that gives some of its military units.
     * @param recipient The state that receives the military units.
     */
    private static void transferMilitaryUnits( State source , State recipient )
    {

        int maxSoldiersCanGive = 0;
        int soldiersShouldGive = 2;
        int soldiersWillGive = 0;
        int totalSourceSoldiers = source.getSoldiers();

        int maxTanksCanGive = 0;
        int tanksShouldGive = 2;
        int tanksWillGive = 0;
        int totalSourceTanks = source.getTanks();

        if ( totalSourceSoldiers > 1 )
        {
            maxSoldiersCanGive = totalSourceSoldiers - 1;
            soldiersWillGive = Math.min( soldiersShouldGive , maxSoldiersCanGive );
        }

        if ( source.getTanks() > 1 )
        {
            maxTanksCanGive = totalSourceTanks - 1;
            tanksWillGive = Math.min( tanksShouldGive , maxTanksCanGive );
        }

        // Transfer soldiers.
        source.setSoldiers( totalSourceSoldiers - soldiersWillGive );
        recipient.setSoldiers( recipient.getSoldiers() + soldiersWillGive );

        // Transfer tanks.
        source.setTanks( totalSourceTanks - tanksWillGive );
        recipient.setTanks( recipient.getTanks() + tanksWillGive );

        // Update populations.
        source.setPopulation( source.getPopulation() - soldiersWillGive - Utils.c * tanksWillGive );
        recipient.setPopulation( recipient.getPopulation() + soldiersWillGive + Utils.c * tanksWillGive );
        
        GraphAdapter adapter = GraphAdapter.getInstance();
        adapter.appendToGlobalLog("    " + source.getName() + " Transfers " +
        		(soldiersWillGive + Utils.c * tanksWillGive) + " population, " + soldiersWillGive +
        		" soldiers, " + tanksWillGive + " tanks");
        source.log_state();
        recipient.log_state();
    }
}
