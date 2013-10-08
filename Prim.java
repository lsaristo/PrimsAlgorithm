/**
 * Implements Prim's algorithm as described in the CS47B Spanning Tree Assignment
 *
 * Written by John Wilkey for CS47B
 */
import java.util.*;
import java.io.*;
public class Prim {
	/* Graph Configuration File */
	final static String CONFIG = "graphConfig.txt";
	final static String STARTVERT = "A";
	final static String GOALVERT = "E";

	/* Class memebers */
	Graph graph;  

	public Prim() {
		graph = createGraph(); 
	}

	private Graph createGraph() {
		Graph newGraph = new Graph();
		try {
			Scanner inScan = new Scanner(new BufferedReader(new FileReader(CONFIG)));
			while(inScan.hasNext())
				newGraph.addEdge(inScan.next(), inScan.next(), (double)inScan.nextInt());
		}
		catch (IOException e) {
			System.out.println("ERROR: Unexpected end of configuration or no configuration found");
		}
		return newGraph;
	}

	private void primsAlgorithm() {
        ArrayList<Graph.Vertex> tree = new ArrayList<Graph.Vertex>(); 		
		Deque<Graph.Vertex> Q = new ArrayDeque<Graph.Vertex>();
        for(Graph.Vertex v : graph)
            Q.push(v);
        Graph.Vertex currentVertex = graph.getVertex(STARTVERT); 
		tree.add(currentVertex);
        Q.remove(currentVertex);
		System.out.println("Before looping Q is " + Q + " and tree is " + tree);		
		while(Q.size() > 0) {
			if(currentVertex.equals(GOALVERT))
				System.out.println("FOUND A GOAL: " + currentVertex); 
            for(Graph.Edge adjacentEdge : currentVertex.neighbors) {
                System.out.println("Cost " + adjacentEdge.cost + " size of Q: " + Q.size() + 
						" current vertex v is " + currentVertex + adjacentEdge + " list Q: " + Q + ". List of neighbors for CV: "
						+ currentVertex.neighbors);
				tree.add(adjacentEdge.dest);
				System.out.println("Q is " + Q + " tree is " + tree);
          	}                            	
			currentVertex = Q.pop();
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
