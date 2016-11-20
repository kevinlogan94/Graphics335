package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
				blocks[r][c].addMouseListener(mouseListener);
				add(blocks[r][c]);
			}
		}
		
		addMines();	
		setVisible(true);
		
	}
	
	MouseAdapter mouseListener = new MouseAdapter() {
		 public void mousePressed(MouseEvent mouseEvent) {
		        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
		          try {
		  			flagBlock((Block)(mouseEvent.getSource()));
		  		  } catch(Exception exc) {
		  			System.err.println(
		  				exc.getMessage()+"Board's ActionListener only for Block objects"
		  			);
		  		  }
		        }
		 }
   };

	public void actionPerformed(ActionEvent e) {
		try {
			revealBlock((Block)(e.getSource()));
		} catch(Exception exc) {
			System.err.println(
				exc.getMessage()+"Board's ActionListener only for Block objects"
			);
		}
	}
	
	//public void MouseListener MouseListener(){}
	private void revealBlock(Block block){
		block.setEnabled(false);

		if(block.isMine()){
			showGameOver();
		}
		else if(block.noMines()){
			revealAdjacent(block);
		}
		else{
			block.setText(Integer.toString(block.getType()));
		}

		visibleBlocks--;
		if (visibleBlocks == totalMines) {
			showGameOver();
		}


	}
	
	private void flagBlock(Block block){
		if(block.isEnabled()){
			try {
	            Image img = ImageIO.read(getClass().getResource("Flag.png"));
	            block.setIcon(new ImageIcon(img));
	            block.setEnabled(false);
	          } catch (IOException ex) {
	          }
		}
		
	}
	
	private void showGameOver() {
		if (visibleBlocks != totalMines) { //Lose
			revealMines();
			JOptionPane.showMessageDialog(this,"Lose");
		} else {
			JOptionPane.showMessageDialog(this,"Win");
		}
	}
	
	private void revealMines() {
		for (int c = 0; c < gridSize.width; c++) {
			for (int r = 0; r < gridSize.height; r++) {
				if(blocks[r][c].isMine()){
					try {
			            Image img = ImageIO.read(getClass().getResource("Mine.png"));
			            blocks[r][c].setIcon(new ImageIcon(img));
			          } catch (IOException ex) {
			          }
				}
			}
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
	
	private void addMines() {
		int mineCount = 0;
		Random random = new Random();
		
		while (mineCount != totalMines) {
			int r = random.nextInt(blocks.length);//Random based row length
			int c = random.nextInt(blocks[0].length);//Random based on column length
			if (!blocks[r][c].isMine()) {
				blocks[r][c].setMine();
				//Setup adjacent blocks to show number of adjacent mines.
				for (int row = r-1;row <= r+1; row++) {
					for (int col = c-1; col <= c+1; col++) {
						if (row == r && col == c)
							continue; //Skip Centre
						if (row < 0 || row > gridSize.height-1 ||col < 0 || col > gridSize.width-1)
							continue; //Out of Bounds
						if(!blocks[row][col].isMine()){
							blocks[row][col].addMine();
							}
					}
				}
				
				mineCount++;
			}
		}
	}
}
