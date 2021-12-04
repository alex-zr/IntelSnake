package jon.com.ua.pathfind.dijkstra1;

import jon.com.ua.Element;
import jon.com.ua.Field;
import jon.com.ua.Snake;

import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 8/21/13
 */
public class Util {
    public static int size = 6;
//    public static final int arr[][] = new int[size][size];

    public static int[][] readFile() {
        int arr[][] = new int[size][size];
        try {
            RandomAccessFile f = new RandomAccessFile("array.dat", "r"); //файл с матрицей
            String s;
            int a[] = new int[size * size];
            int j = 0, i = 0, p = 0;
            while ((s = f.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(s);
                while (st.hasMoreTokens()) {
                    int k = Integer.parseInt(st.nextToken());
                    a[p] = k;
                    System.out.print(a[p] + " ");
                    p++;
                }
                System.out.println();
            }
            f.close();
            p = 0;
            for (i = 0; i < arr.length; i++) {
                for (j = 0; j < arr.length; j++) {
                    arr[i][j] = a[p];
                    p++;
                }
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        return arr;
    }

    public static int printPath(int[] map, int point) {
        StringBuilder result = new StringBuilder();
        while (true) {
            result.insert(0, point);
            point = map[point];
            if (point < 0) {
                break;
            }
            result.insert(0, ">");
        }
        System.out.print(result);
        return point;
    }

    public static Graph oldCreateGraph(int arr[][], int n) {
        Graph graph = new Graph(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] != 0) {
                    graph.addEdge(i, j, arr[i][j]);
                }
            }
        }
        return graph;
    }

    public static Graph createGraph(Snake snake, Field field) {
        Graph graph = new Graph(field.getWidth() * field.getHeight());
        Element head = snake.getLead();
        int headVertex = matrToVect(field, new Dimension(head.getX(), head.getY()));

        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                Element currentField = new Element(Color.BLACK, "", i, j);
//                addEdgeIfPossible(graph, snake, field, currentField, currentField);
/*
                boolean isSnake = snake.isBody(currentField);
                boolean isHead = snake.isCollision(currentField, head);
                if (!isSnake || isHead) {
                    graph.addEdge(i, j, 1);
                }
*/
                Element upField = getUpField(field, currentField);
                addEdgeIfPossible(graph, snake, field, currentField, upField);
                Element downField = getDownField(field, currentField);
                addEdgeIfPossible(graph, snake, field, currentField, downField);
                Element leftField = getLeftField(field, currentField);
                addEdgeIfPossible(graph, snake, field, currentField, leftField);
                Element rightField = getRightField(field, currentField);
                addEdgeIfPossible(graph, snake, field, currentField, rightField);
            }
        }
        return graph;
    }

    private static Element getRightField(Field field, Element element) {
        Element res = new Element(Color.BLACK, "", element.getX() + 1, element.getY());
        if (!field.isInBounds(res)) {
            return null;
        }
        return res;
    }

    private static Element getLeftField(Field field, Element element) {
        Element res = new Element(Color.BLACK, "", element.getX() - 1, element.getY());
        if (!field.isInBounds(res)) {
            return null;
        }
        return res;
    }

    private static Element getDownField(Field field, Element element) {
        Element res = new Element(Color.BLACK, "", element.getX(), element.getY() + 1);
        if (!field.isInBounds(res)) {
            return null;
        }
        return res;
    }

    private static Element getUpField(Field field, Element element) {
        Element res = new Element(Color.BLACK, "", element.getX(), element.getY() - 1);
        if (!field.isInBounds(res)) {
            return null;
        }
        return res;
    }

    private static boolean addEdgeIfPossible(Graph graph, Snake snake, Field field, Element from, Element to) {
        if (to == null) {
            return false;
        }
        boolean res = false;

        boolean isSnake = snake.isBody(to);
        boolean isHead = snake.isCollision(to, snake.getLead());
        int fromVertex = matrToVect(field, from);
        int toVertex = matrToVect(field, to);
        if (!isSnake || isHead) {
            graph.addEdge(fromVertex, toVertex, 1);
            res = true;
        }
        return res;
    }

    public static int matrToVect(Field field, Element elem) {
        return matrToVect(field, new Dimension(elem.getX(), elem.getY()));
    }

    public static int matrToVect(Field field, Dimension dim) {
        return (int) (dim.getHeight() * field.getWidth() + dim.getWidth());
    }

    public static Element vectToMatr(Field field, int vertex) {
        int y = vertex / field.getWidth();
        int x = vertex % field.getWidth();
        return new Element(Color.BLACK, "", x, y);
    }
}
