package EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import GraphAdapter.GraphAdapter;

/**
 * Objects of this class listen to "Show Global Log" menu item presses of the "View" menu.
 * Pressing on this item causes the global log to be displayed.
 */
public class ShowGlobalLogListener implements ActionListener
{
	private static final long serialVersionUID = 1L;
	/**
	 * Created a new thread, which periodically updates the window
	 * that displays the global log.
	 */
	public void actionPerformed(ActionEvent e)
	{
		(new Thread()
		{
			public void run()
			{
				LogView lv = new LogView();
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
	 * This class extends JFrame. The frame is used as a container for the global log text.
	 */
	private static class LogView extends JFrame
	{
		private static final long serialVersionUID = 1L;
		/**
		 * An instance of LogViewText. This instance is the text area for the global log.
		 */
		private LogViewText lv;
		/**
		 * The constructor. Creates a JScrollPane with the global log text and adds the
		 * JScrollPane to the LogView.
		 */
		public LogView()
		{
			super("Global Log");
			lv = new LogViewText();
			add(new JScrollPane(lv));
			setSize(600, 300);
		}
		/**
		 * Method that causes the text area to refresh itself.
		 */
		public void myPaint()
		{
			lv.myPaint();
		}
		/**
		 * An object of this class keeps a reference to the global log. An object of this
		 * class uses this reference to keep the global text that is displayed in the JFrame
		 * to up to date.
		 */
		private static class LogViewText extends JTextArea
		{
			/**
			 * The variable used to reference the global log.
			 */
			private ArrayList<String> globalLog;
			/**
			 * Integer variable used to keep track of which lines
			 * have already been added to the text being displayed.
			 */
			private int nextLine;
			/**
			 * Constructor. Retrieves the global log and sets the text area to be uneditable. 
			 */
			public LogViewText()
			{
				setEditable(false);
				globalLog = GraphAdapter.getInstance().getGlobalLog();
				nextLine = 0;
			}
			/**
			 * Method used to refresh the text being displayed.
			 */
			public void myPaint()
			{
				updateText();
				this.paint(this.getGraphics());
			}
			/**
			 * Method that updates the inherited variable that holds the text being displayed.
			 */
			public void updateText()
			{
				while (nextLine < globalLog.size())
				{
					append(globalLog.get(nextLine++) + "\n");
				}
			}
		}
	}
}
