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
	public Hashtable<Integer,Edge> edges = new Hashtable<Integer,Edge>();	

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
		edges.put(e.hashCode(), e);
    }

    /**
     * If vertexName is not present, add it to database.
     * In either case, return the Vertex.
     */
    public Vertex getVertex( String vertexName )
    {
        Vertex v = database.get( vertexName );
        if( v == null ) {
            v = new Vertex( vertexName, Double.POSITIVE_INFINITY );
            database.put( vertexName, v );
        }
        return v;
    }

	@Override
	public String toString() {
		String toString = "\n-Vertex: Edges:--------------------------------\n";
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
		
		public Edge( Edge e ) {
			dest = e.dest;
			cost = e.cost;
			source = e.source;
		}

		@Override
		public String toString() {
			return "" + source + "<-->" + dest;
		}

	}

	/* A vertex in the graph. Implements comparable */
	static class Vertex implements Comparable<Vertex> 
	{
		public boolean	  deleted;
    	public String     name;   // Vertex name
		public double	  id;	  // Logical GUID for the vertex
    	public Vertex     prev;   // Previous vertex on shortest path
    	public List<Edge> neighbors;
		public String	  pointer; // Pointer to currentVertex in Prim's alg. 
		
		@Override
		public String toString()
			{ return name; }

		@Override
		public int compareTo(Vertex other) {
			if(id != other.id) {
				return (id > other.id ? 1 : -1);
			}
			return 0;
		}

		@Override
		public boolean equals(Object other) {
			return name.equals(((Vertex)other).name);
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		public void reset()
			{  prev = null; neighbors = null; }

    	public Vertex( String nm, Double guid ) { 
			name = nm; 
			id = (double)guid;
			neighbors = new LinkedList<Edge>();
			pointer = null;
		}

		public Vertex( Vertex v) {
			name = v.name;
			id = v.id;
			neighbors = v.neighbors;
			pointer = v.pointer;
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
