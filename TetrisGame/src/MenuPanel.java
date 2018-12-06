import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

public class MenuPanel extends JPanel implements ActionListener
{
	
	private JLabel welcomeMessage;
	private JButton startButton, exitButton;
	private TetrisGame mainControl;
	
	public MenuPanel(TetrisGame mainControl)
	{
		setPreferredSize(new Dimension((GamePanel.BOARD_WIDTH + 2) * Tetromino.LENGTH,
				GamePanel.BOARD_HEIGHT * Tetromino.LENGTH));
		
		welcomeMessage = new JLabel("Welcome!");
		welcomeMessage.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		welcomeMessage.setForeground(Color.BLACK);
		
		startButton = new JButton("Start New Game");
		startButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		startButton.setForeground(Color.BLACK);
		startButton.addActionListener(this);
		
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
					.addComponent(welcomeMessage)
					.addComponent(startButton)
					.addComponent(exitButton)
				)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		layout.linkSize(SwingConstants.HORIZONTAL, startButton, exitButton);
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(welcomeMessage)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(startButton)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(exitButton)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		
		this.mainControl = mainControl;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == startButton)
		{
			mainControl.startNewGame();
		}
		else if (e.getSource() == exitButton)
		{
			System.exit(0);
		}
	}
	
}
