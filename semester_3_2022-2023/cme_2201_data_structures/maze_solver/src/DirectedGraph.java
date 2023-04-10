package GraphPackage;

import java.util.Iterator;

// Classes that implement various ADTs
import ADTPackage.DictionaryInterface;
import ADTPackage.HeapPriorityQueue;
import ADTPackage.LinkedQueue;
import ADTPackage.LinkedStack;
import ADTPackage.PriorityQueueInterface;
import ADTPackage.QueueInterface;
import ADTPackage.StackInterface;
import ADTPackage.UnsortedLinkedDictionary;

/**
 * A class that implements the ADT directed graph.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.1
 */
public class DirectedGraph<T> implements GraphInterface<T> {
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;

	public DirectedGraph() {
		vertices = new UnsortedLinkedDictionary<>();
		edgeCount = 0;
	} // end default constructor

	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null; // Was addition to dictionary successful?
	} // end addVertex

	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean result = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		if ((beginVertex != null) && (endVertex != null))
			result = beginVertex.connect(endVertex, edgeWeight);
		if (result)
			edgeCount++;
		return result;
	} // end addEdge

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	} // end addEdge

	public boolean hasEdge(T begin, T end) {
		boolean found = false;
		VertexInterface<T> beginVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);
		if ((beginVertex != null) && (endVertex != null)) {
			Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
			while (!found && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor))
					found = true;
			} // end while
		} // end if

		return found;
	} // end hasEdge

	public boolean isEmpty() {
		return vertices.isEmpty();
	} // end isEmpty

	public void clear() {
		vertices.clear();
		edgeCount = 0;
	} // end clear

	public int getNumberOfVertices() {
		return vertices.getSize();
	} // end getNumberOfVertices

	public int getNumberOfEdges() {
		return edgeCount;
	} // end getNumberOfEdges

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		} // end while
	} // end resetVertices

	public StackInterface<T> getTopologicalOrder() {
		resetVertices();

		StackInterface<T> vertexStack = new LinkedStack<>();
		int numberOfVertices = getNumberOfVertices();
		for (int counter = 1; counter <= numberOfVertices; counter++) {
			VertexInterface<T> nextVertex = findTerminal();
			nextVertex.visit();
			vertexStack.push(nextVertex.getLabel());
		} // end for

		return vertexStack;
	} // end getTopologicalOrder

	protected VertexInterface<T> findTerminal() {
		boolean found = false;
		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

		while (!found && vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();

			// If nextVertex is unvisited AND has only visited neighbors)
			if (!nextVertex.isVisited()) {
				if (nextVertex.getUnvisitedNeighbor() == null) {
					found = true;
					result = nextVertex;
				} // end if
			} // end if
		} // end while

		return result;
	} // end findTerminal

	// Used for testing
	public void displayEdges() {
		System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
		System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			((Vertex<T>) (vertexIterator.next())).display();
		} // end while
	} // end displayEdges

	private class EntryPQ implements Comparable<EntryPQ> {
		private VertexInterface<T> vertex;
		private VertexInterface<T> previousVertex;
		private double cost; // cost to nextVertex

		private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex) {
			this.vertex = vertex;
			this.previousVertex = previousVertex;
			this.cost = cost;
		} // end constructor

		public VertexInterface<T> getVertex() {
			return vertex;
		} // end getVertex

		public VertexInterface<T> getPredecessor() {
			return previousVertex;
		} // end getPredecessor

		public double getCost() {
			return cost;
		} // end getCost

		public int compareTo(EntryPQ otherEntry) {
			// Using opposite of reality since our priority queue uses a maxHeap;
			// could revise using a minheap
			return (int) Math.signum(otherEntry.cost - cost);
		} // end compareTo

		public String toString() {
			return vertex.toString() + " " + cost;
		} // end toString
	} // end EntryPQ

	// love penguins
	// (o<    (o<    (o<
 	// //\    //\    //\
 	// V_/_   V_/_   V_/_ 

	@Override
	public QueueInterface<T> getBreadthFirstTraversal(T origin, T end) {
		resetVertices();
		VertexInterface<T> originVertex = vertices.getValue(origin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

		originVertex.visit();
		traversalOrder.enqueue(origin);
		vertexQueue.enqueue(originVertex);
		while (!vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			while (neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					traversalOrder.enqueue(nextNeighbor.getLabel());
					vertexQueue.enqueue(nextNeighbor);
					if (nextNeighbor.equals(endVertex)) {
						return traversalOrder;
					}
				}
			}
		}

		System.out.println("Couldn't find the vertex " + end + " from the vertex " + origin + "!\n");
		return null;
	}

	@Override
	public QueueInterface<T> getDepthFirstTraversal(T origin, T end) {
		resetVertices();
		VertexInterface<T> originVertex = vertices.getValue(origin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		QueueInterface<T> traversalOrder = new LinkedQueue<>();
		StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();

		originVertex.visit();
		traversalOrder.enqueue(origin);
		vertexStack.push(originVertex);
		while (!vertexStack.isEmpty()) {
			VertexInterface<T> topVertex = vertexStack.peek();
			VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();
			if (nextNeighbor != null) {
				nextNeighbor.visit();
				traversalOrder.enqueue(nextNeighbor.getLabel());
				vertexStack.push(nextNeighbor);
				if (nextNeighbor.equals(endVertex)) {
					return traversalOrder;
				}
			} else {
				vertexStack.pop();
			}
		}

		System.out.println("Couldn't find the vertex " + end + " from the vertex " + origin + "!\n");
		return null;
	}

	@Override
	public int getShortestPath(T begin, T end, StackInterface<T> path) {
		resetVertices();
		VertexInterface<T> originVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		boolean done = false;
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

		originVertex.visit();
		vertexQueue.enqueue(originVertex);
		while (!done && !vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			while (!done && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					nextNeighbor.setCost(1 + frontVertex.getCost());
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.enqueue(nextNeighbor);
				}
				if (nextNeighbor.equals(endVertex)) {
					done = true;
				}
			}
		}

		double pathLength = endVertex.getCost();
		path.push(endVertex.getLabel());

		VertexInterface<T> vertex = endVertex;
		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}

		return (int) pathLength;
	}

	@Override
	public double getCheapestPath(T begin, T end, StackInterface<T> path) {
		resetVertices();
		VertexInterface<T> originVertex = vertices.getValue(begin);
		VertexInterface<T> endVertex = vertices.getValue(end);

		boolean done = false;
		PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<>();

		priorityQueue.add(new EntryPQ(originVertex, 0, null));
		while (!done && !priorityQueue.isEmpty()) {
			EntryPQ frontEntry = priorityQueue.remove();
			VertexInterface<T> frontVertex = frontEntry.getVertex();
			if (!frontVertex.isVisited()) {
				frontVertex.visit();
				frontVertex.setCost(frontEntry.getCost());
				frontVertex.setPredecessor(frontEntry.getPredecessor());
			}

			if (frontVertex.equals(endVertex)) {
				done = true;
			} else {
				Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
				Iterator<Double> weightIterator = frontVertex.getWeightIterator();
				while (neighbors.hasNext()) {
					VertexInterface<T> neighbor = neighbors.next();
					Double weightOfEdgeToNeighbor = weightIterator.next();
					if (!neighbor.isVisited()) {
						Double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
						priorityQueue.add(new EntryPQ(neighbor, nextCost, frontVertex));
					}
				}
			}
		}

		double pathCost = endVertex.getCost();
		path.push(endVertex.getLabel());

		VertexInterface<T> vertex = endVertex;
		while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
		}
		return pathCost;
	}

	// just for print use case beacuse
	// the graph is implemented as adjancency list
	public void printAdjancencyMatrix() {
		// create matrix
		int[][] matrix = new int[getNumberOfVertices()][getNumberOfVertices()];
		DictionaryInterface<T, Integer> indexes = new UnsortedLinkedDictionary<>();

		Iterator<T> it = vertices.getKeyIterator();
		int index = getNumberOfVertices() - 1;
		while (it.hasNext()) {
			indexes.add(it.next(), index);
			index--;
		}

		Iterator<T> keyIt = indexes.getKeyIterator();
		Iterator<Integer> valIt = indexes.getValueIterator();
		while (keyIt.hasNext()) {
			Iterator<VertexInterface<T>> k = vertices.getValue(keyIt.next()).getNeighborIterator();
			int idex = valIt.next();
			while (k.hasNext()) {
				int indexOfNeighbor = indexes.getValue(k.next().getLabel());
				matrix[idex][indexOfNeighbor] = 1;
			}
		}
		// end matrix
		// adjancecy matrix contructed
		// could be split two function getAdjMatrix, printAdjMatrix

		String[] names = new String[getNumberOfVertices()];
		keyIt = indexes.getKeyIterator();
		valIt = indexes.getValueIterator();
		while (keyIt.hasNext()) {
			names[valIt.next()] = (String) keyIt.next();
		}

		// print matrix
		System.out.println("Adjancency Matrix");
		System.out.format("%-3s ", "");
		for (int k = 0; k < matrix.length; k++) {
			System.out.format("%-3s ", names[k]);
		}
		System.out.println();

		for (int k = 0; k < matrix.length; k++) {
			System.out.format("%-3s ", names[k]);
			for (int k2 = 0; k2 < matrix.length; k2++) {
				System.out.format("%-3s ", matrix[k][k2]);

			}
			System.out.println();
		}

	}

	// counts visited vertices
	public int getNumOfVisited() {
		int counter = 0;
		Iterator<VertexInterface<T>> it = vertices.getValueIterator();
		while (it.hasNext()) {
			if (it.next().isVisited())
				counter++;
		}
		return counter;
	}

} // end DirectedGraph