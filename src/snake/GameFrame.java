package snake;
import javax.swing.JFrame;
public class GameFrame extends JFrame{

	GameFrame(){
			
		
        GamePanel gamePanel = new GamePanel(); // Create an instance of GamePanel
        this.add(gamePanel); // Add GamePanel to GameFrame
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        // Pass the gamePanel instance to SnakeGame constructor
        new SnakeGame(gamePanel);
		
	}
}
