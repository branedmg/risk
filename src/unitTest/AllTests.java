package unitTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for unitTest");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestState.class);
		suite.addTestSuite(TestArmy.class);
		suite.addTestSuite(TestHighTechArmy.class);
		suite.addTestSuite(TestArmyFactory.class);
		suite.addTestSuite(TestProductionStage.class);
		suite.addTestSuite(TestCountry.class);
		suite.addTestSuite(TestStrategicIntelligenceStage.class);
		suite.addTestSuite(TestGraphAdapter.class);
		suite.addTestSuite(TestUtils.class);
		suite.addTestSuite(TestGraphicalUserInterface.class);
		suite.addTestSuite(TestLowTechArmy.class);
		suite.addTestSuite(TestStrategicMovementsStage.class);
		suite.addTestSuite(TestMap.class);
		suite.addTestSuite(TestCountries.class);
		//$JUnit-END$
		return suite;
	}

}
