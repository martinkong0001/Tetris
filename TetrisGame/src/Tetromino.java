import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tetromino implements Runnable
{
	
	private int x, y;
	private int direction;
	private int[][][] orientations;
	private Color color;
	public static final int LENGTH = 40;
	
	private Color[][] board;
	private GamePanel gamePanel;
	
	public Tetromino(Color[][] board, GamePanel gamePanel)
	{
		int shape = (int) (Math.random() * 7);
		if (shape == 0)
		{
			orientations = new int[][][]
			{
				{{0, 1}, {1, 1}, {2, 1}, {3, 1}},
				{{2, 0}, {2, 1}, {2, 2}, {2, 3}},
				{{0, 2}, {1, 2}, {2, 2}, {3, 2}},
				{{1, 0}, {1, 1}, {1, 2}, {1, 3}}
			};
			color = Color.CYAN;
		}
		else if (shape == 1)
		{
			orientations = new int[][][]
			{
				{{0, 0}, {0, 1}, {1, 1}, {2, 1}},
				{{1, 0}, {2, 0}, {1, 1}, {1, 2}},
				{{0, 1}, {1, 1}, {2, 1}, {2, 2}},
				{{1, 0}, {1, 1}, {1, 2}, {0, 2}}
			};
			color = Color.BLUE;
		}
		else if (shape == 2)
		{
			orientations = new int[][][]
			{
				{{0, 1}, {1, 1}, {2, 1}, {2, 0}},
				{{1, 0}, {1, 1}, {1, 2}, {2, 2}},
				{{0, 1}, {1, 1}, {2, 1}, {0, 2}},
				{{0, 0}, {1, 0}, {1, 1}, {1, 2}}
			};
			color = Color.ORANGE;
		}
		else if (shape == 3)
		{
			orientations = new int[][][]
			{
				{{1, 0}, {2, 0}, {1, 1}, {2, 1}},
				{{1, 0}, {2, 0}, {1, 1}, {2, 1}},
				{{1, 0}, {2, 0}, {1, 1}, {2, 1}},
				{{1, 0}, {2, 0}, {1, 1}, {2, 1}}
			};
			color = Color.YELLOW;
		}
		else if (shape == 4)
		{
			orientations = new int[][][]
			{
				{{0, 1}, {1, 0}, {1, 1}, {2, 0}},
				{{1, 0}, {1, 1}, {2, 1}, {2, 2}},
				{{0, 2}, {1, 1}, {1, 2}, {2, 1}},
				{{0, 0}, {0, 1}, {1, 1}, {1, 2}}
			};
			color = Color.GREEN;
		}
		else if (shape == 5)
		{
			orientations = new int[][][]
			{
				{{0, 1}, {1, 0}, {1, 1}, {2, 1}},
				{{1, 0}, {1, 1}, {2, 1}, {1, 2}},
				{{0, 1}, {1, 1}, {1, 2}, {2, 1}},
				{{1, 0}, {0, 1}, {1, 1}, {1, 2}}
			};
			color = Color.MAGENTA;
		}
		else if (shape == 6)
		{
			orientations = new int[][][]
			{
				{{0, 0}, {1, 0}, {1, 1}, {2, 1}},
				{{2, 0}, {1, 1}, {2, 1}, {1, 2}},
				{{0, 1}, {1, 1}, {1, 2}, {2, 2}},
				{{1, 0}, {0, 1}, {1, 1}, {0, 2}}
			};
			color = Color.RED;
		}
		x = (GamePanel.BOARD_WIDTH - 1) / 2;
		y = -2;
		direction = 0;
		this.board = board;
		this.gamePanel = gamePanel;
	}
	
	public void drawTetromino(Graphics g)
	{
		Color originalColor = g.getColor();
		g.setColor(color);
		for (int i = 0; i < 4; i++)
		{
			g.fillRect((x + orientations[direction][i][0] + 1) * LENGTH,
					(y + orientations[direction][i][1]) * LENGTH, LENGTH, LENGTH);
		}
		g.setColor(originalColor);
	}
	
	public boolean touchWall()
	{
		for (int i = 0; i < 4; i++)
		{
			int xCoord = x + orientations[direction][i][0];
			if (xCoord < 0 || xCoord >= GamePanel.BOARD_WIDTH)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean touchBottom()
	{
		for (int i = 0; i < 4; i++)
		{
			int xCoord = x + orientations[direction][i][0];
			int yCoord = y + orientations[direction][i][1];
			if (yCoord >= GamePanel.BOARD_HEIGHT ||
					(yCoord >= 0 && board[xCoord][yCoord] != null))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean recordColors()
	{
		for (int i = 0; i < 4; i++)
		{
			int xCoord = x + orientations[direction][i][0];
			int yCoord = y + orientations[direction][i][1];
			if (yCoord < 0)
			{
				return true;
			}
			else
			{
				board[xCoord][yCoord] = color;
			}
		}
		return false;
	}
	
	public void eliminateRows()
	{
		int numRowsDeleted = 0;
		int[] deletedRows = new int[4];
		for (int i = 0; i < 4; i++)
		{
			deletedRows[i] = -1;
		}
		for (int j = 0; j < board[0].length; j++)
		{
			boolean delete = true;
			for (int i = 0; i < board.length; i++)
			{
				if (board[i][j] == null)
				{
					delete = false;
					break;
				}
			}
			if (delete == true)
			{
				deletedRows[numRowsDeleted] = j;
				numRowsDeleted++;
			}
		}
		if (numRowsDeleted > 0)
		{
			for (int i = 0; i < 4; i++)
			{
				if (deletedRows[i] != -1)
				{
					deleteOneRow(deletedRows[i]);
				}
			}
			gamePanel.increaseScore(numRowsDeleted);
			eliminateRows();
		}
	}
	
	public void deleteOneRow(int index)
	{
		for (int i = 0; i < board.length; i++)
		{
			board[i][index] = null;
		}
		for (int j = index - 1; j >= 0; j--)
		{
			for (int i = 0; i < board.length; i++)
			{
				if (board[i][j] != null)
				{
					board[i][j + 1] = board[i][j];
					board[i][j] = null;
				}
			}
		}
	}
	
	public void moveUp()
	{
		y--;
	}
	
	public void moveDown()
	{
		y++;
	}
	
	public void moveLeft()
	{
		x--;
	}
	
	public void moveRight()
	{
		x++;
	}
	
	public void rotateLeft()
	{
		direction = (direction + 3) % 4;
	}
	
	public void rotateRight()
	{
		direction = (direction + 1) % 4;
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				Thread.sleep(500);
				gamePanel.keyPressed(new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED,
						System.currentTimeMillis(), 0, KeyEvent.VK_S, 's'));
			}
		}
		catch (InterruptedException e)
		{
			return;
		}
	}
	
}
