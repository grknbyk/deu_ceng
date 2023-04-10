import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import ADTPackage.LinkedListWithIterator;
import ADTPackage.LinkedStack;
import ADTPackage.QueueInterface;
import ADTPackage.StackInterface;
import GraphPackage.UndirectedGraph;

public class Test {

    private static int width, height;
    private static String startPoint, endPoint;
    private static StringBuilder pureMaze;

    private static LinkedListWithIterator<String> getVerticesFromFile(String fileName) throws IOException {
        LinkedListWithIterator<String> vertices = new LinkedListWithIterator<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                pureMaze.append(line + "\n");
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {
                        vertices.add(height + "-" + i);
                        width = line.length();
                    }
                }
                height++;
            }
        } catch (Exception e) {
            System.out.println("getVerticesFromFile: " + e.toString());
        }

        endPoint = String.valueOf((height - 2) + "-" + (width - 1));

        return vertices;
    }

    private static UndirectedGraph constructGraphFromVertices(LinkedListWithIterator<String> vertices) {
        UndirectedGraph graph = new UndirectedGraph<>();
        Iterator<String> vertexIterator = vertices.getIterator();

        // adds vertices to graph
        while (vertexIterator.hasNext()) {
            String vertex = vertexIterator.next();
            graph.addVertex(vertex);
        }

        // adds edges
        vertexIterator = vertices.getIterator();
        while (vertexIterator.hasNext()) {
            String src = vertexIterator.next();
            String[] srcInf = src.split("-");
            int row = Integer.valueOf(srcInf[0]);
            int col = Integer.valueOf(srcInf[1]);

            // 0,1 1,0 -1,0 0,-1
            String[] possibleNeighbors = {
                    String.valueOf(row + "-" + (col - 1)),
                    String.valueOf(row + "-" + (col + 1)),
                    String.valueOf((row + 1) + "-" + col),
                    String.valueOf((row - 1) + "-" + col),
            };

            for (String neighbor : possibleNeighbors) {
                if (vertices.contains(neighbor)) {// if vertex exists
                    graph.addEdge(src, neighbor, (int) (Math.random() * 4 + 1));
                }
            }
        }

        return graph;
    }

    private static void printEscapePath(String mazeString, StackInterface<String> path) {
        StringBuilder maze = new StringBuilder(mazeString);

        while (!path.isEmpty()) {
            String[] yx = path.pop().split("-");
            int pos = 0; // position in string by vertexLabel
            pos += Integer.valueOf(yx[1]);
            pos += Integer.valueOf(yx[0]) * (width + 1); // +1 for '\n' character
            maze.setCharAt(pos, '.');
        }

        System.out.print(maze);
    }

    private static StackInterface<String> queueToStack(QueueInterface<String> queue) { // converts
        if (queue.isEmpty())
            return null;
        else {
            StackInterface<String> stack = new LinkedStack<>();
            while (!queue.isEmpty())
                stack.push(queue.dequeue());
            return stack;
        }

    }

    private static void test(String fileName, boolean[] filters) {
        try {
            // INITIALIZATION //
            pureMaze = new StringBuilder();
            startPoint = "0-1";
            endPoint = null; // assigned at reading file (getVerticesFromFile)
            width = 0; // changes at reading file (getVerticesFromFile)
            height = 0; // changes at reading file (getVerticesFromFile)
            LinkedListWithIterator<String> readMaze = getVerticesFromFile(fileName);
            UndirectedGraph tempMaze = constructGraphFromVertices(readMaze);

            // endPoint = "1-1"; for optional endPoint

            // TEST SECTION//
            if (filters[0]) {
                System.out.print("Adjancency List");
                tempMaze.displayEdges();
                System.out.println();
            }

            if (filters[1]) {
                tempMaze.printAdjancencyMatrix();
                System.out.println();
            }

            if (filters[2]) {
                System.out.println("The number of vertices: " + tempMaze.getNumberOfVertices());
            }

            if (filters[3]) {
                System.out.println("The number of edges: " + tempMaze.getNumberOfEdges() + "\n");
            }

            if (filters[4]) {
                QueueInterface<String> queue = tempMaze.getBreadthFirstTraversal(startPoint, endPoint);
                System.out.println("Breadthfirst search for " + startPoint + " to " + endPoint + ":");
                printEscapePath(pureMaze.toString(), queueToStack(queue));
                System.out.println("The number of visited vertices: " + tempMaze.getNumOfVisited() + "\n");
            }

            if (filters[5]) {
                QueueInterface<String> queue2 = tempMaze.getDepthFirstTraversal(startPoint, endPoint);
                System.out.println("DepthFirst search for " + startPoint + " to " + endPoint + ":");
                printEscapePath(pureMaze.toString(), queueToStack(queue2));
                System.out.println("The number of visited vertices: " + tempMaze.getNumOfVisited() + "\n");
            }

            if (filters[6]) {
                System.out.println("Shortest Path");
                StackInterface<String> sPath = new LinkedStack<>();
                tempMaze.getShortestPath(startPoint, endPoint, sPath);
                printEscapePath(pureMaze.toString(), sPath);
                System.out.println("The number of visited vertices: " + tempMaze.getNumOfVisited());
                System.out.println();
            }

            if (filters[7]) {
                System.out.println("Cheapest Path");
                StackInterface<String> cPath = new LinkedStack<>();
                Double cost = tempMaze.getCheapestPath(startPoint, endPoint, cPath);
                printEscapePath(pureMaze.toString(), cPath);
                System.out.println("The number of visited vertices: " + tempMaze.getNumOfVisited());
                System.out.format("The cost of the cheapest path: %.2f\n\n", cost);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        // end point assumed as right bottom
        // but you could change it on row 116

        // FILTERS for informations
        // set true to print the info,
        // false to not print the info
        boolean[] filters = {
                true, // adjancency list
                true, // adjancency matrix
                true, // number of vertices
                true, // number of edges
                true, // breadth first search
                true, // deep first search
                true, // shortest path
                true, // cheapest path
        };

        test("maze1.txt", filters);

    }
}