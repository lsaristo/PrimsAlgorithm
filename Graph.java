import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;

/**
 * The Graph class
 */
public class Graph implements Iterable<Graph.Vertex>
{
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String,Vertex> database = new HashMap<String,Vertex>( );

    /**
     * Add a new edge to the graph.
     */
    public void addEdge( String sourceName, String destName, double cost )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
		v.neighbors.add(new Edge(w, cost));
    }

    /**
     * If vertexName is not present, add it to database.
     * In either case, return the Vertex.
     */
    public Vertex getVertex( String vertexName )
    {
        Vertex v = database.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName );
            database.put( vertexName, v );
        }
        return v;
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     */
    private void printPath( Vertex dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }
    
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll( )
    {
        for( Vertex v : database.values( ) )
            v.reset( );
    }

	// An edge. Connection between two Verticies
	static class Edge
	{
		public double cost;
		public Vertex dest;
		
		public Edge( Vertex d, double c )  {
        	dest = d;
        	cost = c;
		}
	
		public String toString() {
			return "-->" + dest;
		}

	}

	// Represents a vertex in the graph.
	static class Vertex
	{
    	public String     name;   // Vertex name
    	public Vertex     prev;   // Previous vertex on shortest path
    	public List<Edge> neighbors;
		
		@Override
		public String toString()
			{ return name; }

		public void reset()
			{  prev = null; neighbors = null; }

    	public Vertex( String nm ) { 
			name = nm; 
			neighbors = new LinkedList<Edge>();
		}

	}

   /* Implements Iterable interface */
	@Override
    public Iterator<Vertex> iterator() 
		{ return new GraphIterator(this); }

    /**
     * GraphIterator class
     */
    class GraphIterator implements Iterator<Vertex> {
        Iterator vertIter;
		
        GraphIterator(Graph inGraph) {
            vertIter = inGraph.database.values().iterator();
        }
        
		@Override
        public boolean hasNext() 
			{ return vertIter.hasNext(); }

		@Override
        public Vertex next() 
			{ return (Vertex)vertIter.next(); }        
	
		@Override
		public void remove() {} // Not implemented.

    } // End graph iterator class
} // End graph class
