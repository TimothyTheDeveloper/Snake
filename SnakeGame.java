package com.codegym.games.snake;
import com.codegym.engine.cell.*;


public class SnakeGame extends Game {
    public static final int WIDTH = 15, HEIGHT = 15;
    private Snake snake = null;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    
    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    
    private void createGame() {
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        isGameStopped = false;
        
        drawScene();
        
        turnDelay = 300;
        setTurnTimer(turnDelay);
        
        score = 0;
        setScore(score);
        
    }
    
    private void drawScene() {
        for(int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.LIGHTSEAGREEN, "");
            }
        }
        
        snake.draw(this);
        apple.draw(this);
    }
    
    private void createNewApple() {
        boolean check = true;
        while(check) {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            apple = new Apple(x, y);
            
            check = snake.checkCollision(apple);
        }   
    }
    
    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.LIGHTBLUE, "Game Over", Color.RED, 75);
    } 
    
    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.LIGHTBLUE, "You WON!", Color.GREEN, 75);
    }
    
    
    @Override
    public void onTurn(int number) {
        snake.move(apple);
        if(apple.isAlive == false) {
            createNewApple();
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            score += 5;
            setScore(score);
        }
        if(snake.isAlive == false) {
            gameOver();
        }
        if(snake.getLength() > GOAL) {
            win();
        }
        drawScene();
      
        
    }
    
    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        } 
        
        if(isGameStopped == true) {
            if(key == Key.SPACE) {
                createGame();
            }
        }
        
    }
}