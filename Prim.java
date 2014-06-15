import java.util.*;
import java.io.*;

/**
 * Driver to implement Prim's algorithm.
 */
public class Prim {
	final static String CONFIG = "graphConfig.txt";
	final static String STARTVERT = "1";
	public Graph graph;  

	public Prim() {
		graph = createGraph(); 
		graph.getVertex(STARTVERT).id = 0;
	}

    /**
     * Generate a Graph from the config file specified in CONFIG.
     *
     * @see Graph.java
     */
	private Graph createGraph() {
		Graph newGraph = new Graph();

		try {
			Scanner inScan = new Scanner(new BufferedReader(new FileReader(CONFIG)));
			
            while(inScan.hasNext()) {
				newGraph.addEdge(
                    inScan.next()
                    , inScan.next()
                    , (double)inScan.nextInt()
                );
            }
		} catch (IOException e) {
			System.out.println("ERROR: Unexpected end of config or no config found");
            return null;
		}

		System.out.println("Created graph: " + newGraph);
		return newGraph;
	}

	/**
	 * Prim's algorithm.
	 * This verion uses a Binary Heap (PriorityQueue) and an adjacency list
	 *	(built into Graph.Vertex) to achieve running time of: 
	 *	1) The number of edges in the graph (iterating through each Vertex's
	 *		adjacency list). 
	 *	2) For each of these verticies. Add it's adjacent verticies to the 
	 *		PriorityQueue (done in log(n) time), then removing the lightest 
	 *		edge in the Queue (also done in log(n) time). 
	 */ 
	private void primsAlgorithm() {
		Graph newGraph = new Graph();
		PriorityQueue<Graph.Vertex> Q = new PriorityQueue<Graph.Vertex>();
		Q.add(graph.getVertex(STARTVERT));
		Hashtable<String, Graph.Vertex> db = new Hashtable<String, Graph.Vertex>();
		db.put(graph.getVertex(STARTVERT).name, graph.getVertex(STARTVERT));
		
        while(Q.size() > 0) {
			Graph.Vertex currentVertex = Q.poll();
			if(currentVertex.deleted) 
				continue;

			if(currentVertex.pointer != null) {
				newGraph.addEdge(currentVertex.pointer, currentVertex.name, currentVertex.id);
			} else {
				newGraph.getVertex(currentVertex.name);	
            }

	        for(Graph.Edge adjacentEdge : currentVertex.neighbors) {
				Graph.Vertex adjacentVertex = 
                    new Graph.Vertex(
                        adjacentEdge.source.equals(currentVertex)
					    ? adjacentEdge.dest 
                        : adjacentEdge.source
                    );
				
				adjacentVertex.id = adjacentEdge.cost;
				adjacentVertex.pointer = currentVertex.name;

				if(!db.contains(adjacentVertex) ) {
					Q.add(adjacentVertex);
					db.put(adjacentVertex.name, adjacentVertex);
				} else if (db.get(adjacentVertex.name).id > adjacentVertex.id) {
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
     * Primary program entry point.
     *
     * @args vertices in the Graph.
     *
     * @see Graph.java
     */
    public static void main(String[] args) {
		Prim puzzle = new Prim();
		puzzle.primsAlgorithm();
    
	} // End main in Prim
} // End Prim Class
