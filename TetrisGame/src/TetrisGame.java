
import javax.swing.JFrame;

public class TetrisGame extends JFrame
{
	
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private GameEndPanel gameEndPanel;
	
	public TetrisGame()
	{
		displayMenu();
		setTitle("Tetris");
		setLocation(200, 100);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void displayMenu()
	{
		if (gameEndPanel != null)
		{
			remove(gameEndPanel);
			gameEndPanel = null;
		}
		menuPanel = new MenuPanel(this);
		add(menuPanel);
		setVisible(true);
	}
	
	public void startNewGame()
	{
		if (menuPanel != null)
		{
			remove(menuPanel);
			menuPanel = null;
		}
		gamePanel = new GamePanel(this);
		add(gamePanel);
		setVisible(true);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gamePanel.addKeyListener(gamePanel);
		Thread thread = new Thread(gamePanel);
		thread.start();
	}
	
	public void gameOver(int score)
	{
		if (gamePanel != null)
		{
			remove(gamePanel);
			gamePanel = null;
			gameEndPanel = new GameEndPanel(score, this);
			add(gameEndPanel);
			setVisible(true);
		}
	}

	public static void main(String[] args)
	{
		new TetrisGame();
	}

}
