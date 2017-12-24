import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Field extends JPanel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	
	private Box[][] boxes; 
	private int numberOfMines;
	private int sizeLine, sizeColumn; // Size of the field
	Minesweeper minesweeper;
	private int firstTime=1; // To detect the first click on the field
	
	public Field(int sizeC, int sizeL, int nbMines, Minesweeper d) 
	{
		sizeLine = sizeL;
		sizeColumn = sizeC;
		numberOfMines = nbMines;
		minesweeper = d;
		
		boxes = new Box[sizeColumn][sizeLine]; // We initialize all the boxes
		for (int i=0; i<sizeColumn; i++)
			for (int j=0; j<sizeLine; j++)
				boxes[i][j]=new Box();

		createNewField(); // Random placement of the mine
	}
	
	public void createNewField()
	{
		for (int i=0; i<sizeColumn; i++) // We reinitialize
			for (int j=0; j<sizeLine; j++)
			{
				if (boxes[i][j].getMouseListeners().length==0)
					boxes[i][j].addMouseListener(this);
				boxes[i][j].setCase(false);
				boxes[i][j].cover();
				boxes[i][j].removeFlag();
				boxes[i][j].setNbMinesAdjacentes(0);
			}
		System.out.println();
		RandomPlacement(boxes, sizeColumn, sizeLine); // Random placement of the mines
		setGrid();
		display();
	}
	
	public void setGrid()
	{
		this.setLayout(new GridLayout(sizeColumn, sizeLine)); // We create the grid
		for (int i=0; i<sizeColumn; i++) // We browse the array
			for (int j=0; j<sizeLine; j++)
				this.add(boxes[i][j]); // We add the boxes to the JPanel
	}
	
	public void RandomPlacement(Box[][] presenceMines, int tX, int tY)
	{
		int count = 0;
		while (count<numberOfMines) // while we don't have the right number of mines
		{
			Random r = new Random(); // Random number
			int i = r.nextInt(tX); // between 0 and tX
			int j = r.nextInt(tY); // between 0 and tY
			if (!presenceMines[i][j].getCase()) // Is there already a mine here ?
			{
				presenceMines[i][j].setCase(true); // No, we put one
				incrementNearbyMines(presenceMines, tX, tY, i, j); // We indicate to the nearby boxes that there is a mine next to it
				count++;
			}
		}
	}
	
	public void incrementNearbyMines(Box[][] presenceMines, int tX, int tY, int i, int j)
	{
		// First, we have to check that is index is not out of the array
		if ((i-1)>=0) presenceMines[i-1][j].addOneNearbyMine(); // above box
		if ((i-1)>=0 && (j-1)>=0) presenceMines[i-1][j-1].addOneNearbyMine(); // box which is on the diagonal left above
		if ((i-1)>=0 && (j+1)<tY) presenceMines[i-1][j+1].addOneNearbyMine(); // box which is on the diagonal right above
		if (j-1>=0) presenceMines[i][j-1].addOneNearbyMine(); // left box
		if (j+1<tY) presenceMines[i][j+1].addOneNearbyMine(); // right box
		if ((i+1)<tX && (j-1)>=0) presenceMines[i+1][j-1].addOneNearbyMine(); // box which is on the diagonal left below
		if ((i+1)<tX) presenceMines[i+1][j].addOneNearbyMine(); // below box
		if ((i+1)<tX && (j+1)<tY) presenceMines[i+1][j+1].addOneNearbyMine(); // box which is on the diagonal right below
	}
	
	// helper function
	public void display()
	{
		for (int i=0; i<sizeColumn; i++) // We browse the array
		{
			for (int j=0; j<sizeLine; j++)
			{
				if (!boxes[i][j].getCase()) // 
					System.out.print(" " + boxes[i][j].getNbMinesAdjacentes()); 
				else // S'il y a une mine
					System.out.print(" M");	
			}
			System.out.println();
		}
	}
	
	public void getAllMines()
	{
		for (int i=0; i<sizeColumn; i++)
			for (int j=0; j<sizeLine; j++)
			{
				if (boxes[i][j].getCase())
					boxes[i][j].discover();
				boxes[i][j].removeMouseListener(this);
			}
	}
	
	public boolean gameWon()
	{
		for (int i=0; i<sizeColumn; i++)
			for (int j=0; j<sizeLine; j++)
			{
				if (boxes[i][j].isCovered() && !boxes[i][j].getCase())
					return false;
			}
		return true;
	}
	
	public void discoverNearbyMines(int tX, int tY, int i, int j)
	{
		// First, we have to check that is index is not out of the array
		if ((i-1)>=0 && boxes[i-1][j].isCovered())
		{
			boxes[i-1][j].discover(); // box above
			if (boxes[i-1][j].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i-1, j);
		}
		if ((i-1)>=0 && (j-1)>=0 && boxes[i-1][j-1].isCovered())
		{
			boxes[i-1][j-1].discover(); // box on the diagonal left above
			if (boxes[i-1][j-1].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i-1, j-1);
		}
		if ((i-1)>=0 && (j+1)<tY && boxes[i-1][j+1].isCovered())
		{
			boxes[i-1][j+1].discover(); // box on the diagonal right above
			if (boxes[i-1][j+1].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i-1, j+1);
		}
		if (j-1>=0 && boxes[i][j-1].isCovered())
		{
			boxes[i][j-1].discover(); // left box
			if (boxes[i][j-1].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i, j-1);
		}
		if (j+1<tY && boxes[i][j+1].isCovered())
		{
			boxes[i][j+1].discover(); // right box
			if (boxes[i][j+1].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i, j+1);
		}
		if ((i+1)<tX && (j-1)>=0 && boxes[i+1][j-1].isCovered())
		{
			boxes[i+1][j-1].discover();  // box on the diagonal left below
			if (boxes[i+1][j-1].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i+1, j-1);
		}
		if ((i+1)<tX && boxes[i+1][j].isCovered())
		{
			boxes[i+1][j].discover(); // below box
			if (boxes[i+1][j].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i+1, j);
		}
		if ((i+1)<tX && (j+1)<tY && boxes[i+1][j+1].isCovered())
		{
			boxes[i+1][j+1].discover(); // box on the diagonal right below
			if (boxes[i+1][j+1].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i+1, j+1);
		}
		


	}
	public void mouseClicked(MouseEvent e)
	{
		Object source = e.getSource(); 
		if (firstTime==1) 
		{
			minesweeper.timerOn();
			firstTime=0;
		}
		if (SwingUtilities.isLeftMouseButton(e)) 
		{
			for (int i=0; i<sizeColumn; i++)
				for (int j=0; j<sizeLine; j++)
				{
					if (!boxes[i][j].getCase() && source==boxes[i][j])
					{
						boxes[i][j].discover();
						if (boxes[i][j].getNbMinesAdjacentes()==0) discoverNearbyMines(sizeColumn, sizeLine, i,j);

						boxes[i][j].removeMouseListener(this);
					}
					else if (boxes[i][j].getCase() && source==boxes[i][j])
					{
						getAllMines();
						minesweeper.gameLost();
					}
				}
	
		}
		else if (SwingUtilities.isRightMouseButton(e))
		{
			for (int i=0; i<sizeColumn; i++)
				for (int j=0; j<sizeLine; j++)
				{
					if (!boxes[i][j].hasOneFlag() && source==boxes[i][j] && minesweeper.getNbMines()>0)
					{
						boxes[i][j].addFlag();
						minesweeper.addFlag();
					}
					else if (boxes[i][j].hasOneFlag() && source==boxes[i][j])
					{
						boxes[i][j].removeFlag();
						minesweeper.removeFlag();
					}
				}
		}
		if (gameWon())
		{
			minesweeper.gameWon();
			for (int i=0; i<sizeColumn; i++)
				for (int j=0; j<sizeLine; j++)
					boxes[i][j].removeMouseListener(this);
		}
	}
	
	public void mouseEntered(MouseEvent arg0)
	{
		// Rien à faire : on ne l'utilise pas
	}

	public void mouseExited(MouseEvent arg0)
	{
		// Rien à faire : on ne l'utilise pas
		
	}

	public void mousePressed(MouseEvent arg0)
	{
		// Rien à faire : on ne l'utilise pas
		
	}

	public void mouseReleased(MouseEvent arg0)
	{
		// Rien à faire : on ne l'utilise pas
		
	}
}