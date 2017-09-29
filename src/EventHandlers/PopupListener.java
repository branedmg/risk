package EventHandlers;

import gui.GraphicalUserInterface;
import map.*;
import map.Map;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import map.State.TechLevel;
import org.jgraph.*;
import org.jgraph.graph.*;
import GraphAdapter.GraphAdapter;
/**
 * This has to be the most complicated of all the event handlers.
 * An object of this class listens to presses of the "Show Details" menu item
 * of the "Edit" menu. The job of this event handler is to show the details of
 * a selected state from the map. This handler doesn't allow the user to view
 * more than one state at a time. This handler also takes care of altering the
 * state's attributes.
 */
public class PopupListener implements ActionListener
{
    /**
     * The main user interface where the graph is held.
     */
    GraphicalUserInterface gui;
    String name, country, colour, population, soldiers, numState, tanks, strength;
    JTextField txtStateName, txtCountry, txtPopulation, txtSoldiers, txtTanks;
    JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9;
    JComboBox techLevelComboBox;

    /**
     * The selected cell.
     */
    DefaultGraphCell stateCell;

    /**
     * The state contained in the selected cell.
     */
    State state;

    /**
     * The country the selected state belongs to when the listener is first
     * triggered by the user.
     */
    Country oldCountry;
    
    /**
     * If the user changes the country to which the state belongs to, then
     * newCountry will be different from oldCountry.
     */
    Country newCountry;

    /**
     * A reference to the object where the countries that make up the map are
     * held.
     */
    Countries countries;

    JLabel lblCountry, lblColour, lblStateStrength;

    /**
     * Constructor
     * @param gui The main user iterface that holds the map's graph.
     */
    public PopupListener()
    {
        gui = GraphicalUserInterface.getInstance();
    }

    /**
     * The method that's called when the user presses the "Show Details" menu
     * item from the "Edit" menu. The method creates a frame where the user
     * can make changes to the state's properties.
     * @param actionEvent Not used.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) 
    {
        if ( !GraphAdapter.getInstance().isPaused() )
    	{
    		return;
    	}
        JGraph graph = gui.getGraph();
        GraphModel model = graph.getModel();
        Object[] selectedCells = graph.getSelectionCells();
        if (selectedCells.length == 1 && !model.isEdge(selectedCells[0]) && !model.isPort(selectedCells[0])) 
        {
            //Popup Window for State details
            JFrame frame = new JFrame("State Inspection Window");
            Container content = frame.getContentPane();
            content.removeAll();
            Border border = new CompoundBorder(new EtchedBorder(), new LineBorder(Color.RED));
            JLabel lblStateName = new JLabel("State: ");
            JLabel lblCountryName = new JLabel("Country: ");
            JLabel lblPopulation = new JLabel("Population: ");
            JLabel lblSoldiers = new JLabel("Soldiers: ");
            JLabel lblTanks = new JLabel("Tanks: ");
            JLabel lblTechLevel = new JLabel("Tech. Level: ");
            JLabel lblColor = new JLabel("Color: ");
            JLabel lblStrength = new JLabel("Strength: ");
            panel1 = new JPanel();
            panel2 = new JPanel();
            panel3 = new JPanel();
            panel4 = new JPanel();
            panel5 = new JPanel();
            panel6 = new JPanel();
            panel7 = new JPanel();
            panel8 = new JPanel();
            panel9 = new JPanel();
            JButton btnSave = new JButton("Save");
            JButton btnChangeCountry = new JButton("Change Country");
            txtStateName = new JTextField(20);
            txtPopulation = new JTextField(20);
            txtSoldiers = new JTextField(20);
            txtTanks = new JTextField(20);
            techLevelComboBox = new JComboBox( new String[] { "low" , "high" } );
            lblColour = new JLabel();
            lblCountry = new JLabel();
            lblStateStrength = new JLabel();
            stateCell = ((DefaultGraphCell)selectedCells[0]);
            state = (State)stateCell.getUserObject();
            oldCountry = state.getCountry();
            if (state.getName() == null) 
            {
                name = "null";
            }
            else 
            {
                name = state.getName();
            }
            
            if (state.getCountry() == null || state.getCountry().getName() == null) 
            {
                country = "null";
            }
            else 
            {
                country = state.getCountry().getName().trim();
            }
            
            population = Integer.toString(state.getPopulation());
            soldiers = Integer.toString(state.getSoldiers());
            tanks = Integer.toString(state.getTanks());
            if ( state.getTechLevel() == TechLevel.low ) {
                techLevelComboBox.setSelectedIndex( 0 );
            }
            else {
                techLevelComboBox.setSelectedIndex( 1 );
            }

            if (state.getCountry() == null || state.getCountry().getColor() == null)
            {
                colour = "No Color";
            }
            else 
            {
                colour = state.getCountry().getColor().toString();
            }
            strength = Integer.toString(state.getMilitaryStrength());
            lblStateStrength.setText(strength);
            txtStateName.setText(name);
            lblCountry.setText(country);
            txtPopulation.setText(population);
            txtSoldiers.setText(soldiers);
            txtTanks.setText( tanks );
            lblColour.setText(colour);
            Color c = state.getCountry().getColor();

            panel1.add(lblStateName);
            panel1.add(txtStateName);
            panel1.setBorder(border);

            panel2.add(lblCountryName);
            panel2.add(lblCountry);
            panel2.setBorder(border);

            panel3.add(lblPopulation);
            panel3.add(txtPopulation);
            panel3.setBorder(border);

            panel4.add(lblSoldiers);
            panel4.add(txtSoldiers);
            panel4.setBorder(border);

            panel5.add( lblTanks );
            panel5.add( txtTanks );
            panel5.setBorder( border );

            panel6.add( lblTechLevel );
            panel6.add( techLevelComboBox );
            panel6.setBorder( border );

            panel7.add( lblStrength );
            panel7.add( lblStateStrength );
            panel7.setBorder( border );

            panel8.add(lblColor);
            panel8.add(lblColour);
            panel8.setBorder(border);
            panel8.setBackground(c);

            panel9.add(btnChangeCountry);
            panel9.add(btnSave);

            frame.setLayout(new GridLayout(9, 1));
            frame.add(panel1);
            frame.add(panel2);
            frame.add(panel3);
            frame.add(panel4);
            frame.add(panel5);
            frame.add(panel6);
            frame.add( panel7 );
            frame.add( panel8 );
            frame.add(panel9);

            btnSave.addActionListener(new MyAction(frame));
            btnChangeCountry.addActionListener(new ChangeCountryListener(gui, lblCountry, lblColour));
            frame.setSize(400, 650);
            frame.setVisible(true);
        }
    }


    /**
     * This class' object listens to presses of the "Save" button in the
     * state editor frame. It takes care of making the changes to state.
     */
    public class MyAction implements ActionListener
    {
        /**
         * A reference to the state editor frame. It's used to close the
         * application afther changing the state's attributes.
         */
    	JFrame frame;

        /**
         * Constructor
         * @param frame The state editor frame.
         */
        public MyAction(JFrame frame) 
        {
            this.frame = frame;
        }

        /**
         * This method is called when the "Save" button in the state editor
         * is pressed. The method fetches the information from the editor
         * and makes the necessary changes to the state.
         * @param e Not used.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            newCountry = state.getCountry();
            newCountry.setName(lblCountry.getText());
            state.setName(txtStateName.getText().trim());
            state.setPopulation(Integer.parseInt(txtPopulation.getText().trim()));
            state.setSoldiers(Integer.parseInt(txtSoldiers.getText().trim()));
            state.setTanks(Integer.parseInt(txtTanks.getText().trim()));
            state.setTechLevel( ( techLevelComboBox.getSelectedIndex() == 1 ) ? TechLevel.high : TechLevel.low );
            if (oldCountry != newCountry) {
                oldCountry.deleteState(state);
                newCountry.addState(state);
                state.setCountry(newCountry);
                Hashtable nested = new Hashtable();
                Hashtable newProperties = new Hashtable();
                GraphConstants.setBackground(newProperties, newCountry.getColor());
                nested.put(stateCell, newProperties);
                gui.getGraph().getGraphLayoutCache().edit(nested);
            }
            frame.setVisible(false);
         }

    }

    /**
     * This class' object listens to presses of the "Change Country" button
     * in the state editor. This class is responsible for creating a new
     * frame, which will let the user change the country the state belongs to.
     */
    public class ChangeCountryListener implements ActionListener
    {
        /**
         * A reference to the main user interface that holds the map's graph.
         */
        GraphicalUserInterface gui;

        /**
         * A reference to the label that holds the name of the country
         * the state belongs to in the state editor. This label is used
         * to change the label's text in case the country is changed.
         */
        JLabel lblCountry;

        /**
         * A reference to the label that holds the name of the color of the
         * country the state belongs to in the state editor. This label is used
         * to change the label's text in case the country is changed, and hence,
         * the state's color.
         */
        JLabel lblColour;

        /**
         * Constructor see this class' field elements to know what the
         * marameters of this constructor are.
         * @param gui
         * @param lblCountry
         * @param lblColour
         */
        public ChangeCountryListener(GraphicalUserInterface gui, JLabel lblCountry, JLabel lblColour) {
            this.gui = gui;
            this.lblCountry = lblCountry;
            this.lblColour = lblColour;
        }

        /**
         * This method is called when the "Change Country" button is pressed.
         * This method is responsible for creating a new frame that provides a
         * list of countries the state can belong to.
         * @param e Not used.
         */
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Country Chooser");
            frame.getContentPane().setLayout(new FlowLayout());
            countries = ( (Map) ( ( (DefaultGraphCell) gui.getGraph().getModel().getRootAt(0) ).getUserObject() ) ).getCountries();
            int numberOfCountries = countries.getCountries().size();
            Country[] countryArray = new Country[numberOfCountries];
            for (int i = 0; i < countryArray.length; ++i) {
                countryArray[i] = countries.getCountries().get(i);
            }

            JComboBox countryCombo = new JComboBox(countryArray);
            JButton setCountry = new JButton("Set Country");
            setCountry.addActionListener(new SetCountryListener(countryCombo, frame, lblCountry, lblColour));

            frame.getContentPane().add(countryCombo);
            frame.getContentPane().add(setCountry);
            frame.pack();
            frame.setVisible(true);
         }
    }

    /**
     * An object of this class listens to presses of the "Set Country" button
     * in the country selection frame. It is responsible for updating the name
     * of the country the state belongs to if the country is changed. Also if
     * the country is changed, then the color is also updated.
     */
    public class SetCountryListener implements ActionListener {

        /**
         * A reference to the combo box that enumerates all of the available
         * countries in the country selection frame. It is used to obtain the
         * selected country.
         */
        JComboBox countryCombo;

        /**
         * A refrence to the country selection frame. It is used to hide the
         * frame once the changes to the state editor's country and color labels
         * are made.
         */
        JFrame frame;

        /**
         * This references the state editor's country label where the name
         * of the country the state belongs to is stored.
         */
        JLabel lblCountry;

        /**
         * This reference the state editor's color label where the name of the
         * color of the country to which the state belongs to is stored.
         */
        JLabel lblColour;

        /**
         * Constructor. See this classes field elements to understand what the
         * parameters are.
         * @param countryCombo
         * @param frame
         * @param lblCountry
         * @param lblColour
         */
        public SetCountryListener(JComboBox countryCombo, JFrame frame, JLabel lblCountry, JLabel lblColour) {
            this.countryCombo = countryCombo;
            this.frame = frame;
            this.lblCountry = lblCountry;
            this.lblColour = lblColour;
        }

        /**
         * This method is called when the "Set Country" button is pressed.
         * The method makes the changes to the state editor's labels and hides
         * the country selection frame.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            state.setCountry((Country)countryCombo.getSelectedItem());
            state.getCountry().setColor(state.getCountry().getColor());
            lblCountry.setText(state.getCountry().getName());
            lblColour.setText(state.getCountry().getColor().toString());
            panel7.setBackground(state.getCountry().getColor());
            frame.setVisible(false);
        }

    }
}
