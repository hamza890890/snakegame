
package snake;
import java.awt.Rectangle;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SnakeGame {
     private Clip eatSound;
     private Clip gameSound;
    public SnakeGame(GamePanel gamePanel) {
        loadSounds();
        checkCollisionWithFood(gamePanel); // Call the method with gamePanel parameter
        // Other initialization code
    }

private void loadSounds() {
    try {
    File soundFile = new File("C:\\Users\\bluev\\OneDrive\\Documents\\NetBeansProjects\\Snake\\Tasty.wav");

    AudioInputStream eatSoundStream = AudioSystem.getAudioInputStream(soundFile);
        
        eatSound = AudioSystem.getClip();
        eatSound.open(eatSoundStream);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void loadGameSound() {
    try {
    File soundFile = new File("C:\\Users\\bluev\\OneDrive\\Documents\\NetBeansProjects\\Snake\\Ruby Delusions.wav");

    AudioInputStream eatSoundStream = AudioSystem.getAudioInputStream(soundFile);
        
        gameSound = AudioSystem.getClip();
        gameSound.open(eatSoundStream);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void playGameSound() {
    
    if (gameSound != null) {
        gameSound.setFramePosition(0);
        try {
            Thread.sleep(1); // Add a small delay (100 milliseconds) before playing the sound
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        gameSound.start(); // Play the sound
    }
}

    // Example: In your GamePanel class
private void checkCollisionWithFood(GamePanel gamePanel) {
    Rectangle snakeHead = gamePanel.getSnakeHead();
    Rectangle foodBounds = gamePanel.getFoodBounds();
    
    if (snakeHead.intersects(foodBounds)) {
        // Snake consumed food
        
        playEatSound();
        // Add logic for handling the consumed item
    }
}

private void playEatSound() {
    
    if (eatSound != null) {
        eatSound.setFramePosition(0);
        try {
            Thread.sleep(1); // Add a small delay (100 milliseconds) before playing the sound
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        eatSound.start(); // Play the sound
    }
}


	public static void main(String[] args) {
                
                GamePanel gamePanel = new GamePanel(); // Create an instance of GamePanel
                SnakeGame snakeGame = new SnakeGame(gamePanel);
                
		new GameFrame();
              
               
               
	}
}

