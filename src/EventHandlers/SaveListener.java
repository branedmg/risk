package EventHandlers;

import gui.GraphicalUserInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.beans.XMLEncoder;

import javax.swing.JOptionPane;

import GraphAdapter.GraphAdapter;
import Serialization.PersistenceDelegatesConfiguration;

import org.jgraph.JGraph;

/**
 * An object of this class listens to presses of the "Save" item menu
 * of the "File" menu.
 */
public class SaveListener implements ActionListener 
{
    /**
     * A reference to the main user interface where the graph is stored.
     */
    private GraphicalUserInterface gui;

    /**
     * Counstructor.
     * @param gui A reference to the main user interface where the graph
     * is stored.
     */
    public SaveListener() 
    {
        gui = GraphicalUserInterface.getInstance();
    }

    /**
     * This method is called when the "Save" menu item is called from
     * the "File" menu. It requests from the user a location and name for
     * the file. This file will store all the information of the graph.
     * @param actionEvent Not used.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) 
    {
    	if ( !GraphAdapter.getInstance().isPaused() )
    	{
    		return;
    	}
        // Have the user choose a file name.
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter mapFileFilter = new FileNameExtensionFilter("Map files", "map");
        chooser.setFileFilter(mapFileFilter);
        int selection = chooser.showSaveDialog(gui.getContentPane());
        if (selection != JFileChooser.CANCEL_OPTION)
        {
            File saveToFile = chooser.getSelectedFile();
            if (!saveToFile.exists()) 
            {
                try 
                {
                	if ( saveToFile.getName().equals("") )
                	{
                		throw new Exception("The name of the file to save to can't be the empty string.");
                	}
                	if ( !saveToFile.getName().toLowerCase().endsWith(".map") )
                    {
                    	saveToFile = new File(saveToFile.getAbsoluteFile() + ".map");
                    }
                    XMLEncoder encoder = new XMLEncoder(new FileOutputStream(saveToFile));
                    // This sets the XMLEncoder so that it properly
                    // saves the graph's information.
                    PersistenceDelegatesConfiguration.setup(encoder);
                    encoder.writeObject(gui.getGraph().getGraphLayoutCache());
                    encoder.close();
                }
                catch (Exception fnfe) 
                {
                    JOptionPane.showMessageDialog(gui.getContentPane(), fnfe.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else 
            {
                int userChoice = JOptionPane.showInternalConfirmDialog(gui.getContentPane(),
                        "You've chosen to overwrite an already existing file. " +
                        "Do you want to do this?", "Overwrite Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (userChoice == JOptionPane.YES_OPTION) 
                {
                    try 
                    {
                        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(saveToFile));
                        PersistenceDelegatesConfiguration.setup(encoder);
                        encoder.writeObject(gui.getGraph().getGraphLayoutCache());
                        encoder.close();
                    }
                    catch (FileNotFoundException fnfe) 
                    {
                        JOptionPane.showMessageDialog(gui.getContentPane(), fnfe.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (NullPointerException npe) {
                        JOptionPane.showMessageDialog(gui.getContentPane(), npe.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}