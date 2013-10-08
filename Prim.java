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
        ArrayList<Graph.Vertex> Q = new ArrayList<Graph.Vertex>();
        for(Graph.Vertex v : graph)
            Q.add(v);
        Graph.Vertex currentVertex = graph.getVertex(STARTVERT); 
        Q.remove(currentVertex);		
		while(Q.size() > 1) {
			if(currentVertex.equals(GOALVERT))
				System.out.println("FOUND A GOAL: " + currentVertex); 
            for(Graph.Vertex adjacent : currentVertex.neighbors) {
            	if(currentVertex.cost(adjacent) > 0)
                	System.out.println("Cost for neighbor is " + currentVertex.cost(adjacent) + " size of Q is " + Q.size() + 
						" current vertex v is " + currentVertex + " and current neighbor is " + adjacent + " list Q is " + Q);
			tree.add(adjacent);
			currentVertex = adjacent;
			Q.remove(adjacent);
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
