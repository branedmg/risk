package stages;

import java.util.ArrayList;
import java.util.Random;

import utils.Utils;

import GraphAdapter.GraphAdapter;

import map.Country;
import map.State;
import map.State.TechLevel;

/**
 * This class has one static method. This method will be responsible for dealing with the
 * logic of having state's battle it out. It will decide who wins, and change the losing state's
 * information as necessary.
 */
public class MilitaryTheatresStage {
	/**
	 * This method runs the the military theatres stage for the specified country.
	 * @param country The country to run the military theatres stage on.
	 */
    public static void runMilitaryTheatresStage(Country country)
    {
    	GraphAdapter adapter = GraphAdapter.getInstance();
    	adapter.appendToGlobalLog("Military Theatres Stage For " + country.getName());
        /*
         * This method must start threads for each battle. First, the
         * states that will battle must be located.
         */
    	ArrayList<State> friendlyStates = country.getStates();
    	ArrayList<State> battlingStates = new ArrayList<State>();
    	for (State s : friendlyStates)
    	{
    		if (s.getAttack() != null)
    		{
    			battlingStates.add(s);
    		}
    	}
    	/*
    	 * Now that we have the states that have to battle, we will create the necessary
    	 * threads that will be responsible for running the battles.
    	 */
    	ArrayList<Thread> battleThreads = new ArrayList<Thread>();
    	Thread t;
    	for (State s : battlingStates)
    	{
    		t = new BattleThread(s);
    		battleThreads.add(t);
    		t.start();
    	}
    	/*
    	 * Now we must check if the threads are complete before returning.
    	 */
    	int numberOfThreadsStillRunning;
    	while (true)
    	{
    		numberOfThreadsStillRunning = 0;
    		for (Thread tt : battleThreads)
    		{
    			if (tt.isAlive())
    			{
    				++numberOfThreadsStillRunning;
    			}
    		}
    		if (0 == numberOfThreadsStillRunning)
    		{
    			return;
    		}
    	}
    }
    
    /**
     * An Instance of this class runs a battle. 
     */
    private static class BattleThread extends Thread
    {
    	/**
    	 * The battling state for this thread.
    	 */
    	private map.State battlingState;
    	
    	/**
    	 * The constructor needs the state that will be battling.
    	 * @param battlingState The state that will be battling.
    	 */
    	public BattleThread(map.State battlingState)
    	{
    		this.battlingState = battlingState;
    	}
    	
    	/**
    	 * This method runs a single battle for the state that was specified
    	 * during the construction of this class.
    	 */
    	@Override
    	public void run()
    	{
    		GraphAdapter adapter = GraphAdapter.getInstance();
    		String s = "  " + battlingState.getName() + " versus " +
    				battlingState.getAttack().getName() + "\n";
    		s += "    " + battlingState.getName() + ": (country, population, soldiers, tanks) = (" + 
    				battlingState.getCountry().getName() + ", " + battlingState.getPopulation() + ", " +
    				battlingState.getSoldiers() + ", " + battlingState.getTanks() + ")\n";
    		s+= "    " + battlingState.getAttack().getName() + ": (country, population, soldiers, " +
    				"tanks) = (" + battlingState.getAttack().getCountry().getName() + ", " +
    				battlingState.getAttack().getPopulation() + ", " +
    				battlingState.getAttack().getSoldiers() + ", " +
    				battlingState.getAttack().getTanks() + ")\n";
    		while (true)
    		{
    			/*
        		 * Since it's possible that two states be attacking the same state,
        		 * it's important that the multiple threads have access to the state
        		 * to attack one at a time. To ensure this, every time a thread attacks
        		 * a state, the state being attacked must be locked.
        		 */
	    		synchronized (battlingState.getAttack())
	    		{
	    			/*
	        		 * The attacker attacks only if the defender has a different country.
	        		 */
	    			if (battlingState.getCountry() != battlingState.getAttack().getCountry())
	    			{
	    				/*
	    				 * A die value is needed for the attacker and the defender. If the
	    				 * attacker's die value is greater, then it wins. It loses otherwise.
	    				 */
	    				map.State winner;
	    				map.State loser;
	    				Random rand = new Random(System.currentTimeMillis());
	    				if (rand.nextInt(6) < rand.nextInt(6))
	    				{
	    					/*
	    					 * The attacker wins, and the defender loses.
	    					 */
	    					winner = battlingState;
	    					loser = battlingState.getAttack();
	    				}
	    				else
	    				{
	    					/*
	    					 * The defender wins, and the attacker loses.
	    					 */
	    					winner = battlingState.getAttack();
	    					loser = battlingState;
	    				}
	    				/*
	    				 * The loser's population, soldier, and tank values must now
	    				 * be updated.
	    				 */
	    				int initialPopulation = loser.getPopulation();
	    				int initialSoldiers = loser.getSoldiers();
	    				int initialTanks = loser.getTanks();
	    				if (0 < initialSoldiers)
	    				{
	    					loser.setSoldiers(initialSoldiers - 1);
	    					loser.setPopulation(initialPopulation - 1);
	    				}
	    				else if (0 < initialTanks)
	    				{
	    					if (Utils.c > 1)
	    					{
	    						loser.setSoldiers(Utils.c - 1);
	    					}
	    					loser.setTanks(initialTanks - 1);
	    					loser.setPopulation(initialPopulation - 1);
	    				}
	    				/*
	    				 * If the loser's soldier and tank values are both zero, then
	    				 * the loser has been conquered by the winner.
	    				 */
	    				if (loser.getSoldiers() == 0 && loser.getTanks() == 0)
	    				{
	    					s += "    " + winner.getName() + " wins (" + loser.getName() + " lost)\n";
	    					/*
	    					 * The loser now needs to be removed from its country
	    					 * and added to the winner's country.
	    					 */
	    					loser.getCountry().deleteState(loser);
	    					winner.getCountry().addState(loser);
	    					/*
	    					 * The country of the loser must be set to the country
	    					 * of the winner.
	    					 */
	    					loser.setCountry(winner.getCountry());
	    					/*
	    					 * The color of the loser must be set to the winner's
	    					 * country's color.
	    					 */
	    					adapter.synchronizeColor(loser);
	    					/*
	    					 * The winner now transfers half of its soldiers
	    					 * and half of its tanks to the loser.
	    					 */
	    					int soldiersToTransfer = winner.getSoldiers() / 2;
	    					int tanksToTransfer = winner.getTanks() / 2;
	    					int populationToTransfer = soldiersToTransfer + Utils.c * tanksToTransfer;
	    					winner.setSoldiers(winner.getSoldiers() - soldiersToTransfer);
	    					winner.setTanks(winner.getTanks() - tanksToTransfer);
	    					winner.setPopulation(winner.getPopulation() - populationToTransfer);
	    					loser.setSoldiers(loser.getSoldiers() + soldiersToTransfer);
	    					loser.setTanks(loser.getTanks() + tanksToTransfer);
	    					loser.setPopulation(loser.getPopulation() + populationToTransfer);
	    					s += "    " + winner.getName() + " transfers " + populationToTransfer +
	    							" population, " + soldiersToTransfer + " soldiers, " +
	    							tanksToTransfer + " tanks\n";
	    					winner.log_state();
	    					loser.log_state();
	    					/*
	    					 * If the above modifications give nothing to the loser,
	    					 * then give the loser (for free) either:
	    					 * 1. Utils.c + 1 soldiers if the loser's tech level is low.
	    					 * 2. 1 soldier and 1 tank if the loser's tech level is high.
	    					 */
	    					if (loser.getSoldiers() == 0 && loser.getTanks() == 0)
	    					{
	    						s += "    " + winner.getName() + " transferred nothing\n";
	    						if (loser.getTechLevel() == TechLevel.low)
	    						{
	    							soldiersToTransfer = Utils.c + 1;
	    							tanksToTransfer = 0;
	    						}
	    						else
	    						{
	    							soldiersToTransfer = 1;
	    							tanksToTransfer = 1;
	    						}
	    						loser.setSoldiers(soldiersToTransfer);
	    						loser.setTanks(tanksToTransfer);
	    						loser.setPopulation(loser.getPopulation() + soldiersToTransfer +
	    								Utils.c * tanksToTransfer);
	    						s += "      " + loser.getName() + " receives " + (soldiersToTransfer +
	    								Utils.c * tanksToTransfer) + " population, " + soldiersToTransfer +
	    								" soldiers, " + tanksToTransfer + " tanks\n";
	    						winner.log_state();
		    					loser.log_state();
	    					}
	    					s += "    " + battlingState.getName() + ": (country, population, soldiers, tanks) = (" + 
	        				battlingState.getCountry().getName() + ", " + battlingState.getPopulation() + ", " +
	        				battlingState.getSoldiers() + ", " + battlingState.getTanks() + ")\n";
	    					s+= "    " + battlingState.getAttack().getName() + ": (country, population, soldiers, " +
	        				"tanks) = (" + battlingState.getAttack().getCountry().getName() + ", " +
	        				battlingState.getAttack().getPopulation() + ", " +
	        				battlingState.getAttack().getSoldiers() + ", " +
	        				battlingState.getAttack().getTanks() + ")";
	    					adapter.appendToGlobalLog(s);
	    				}
	    			}
	    			else
	    			{
	    				return;
	    			}
	    		}
    		}
    	}
    }
}
