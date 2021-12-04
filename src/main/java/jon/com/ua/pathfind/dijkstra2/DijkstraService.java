package jon.com.ua.pathfind.dijkstra2;

import jon.com.ua.Element;
import jon.com.ua.Field;
import jon.com.ua.Snake;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 8/21/13
 */
public class DijkstraService {
    public static int size = 6;
//    public static final int arr[][] = new int[size][size];

    public static Vertex[][] createGraph(Snake snake, Field field) {

        Vertex[][] nodes = new Vertex[field.getHeight()][field.getWidth()]; //instance variable

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Element element = new Element(Color.WHITE, "empty", j, i);
                if (!snake.isBodyWithoutHead(element)) {
                    nodes[i][j] = new Vertex(element);
                }
            }
        }

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                if (nodes[i][j] != null) {
                    addNeighborEdges(nodes, i, j);
                }
            }
        }

//        sourceVertex = nodes[sourceVertex.getElement().getY()][sourceVertex.getElement().getX()];
        return nodes;
    }

    private static void addNeighborEdges(Vertex[][] nodes, int y, int x) {
        Vertex currentVertex = nodes[y][x];
        if (currentVertex != null) {
            if ((x - 1 >= 0 && x - 1 < nodes.length) && !currentVertex.getAdjacencies().contains(new Edge(nodes[y][x - 1], 1))) {
                currentVertex.getAdjacencies().add(new Edge(nodes[y][x - 1], 1));
            }
            if ((x + 1 >= 0 && x + 1 < nodes.length) && !currentVertex.getAdjacencies().contains(new Edge(nodes[y][x + 1], 1))) {
                currentVertex.getAdjacencies().add(new Edge(nodes[y][x + 1], 1));
            }
            if ((y - 1 >= 0 && y - 1 < nodes.length) && !currentVertex.getAdjacencies().contains(new Edge(nodes[y - 1][x], 1))) {
                currentVertex.getAdjacencies().add(new Edge(nodes[y - 1][x], 1));
            }
            if ((y + 1 >= 0 && y + 1 < nodes.length) && !currentVertex.getAdjacencies().contains(new Edge(nodes[y + 1][x], 1))) {
                currentVertex.getAdjacencies().add(new Edge(nodes[y + 1][x], 1));
            }
        }
    }

    public static Vertex createGraphOld(Snake snake, Field field, Vertex sourceVertex, Vertex destinationVertex) {
        Element head = snake.getLead();
        Vertex headVertex = new Vertex(head);

        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                //Element currentElement;
                Vertex currentVertex;
                if (isVertex(sourceVertex, i, j)) {
                    currentVertex = sourceVertex;
                } else if (isVertex(destinationVertex, i, j)) {
                    currentVertex = destinationVertex;
                } else {
                    currentVertex = new Vertex(i, j);
                }
                //Vertex currentVertex = new Vertex(currentElement);
                Vertex[][] verteces = new Vertex[field.getHeight()][field.getWidth()];
                Vertex upVertex = getUpVertex(field, currentVertex);

                addVertexIfPossible(verteces, snake, upVertex);
                Vertex downVertex = getDownVertex(field, currentVertex);
                addVertexIfPossible(verteces, snake, downVertex);
                Vertex leftVertex = getLeftVertex(field, currentVertex);
                addVertexIfPossible(verteces, snake, leftVertex);
                Vertex rightVertex = getRightVertex(field, currentVertex);
                addVertexIfPossible(verteces, snake, rightVertex);

                Compass compass = new Compass(upVertex, downVertex, leftVertex, rightVertex);
                addVertexAndEdgesIfPossible(verteces, snake, currentVertex, compass);
            }
        }

//        v0.adjacencies = new Edge[]{
//                new Edge(v1, 5),
//                new Edge(v2, 10),
//                new Edge(v3, 8)};
//        v1.adjacencies = new Edge[]{
//                new Edge(v0, 5),
//                new Edge(v2, 3),
//                new Edge(v4, 7)};
//        v2.adjacencies = new Edge[]{
//                new Edge(v0, 10),
//                new Edge(v1, 3)};
//        v3.adjacencies = new Edge[]{
//                new Edge(v0, 8),
//                new Edge(v4, 2)};
//        v4.adjacencies = new Edge[]{
//                new Edge(v1, 7),
//                new Edge(v3, 2)};
        return headVertex;//v0;
    }

    private static boolean isVertex(Vertex sourceVertex, int i, int j) {
        return sourceVertex.getElement().getX() == i &&
                sourceVertex.getElement().getY() == j;
    }


    private static boolean addVertexAndEdgesIfPossible(Vertex[][] vertexes, Snake snake, Vertex vertex, Compass compass) {
        if (vertex == null) {
            return false;
        }
        boolean res = false;

        boolean isSnake = snake.isBody(vertex);
        boolean isHead = snake.isCollision(vertex, snake.getLead());
        if (!isSnake || isHead) {
            vertex.getAdjacencies().addAll(compass.getNotNullDirections());
            res = true;
        }
        return res;
    }

    private static boolean addVertexIfPossible(Vertex[][] vertexes, Snake snake, Vertex vertex) {
        if (vertex == null) {
            return false;
        }
        boolean res = false;

        boolean isSnake = snake.isBody(vertex);
        boolean isHead = snake.isCollision(vertex, snake.getLead());
        boolean isInBounds = vertex.getElement().getX() < vertexes.length &&
                vertex.getElement().getY() < vertexes.length;
        if ((!isSnake || isHead) && isInBounds) {
            vertexes[vertex.getElement().getX()][vertex.getElement().getY()] = vertex;
            res = true;
        }
        return res;
    }


    private static Vertex getRightVertex(Field field, Vertex vertex) {
        Element element = vertex.getElement();
        Element newElem = new Element(Color.BLACK, "", element.getX() + 1, element.getY());
        if (!field.isInBounds(newElem)) {
            return null;
        }
        return new Vertex(newElem);
    }

    private static Vertex getLeftVertex(Field field, Vertex vertex) {
        Element element = vertex.getElement();
        Element newElem = new Element(Color.BLACK, "", element.getX() - 1, element.getY());
        if (!field.isInBounds(newElem)) {
            return null;
        }
        return new Vertex(newElem);
    }

    private static Vertex getDownVertex(Field field, Vertex vertex) {
        Element element = vertex.getElement();
        Element newElem = new Element(Color.BLACK, "", element.getX(), element.getY() + 1);
        if (!field.isInBounds(newElem)) {
            return null;
        }
        return new Vertex(newElem);
    }

    private static Vertex getUpVertex(Field field, Vertex vertex) {
        Element element = vertex.getElement();
        Element newElem = new Element(Color.BLACK, "", element.getX(), element.getY() - 1);
        if (!field.isInBounds(newElem)) {
            return null;
        }
        return new Vertex(newElem);
    }
}
