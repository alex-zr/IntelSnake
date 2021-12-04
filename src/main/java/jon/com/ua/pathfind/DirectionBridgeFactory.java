package jon.com.ua.pathfind;

import jon.com.ua.Field;
import jon.com.ua.Fruit;
import jon.com.ua.Snake;
import jon.com.ua.pathfind.dijkstra1.DijDirectionBridge;
import jon.com.ua.pathfind.dijkstra2.DijkstraDirectionBridge;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 8/21/13
 */
public class DirectionBridgeFactory {
    private Field field;
    private Snake snake;
    private List<Fruit> fruits;
    private JFrame main;

    public DirectionBridgeFactory(JFrame main, Field field, Snake snake, List<Fruit> fruits) {
        this.field = field;
        this.snake = snake;
        this.fruits = fruits;
        this.main = main;
    }

    public DirectionBridge getKeyboardController() {
        return new KeyboardController(main);
    }

    public DirectionBridge getDijBridge() {
        return new DijDirectionBridge(snake, fruits, field);
    }

    public DirectionBridge getDijkstraBridge() {
        return new DijkstraDirectionBridge(snake, fruits, field);
    }

    public DirectionBridge getSimpleDirectionBridge() {
        return new SimpleDirectionBridge(snake, fruits);
    }
}
