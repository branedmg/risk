package EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import map.Country;

import GraphAdapter.GraphAdapter;

/**
 * This class listens to "Show Countries Details" menu item presses in the "View" menu.
 * The window that pops up as a consequence of pressing the menu item shows each country
 * along with the states in it.
 */
public class ShowCountriesDetailsListener implements ActionListener {

	/**
	 * Responsible for displaying the details of each country.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFrame frame = new JFrame("Countries Details");
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		GraphAdapter adapter = GraphAdapter.getInstance();
		ArrayList<Country> countries = adapter.getCountries().getCountries();
		for (Country c : countries)
		{
			textArea.append("Country " + c.getName() + "\n");
			for (map.State s : c.getStates())
			{
				textArea.append("State " + s.getName() + ": (population, soldiers, tanks," +
						" attack, reinforce) = " + "(" + s.getPopulation() + ", " +
						s.getSoldiers() + ", " + s.getTanks() + ", " + (s.getAttack() == null ? "null" :
							s.getAttack().getName()) + ", " + s.isReinforce() + ")\n");
			}
			textArea.append("\n");
		}
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.add(scrollPane);
		frame.setSize(600, 300);
		frame.setVisible(true);
	}

}
