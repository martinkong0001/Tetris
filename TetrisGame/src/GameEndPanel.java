import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

public class GameEndPanel extends JPanel implements ActionListener
{
	
	private JLabel gameOverMessage, scoreMessage;
	private JButton menuButton, exitButton;
	private TetrisGame mainControl;
	
	public GameEndPanel(int score, TetrisGame mainControl)
	{
		gameOverMessage = new JLabel("Game Over!");
		gameOverMessage.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		gameOverMessage.setForeground(Color.BLACK);
		
		scoreMessage = new JLabel("Your score is: " + score);
		scoreMessage.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		scoreMessage.setForeground(Color.BLACK);
		
		menuButton = new JButton("Go Back to Menu");
		menuButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		menuButton.setForeground(Color.BLACK);
		menuButton.addActionListener(this);
		
		exitButton = new JButton("Exit Game");
		exitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		exitButton.setForeground(Color.BLACK);
		exitButton.addActionListener(this);
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(gameOverMessage)
					.addComponent(scoreMessage)
					.addComponent(menuButton)
					.addComponent(exitButton)
				)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		layout.linkSize(SwingConstants.HORIZONTAL, menuButton, exitButton);
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(gameOverMessage)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(scoreMessage)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(menuButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(exitButton)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		
		this.mainControl = mainControl;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == menuButton)
		{
			mainControl.displayMenu();
		}
		else if (e.getSource() == exitButton)
		{
			System.exit(0);
		}
	}
	
}
