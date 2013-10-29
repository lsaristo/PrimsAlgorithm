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
		graph.getVertex(STARTVERT).id = 0;
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

	/**
	 * Prim's algorithm.
	 */ 
	private void primsAlgorithm() 
	{
		Graph newGraph = new Graph();
		PriorityQueue<Graph.Vertex> Q = new PriorityQueue<Graph.Vertex>();
		Q.add(graph.getVertex(STARTVERT));
		Hashtable<String, Graph.Vertex> db = new Hashtable<String, Graph.Vertex>();
		db.put(graph.getVertex(STARTVERT).name, graph.getVertex(STARTVERT));
		while(Q.size() > 0) 
		{
			Graph.Vertex currentVertex = Q.poll();
			if(currentVertex.deleted) 
				continue;

			if(currentVertex.pointer != null) 
				newGraph.addEdge(currentVertex.pointer, currentVertex.name, currentVertex.id);
			else
				newGraph.getVertex(currentVertex.name);	

	        for(Graph.Edge adjacentEdge : currentVertex.neighbors) 
			{
				Graph.Vertex adjacentVertex = new Graph.Vertex((adjacentEdge.source.equals(currentVertex) ? adjacentEdge.dest : adjacentEdge.source));
				adjacentVertex.id = adjacentEdge.cost;
				adjacentVertex.pointer = currentVertex.name;
				adjacentVertex.id = adjacentEdge.cost;
				
				if(db.contains(adjacentVertex)) System.out.println("FOUND VERT " + db.get(adjacentVertex.name) + " aka " + adjacentVertex);
				if(!db.contains(adjacentVertex) ) {
					Q.add(adjacentVertex);
					db.put(adjacentVertex.name, adjacentVertex);
				}
				else if (db.get(adjacentVertex.name).id > adjacentVertex.id) {
					db.get(adjacentVertex.name).deleted = true;
					db.remove(adjacentVertex.name);
					db.put(adjacentVertex.name, adjacentVertex);
					Q.add(adjacentVertex);
				}
			}
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
