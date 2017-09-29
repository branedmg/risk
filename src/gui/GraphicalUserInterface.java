package gui;

import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jgraph.graph.GraphModel;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.JGraph;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import map.Countries;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import EventHandlers.AddCountryListener;
import EventHandlers.AddEdgeListener;
import EventHandlers.DeleteListener;
import EventHandlers.ShowCountriesDetailsListener;
import EventHandlers.ShowGlobalLogListener;
import EventHandlers.OpenListener;
import EventHandlers.PopupListener;
import EventHandlers.SaveListener;
import EventHandlers.ShowStateLogListener;
import EventHandlers.StageController;
import EventHandlers.CountryDetailsListener;

import EventHandlers.StageController;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import map.Country;
import map.Map;

/**
 * This class defines the main window of our program. It defines the
 * main components of the user interface. All event handlers registered
 * to components (menu items) of this class are instantiated with a
 * reference to this class. Using this class' getGraph method, the
 * event handlers can obtain an up to date version of the graph's model
 * and view.
 */
public class GraphicalUserInterface extends JFrame 
{
	/**
	 * Singleton instance of the graphical user interface.
	 */
    private static GraphicalUserInterface instance = null;
    private JMenuBar menuBar;
    
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu viewMenu;
    private JMenu helpMenu;
    private JMenu simulationMenu;
    
    private JMenuItem open; 
    private JMenuItem save;
    private JMenuItem addCountry;
    private JMenuItem addEdge;
    private JMenuItem delete;
    private JMenuItem showDetails;
    private JMenuItem help;
    private JMenuItem start, pause;
    private JMenuItem showGlobalLog;
    private JMenuItem showStateLog;
    private JMenuItem showCountryDetails;
    private JMenuItem showCountriesDetails;
    
    private JGraph graph;

    private Dimension defaultDimension = new Dimension(600, 600);

    /**
     * Returns the singleton instance of GraphicalUserInterface
     * @return
     */
    public static GraphicalUserInterface getInstance() 
    {
        if (instance == null) 
        {
            instance = new GraphicalUserInterface();
        }
        return instance;
    }
    
    /**
     * Creates an object of this class.
     */
    private GraphicalUserInterface()
    {
        super("Map Editor and Risk Simulator");
        
        /*
         * Create all of the components required to represent my graph
         */
        DefaultGraphModel model = new DefaultGraphModel();
        GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory(), true);
        graph = new JGraph(model, view);
        
        /*
         * The next few lines adds the Map object to the graph model.
         * you can access this object from the listeners as follows.
         * (Map)((DefaultGraphCell)gui.getGraph().getModel().getRootAt(0)).getUserObject()
         */
        DefaultGraphCell mapCell = new DefaultGraphCell(new Map());
        GraphConstants.setSelectable(mapCell.getAttributes(), false);
        GraphConstants.setEditable(mapCell.getAttributes(), false);
        graph.getGraphLayoutCache().insert(mapCell);
        graph.getGraphLayoutCache().setVisible(mapCell, false);
        
        instance = this;
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        viewMenu = new JMenu("View");
        helpMenu = new JMenu("Help");
        simulationMenu = new JMenu("Simulation");

        /*
         * Create the menu items for the file menu.
         */
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");

        /*
         * Create the menu items for the edit menu.
         */
        addCountry = new JMenuItem("Add Country");
        addEdge = new JMenuItem("Add Edge");
        delete = new JMenuItem("Delete");
        showDetails = new JMenuItem("Show State Details");
        //showCountryDetails = new JMenuItem("Show Country Details");

        /*
         * Create the menu items for the view menu.
         */
        showGlobalLog = new JMenuItem("Show Global Log");
        showStateLog = new JMenuItem("Show State Log");
        showCountriesDetails = new JMenuItem("Show Countries Details");
        
        /*
         * Create the menu items for the help menu.
         */
        help = new JMenuItem("Help");

        /*
         * Create the menu items for the simulation menu.
         */
        start = new JMenuItem("Start");
        pause = new JMenuItem("Pause");

        /*
         * Register listeners for each menu item.
         * First for file menu items.
         */
        open.addActionListener(new OpenListener());
        save.addActionListener(new SaveListener());
        
        /*
         * Now the edit menu items.
         */
        addCountry.addActionListener(new AddCountryListener());
        addEdge.addActionListener(new AddEdgeListener());
        delete.addActionListener(new DeleteListener());
        showDetails.addActionListener(new PopupListener());
        //showCountryDetails.addActionListener(new CountryDetailsListener());

        /*
         * Now the view menu items.
         */
        showGlobalLog.addActionListener(new ShowGlobalLogListener());
        showStateLog.addActionListener(new ShowStateLogListener());
        showCountriesDetails.addActionListener(new ShowCountriesDetailsListener());
        
        /*
         * Now the simulation menu items.
         */
        {
        	ActionListener stageController = new StageController();
        	start.addActionListener( stageController );
            pause.addActionListener( stageController );
        }

        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(save);

        editMenu.add(addCountry);
        editMenu.add(addEdge);
        editMenu.addSeparator();
        editMenu.add(delete);
        editMenu.addSeparator();
        editMenu.add(showDetails);
        //editMenu.add(showCountryDetails);
        
        viewMenu.add(showGlobalLog);
        viewMenu.add(showStateLog);
        viewMenu.add(showCountriesDetails);
                
        simulationMenu.add(start);
        simulationMenu.add(pause);
        
        helpMenu.add(help);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(simulationMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        setSize(defaultDimension);
        add(new JScrollPane(graph));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
    }

    /**
     * Used to get a reference to the graph object.
     * @return Returns the graph object that represents the logical
     * and viewable (color, size of nodes, etc.) parts of our graph.
     */
    public JGraph getGraph() 
    {
        return graph;
    }

}
