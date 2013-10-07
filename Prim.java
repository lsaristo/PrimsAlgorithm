/**
 * Implements Prim's algorithm as described in the CS47B Spanning Tree Assignment
 *
 * Written by John Wilkey for CS47B
 */
import java.util.*;
public class Prim {
	/* Graph Configuration File */
	final static String CONFIG = "graphConfig.txt";

	/* Class memebers */
	Graph graph;  

	public Prim() {
		graph = createGraph(); 
	}

	private Graph createGraph() {
		Graph newGraph = null;
		try {
			Scanner inScan = new Scanner(new BufferedReader(new FileReader(CONFIG)));
			while(inScan.hasNext())
				newGraph.getVertex(inScan.next(), inScan.next(), inScan.next());
		}
		catch (IOException e) {
			System.out.println("ERROR: Unexpected end of configuration or no configuration found");
		}
		return newGraph;
	}

	private void primsAlgorithm() {
        ArrayList<Graph> tree = new Arraylist<Graph>(); 		
        ArrayList<Vertex> Q = new ArrayList<Vertex>();
        for(Vertex v : graph)
            Q.add(v);
        Vertex firstVertex = graph.getVertex("A");
        tree.add(firstVertex);
        Q.remove(firstVertex);		

		while(Q.size > 0) {
            Vertex temp = new Vertex("X", "Y", Double.POSITIVE_INFINITY);
            double cost = Double.POSITIVE_INFINITY;
		    for(Vertex v : tree) {
                for(Vertex adj : v.adj()) {
                    if(adj.cost() < temp.cost())
                        temp = adj();
                }
                v.getVertex(temp);
            }                	
		}
	}

    /**
     * args to the program should be the list of vertices.
     */
    public static void main(String[] args) {
		Prim puzzle = new Prim();
		puzzle.primsAlgorithm();
    
	} // End main in Prim
} // End Prim Class
