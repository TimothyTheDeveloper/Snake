package com.codegym.games.snake;
import java.util.List;
import java.util.ArrayList;
import com.codegym.engine.cell.*;


public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;
    
    public Snake(int x, int y) {
        //super(x, y);
        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x+1, y);
        GameObject third = new GameObject(x+2, y);
        
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }
    
    public void setDirection(Direction direction) {
        boolean cordX = snakeParts.get(0).x == snakeParts.get(1).x;
        boolean cordY = snakeParts.get(0).y == snakeParts.get(1).y;
        
        if (direction == Direction.LEFT && this.direction != Direction.RIGHT && cordX) {
            this.direction = direction;
        } else if (direction == Direction.RIGHT && this.direction != Direction.LEFT && cordX) {
            this.direction = direction;
        } else if (direction == Direction.UP && this.direction != Direction.DOWN && cordY) {
            this.direction = direction;
        } else if (direction == Direction.DOWN && this.direction != Direction.UP && cordY ) {
            this.direction = direction;
        }
    }
    
    
    public void move(Apple apple) {
         GameObject snakeHead = createNewHead();
         boolean check = checkCollision(snakeHead);
         if(check) {
             isAlive = false;
             return;
         }
        else if(snakeHead.x >= SnakeGame.WIDTH || snakeHead.x < 0 || snakeHead.y >= SnakeGame.HEIGHT
        || snakeHead.y < 0){
            isAlive = false;
        } 
        
        else if(apple.x == snakeHead.x && apple.y == snakeHead.y) {
            apple.isAlive = false;
            snakeParts.add(0 , snakeHead);
           /*
            GameObject pIA = snakParts.get(snakeParts.size() - 1); //position of last piece of snake in the ArrayList
            int xAdd = coordinateAdd(direction);
            int yAdd = coordinateAdd(direction);
            GameObject bodyPiece = new GameObject((pIA.x + xAdd), (pIA.y + yADD));
            snakeParts.add(bodyPiece);
            */
        } else if (isAlive == true) {
            snakeParts.add(0 , snakeHead);
           // snakeParts.add(1, createNewBody());
            removeTail();
        } 
        
        
    }
    
    public int getLength() {
        return snakeParts.size();
    }
    

/*
    
    private int coordinateAdd(Direction direction) {
        switch (direction) {
            case LEFT : 
                return 1;
                break;
            case RIGHT :
                return -1;
                break;
            case UP :
                return -1;
                break;
            case DOWN :
                return 1;
                break;
            default :
                return 0;
                break;
        }

            if(direction == Direction.RIGHT) {
               coordinate = -1; 
            } else if (direction == Direction.LEFT) {
                coordinate = 1;

    } */
    
    public GameObject createNewBody() {
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;
        
        GameObject newHead = null;
        
        if(direction == Direction.LEFT) {
            newHead = new GameObject(headX + 1, headY);
        } else if (direction == Direction.DOWN) {
            newHead = new GameObject(headX, headY - 1);
        } else if (direction == Direction.UP) {
            newHead = new GameObject(headX, headY + 1);
        } else if (direction == Direction.RIGHT) {
            newHead = new GameObject(headX - 1, headY);
        }
        
        return newHead;
    }
    
    public GameObject createNewHead() {
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;
        
        GameObject newHead = null;
        
        if(direction == Direction.LEFT) {
            newHead = new GameObject(headX-1, headY);
        } else if (direction == Direction.DOWN) {
            newHead = new GameObject(headX, headY + 1);
        } else if (direction == Direction.UP) {
            newHead = new GameObject(headX, headY - 1);
        } else if (direction == Direction.RIGHT) {
            newHead = new GameObject(headX + 1, headY);
        }
        
        return newHead;
    }
    
    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }
    
    public boolean checkCollision(GameObject object) {
        boolean check = false;
        for(int i = 0; i < snakeParts.size(); i++) {
            if(object.x == snakeParts.get(i).x && object.y == snakeParts.get(i).y) {
                check = true;
            }
        }
        return check;
    }
    
    public void draw(Game game) {
        game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.GREEN, 75);
        for(int i = 1; i < snakeParts.size(); i++) {
            game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.GREEN, 75);
         //   game.setCellValueEx(snakeParts.get(2).x ,snakeParts.get(2).y, Color.NONE, BODY_SIGN, Color.GREEN, 75);
        }
        if(!isAlive) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
            for(int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
                
                //game.setCellValueEx(snakeParts.get(2).x ,snakeParts.get(2).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }
}