package ArmyFactory;

/**
 * Instances of this class aren't used. Subtypes of this class are used.
 * @see LowTechArmy
 * @see HighTechArmy
 * @see ArmyFactory
 */
public class Army 
{
	/**
	 * <p>This constant is used to compute the new population of a state during the production stage.</p>
	 * 
	 * <p>newPopulation = oldPopulation * POPULATIONINCREASEFACTOR</p>
	 */
	public static final double POPULATIONINCREASEFACTOR = 0.12;
	/**
	 * <p>This constant is used to compute the new number of soldiers of a state during the production stage.</p>
	 * 
	 * <p>newSoldiers = oldSoldiers + newPopulation * SOLDIERINCREASEFACTOR</p>
	 */
    public static final double SOLDIERINCREASEFACTOR = 0.07;
    /**
	 * <p>This constant is used to compute the new number of tanks of a state during the production stage.</p>
	 * 
	 * <p>newTanks = oldTanks + newPopulation * TANKINCREASEFACTOR</p>
	 */
    public static final double TANKINCREASEFACTOR = 0.03;
    
    /**
     * This is the value to be added to the old population.
     */
	protected int addedPopulation;
	/**
	 * This is the value to be added to the old number of soldiers.
	 */
	protected int addedSoldiers;
	/**
	 * This is the value to be added to the old number of soldiers.
	 */
	protected int addedTanks;
	/**
	 * Returns the population to be added to the sate.
	 * @return Population to be added.
	 */
	public int getAddedPopulation() {
		return addedPopulation;
	}
	/**
	 * Returns the number of soldiers to be added to the state.
	 * @return Number of soldiers to be added.
	 */
	public int getAddedSoldiers() {
		return addedSoldiers;
	}
	/**
	 * Returns the number of tanks to be added to the state.
	 * @return
	 */
	public int getAddedTanks() {
		return addedTanks;
	}
}
