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
		ArrayList<Vertex> vertList = new ArrayList<Vertex>();	
		vertList.add(graph.getVertex("A"));
		while(vertList.size > 0) {
			// Breadth-first alg goes here. 
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
