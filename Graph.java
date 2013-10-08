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
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );

    /**
     * Add a new edge to the graph.
     */
    public void addEdge( String sourceName, String destName, double cost )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( new Edge( w, cost ) );
		v.neighbors.add(w);
    }

    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
    public void printPath( String destName )
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            System.out.print( "(Cost is: " + w.dist + ") " );
            printPath( w );
            System.out.println( );
        }
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     */
    public Vertex getVertex( String vertexName )
    {
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName );
            vertexMap.put( vertexName, v );
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
        for( Vertex v : vertexMap.values( ) )
            v.reset( );
    }

	// Represents an edge in the graph.
	static class Edge
	{
    	public Vertex     dest;   // Second vertex in Edge
    	public double     cost;   // Edge cost
    
    	public Edge( Vertex d, double c )
    	{
        	dest = d;
        	cost = c;
    	}
	}

	// Represents an entry in the priority queue for Dijkstra's algorithm.
	static class Path implements Comparable<Path>
	{
    	public Vertex     dest;   // w
    	public double     cost;   // d(w)
    
   		public Path( Vertex d, double c )
    	{
        	dest = d;
        	cost = c;
    	}
    
    	public int compareTo( Path rhs )
    	{
        	double otherCost = rhs.cost;
        
        	return cost < otherCost ? -1 : cost > otherCost ? 1 : 0;
    	}
	}

	// Represents a vertex in the graph.
	static class Vertex
	{
    	public String     name;   // Vertex name
    	public List<Edge> adj;    // Adjacent vertices
    	public double     dist;   // Cost
    	public Vertex     prev;   // Previous vertex on shortest path
    	public int        scratch;// Extra variable used in algorithm
		public List<Vertex> neighbors; // Actual Vertices in adjacent vertex;

		public double cost(Vertex v) {
			for(Edge e : adj) {
				if (e.equals(v))
					return e.cost;
				}
			return Double.POSITIVE_INFINITY;
		}
		
		@Override
		public String toString() {
			return name;
		}

		public void reset()
			{ dist = Graph.INFINITY; prev = null; scratch = 0; }

    	public Vertex( String nm )
	    	  { name = nm; adj = new LinkedList<Edge>( ); neighbors = new LinkedList<Vertex>(); reset( ); }

	}

   /* Implements Iterable interface */
	@Override
    public Iterator<Vertex> iterator() {
        return new GraphIterator(this);
    }

    /**
     * GraphIterator class
     */
    class GraphIterator implements Iterator<Vertex> {
        Iterator vertIter;
		
        GraphIterator(Graph inGraph) {
            vertIter = inGraph.vertexMap.values().iterator();
        }
        
		@Override
        public boolean hasNext() {
            return vertIter.hasNext();
        }

		@Override
        public Vertex next() {
            return (Vertex)vertIter.next();
        }        
	
		@Override
		public void remove() {} // Not implemented.

    } // End graph iterator class
} // End graph class
