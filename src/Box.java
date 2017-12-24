import javax.swing.*;
import java.awt.*;

public class Box extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private boolean presenceMine;
	private int nbNearbyMines;
	private boolean isCovered; // indique si la case est découverte ou pas
	private ImageIcon imageMine = new ImageIcon("mine.jpg");
	private boolean hasOneFlag;
	
	public Box()
	{
		presenceMine = false; // Par défaut il n'y a pas de mine
		nbNearbyMines = 0; // Par défaut il n'y a pas de mines adjacentes
		isCovered = false; // La case est couverte au départ
		hasOneFlag=false; // la case n'a pas de drapeau au départ
		setPreferredSize(new Dimension(22,22));
		setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
	public boolean getCase()
	{
		return presenceMine;
	}
	
	public int getNbMinesAdjacentes()
	{
		return nbNearbyMines;
	}
	
	public void setNbMinesAdjacentes(int nb)
	{
		nbNearbyMines=nb;
	}
	
	public void addOneNearbyMine()
	{
		nbNearbyMines++; // On incrémente les cases adjacentes à chaque ajout d'une nouvelle mine
	}
	
	public void setCase (boolean set)
	{
		presenceMine = set;
	}
	
	public void cover()
	{
		isCovered = true;
		setBorder(BorderFactory.createRaisedBevelBorder());
		repaint();
	}
	
	public void discover()
	{
		isCovered = false;
		setBorder(BorderFactory.createLoweredBevelBorder());
		repaint();
	}


	public boolean isCovered()
	{
		return isCovered;
	}
	
	public void addFlag()
	{
		hasOneFlag = true;
		repaint();
	}
	
	public void removeFlag()
	{
		hasOneFlag = false;
		repaint();
	}
	
	public boolean hasOneFlag()
	{
		return hasOneFlag;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (!isCovered && getCase())
		{
			imageMine.paintIcon(this,  g, 2, 0);
		}
		else if (!isCovered && !getCase())
		{
			g.drawString(String.valueOf(getNbMinesAdjacentes()), 5, 15);
		}
		else if (hasOneFlag)
		{
			g.setColor(Color.red);
			g.drawLine(5, 0, 5, 20);
			g.fillRect(5,0,15,10);
		}

	}


}