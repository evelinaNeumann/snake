import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel {
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

    Tile snakeHead;

    SnakeGame(int boardHeidht, int boardWidth){
        this.boardWidth = boardWidth;
        this.boardHeidht = boardHeidht;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeidht));
        setBackground(Color.black);

        snakeHead = new Tile (5,5); //default new starting place
    }

    public void paintComponent (Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //Snake
        g.setColor(Color.green);
        g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
    }

}
