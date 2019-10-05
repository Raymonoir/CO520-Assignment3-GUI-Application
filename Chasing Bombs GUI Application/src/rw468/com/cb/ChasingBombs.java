package rw468.com.cb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This is the main class for the game Chasing Bombs, everything is controlled within this class
 * such as the window the game is drawn in and the game logic. This is also the outter of a nested class.
 * 
 * @author Raymond Ward
 * @Version 08/03/2019
 *
 */

public class ChasingBombs
{
	Dimension windowSize = new Dimension(900,500);
	private JFrame frame = new JFrame("Chasing Bombs");
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	
	private List <GridCell> grid;
	private int score;
	private JLabel scoreLabel;
	private int difficulty;
	private boolean gameStarted;
	
	
	/**
	 * The initialisation of the ChasingBomb object, setting class fields such as score and difficulty
	 * as well as creating the main frame.
	 */
	
	ChasingBombs()
	{
		score = 0;
		scoreLabel = new JLabel();
		difficulty = 5; //Default difficulty = easy
	
		
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(windowSize);
		
		
		buildPanels();
		
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	
	/**
	 * This method builds the 3 panels and their contents that create the window for Chasing Bombs
	 */
	private void buildPanels() 
	{
		frame.add(panel1, BorderLayout.WEST);
		frame.add(panel2, BorderLayout.CENTER);
		frame.add(panel3, BorderLayout.EAST);
		
		panel1.setPreferredSize(new Dimension(windowSize.width/3,windowSize.height));
		panel2.setPreferredSize(new Dimension(windowSize.width/3,windowSize.height));
		panel3.setPreferredSize(new Dimension(windowSize.width/3,windowSize.height));
		
		panel1.setBackground(Color.BLUE);
		panel2.setBackground(Color.YELLOW);
		panel3.setBackground(Color.MAGENTA);
		
		panel1.setLayout(new GridLayout(2,5));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		
		
		//Panel 1
		
		grid = buildGrid();
		
		
		
		//Panel 2

		JButton strtButt = new JButton("Start this game");
		JButton exitButt = new JButton("Exit");
		JButton ngButt = new JButton("Start new game");
		
		
		strtButt.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButt.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		ngButt.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		ngButt.addActionListener(soruce -> restartGame());
		
		
		strtButt.addActionListener(source -> 
		{
			panel2.remove(strtButt);
			scoreLabel.setText("Game has started, make your guess!");
			addListeners();
			panel2.add(ngButt);
			
		});
		
		exitButt.addActionListener(source -> System.exit(0));
		
		panel2.add(strtButt);
		panel2.add(exitButt);
		panel2.add(scoreLabel);
		
		
		
		
		//Panel 3
		
		JButton eButt = new JButton("Easy");
		JButton iButt = new JButton("Intermediate");
		JButton hButt = new JButton("Hard");
		JLabel diffLabel = new JLabel("Difficulty is: Easy");
		
		
		eButt.setAlignmentX(Component.CENTER_ALIGNMENT);
		iButt.setAlignmentX(Component.CENTER_ALIGNMENT);
		hButt.setAlignmentX(Component.CENTER_ALIGNMENT);
		diffLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		eButt.addActionListener(source -> 
		{
			difficulty = 5;
			diffLabel.setText("Difficulty is: Easy");
		});
		
		iButt.addActionListener(source -> 
		{
			difficulty = 7;
			diffLabel.setText("Difficulty is: Intermediate");
			
		});
		hButt.addActionListener(source -> 
		{
			difficulty = 9;
			diffLabel.setText("Difficulty is: Hard");
			
		});
	
		
		panel3.add(eButt);
		panel3.add(iButt);
		panel3.add(hButt);
		panel3.add(diffLabel);
		
		
	}
	
	/**
	 * This method is used to add the listeners to grid when the game has been begun
	 */
	private void addListeners()
	{
		for (GridCell c : grid)
		{
			c.addMouseListener(c);	
		}
	}
	/**
	 * This method is used to remove the listeners from the grid when the game has been completed
	 */
	private void removeListeners()
	{
		for (GridCell c : grid)
		{
			for (MouseListener m : c.getMouseListeners())
			{
				c.removeMouseListener(m);
			}
			
		}
	}
	/**
	 * This method is called if the game is lost
	 */
	private void gameLost()
	{
		scoreLabel.setText("You lost, your score was: " + score);
		removeListeners();

	}
	
	
	/**
	 * This method creates a new ChasingBombs object to effectively restart the game
	 */
	private void restartGame()
	{
		ChasingBombs cbv = new ChasingBombs();
	}
	
	/**
	 * This method is called when the game has been won
	 */
	private void gameWon() 
	{
		scoreLabel.setText("You Won!");
		removeListeners();
		
	}

	
	/**
	 * This method forms an arraylist of GridCell objects, placing the "bomb" within one of the cells as then adds them to the first panel
	 * 
	 * @return grid, An arraylist of GridCell objects which creates the grid in panel 1
	 */
	private List<GridCell> buildGrid()
	{
		Random r = new Random();
		int bombChance = Math.round(r.nextInt(10));
		List<GridCell> grid = new ArrayList<>();
		
		for (int i = 0; i < 2; i ++)
		{
			for (int j = 0; j < 5; j ++)
			{
				boolean bomb = false;
				if (i * 5 + j == bombChance)
				{
					bomb = true;
				}
				
				GridCell current = new GridCell(bomb);
				panel1.add(current);
				grid.add(current);
				
			}
		}
		
		return grid;
	}
	
	/**
	 * This class is the blueprint for each cell within the grid in panel 1, it extends JPanel as well as implementing MouseListener so
	 * it has the functionality to detect when a user clicks on the panel
	 * 
	 * @author Raymond Ward
	 * @version 08/03/2019
	 */
	
	public class GridCell extends JPanel implements MouseListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean bomb;
		private boolean clicked;
		
		
		/**
		 * The constructor for the GridCell object, setting each cells background and border
		 * @param bomb, whether this cell does or does not contain the bomb
		 */
		GridCell(boolean bomb)
		{
			this.bomb = bomb;
			this.clicked = false;
			setBackground(Color.GRAY);
			setBorder(BorderFactory.createLineBorder(Color.white, 2));
		}

		/**
		 * The mouseClicked method inherited from the MouseListener, adds the functionality for each panel to change background, increment score
		 * and win or lose the game depending on the bomb field, current score and difficulty
		 */
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			JPanel clickedPan = (JPanel) e.getSource();
			
			if (!clicked)
			{
				if (bomb)
				{
					clickedPan.setBackground(Color.BLACK);
					gameLost();
				}
					
				else
				{
					clickedPan.setBackground(Color.PINK);
					score++;
					scoreLabel.setText("Your score is: " + score);
					if (score == difficulty)
					{
						gameWon();
					}
				}
					
				
				
				clicked = true;
			}	
			
			
			
			
		}


		/**
		 * Unused
		 */
		@Override
		public void mousePressed(MouseEvent e) {}

		/**
		 * Unused
		 */
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		/**
		 * Unused
		 */
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		/**
		 * Unused
		 */
		@Override
		public void mouseExited(MouseEvent e) {}
		
		
		

	}


	public static void main (String [] args)
	{
		ChasingBombs cbv = new ChasingBombs();
	}
	
	
	

}
