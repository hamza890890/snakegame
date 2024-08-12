package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class GamePanel extends JPanel implements ActionListener {
    

    public Rectangle getSnakeHead() {
        return new Rectangle(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
    }

    public Rectangle getFoodBounds() {
        return new Rectangle(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    }
    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    private Clip eatSound;
    private int highScore = 0;
    private boolean gameOver = false;
    private Clip gameSound;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
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
    public void startGame() {
        loadGameSound();
        
       if (gameSound != null) {
        gameSound.setFramePosition(0);
        gameSound.loop(Clip.LOOP_CONTINUOUSLY);
        
        playGameSound();
       }
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.blue);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,
                    (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());

            g.drawString("High Score: " + highScore,
                    (SCREEN_WIDTH - metrics.stringWidth("High Score: " + highScore)) / 2,
                    g.getFont().getSize() * 2);
            if (applesEaten > highScore) {
                highScore = applesEaten;
                repaint();
            }

        } else {
            gameOver(g);
        }
    }
    

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    private void playEatSound() {
        if (eatSound != null) {
            eatSound.start();
            eatSound.setFramePosition(0);
            try {
                Thread.sleep(1); // Add a small delay (1 milliseconds) before playing the sound
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Play the sound
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            loadSounds();
            playEatSound();
            applesEaten++;
            newApple();

        }
    }

    public void checkCollisions() {
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        if (applesEaten > highScore) {
            highScore = applesEaten;
            repaint();
        }
        //Score
        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));

        g.drawString("High Score: " + highScore,
                (SCREEN_WIDTH - metrics1.stringWidth("High Score: " + highScore)) / 2,
                g.getFont().getSize() * 2);

        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over! Press SPACE to play again.",
                (SCREEN_WIDTH - metrics2.stringWidth("Game Over! Press SPACE to play again.")) / 2,
                SCREEN_HEIGHT / 2);
        // Set gameOver to true to indicate that the game is over
        gameOver = true;
    }

    private void loadSounds() {
        try {
            File soundFile = new File("C:\\Users\\bluev\\OneDrive\\Documents\\NetBeansProjects\\Snake\\Tasty.wav");
            AudioInputStream eatSoundStream = AudioSystem.getAudioInputStream(soundFile);

            eatSound = AudioSystem.getClip();
            eatSound.open(eatSoundStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add any code that should be executed on each timer tick here
        // For example, you can call your move and check methods
        move();
        checkApple();
        checkCollisions();
        repaint(); // This will repaint the panel after each timer tick
    }

    private void resetGame() {
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        running = true;
        newApple();

        // Set initial positions of the snake's head
        x[0] = 0;
        y[0] = 0;

        // Reset the positions of the other body parts
        for (int i = 1; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        // Set gameOver to false to indicate that the game is not over
        gameOver = false;

        // Restart the timer
        timer.restart();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (gameOver) {
                        // Reset game state
                        resetGame();
                        // Redraw the screen
                        repaint();
                    }
                    break;
            }
        }
    }
}
