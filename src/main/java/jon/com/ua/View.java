package jon.com.ua;

import jon.com.ua.pathfind.DirectionBridge;
import jon.com.ua.pathfind.DirectionBridgeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 1/23/13
 */
public class View extends javax.swing.JPanel {
    public static final int FIELD_WIDTH = 10;
    public static final int FIELD_HEIGHT = 10;
    public static final int SNAKE_LENGTH = 2;
    public static final Color FIELD_COLOR = Color.LIGHT_GRAY;
    public static int DELAY = 250;
    private static final int GAME_OVER_DELAY = 3000;

    private final Field field = new Field(FIELD_WIDTH, FIELD_HEIGHT);
    private final Snake snake = new Snake(SNAKE_LENGTH);
    private final List<Fruit> fruits = new ArrayList<>();
    private final Random rnd = new Random();
    private String textOnCenter;
    private DirectionBridge controller;
    private int score;
    private boolean isPause;
    private PlaySound playSound;

    public View(JFrame main) {
        controller = new DirectionBridgeFactory(main, field, snake, fruits).getDijkstraBridge();
//        controller = new DirectionBridgeFactory(main, field, snake, fruits).getKeyboardController();
        putFruit();
        // For disable background sound
        new PlayBackground().start();
        main.addKeyListener(new KeyAdapter() {
            private int backFramePosition;
            private int framePosition;
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isPause = !isPause;
                    if (isPause) {
                        backFramePosition = PlayBackground.clip.getFramePosition();
                        PlayBackground.clip.stop();
                        framePosition = PlaySound.clip.getFramePosition();
                        PlaySound.clip.stop();
                    } else {
                        PlayBackground.clip.setFramePosition(backFramePosition);
                        PlayBackground.clip.start();
                        PlaySound.clip.setFramePosition(framePosition);
                        PlaySound.clip.start();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    DELAY+= 50;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && DELAY >= 50) {
                    DELAY-= 50;
                }
                System.out.println("press");
            }
        });
        new Thread() {
            @Override
            public void run() {
                while (!interrupted()) {
                    if (isPause) {
                        continue;
                    }
                    View.this.sleep(DELAY);
                    setSnakeDirection();
                    setSnakePathToTarget();
                    snake.move();
                    Fruit fruit = checkSnakeEatedFruit();
                    if (snake.getBittenItself() != null) {
                        gameOver();
                    }
                    //checkSnakeEatedItself();
                    repaint();
                    checkSnakeDead();
                }
            }
        }.start();
    }

    private void setSnakeDirection() {
        snake.setDirection(controller.getDirection());
    }

    private void setSnakePathToTarget() {
        snake.setPath(controller.getPath());
    }

    private void checkSnakeEatedItself() {
        if (snake.getBittenItself() != null) {
            gameOver();
        }
    }

    private Fruit checkSnakeEatedFruit() {
        Fruit eatedFruit = tryToEatFruitAndGet();
        if (eatedFruit != null) {
            incraseScore(eatedFruit.getSCORE());
            removeEatedFruit(eatedFruit);
            putFruit();
            snake.grow();
            // For disable event sound

            playSound = new PlaySound();
            playSound.start();
        }
        return null;
    }

    private void removeEatedFruit(Fruit eatedFruit) {
        Iterator<Fruit> itr = fruits.iterator();
        while (itr.hasNext()) {
            Fruit fruit = itr.next();
            if (fruit.equals(eatedFruit)) {
                itr.remove();
            }
        }
    }

    private void incraseScore(int score) {
        this.score += score;
    }

    private Fruit tryToEatFruitAndGet() {
        for (Fruit fruit : fruits) {
            for (Element head : snake.getHeads()) {
                if (fruit.getX() == head.getX() && fruit.getY() == head.getY()) {
                    return fruit;
                }
            }
        }
        return null;
    }

    private void putFruit() {
        Fruit fruit = null;
        do {
            int rndX = rnd.nextInt(field.getWidth());
            int rndY = rnd.nextInt(field.getHeight());
            fruit = new Fruit(rndX, rndY);
        } while (snake.isBody(fruit));

        fruits.add(fruit);
    }

    private void checkSnakeDead() {
        textOnCenter = null;
        Element head = snake.getHeads().peekFirst();
        if (!MatrixUtil.isInBounds(head, field.getWidth(), field.getHeight())) {
//        if (field.isXOutOfBounds(head.getX()) || field.isYOutOfBounds(head.getY())) {
            gameOver();
        }
    }

    private void gameOver() {
//        snake.hide();
        textOnCenter = "Game over";
        System.out.println("Snake direction: " + snake.getDirection());
        repaint();
        // TODO: correct
        sleep(GAME_OVER_DELAY);
        score = 0;
        controller.setDirection(Direction.RIGHT);
        fruits.clear();
        snake.init(SNAKE_LENGTH);
        putFruit();
    }

    private void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }

    private static void initWindow() {
        JFrame main = new JFrame();
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final View view = new View(main);
        //setController(main, view.snake);

        main.setContentPane(view);
        main.setBounds(100, 100, 595, 600);
        main.setVisible(true);
    }

/*
    private static void setController(JFrame main, final Snake snake) {
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.setOldDirection(snake.getDirection());

                if (e.getKeyCode() == KEY_LEFT && snake.getOldDirection() != Direction.RIGHT) {
                    snake.setDirection(Direction.LEFT);
                }

                if (e.getKeyCode() == KEY_RIGHT && snake.getOldDirection() != Direction.LEFT) {
                    snake.setDirection(Direction.RIGHT);
                }

                if (e.getKeyCode() == KEY_UP && snake.getOldDirection() != Direction.DOWN) {
                    snake.setDirection(Direction.UP);
                }

                if (e.getKeyCode() == KEY_DOWN && snake.getOldDirection() != Direction.UP) {
                    snake.setDirection(Direction.DOWN);
                }
            }
        });
    }
*/

    public void paintComponent(Graphics g) {
        int cellHeight = getHeight() / field.getHeight();
        int cellWidth = getWidth() / field.getWidth();
        clearScreen(g);
        paintGrid(g, cellHeight, cellWidth);
        snake.paint(g, cellHeight, cellWidth);
        paintFruits(g, cellHeight, cellWidth);
        snake.paintPath(g, cellHeight, cellWidth);
        paintCenterText(g);
        paintScore(g);
        paintDelay(g);
    }

    private void paintScore(Graphics g) {
        Color tmpColor = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 50, 50);
        g.setColor(tmpColor);
    }

    private void paintDelay(Graphics g) {
        Color tmpColor = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Delay: " + DELAY, 50, 70);
        g.setColor(tmpColor);
    }

    private void paintFruits(Graphics g, int cellHeight, int cellWidth) {
        for (Fruit fruit : fruits) {
            fruit.paint(g, cellHeight, cellWidth);
        }
    }

    private void paintCenterText(Graphics g) {
        if (textOnCenter == null) {
            return;
        }
        Rectangle clipBounds = g.getClipBounds();
        int fintSize = 30;
        g.setFont(new Font("Arial", Font.PLAIN, fintSize));
        Color tmpColor = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString(textOnCenter, (int) (clipBounds.getWidth() / 2) - textOnCenter.length() / 2 * 30, (int) (clipBounds.getHeight() / 2));
        g.setColor(tmpColor);
    }


    private void paintGrid(Graphics g, int cellHeight, int cellWidth) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < field.getHeight(); i++) {
            g.drawLine(0, y, getWidth(), y);
            y += cellHeight;
        }
        for (int i = 0; i < field.getWidth(); i++) {
            g.drawLine(x, 0, x, getHeight());
            x += cellWidth;
        }
    }

    private void clearScreen(Graphics g) {
        g.setColor(FIELD_COLOR);
        g.fill3DRect(0, 0, getWidth(), getHeight(), true);
    }
}
