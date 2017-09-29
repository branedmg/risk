/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * It was decided that this class wouldn't be used.
 * @deprecated
 */
public class CountryDetailsListener implements ActionListener
{

    /**
     * The main user interface where the graph is held.
     */
    GraphicalUserInterface gui;
    public String nameCountry, colour, numStates;
    String[] stateName;
    Countries[] countryName;
    public JPanel panel1, panel2, panel3, panel4, panel5;
    public JComboBox CountriesComboBox;
    public JList StatesList;
    public JLabel lblCountryName, lblnumStates, lblStateNames, lblstateNum, lblColor, lblColour;

    /**
     * The state contained in the selected country.
     */
    State state;

    /**
     * The country selected in the combo box
     */
    Country country;

     /**
     * A reference to the object where the countries that make up the map are
     * held.
     */
    Countries countries;

    /**
     * Constructor
     * @param gui The main user iterface that holds the map's graph.
     */
    public CountryDetailsListener()
    {
        gui = GraphicalUserInterface.getInstance();
    }

    /**
     * The method that's called when the user presses the "Show Country Details" menu
     * item from the "Edit" menu. The method creates a frame where the user
     * can view the details of the country.
     * @param actionEvent Not used.
     */
   // @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if ( !GraphAdapter.getInstance().isPaused() )
    	{
    		return;
    	}
        JGraph graph = gui.getGraph();
        GraphModel model = graph.getModel();

        //Popup Window for Country details
            JFrame frame = new JFrame("Country Inspection Window");
            Container content = frame.getContentPane();
            content.removeAll();
            Border border = new CompoundBorder(new EtchedBorder(), new LineBorder(Color.RED));
            lblCountryName = new JLabel("Select Country: ");
            lblnumStates = new JLabel("Number of States: ");
            lblStateNames = new JLabel("Name of States: ");
            lblstateNum = new JLabel();
            lblColor = new JLabel("Color: ");
            lblColour = new JLabel();
            panel1 = new JPanel();
            panel2 = new JPanel();
            panel3 = new JPanel();
            panel4 = new JPanel();
            panel5 = new JPanel();

            JButton btnClose = new JButton("Close");
            JButton btnCountry = new JButton("Details");


            CountriesComboBox = new JComboBox();
            countries = ( (map.Map) ( (DefaultGraphCell) gui.getGraph().getModel().getRootAt(0) ).getUserObject() ).getCountries();
            for(Country c : countries.getCountries())
            {
                 CountriesComboBox.addItem(c.getName());
            }

            CountriesComboBox.setSelectedIndex(0);
            country = new Country();
            country.setName(CountriesComboBox.getSelectedItem().toString());
            country = countries.getCountry(country.getName());

            numStates = Integer.toString(country.getNumberOfStates());
            lblstateNum.setText(numStates);
            StatesList = new JList();
            StatesList.setListData(country.getStates().toArray());
            StatesList.setLayoutOrientation(JList.VERTICAL);
            StatesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            StatesList.setVisibleRowCount(-1);
            JScrollPane listScroller = new JScrollPane(StatesList);
            listScroller.setPreferredSize(new Dimension(200, 20));


            if (country.getName() == null || country.getColor() == null)
            {
                colour = "No Color";
            }
            else
            {
                colour = country.getColor().toString();
            }

            lblColour.setText(colour);
            Color c = country.getColor();

            panel1.add(lblCountryName);
            panel1.add(CountriesComboBox);
            panel1.add(btnCountry);
            panel1.setBorder(border);

            panel2.add(lblnumStates);
            panel2.add(lblstateNum);
            panel2.setBorder(border);

            panel3.add(lblStateNames);
            panel3.add(listScroller);
            panel3.setBorder(border);

            panel4.add(lblColor);
            panel4.add(lblColour);
            panel4.setBorder(border);
            panel4.setBackground(c);

            panel5.add(btnClose);

            frame.setLayout(new GridLayout(8, 1));
            frame.add(panel1);
            frame.add(panel2);
            frame.add(panel3);
            frame.add(panel4);
            frame.add(panel5);

//          btnCountry.addActionListener(new DetailsofCountry(CountriesComboBox.getSelectedItem().toString()));
            btnClose.addActionListener(new CloseFrame(frame));
            frame.setSize(350, 400);
            frame.setVisible(true);
        }

    /**
     * This class' object listens to presses of the "Close" button in the
     * view country frame.
     */
    public class CloseFrame implements ActionListener
    {
        /**
         * A reference to the view country frame. It's used to view the
         * details of the country without changing the country's attributes.
         */
    	JFrame frame;

        /**
         * Constructor
         * @param frame The view country details.
         */
        public CloseFrame(JFrame frame)
        {
            this.frame = frame;
        }

        /**
         * This method is called when the "Details" button
         * is pressed. The method closes the frame which shows the
         * country details.
         * @param e Not used.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            frame.setVisible(false);
         }

    }

    public class DetailsofCountry implements ActionListener
    {
        /**
         * A reference to the view country frame. It's used to close the
         * application without changing the country's attributes.
         */
    	JFrame frame;
        String countryName;
        Country county;

        /**
         * Constructor
         * @param frame The view country frame.
         */
        public DetailsofCountry(String countryName)
        {
            this.countryName = countryName;

        }

        /**
         * This method is called when the "Details" button
         * is pressed. The method shows the country details.
         * @param e Not used.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            county = countries.getCountry(county.getName());
            lblstateNum = new JLabel();
            lblColour = new JLabel();
            numStates = Integer.toString(county.getNumberOfStates());
            lblstateNum.setText(numStates);
            StatesList = new JList();
            StatesList.setListData(county.getStates().toArray());


            if (county.getName() == null || county.getColor() == null)
            {
                colour = "No Color";
            }
            else
            {
                colour = county.getColor().toString();
            }

            lblColour.setText(colour);
            Color c = county.getColor();

            frame.setVisible(true);
         }

    }
   


}
