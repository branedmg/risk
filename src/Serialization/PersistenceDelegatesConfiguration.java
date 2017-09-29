package Serialization;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.jgraph.graph.AbstractCellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;

/**
 * This class has one static method. See the method's documentation for more
 * info.
 */
public class PersistenceDelegatesConfiguration 
{
    /**
     * This code was taken from the JGraph manual. It's the code that
     * sets the XMLEncoder properly so that it knows how the different
     * objects that JGraph uses are constructed. For more information about how
     * Persistence Delegates and XMLEncoder work, check
     * <a href="http://java.sun.com/products/jfc/tsc/articles/persistence4/">
     * Using XMLEncoder</a>.
     * @param encoder The instance of XMLEncoder used to write the graph
     * to the file.
     */
    public static void setup(XMLEncoder encoder) 
    {
        encoder.setPersistenceDelegate(DefaultGraphModel.class,new DefaultPersistenceDelegate(new String[] {"roots","attributes"}));
        encoder.setPersistenceDelegate(GraphLayoutCache.class,new DefaultPersistenceDelegate(new String[] {"model","factory", "cellViews", "hiddenCellViews", "partial"}));
        encoder.setPersistenceDelegate(DefaultGraphCell.class,new DefaultPersistenceDelegate(new String[] {"userObject"}));
        encoder.setPersistenceDelegate(DefaultEdge.class,new DefaultPersistenceDelegate(new String[] {"userObject"}));
        encoder.setPersistenceDelegate(DefaultPort.class,new DefaultPersistenceDelegate(new String[] {"userObject"}));
        encoder.setPersistenceDelegate(AbstractCellView.class,new DefaultPersistenceDelegate(new String[] {"cell","attributes"}));
        
        encoder.setPersistenceDelegate(DefaultEdge.DefaultRouting.class,new PersistenceDelegate() 
        {
            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) 
            {
                return new Expression(oldInstance, GraphConstants.class,
                        "getROUTING_SIMPLE", null);
            }
        });
        
        encoder.setPersistenceDelegate(DefaultEdge.LoopRouting.class,
            new PersistenceDelegate() 
        {
            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                return new Expression(oldInstance, GraphConstants.class,
                        "getROUTING_DEFAULT", null);
            }
        });
        
        encoder.setPersistenceDelegate(ArrayList.class,encoder.getPersistenceDelegate(List.class));
    }
}
