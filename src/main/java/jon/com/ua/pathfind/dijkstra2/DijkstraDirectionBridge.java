package jon.com.ua.pathfind.dijkstra2;

import jon.com.ua.*;
import jon.com.ua.pathfind.DirectionBridge;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 8/23/13
 */
public class DijkstraDirectionBridge implements DirectionBridge {
    private Dijkstra dijkstra = new Dijkstra();
    //private Direction direction;
    private Snake snake;
    private List<Fruit> fruits;
    private Field field;
    private List<Vertex> path;

    public DijkstraDirectionBridge(Snake snake, List<Fruit> fruits, Field field) {
        this.snake = snake;
        this.fruits = fruits;
        this.field = field;
    }

    @Override
    public Direction getDirection() {
        Element destinationElement = fruits.get(0);
        Element sourceElement = snake.getLead();

        Vertex[][] nodes = DijkstraService.createGraph(snake, field);
        Vertex sourceVertex = nodes[sourceElement.getY()][sourceElement.getX()];
        // TODO some times null
        Vertex destinationVertex = nodes[destinationElement.getY()][destinationElement.getX()];
        //Vertex destinationVertex = DijkstraService.elementToVertex(field, destinationElement);
        dijkstra.computePaths(sourceVertex);
        path = dijkstra.getShortestPathTo(destinationVertex);
//        path.add(0, sourceVertex);
//        path.add(destinationVertex);
        System.out.println("Path: " + path);
           //       Element targetElement = DijkstraService.vectToMatr(field, targetVertex);
        Direction direction = calcDirectionByPath(path);
        return direction;
    }

    @Override
    public List<Vertex> getPath() {
        return path;
    }

    private Direction calcDirectionByPath(List<Vertex> path) {
        // TODO path is empty
        if (path.size() < 2) {
            return Direction.UP;
        }
        Element sourceElement = path.get(0).getElement();
        Element targetElement = path.get(1).getElement();
        Direction res;
        if (targetElement.getX() > sourceElement.getX()) {
            res = Direction.RIGHT;
        } else if (sourceElement.getX() > targetElement.getX()) {
            res = Direction.LEFT;
        } else if (targetElement.getY() > sourceElement.getY()) {
            res = Direction.DOWN;
        } else {
            res = Direction.UP;
        }
        System.out.println("Snake: " + snake);
        System.out.println("Source element: " + sourceElement);
        System.out.println("Destination element: " + targetElement);
        System.out.println("Move: " + res);
        return res;
    }

    private Direction calcDirectionByVerteces(Element sourceElement, Element targetElement) {
        Direction res;
        if (targetElement.getX() > sourceElement.getX()) {
            res = Direction.RIGHT;
        } else if (sourceElement.getX() > targetElement.getX()) {
            res = Direction.LEFT;
        } else if (targetElement.getY() > sourceElement.getY()) {
            res = Direction.DOWN;
        } else {
            res = Direction.UP;
        }
        return res;
    }


    @Override
    public void setDirection(Direction direction) {
        //this.direction = direction;
    }
}
