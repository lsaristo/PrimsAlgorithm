import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * The Graph class
 */
public class Graph implements Iterable<Graph.Vertex>
{
    public Map<String,Vertex> database = new HashMap<String,Vertex>( );

    /**
     * Add a new edge to the graph.
     */
    public void addEdge( String sourceName, String destName, double cost )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
		Edge e = new Edge(w, cost);
		e.source = v;
		v.neighbors.add(e);
		w.neighbors.add(e);
    }

    /**
     * If vertexName is not present, add it to database.
     * In either case, return the Vertex.
     */
    public Vertex getVertex( String vertexName )
    {
        Vertex v = database.get( vertexName );
        if( v == null ) {
            v = new Vertex( vertexName );
            database.put( vertexName, v );
        }
        return v;
    }

	@Override
	public String toString() {
		String toString = "\n-Vertex----Edges-------\n";
		for(Vertex v : this) {
			toString += "\n" + v + ": [";
			for(Edge e : v.neighbors) {
				toString += "(" + e + ", " + e.cost + ")";
			}
			toString += "]";
		}
		return toString;
	}

	// An edge. Connection between two Verticies
	static class Edge
	{
		public double cost;
		public Vertex source;
		public Vertex dest;
		
		public Edge( Vertex d, double c )  {
        	dest = d;
        	cost = c;
			source = null;
		}
		
		@Override
		public String toString() {
			return "" + source + "<-->" + dest;
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
