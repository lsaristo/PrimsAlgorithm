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
	final static String STARTVERT = "1";

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
		System.out.println("Created graph: " + newGraph);
		return newGraph;
	}

	private void primsAlgorithm() {
		Graph newGraph = new Graph();
		List<Graph.Vertex> tree = new ArrayList<Graph.Vertex>();
		List<Graph.Edge> edgeDB = new ArrayList<Graph.Edge>();
		Deque<Graph.Vertex> Q = new ArrayDeque<Graph.Vertex>();
        for(Graph.Vertex v : graph)
            Q.push(v);
		tree.add(graph.getVertex(STARTVERT));
        Q.remove(graph.getVertex(STARTVERT));
		while(Q.size() > 0) {
			Graph.Edge minSoFar = new Graph.Edge(new Graph.Vertex("Dummy"), Double.POSITIVE_INFINITY);
			for(Graph.Vertex currentVertex : tree) {
	         	for(Graph.Edge adjacentEdge : currentVertex.neighbors) {
					if(minSoFar.cost > adjacentEdge.cost && !edgeDB.contains(adjacentEdge) 
						&& (!tree.contains(adjacentEdge.source) || !tree.contains(adjacentEdge.dest))) {
						minSoFar = adjacentEdge;
					}
	     		}                
			}
			try
				{ newGraph.addEdge(minSoFar.source.name, minSoFar.dest.name, minSoFar.cost); }
			catch (NullPointerException e)
				{ continue; }

			edgeDB.add(minSoFar);
			if(tree.contains(minSoFar.source))
				tree.add(minSoFar.dest);
			else
				tree.add(minSoFar.source);

			if(Q.contains(minSoFar.dest))
				Q.remove(minSoFar.dest);
			else
				Q.remove(minSoFar.source);
		}
		System.out.println("Finally, here't the new spanning tree " + newGraph);
	}

    /**
     * args to the program should be the list of vertices.
     */
    public static void main(String[] args) {
		Prim puzzle = new Prim();
		puzzle.primsAlgorithm();
    
	} // End main in Prim
} // End Prim Class
