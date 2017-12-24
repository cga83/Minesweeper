import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Minesweeper implements ActionListener
{
	private Field field;
	private static int level;
	private JButton buttonNewGame;
	private JFrame windowGame;
	private JPanel principalPanel;
	private JMenuItem confirmExit;
	private JMenuItem levelEasy;
	private JMenuItem levelIntermediary;
	private JMenuItem levelHard;
	private JLabel displayMines;
	private JLabel displayScore;
	private int nbMines;
	private int nbRemainingMines;
	private Timer timerScore;
	private int countScore=0;
	
	public Minesweeper(int level) throws IOException 
	{
		timerScore = new Timer(100, this); // 100ms Timer

		int sizeFieldLine = 8;
		int sizeFieldColumn = 8;
		switch (level) // On choisit le niveau
		{
			case 1: // easy level
				sizeFieldLine=8;
				sizeFieldColumn=8;
				nbMines=10;
				nbRemainingMines=10;
				break;
			case 2: // intermediary level
				sizeFieldLine=16;
				sizeFieldColumn=16;
				nbMines=40;
				nbRemainingMines=40;
				break;
			case 3: // hard level
				sizeFieldLine=32;
				sizeFieldColumn=16;
				nbMines=99;
				nbRemainingMines=99;
				break;
		}
		field = new Field(sizeFieldLine,sizeFieldColumn, nbMines, this);
		
		IHM();
	}
	
	
	public void IHM ()
	{
		// Creation of the window
		windowGame = new JFrame("Minesweeper");
		
		// Creation of the north panel
		JPanel northPanel = new JPanel(new FlowLayout());
		// Creation of the principal panel
		principalPanel = new JPanel(new BorderLayout());
		
		// Creation of the menus
		JMenuBar menus = new JMenuBar();
		JMenu menuExit = new JMenu("Exit the game ?");
		confirmExit = new JMenuItem("Exit");
		menuExit.add(confirmExit);
		menus.add(menuExit);
		confirmExit.addActionListener(this);
		JMenu menuLevel = new JMenu("Change level");
		levelEasy = new JMenuItem("Easy");
		levelIntermediary = new JMenuItem("Intermediary");
		levelHard = new JMenuItem("Hard");
		menuLevel.add(levelEasy);
		menuLevel.add(levelIntermediary);
		menuLevel.add(levelHard);
		menuLevel.addActionListener(this);
		levelEasy.addActionListener(this);
		levelIntermediary.addActionListener(this);
		levelHard.addActionListener(this);
		menus.add(menuLevel);
		
		windowGame.setJMenuBar(menus);
		
		windowGame.setContentPane(northPanel);
		windowGame.setContentPane(principalPanel);
		
		// Creation of the objects
		buttonNewGame = new JButton("New game");
		buttonNewGame.addActionListener(this); 
		displayScore = new JLabel("Score : "+countScore);
		displayMines = new JLabel("Number of remainig mines : "+String.valueOf(nbMines));

		// We add the objects
		northPanel.add(displayScore);
		northPanel.add(buttonNewGame);
		northPanel.add(displayMines);
		principalPanel.add(northPanel, BorderLayout.NORTH);
		principalPanel.add(field, BorderLayout.CENTER);
		
		windowGame.pack();
		windowGame.setVisible(true);
	}
	
	public void addFlag()
	{
		nbRemainingMines--;
		displayMines.setText(String.valueOf(nbRemainingMines));
	}
	
	public void removeFlag()
	{
		nbRemainingMines++;
		displayMines.setText(String.valueOf(nbRemainingMines));
	}
	
	
	public int getNbMines()
	{
		return nbRemainingMines;
	}
	
	
	public static void main (String[] args)
	{
		level = 1;
		try {
			new Minesweeper(level);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gameWon()
	{
		JOptionPane.showMessageDialog(windowGame, "You won !");
		timerScore.stop();
	}
	
	public void gameLost()
	{
		JOptionPane.showMessageDialog(windowGame, "You lost !");
		timerScore.stop();
	}
	
	public void timerOn()
	{
		timerScore.start();
	}

	
	public void actionPerformed(ActionEvent e)
	{
		countScore++;
		displayScore.setText("Score : "+ countScore);
		Object source = e.getSource(); 
		if (source==buttonNewGame)
		{
			countScore=0;
			timerScore.restart();
			field.createNewField();
			displayMines.setText("Number of remaining mines : "+String.valueOf(nbMines));
			nbRemainingMines=nbMines;
		}
		else if (source==confirmExit)
		{
			System.exit(0);
		}
		else if (source==levelEasy)
		{
			timerScore.restart();
			countScore=0;
			Field champFacile;
			principalPanel.remove(field);
			champFacile = new Field(8,8,10,this);
			nbMines=10;
			displayMines.setText("Number of remaining mines : "+ String.valueOf(nbMines));
			nbRemainingMines=nbMines;
			field = champFacile;
			principalPanel.add(field, BorderLayout.CENTER);
			windowGame.pack();
		}
		else if (source==levelIntermediary)
		{
			timerScore.restart();
			countScore=0;
			Field champIntermediaire;
			principalPanel.remove(field);
			champIntermediaire = new Field(16,16,40,this);
			nbMines=40;
			displayMines.setText("Number of remaining mines : "+String.valueOf(nbMines));
			nbRemainingMines=nbMines;
			field = champIntermediaire;
			principalPanel.add(field, BorderLayout.CENTER);
			windowGame.pack();
		}
		else if (source==levelHard)
		{
			timerScore.restart();
			countScore=0;
			timerScore.restart();
			Field champDifficile;
			principalPanel.remove(field);
			champDifficile = new Field(32, 16, 99, this);
			nbMines=99;
			displayMines.setText("Number of remaining mines : "+String.valueOf(nbMines));
			nbRemainingMines=nbMines;
			field = champDifficile;
			principalPanel.add(field, BorderLayout.CENTER);
			windowGame.pack();
		}
	}
}
