package EventHandlers;

import GraphAdapter.GraphAdapter;
import gui.GraphicalUserInterface;
import map.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import org.jgraph.*;
import org.jgraph.graph.*;

/**
 * Class that listens to user presses of the "Add Country" menu item
 * in the "edit" menu.
 */
public class AddCountryListener extends JFrame implements ActionListener {

    GraphicalUserInterface gui;
    State state;
    Country countries;
    Color color;
    Container content;
    JTextField txtColor, txtNumState, txtCountryName;
    JColorChooser chooser;
    JFrame frame, frameColor;
    JLabel lblNumState, lblCountryName, banner;
    JPanel panel1, panel2, panel3;
    JButton btnCreate, btnColor;

    /**
     * Constructor.
     * @param gui The user interface that holds the graph to be altered.
     */
    public AddCountryListener() {
        gui = GraphicalUserInterface.getInstance();
    }

    /**
     * Causes a new window to popup. This window then asks
     * the user to indicate the name, the number of states, and
     * the color of the country. The names of individual states must be
     * set later.
     * @param actionEvent Not used.
     */
    public void actionPerformed(ActionEvent actionEvent) {

        if ( !GraphAdapter.getInstance().isPaused() )
        {
          return;  
        }
        JGraph graph = gui.getGraph();
        frame = new JFrame("Add Country Window");
        Container content = getContentPane();
        content.removeAll();
        state = new State();
        countries = new Country();

        Border border = new CompoundBorder(new EtchedBorder(), new LineBorder(Color.RED));
        lblNumState = new JLabel("Number of states: ");
        lblCountryName = new JLabel("Country: ");

        btnCreate = new JButton("Create");
        btnColor = new JButton("Color");

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();

        txtCountryName = new JTextField("                                      ");
        txtCountryName.setBounds(10, 100, 100, 20);
        txtNumState = new JTextField("                                   ");

        panel1.add(lblCountryName);
        panel1.add(txtCountryName);
        panel1.setBorder(border);
        panel1.setBounds(10, 20, 300, 50);

        panel2.add(lblNumState);
        panel2.add(txtNumState);
        panel2.setBorder(border);
        panel2.setBounds(10, 100, 300, 50);

        panel3.add(btnCreate);
        panel3.add(btnColor);
        panel3.setBounds(10, 200, 300, 50);

        frame.setLayout(new GridLayout(3, 1));
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);

        frame.setSize(350, 200);
        frame.setVisible(true);

        btnColor.addActionListener(new ColorAction());
        btnCreate.addActionListener(new CreateAction());
        btnColor.setSelected(false);
    }

    /**
     * Listens to the presses of the "color" button of the
     * "add country" popup window.
     */
    public class ColorAction implements ActionListener {

        /**
         * Opens a new window that allows the user to choose
         * a color for the country.
         * @param e Not used.
         */
        public void actionPerformed(ActionEvent e) {
            // Create a color dialog box to choose the color of the country
            chooser = new JColorChooser();
            frameColor = new JFrame("ColorChooser");
            color = JColorChooser.showDialog(frameColor, "Choose a Color", Color.PINK);

            setEnabled(false);
            btnColor.setSelected(true);

        }
    }

    /**
     * Listens to presses of the "create" button in the "add country"
     * window.
     */
    public class CreateAction implements ActionListener {

        /**
         * Retrieves the information input into the text fields of
         * the "add country" window and makes the necessary modifications
         * to the graph object.
         * @param e Not used.
         */
        public void actionPerformed(ActionEvent e) {
            if (color != null) {
                Countries countries = ( (map.Map) ( (DefaultGraphCell) gui.getGraph().getModel().getRootAt(0) ).getUserObject() ).getCountries();
                Country newCountry = new Country();
                newCountry.setStates(new ArrayList<State>());
                newCountry.setName(txtCountryName.getText().trim());
                newCountry.setColor(color);

                // creates the given number of states in a country
                int number = Integer.parseInt(txtNumState.getText().trim());
                DefaultGraphCell[] newCells = new DefaultGraphCell[number];
                for (int i = 0; i < number; i++) {

                    State newState = new State();
                    newState.setCountry(newCountry);
                    newCountry.addState(newState);
                    newCells[i] = new DefaultGraphCell(newState);
                    GraphConstants.setBackground(newCells[i].getAttributes(), color);
                    GraphConstants.setEditable(newCells[i].getAttributes(), false);
                    GraphConstants.setOpaque(newCells[i].getAttributes(), true);
                    GraphConstants.setBounds(newCells[i].getAttributes(), new Rectangle2D.Double((i / 3) * 20, i * 40, 40, 30));
                    newCells[i].add(new DefaultPort());
                }
                gui.getGraph().getGraphLayoutCache().insert(newCells);
                countries.addCountry(newCountry);
                frame.setVisible(false);
            } else {
                JOptionPane.showInternalMessageDialog(gui.getContentPane(), "Please Select a Color");
            }

        }
    }
}
