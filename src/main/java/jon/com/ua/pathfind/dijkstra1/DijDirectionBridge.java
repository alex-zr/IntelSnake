package jon.com.ua.pathfind.dijkstra1;

import jon.com.ua.*;
import jon.com.ua.pathfind.DirectionBridge;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 8/22/13
 */
public class DijDirectionBridge implements DirectionBridge {
    private Dij dijkstra = new Dij();
    //private Direction direction;
    private Snake snake;
    private List<Fruit> fruits;
    private Field field;

    public DijDirectionBridge(Snake snake, List<Fruit> fruits, Field field) {
        this.snake = snake;
        this.fruits = fruits;
        this.field = field;
    }

    @Override
    public Direction getDirection() {
        Graph g = Util.createGraph(snake, field);
        long[] path = new long[g.n];
        int[] pred = new int[g.n];
        int s = Util.matrToVect(field, snake.getLead());
        Element sourceElem = fruits.get(0);
        int t = Util.matrToVect(field, sourceElem);
        int z = dijkstra.dijkstra(g, s, t, path, pred);
        System.out.println("Shortest path: " + path[z]);
        int targetVertex = Util.printPath(pred, z);
        Element targetElement = Util.vectToMatr(field, targetVertex);
        Direction direction = calcDirectionByVerteces(sourceElem, targetElement);
        return direction;
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
