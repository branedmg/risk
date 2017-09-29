package stages;

import map.Country;
import map.State;
import GraphAdapter.GraphAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
/**
 * Holds the methods responsible for implementing the strategic intelligence
 * stage.
 */
public class StrategicIntelligenceStage {
    /**
     * The country whose turn it is.
     */
    static Country country;
    /**
     * Used to get information from the graph that represents the map.
     */
    static GraphAdapter graphadapter;

    /**
     * This method gathers the intelligence necessary for the other stages
     * to run properly.
     * @param country The country whose turn it is.
     */
    public static void runStrategicIntelligenceStage( Country country )
    {
        // Before running this stage, the flags must be initialized.
        for ( State s : country.getStates() )
        {
            s.setReinforce( false );
            s.setAttack( null );
        }
        StrategicIntelligenceStage.country = country;
        StrategicIntelligenceStage.graphadapter = GraphAdapter.getInstance();
        graphadapter.appendToGlobalLog("Strategic Intelligence Stage For " + country.getName());
        State[] states = country.getStatesArray();
        ArrayList<State> EnemyStates;
        ArrayList<State> FriendlyStates1;
        ArrayList<State> FriendlyStates2;
        State WeakestEnemy1 = null;
        State WeakestEnemy2 = null;
        State strongest1 = null;
        State strongest2 = null;
        
        if (states.length != 0)
        {
	        EnemyStates = getEnemyStates(states);
	        if (EnemyStates.size() != 0)
	        {
	        	Collections.sort(EnemyStates);
	        	WeakestEnemy1 = EnemyStates.get(0);
	        	graphadapter.appendToGlobalLog("  " + country.getName() + "'s Weakest Enemy State: " +
	        			WeakestEnemy1.getName());
	        	FriendlyStates1 = getFriendlyStates(WeakestEnemy1);
	        	if (FriendlyStates1.size() != 0)
	        	{
	        		Collections.sort(FriendlyStates1, Collections.reverseOrder());
	        		strongest1 = FriendlyStates1.get(0);
	        		strongest1.setAttack(WeakestEnemy1);
	        		graphadapter.appendToGlobalLog("  " + strongest1.getName() + "'s Attack Flag: " +
	                		WeakestEnemy1.getName());
	        	}
	        	
	        	if (EnemyStates.size() > 1)
	        	{
	        		WeakestEnemy2 = EnemyStates.get(1);
	        		graphadapter.appendToGlobalLog("  " + country.getName() + "'s 2nd Weakest Enemy State: " +
	            			WeakestEnemy2.getName());
	        		FriendlyStates2 = getFriendlyStates(WeakestEnemy2);
	        		if (FriendlyStates2.size() != 0)
	        		{
	        			Collections.sort(FriendlyStates2, Collections.reverseOrder());
	        			int i = 0;
	        			while (i < FriendlyStates2.size() && FriendlyStates2.get(i) == strongest1)
	        			{
	        				++i;
	        			}
	        			if (i < FriendlyStates2.size())
	        			{
	        				strongest2 = FriendlyStates2.get(i);
	        				strongest2.setAttack(WeakestEnemy2);
	        				graphadapter.appendToGlobalLog("  " + strongest2.getName() + "'s Attack Flag: " +
        	                		WeakestEnemy2.getName());
	        			}
	        		}
	        	}
	        	
	        	/*
	        	 * Here we need to add the second substage. This substage raises
	        	 * The reinforce flags for the states that are too weak to defend
	        	 * themselves.
	        	 */
	            State[] friendlyStateArray = new State[ 1 ];
	            for ( int i = 0 ; i < states.length ; ++i )
	            {
	                /*
	                 * For each states[i], the enemies that surround it
	                 * must be looked at. One at a time, the enemy state's
	                 * strength is compared to states[i]. If there is one
	                 * enemy state that is stronger, then the reinforcement
	                 * flag is raised.
	                 */
	                friendlyStateArray[0] = states[i];
	                EnemyStates = getEnemyStates( friendlyStateArray );
	                for ( State enemyState : EnemyStates )
	                {
	                    /*
	                     * The comparison here may be a little too strict. We may
	                     * want to relax this later on.
	                     */
	                    if ( states[i].compareTo( enemyState ) < 0 && states[i].getAttack() == null)
	                    {
	                        states[i].setReinforce( true );
	                        graphadapter.appendToGlobalLog("  " + states[i].getName() + "'s Reinforce Flag" +
	                        		" Has Been Set");
	                        states[i].log_state();
	                    }
	                }
	            }
	        }
	        else
	        {
	        	graphadapter.appendToGlobalLog("  " + country.getName() + " Has No Bordering Enemies");
	        }
        }
    }

//        if ( states.length != 0 )
//        {
//            EnemyStates = getEnemyStates( states );
//            if ( !EnemyStates.isEmpty() )
//            {
//                Collections.sort( EnemyStates );
//            	WeakestEnemy1 = EnemyStates.get( 0 );
//            	System.out.println("Weakest enenmy is " + WeakestEnemy1.toString());
//            	graphadapter.appendToGlobalLog("  " + country.getName() + "'s Weakest Enemy State: " +
//            			WeakestEnemy1.getName());
//            	if (EnemyStates.size() > 1)
//            	{
//            		WeakestEnemy2 = EnemyStates.get( 1 );
//            		graphadapter.appendToGlobalLog("  " + country.getName() + "'s 2nd Weakest Enemy State: " +
//                			WeakestEnemy2.getName());
//            	}
//
//                FriendlyStates1 = getFriendlyStates( WeakestEnemy1 );
//
//                String Strongest1 = null;
//                String Strongest2 = null;
//
//                if ( !FriendlyStates1.isEmpty() )
//                {
//                    Collections.sort( FriendlyStates1 , Collections.reverseOrder() );
//                    State StrongestFriendly = FriendlyStates1.get( 0 );
//                    StrongestFriendly.setAttack( WeakestEnemy1 );
//                    StrongestFriendly.log_state();
//                    Strongest1 = StrongestFriendly.getName();
//                    graphadapter.appendToGlobalLog("  " + Strongest1 + "'s Attack Flag: " +
//                    		WeakestEnemy1.getName());
//                }
//
//                if (WeakestEnemy2 != null)
//                {
//	                FriendlyStates2 = getFriendlyStates( WeakestEnemy2 );
//	                if ( !FriendlyStates2.isEmpty() )
//	                {
//	                    Collections.sort( FriendlyStates2 , Collections.reverseOrder() );
//	                    State StrongestFriendly1 = FriendlyStates2.get( 0 );
//	                    Strongest2 = StrongestFriendly1.getName();
//	                    //need to compare the state.
//	                    for (int i = 1 ; i< FriendlyStates2.size(); i++)
//	                    {
//	                       if ( Strongest1.equals(Strongest2))
//	                       {
//	                    	   State StrongestFriendly2 = FriendlyStates2.get( i );
//	                    	   Strongest2 = StrongestFriendly2.getName();
//	                       }
//	                       else break;
//	                    }
//	                    StrongestFriendly1.setAttack( WeakestEnemy2 );
//	                    graphadapter.appendToGlobalLog("  " + Strongest2 + "'s Attack Flag: " +
//	                    		WeakestEnemy2.getName());
//	                     StrongestFriendly1.log_state();
//	                }
//                }
//            }

//            // Here we need to add the second substage. This substage raises
//            // The reinforce flags for the states that are too weak to defend
//            // themselves.
//            EnemyStates.clear();
//            State[] friendlyStateArray = new State[ 1 ];
//            for ( int i = 0 ; i < states.length ; ++i )
//            {
//                // For each states[i], the enemies that surround it
//                // must be looked at. One at a time, the enemy state's
//                // strength is compared to states[i]. If there is one
//                // enemy state that is stronger, then the reinforcement
//                // flag is raised.
//                friendlyStateArray[0] = states[i];
//                EnemyStates = getEnemyStates( friendlyStateArray );
//                for ( State enemyState : EnemyStates )
//                {
//                    // The comparison here may be a little too strict. We may
//                    // want to relax this later on.
//                    if ( states[i].compareTo( enemyState ) < 0 && states[i].getAttack() == null)
//                    {
//                        states[i].setReinforce( true );
//                        graphadapter.appendToGlobalLog("  " + states[i].getName() + "'s Reinforce Flag" +
//                        		" Has Been Set");
//                        states[i].log_state();
//                    }
//                }
//            }
//            if (states.length == 0)
//            {
//            	graphadapter.appendToGlobalLog("  " + country.getName() + " Is Empty");
//            }
//            else if (EnemyStates.isEmpty())
//            {
//            	graphadapter.appendToGlobalLog("  " + country.getName() + " Has No Bordering Enemies");
//            }
//        }
//    }

    /**
     * Returns a list of enemy neighboring states to the array of states provided.
     * @param states The array of states for which the neighboring enemy states are required.
     * @return A list of neighboring enemy states to the provided array of states.
     */
    private static ArrayList<State> getEnemyStates( State[] states )
    {
        HashSet<State> enemies = new HashSet<State>();
        for (State s : states) {
        	enemies.addAll(getEnemyStates(s));
        }
        return new ArrayList<State>(enemies);
    }
    
    /**
     * Returns a set of neighboring enemy states to the provided state.
     * @param state The state for which the neighboring enemy states are required.
     * @return A set of neighboring enemy states to the given state.
     */
    private static HashSet<State> getEnemyStates(State state) {
    	State[] neighbors = graphadapter.getNeighbors(state);
    	HashSet<State> output = new HashSet<State>();
    	for (State s : neighbors) {
    		if (s.getCountry() != country) {
    			output.add(s);
    		}
    	}
    	return output;
    }

    /**
     * This method is used to retrieve the neighboring enemy states of the provided
     * state.
     * @param WeakestEnemy Any state belonging to the map.
     * @return A list of neighboring enemy states to the given state
     */
    private static ArrayList<State> getFriendlyStates( State WeakestEnemy )
    {
    	State[] neighbors = graphadapter.getNeighbors(WeakestEnemy);
    	ArrayList<State> output = new ArrayList<State>();
    	for (State s : neighbors) {
    		if (s.getCountry() == country) {
    			output.add(s);
    		}
    	}
    	return output;
    }
}
