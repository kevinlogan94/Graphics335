package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MinePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int totalMines;
	private Block[][] blocks;
	private Dimension gridSize;
	private int visibleBlocks;
	
	public MinePanel(int mineAmount, int boardWidth, int boardHeight){
		setBackground(Color.green);
        setBounds(0, 35, 500, 200);
        
        gridSize = new Dimension(boardWidth, boardHeight);
		//setPreferredSize(new Dimension(800,600));
		setLayout(new GridLayout(gridSize.height,gridSize.width));
		
		
		totalMines = mineAmount;
		visibleBlocks = gridSize.width * gridSize.height;
		
		blocks = new Block[gridSize.height][gridSize.width];
		for (int c = 0; c < gridSize.width; c++) {
			for (int r = 0; r < gridSize.height; r++) {
				blocks[r][c] = new Block(r,c);
				blocks[r][c].addActionListener(this);
				add(blocks[r][c]);
			}
		}
		
		//addMines();
		
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			revealBlock((Block)(e.getSource()));
			//reset();
		} catch(Exception exc) {
			System.err.println(
				exc.getMessage()+"Board's ActionListener only for Block objects"
			);
		}
	}
	
	private void revealBlock(Block block){
		block.setEnabled(false);

		if(block.noMines()){
			revealAdjacent(block);
		}
	}
	
	//Reveal all adjacent blocks
	public void revealAdjacent(Block block){
		int row = block.getRow();
		int column = block.getColumn();
		
		for (int r = row-1; r <= row+1; r++){
			for (int c = column-1; c <= column+1; c++){
//				if (r == row && c == column)
//					continue; //Skip Centre
				//If it's out of bounds continue to the next block
				if(c < 0 || r < 0 || r > gridSize.height-1 || c > gridSize.width-1) {
					continue;
				}
				//If it is enabled reveal the block
				if(blocks[r][c].isEnabled()){
					revealBlock(blocks[r][c]);
				}
			}		
		}	
	}
	
	public void reset(){
		for (int c = 0; c < gridSize.width; c++) {
			for (int r = 0; r < gridSize.height; r++) {
				(blocks[r][c]).setEnabled(true);
			}
		}
		
		//addMines();
	}
}
