package EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jgraph.graph.DefaultGraphCell;

import map.State;

import GraphAdapter.GraphAdapter;

/**
 * Objects of this class listen to "Show State Log" menu item presses of the "View" menu.
 * Pressing on this item causes the state log for the selected state to be displayed.
 * The state log menu item will only work when 1 state is selected. Multiple state log
 * JFrames can be opened, but only one at a time.
 */
public class ShowStateLogListener implements ActionListener
{
	private static final long serialVersionUID = 1L;
	/**
	 * This constructor does nothing.
	 */
	public ShowStateLogListener() {}
	/**
	 * This method retrieves the selected state, and starts a thread that displays the state's
	 * log.
	 */
	public void actionPerformed(ActionEvent e)
	{
		/*
		 * Find which state is selected.
		 */
		GraphAdapter adapter = GraphAdapter.getInstance();
		Object[] selectedCells = adapter.getSelectedCells();
		if (selectedCells == null || selectedCells.length == 0 || selectedCells.length > 1 ||
				!adapter.isVertex(selectedCells[0]))
		{
			return;
		}
		final State selectedState = (State) ((DefaultGraphCell) selectedCells[0]).getUserObject();
		/*
		 * Create a new JFrame that will display the selected state's local log.
		 */
		(new Thread()
		{
			public void run() {
				LogView lv = new LogView(selectedState);
				lv.setVisible(true);
				while (lv.isVisible())
				{
					lv.myPaint();
					try {
						sleep(1000);
					}
					catch (Exception e) {}
				}
			}
		}).start();
	}
	/**
	 * This class extends JFrame. The frame is used as a container for the state log text.
	 */
	private static class LogView extends JFrame
	{
		private static final long serialVersionUID = 1L;
		/**
		 * An instance of LogViewText. This instance is the text area for the state log.
		 */
		private LogViewText lv;
		/**
		 * Constructor that requires the particular state for which a new JFrame must
		 * be created. Also, a scroll pane is added in case the log is too long.
		 * @param selectedState The state whose state log will be displayed.
		 */
		public LogView(State selectedState)
		{
			super(selectedState.getName() + ": Log");
			lv = new LogViewText(selectedState);
			add(new JScrollPane(lv));
			setSize(600, 300);
		}
		/**
		 * Method that refreshes the text contained in JFrame.
		 */
		public void myPaint()
		{
			lv.myPaint();
		}
		/**
		 * Objects of this class take care of holding the text that needs to be displayed.
		 */
		private static class LogViewText extends JTextArea
		{
			/**
			 * A reference to the state's log.
			 */
			private ArrayList<State> stateLog;
			/**
			 * Integer used to indicate which lines of the state's log have already
			 * been added to the text are.
			 */
			private int nextLine;
			/**
			 * Constructor. It requires the state for which the state log must be
			 * displayed. Also, makes the text are uneditable.
			 * @param selectedState State whose state log will be displayed.
			 */
			public LogViewText(State selectedState)
			{
				setEditable(false);
				stateLog = selectedState.getState_log().getState_history();
				nextLine = 0;
			}
			/**
			 * Method that updates the inherited variable holding the state's log.
			 */
			public void myPaint()
			{
				updateText();
				this.paint(this.getGraphics());
			}
			/**
			 * Method that adds to the inherited variable holding the text the strings
			 * that must be displayed.
			 */
			public void updateText()
			{
				synchronized (stateLog)
				{
					State s;
					while (nextLine < stateLog.size())
					{
						s = stateLog.get(nextLine++);
						append("State " + s.getName() + ": (country, population, soldiers, tanks," +
								" attack, reinforce) = " + "(" + s.getCountry().getName() + ", " + s.getPopulation() + ", " +
								s.getSoldiers() + ", " + s.getTanks() + ", " + (s.getAttack() == null ? "null" :
									s.getAttack().getName()) + ", " + s.isReinforce() + ")\n");
					}
				}
			}
		}
	}
}