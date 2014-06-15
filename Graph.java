import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * A representation of a graph. 
 */
public class Graph implements Iterable<Graph.Vertex> 
{
    public Map<String,Vertex> database = new HashMap<String,Vertex>( );
    public Hashtable<Integer,Edge> edges = new Hashtable<Integer,Edge>();   

    /**
     * Add a new edge to the graph.
     *
     * @param sourceName source vertex.
     * @param destName destination vertex.
     * @param cost of the edge between the two vertices. 
     */
    public void addEdge(String sourceName, String destName, double cost) {
        Vertex v = getVertex(sourceName);
        Vertex w = getVertex(destName);
        Edge e = new Edge(w, cost); 
        e.source = v;
        v.neighbors.add(e);
        w.neighbors.add(e);
        edges.put(e.hashCode(), e);
    }

    /**
     * Return a vertex in this graph by the vertex name.
     * If vertexName is not present, add it to database.
     */
    public Vertex getVertex(String vertexName) {
        Vertex v = database.get( vertexName );

        if(v == null) {
            v = new Vertex( vertexName, Double.POSITIVE_INFINITY );
            database.put( vertexName, v );
        }
        return v;
    }

    /**
     * String representation of this graph.
     */
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

    /**
     * The Graph class implements the iterable interface for  use with
     * the GraphIterator inner class below. This method must be provided
     * to implement Iterable.
     */
    @Override
    public Iterator<Vertex> iterator() { 
        return new GraphIterator(this); 
    }

    /**
     * Edge inner class. Represents an edge between two vertices. 
     */
    static class Edge 
    {
        public double cost;
        public Vertex source;
        public Vertex dest;
    
        /**
         * Construct a new edge. 
         *
         * @param d destination vertex.
         * @param c cost of this edge.
         */ 
        public Edge(Vertex d, double c)  {
            dest = d;
            cost = c;
            source = null;
        }
    
        /**
         * Construct a new edge as a copy of an existing edge.
         *
         * @param e source edge to copy. 
         */ 
        public Edge(Edge e) {
            dest = e.dest;
            cost = e.cost;
            source = e.source;
        }

        /**
         * String representation of this edge.
         */
        @Override
        public String toString() {
            return "" + source + "<-->" + dest;
        }
    }

    /**
     * Vertex inner class.
     */
    static class Vertex implements Comparable<Vertex> 
    {
        public boolean    deleted;
        public String     name;   
        public double     id;     
        public Vertex     prev;   
        public List<Edge> neighbors;
        public String     pointer; 
    
        /**
         * String representation of this Vertex.
         */ 
        @Override
        public String toString()
            { return name; }

        /**
         * Compare this Vertex to another. 
         *
         * @param other Vertex to compare.
         */
        @Override
        public int compareTo(Vertex other) {
            if(id != other.id) {
                return (id > other.id) ? 1 : -1;
            }
            return 0;
        }

        /**
         * Test this Vertex for equality with another. 
         *
         * @param other Vertex to check.
         */
        @Override
        public boolean equals(Object other) {
            return name.equals(((Vertex)other).name);
        }

        /**
         * Overriden hashCode. Use the hashCode of the Vertex's name instead.
         */
        @Override
        public int hashCode() {
            return name.hashCode();
        }

        /**
         * Clear tracking information from this Vertex.
         */
        public void reset() {
            prev = null; neighbors = null; 
        }

        /**
         * Construct a new Vertex.
         *
         * @param nm name of this Vertex.
         * @param guid unique identifier of this Vertex.
         */
        public Vertex(String nm, Double guid) { 
            name = nm; 
            id = (double)guid;
            neighbors = new LinkedList<Edge>();
            pointer = null;
        }

        /**
         * Construct a vertex as a copy of another.
         *
         * @param v Vertex to copy.
         */
        public Vertex(Vertex v) {
            name = v.name;
            id = v.id;
            neighbors = v.neighbors;
            pointer = v.pointer;
        }
    } // End of Vertex inner class. 

    
    /**
     * GraphIterator class
     */
    class GraphIterator implements Iterator<Vertex> {
        Iterator vertIter;
    
        /**
         * Construct a new GraphIterator.
         *
         * @param inGraph Graph to iterate over.
         */ 
        GraphIterator(Graph inGraph) {
            vertIter = inGraph.database.values().iterator();
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
