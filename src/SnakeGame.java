import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile (int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeidht;
    int tileSize = 25; 
 //Snake
    Tile snakeHead;
    ArrayList<Tile>snakeBody;
    //Food for the snake
    Tile food;
    Random random; //an object for the random placing of the food

    //game logic
    Timer gameLoop;
    int velocityX; //The movement of the snake
    int velocityY;
    boolean gameOver = false;


    SnakeGame(int boardHeidht, int boardWidth){
        this.boardWidth = boardWidth;
        this.boardHeidht = boardHeidht;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeidht));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile (5,5); //default new starting place
        snakeBody = new ArrayList<Tile>();
        food = new Tile (10,10);
        random = new Random();
        placeFood ();

        velocityX = 0;
        velocityY = 0;


        gameLoop = new Timer (500, this); //1000 milliseconds
        gameLoop.start();
    }

    public void paintComponent (Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //Grid
        for(int i = 0;i<boardWidth/tileSize;i++){
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeidht);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);

        }
        //Food for the snake

        g.setColor(Color.yellow);
        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        //Snake Head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);

        //Snake Body
        for (int i = 0; i< snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
        }
        //score
        g.setFont(new Font ("Arial", Font.PLAIN, 16));
            if (gameOver){
                g.setColor(Color.red);
                g.drawString("Game Over, you looser, your score is only: " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
            }
             else {
                g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
             }
    }

public void placeFood (){
    food.x = random.nextInt(boardWidth/tileSize);//600/25=24
    food.y = random.nextInt(boardHeidht/tileSize);

}
//function to detect collision between snak and a food
public boolean collision(Tile tile1, Tile tile2){
    return tile1.x == tile2.x && tile1.y == tile2.y;
}

public void move (){
    //eating the food
    if (collision(snakeHead, food)){
        snakeBody.add(new Tile(food.x, food.y));
        placeFood();
    }
    //Snake body
    for (int i = snakeBody.size()-1; i>=0; i--){
        Tile snakePart = snakeBody.get(i);
        //the first member of the snake body, the first after the snake's head
        if (i==0){
            snakePart.x = snakeHead.x;
            snakePart.y = snakeHead.y;
        }
        else {
            Tile prevSnakePart = snakeBody.get(i-1);
            snakePart.x = prevSnakePart.x;
            snakePart.y = prevSnakePart.y;
        }
    }
    //snake head
    snakeHead.x +=velocityX;
    snakeHead.y +=velocityY;

    //game over condition
    for (int i = 0; i < snakeBody.size(); i++){
        Tile snakePart = snakeBody.get(i);
        //collide with the snake head
        if (collision(snakeHead, snakePart)){
            gameOver = true;
        }

    }
    if(snakeHead.x*tileSize <0 || snakeHead.x*tileSize > boardWidth ||
       snakeHead.y*tileSize <0 || snakeHead.y*tileSize > boardHeidht){
        gameOver = true;
       }
}

@Override
public void actionPerformed(ActionEvent e) {
  move(); //updates x and y positions of the snake
  repaint();//repaints the board
  if (gameOver){
    gameLoop.stop();//stopping the game;
  }
}

@Override
public void keyPressed(KeyEvent e) {
    if (e.getKeyCode()==KeyEvent.VK_UP && velocityY !=1){
        velocityX = 0;
        velocityY = -1;
    }
    else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
        velocityX = 0;
        velocityY = 1;
    }
    else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
        velocityX = -1;
        velocityY = 0;
    }
    else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
        velocityX = 1;
        velocityY = 0;
    }
}

//do not need
@Override
public void keyTyped(KeyEvent e) {}

@Override
public void keyReleased(KeyEvent e) {}

}
