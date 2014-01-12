package rmi.client;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * draw a grid specifying the amount of cells and its size
 * 
 */
public class Grid extends JPanel
{
	private int amountCellsX = 1;
	private int amountCellsY = 1;
	private int width = 1;
	private int height = 1;
	int stepsizeX = 1;
	int stepsizeY = 1;

	private List<Point> fillCells;

	public Grid(int cellsX, int cellsY, int width, int height)
	{
		fillCells = new ArrayList<>(25);
		this.amountCellsX = cellsX;
		this.amountCellsY = cellsY;
		this.width = width;
		this.height = height;

		stepsizeX = width / cellsX;
		stepsizeY = height / cellsY;
	}

	/**
	 * render the grid
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (Point fillCell : fillCells)
		{
			int cellX = (fillCell.x * stepsizeX);
			int cellY = (fillCell.y * stepsizeY);
			g.setColor(Color.RED);
			g.fillRect(cellX, cellY, stepsizeX, stepsizeY);
		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);

		for (int x = 0; x <= width; x += stepsizeX)
		{
			g.drawLine(x, 0, x, width);
		}

		for (int y = 0; y <= height; y += stepsizeY)
		{
			g.drawLine(0, y, height, y);
		}
	}

	/**
	 * fill a single cell and clear the other ones
	 * @param x
	 * @param y
	 */
	public void fillCell(int x, int y)
	{
		fillCells.clear();
		fillCells.add(new Point(x, y));
		repaint();
	}

}
