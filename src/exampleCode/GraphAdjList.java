package exampleCode;//Nathan Pattison R00167827
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;

	/**
	* Graph implementation that uses Adjacency Lists to store edges. It
	* contains one linked-list for every vertex i of the graph. The list for i
	* contains one instance of VertexAdjList for every vertex that is adjacent to i.
	* For directed graphs, if there is an edge from vertex i to vertex j then there is
	* a corresponding element in the adjacency list of node i (only). For
	* undirected graphs, if there is an edge between vertex i and vertex j, then there is a
	* corresponding element in the adjacency lists of *both* vertex i and vertex j. The
	* edges are not sorted; they contain the adjacent nodes in *order* of
	* edge insertion. In other words, for a graph, the node at the head of
	* the list of some vertex i corresponds to the edge involving i that was
	* added to the graph least recently (and has not been removed, yet). 
	*/

public class GraphAdjList  implements Graph 
{
	private int vertices;
	private LinkedList<Edge> adjListArray[];
	private boolean directed;

	public GraphAdjList(int V, boolean directed) 
	{
		vertices = V;
		adjListArray = new LinkedList[V];
		this.directed = directed;
		for(int i = 0; i < vertices ; i++)
		{ 
			adjListArray[i] = new LinkedList<>(); 
        } 
	}

	// 1. IMPLEMENTATION METHOD numVerts: 
	public int numVerts() 
	{
		return vertices;
	}

	// 2. IMPLEMENTATION METHOD numEdges:
	public int numEdges() 
	{
		int numEdges = 0;
        if(directed)//if directed graph checks every combination of vertex relationships using hasEdge()
        {
        	for(int i = 0 ; i<vertices ; i++)
        	{
        		for(int ii = 0 ; ii<vertices ; ii++)
        		{
        			if(hasEdge(i, ii))
        			{
        				numEdges++;
        			}
        		}
        	}
        }
        else//otherwise is undirected graph, therefore use the first theorem of graph theory
        {
        	int totalDegrees = 0;
        	for(int i=0 ; i<vertices ; i++)
        	{
        		totalDegrees += getDegree(i);
        	}
        	numEdges = totalDegrees/2;
        }
        return numEdges;
	}
 
	//  3. IMPLEMENTATION METHOD addEdge:
	public void addEdge(int v1, int v2, int w)
	{
		if(v1 >= vertices || v2 >= vertices)//if either input vertex out of range output error
		{
			System.out.println("Invalid vertex input [" + v1 + ", " + v2 + "]");
			return;
		}
		if(!hasEdge(v1, v2))//if the edge does not already exist create it
		{
			adjListArray[v1].push(new Edge(v2, w));
			if(!directed && v1 != v2)//if the graph is undirected add another edge to the LinkedList of the other vertex, unless its a loop (to prevent duplication)
			{
				adjListArray[v2].push(new Edge(v1, w));
			}
		}
		else//otherwise change the weight of the already existing edge
		{
			for(Edge currentEdge : adjListArray[v1])//find the edge pointing to v2 in v1's LinkedList then change its weight
			{
				if(currentEdge.getVertex() == v2)
				{
					currentEdge.setWeight(w);
					break;
				}
			}
			if(!directed)//if its an undirected graph do the same for the other edge pointing to v1 in v2's LinkedList
			{
				for(Edge currentEdge : adjListArray[v2])
				{
					if(currentEdge.getVertex() == v1)
					{
						currentEdge.setWeight(w);
						break;
					}
				}
			}
		}
	}

	// 4. IMPLEMENTATION METHOD removeEdge: 
	public void removeEdge(int v1, int v2)
	{
		if(v1 >= vertices || v2 >= vertices)//if either vertex is out of range output error
		{
			System.out.println("Invalid vertex input [" + v1 + ", " + v2 + "]");
			return;
		}
		for(Edge currentEdge : adjListArray[v1])//check every edge coming from v1 until a match is found with the destination v2, when found delete
		{
			if(currentEdge.getVertex() == v2)
			{
				adjListArray[v1].remove(currentEdge);
				if(!directed && hasEdge(v2, v1))//if undirected graph remove the edge facing the opposite direction, as long as that edge exists (to prevent loop and crash)
				{
					removeEdge(v2, v1);
				}
				break;
			}
		}
	}

	// 5. IMPLEMENTATION METHOD hasEdge:
	public boolean hasEdge(int v1, int v2)
	{
		for(Edge currentEdge : adjListArray[v1])//for every edge coming from v1 until a match is found facing v2
		{
			if(currentEdge.getVertex() == v2)//if found return true
			{
				return true;
			}
		}
		return false;//otherwise return false (no matching edge)
	}

	// 6. IMPLEMENTATION METHOD getWeightEdge:
	public int getWeightEdge(int v1, int v2) 
	{
		if(hasEdge(v1, v2))//checks if the edge exists
		{
			for(Edge currentEdge : adjListArray[v1])//locate the edge
			{
				if(currentEdge.getVertex() == v2)//when located get weight and return it
				{
					return currentEdge.getWeight();
				}
			}
		}
		return 0;//if no match is found return 0
	}

	// 7. IMPLEMENTATION METHOD getNeighbors:
	public LinkedList getNeighbors(int v)
	{
		LinkedList<String> neighbors = new LinkedList<String>();
		for(int i=0 ; i<vertices ; i++)//for every vertex
		{
			if(hasEdge(v, i) && hasEdge(i, v))//if they both have edges going going in and out to each other
			{
				neighbors.add(String.valueOf(i));//add the vertex to the linked list
			}
		}
		return neighbors;
	}

	// 8. IMPLEMENTATION METHOD getDegree:
	public int getDegree(int v)
	{
		int degree = 0;
		if(!directed)//if undirected
		{
			for(int i=0 ; i<vertices ; i++)//for every vertex check if it has an edge connecting them to v
			{
				if(hasEdge(v, i))//if they do have an edge connecting them
				{
					degree++;//increase degree of v by 1
					if(v == i)//if its a loop then increase it again
					{
						degree++;
					}
				}
			}
		}
		else//otherwise is directed
		{
			for(int i=0 ; i<vertices ; i++)//for every vertex
			{
				if(hasEdge(v, i))//if it has an edge  going out of v increase degree
				{
					degree++;
				}
				if(hasEdge(i, v))//if it has an edge going into v increase degree
				{
					degree++;
				}
			}
		}
	   return degree;
	}

	// 9. IMPLEMENTATION METHOD toString:
	public String toString() 
	{
		String output = "";
   		for(int i = 0 ; i<vertices ; i++)
   		{
   			output += i + " - ";
   			Object[] matrix = adjListArray[i].toArray();
   			output += Arrays.toString(matrix);
   			if(i!=vertices-1)
   			{
   				output += "\n";
   			}
   		}
   		return output;
	}
}


