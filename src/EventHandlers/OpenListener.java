package EventHandlers;

import gui.GraphicalUserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import java.io.File;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jgraph.graph.GraphLayoutCache;

import GraphAdapter.GraphAdapter;

/**
 * An object of this class listens to presses of the "Open" menu item of the
 * "File" menu of the main user interface. It's responsible for requesting the
 * user for a file to open, and then tries to open it and display it in the
 * main user interface.
 */
public class OpenListener implements ActionListener 
{
	/**
	 * Used to gain access to the globally shared graph, which needs to be altered
	 * in this class.
	 */
    private GraphicalUserInterface gui;
    /**
     * Constructor. Initializes the variable that references the gui.
     */
    public OpenListener() 
    {
        gui = GraphicalUserInterface.getInstance();
    }
    /**
     * This method is called when the "Open" menu item is selected.
     * @param actionEvent Not used.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) 
    {
    	if ( !GraphAdapter.getInstance().isPaused() )
    	{
    		return;
    	}
        JFileChooser chooser = new JFileChooser();

        // Doesn't allow the user to select more than one file.
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Allows the user to select files with the map extension only.
        FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map files", "map");
        chooser.setFileFilter(mapFileFilter);
        int selection = chooser.showOpenDialog(gui.getContentPane());

        // If the user has selected a file.
        if (selection != JFileChooser.CANCEL_OPTION) 
        {
            try {
                File openFromFile = chooser.getSelectedFile();

                // Gives the user a chance to save the work he's presently
                // working on. If the user chooses to proceed, all of his/her
                // work is lost forever.
                int userResponse = JOptionPane.showConfirmDialog(gui.getContentPane(),
                        "If you open this file without saving the one you're\n" +
                        "already working on, all of your work will be lost.\n" +
                        "Click yes if you don't want to save your work.\n",
                        "Open Without Saving Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (userResponse == JOptionPane.YES_OPTION) 
                {
                    // Retrieve the file, and update the graph in the user
                    // interface.
                    XMLDecoder decoder = new XMLDecoder(new FileInputStream(openFromFile));
                    GraphLayoutCache view = (GraphLayoutCache)decoder.readObject();
                    decoder.close();
                    gui.getGraph().setGraphLayoutCache(view);
                }
            }
            catch (FileNotFoundException fnfe) 
            {
                JOptionPane.showMessageDialog(gui.getContentPane(),fnfe.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
