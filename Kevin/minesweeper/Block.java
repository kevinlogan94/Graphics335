package minesweeper;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;

public class Block extends JButton{

	private static final long serialVersionUID = 1L;
	private int row, col;
	private int type;
	
	public Block(int row, int col) { 
		setPreferredSize(new Dimension(8,8));
		setMargin(new Insets(1,1,1,1));
		
		this.row = row;
		this.col = col;	
		this.type = 0;
	}
	
	public void setMine() { type = 9; }
	public boolean isMine() { return type == 9 ? true : false; }
	public boolean noMines() { return type == 0 ? true : false; }

	public void addMine() { type++; }
	
	public int getRow() { return row; }
	public int getColumn() { return col; }
	public int getType() { return type; }
}
