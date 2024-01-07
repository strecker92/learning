import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.Dimension;



public class SnakeGame extends JPanel implements ActionListener, KeyListener{


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX !=-1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
    int boardWidth;
    int boardHeigth;
    int tileSize = 25;
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Tile food;

    Timer gameLoop;
    int velocityX;
    int velocityY;

    boolean gameOver = false;

    Random random;

    SnakeGame(int boardWidth, int boardHeigth) {
        this.boardWidth = boardWidth;
        this.boardHeigth = boardHeigth;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeigth));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();

    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    private void draw(Graphics g) {
//        //Grid
//        for (int i = 0; i < boardWidth/tileSize ; i++) {
//            g.drawLine(i*tileSize, 0, i*tileSize, boardHeigth);
//            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
//
//        }
        //Snake Head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x *tileSize,snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x *tileSize,snakeHead.y*tileSize, tileSize, tileSize, true);

        //Snake Body
        for (int i = 0; i < snakeBody.size() ; i++) {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //food
        g.setColor(Color.red);
        g.fillRect(food.x *tileSize,food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x *tileSize,food.y*tileSize, tileSize, tileSize, true);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize -16,tileSize);
        }



    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeigth/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body
        for (int i =snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
                }

        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //GameOver condition
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //collide with the snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }

        }

        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
                snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeigth) {
            gameOver = true;
        }




    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }
}
