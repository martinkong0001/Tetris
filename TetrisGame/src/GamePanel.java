import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, Runnable
{
	
	private Tetromino piece;
	private Thread pieceThread;
	private Color[][] board;
	private int score;
	
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 18;
	
	private long lastMoveTime;
	private TetrisGame mainControl;
	
	public GamePanel(TetrisGame mainControl)
	{
		this.mainControl = mainControl;
		board = new Color[BOARD_WIDTH][BOARD_HEIGHT];
		createNewPiece();
	}
	
	public void createNewPiece()
	{
		piece = new Tetromino(board, this);
		pieceThread = new Thread(piece);
		pieceThread.start();
	}
	
	public void gameOver()
	{
		mainControl.gameOver(score);
	}
	
	public void increaseScore(int numRowsCleared)
	{
		if (numRowsCleared == 1)
		{
			score += 40;
		}
		else if (numRowsCleared == 2)
		{
			score += 100;
		}
		else if (numRowsCleared == 3)
		{
			score += 300;
		}
		else if (numRowsCleared == 4)
		{
			score += 1200;
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int pieceLength = Tetromino.LENGTH;
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, pieceLength, BOARD_HEIGHT * pieceLength);
		g.fillRect((BOARD_WIDTH + 1) * pieceLength, 0,
				pieceLength, BOARD_HEIGHT * pieceLength);
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				if (board[i][j] != null)
				{
					g.setColor(board[i][j]);
				}
				else
				{
					g.setColor(Color.BLACK);
				}
				g.fillRect((i + 1) * pieceLength, j * pieceLength,
						pieceLength, pieceLength);
			}
		}
		if (piece != null)
		{
			piece.drawTetromino(g);
		}
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 1; i < BOARD_HEIGHT; i++)
		{
			g.drawLine(0, i * pieceLength,
					(BOARD_WIDTH + 2) * pieceLength, i * pieceLength);
		}
		for (int i = 1; i < BOARD_WIDTH + 2; i++)
		{
			g.drawLine(i * pieceLength, 0,
					i * pieceLength, BOARD_HEIGHT * pieceLength);
		}
	}

	public void keyPressed(KeyEvent arg0)
	{
		if (piece != null)
		{
			if (arg0.getKeyCode() == KeyEvent.VK_A)
			{
				piece.moveLeft();
				if (piece.touchWall())
				{
					piece.moveRight();
				}
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_D)
			{
				piece.moveRight();
				if (piece.touchWall())
				{
					piece.moveLeft();
				}
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_W)
			{
				piece.rotateRight();
				if (piece.touchWall())
				{
					piece.rotateLeft();
				}
				if (piece.touchBottom())
				{
					piece.rotateLeft();
				}
			}
			else if (arg0.getKeyCode() == KeyEvent.VK_S)
			{
				if (System.currentTimeMillis() - lastMoveTime > 150)
				{
					piece.moveDown();
					if (piece.touchBottom() == true)
					{
						pieceThread.interrupt();
						piece.moveUp();
						boolean isGameOver = piece.recordColors();
						if (isGameOver == true)
						{
							removeKeyListener(this);
							mainControl.requestFocusInWindow();
							gameOver();
						}
						else
						{
							piece.eliminateRows();
							createNewPiece();
						}
					}
					lastMoveTime = System.currentTimeMillis();
				}
			}
		}
	}

	public void keyReleased(KeyEvent arg0)
	{
	}

	public void keyTyped(KeyEvent arg0)
	{
	}

	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			repaint();
		}
	}
	
}
