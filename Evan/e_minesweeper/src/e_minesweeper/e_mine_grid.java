package e_minesweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class e_mine_grid extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private int total_mines;
	private e_mine_cell[][] grid;
	private Dimension grid_dimension;
	private int remaining_cells;
	
	public e_mine_grid(int mines, int height, int width) {
		
		System.out.println("started e_mine_grid");
		
		grid_dimension = new Dimension(height, width);
		setLayout(new GridLayout(grid_dimension.height, grid_dimension.width));
		
		total_mines = mines;
		remaining_cells = grid_dimension.height * grid_dimension.width;
		
		//populate the grid[][] array
		grid = new e_mine_cell[grid_dimension.width][grid_dimension.height];
		for (int row=0; row<grid_dimension.height; row++) {
			for (int col=0; col<grid_dimension.width; col++) {
				grid[row][col] = new e_mine_cell(row, col);
				grid[row][col].addActionListener(this);
				add(grid[row][col]);
			}
		}
		
		//add mines randomly
		for (int i=0; i<mines; i++) {
			Random random = new Random();
			int row = random.nextInt(grid.length);
			int col = random.nextInt(grid[0].length);
			while (grid[row][col].get_mine()) {
				row = random.nextInt(grid.length);
				col = random.nextInt(grid[0].length);
				System.out.println("row = " + row + ", col = " + col + "get_mine = " + grid[row][col].get_mine());
			}
			grid[row][col].add_mine();
			
			//update "adjacent mines" of each adjacent cell
			for (int r=row-1; r<=row+1; r++) {
				for (int c=col-1; c<=col+1; c++) {
					if (r==row && c==col)
						continue;
					if (r<0 || c<0 || r>grid_dimension.width-1 || c>grid_dimension.height-1)
						continue;
					if (grid[r][c].get_mine() == false)
						grid[r][c].inc_adjacent();
				}
			}
			
		}
		
		setVisible(true);
		
		System.out.println("created e_mine_grid");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			reveal_cell((e_mine_cell)(e.getSource()));
		} catch(Exception ex) {
			System.err.println("error clicking cell");
		}
	}
	
	public void reveal_cell(e_mine_cell cell) {
				
		cell.setEnabled(false);
		if (cell.get_mine())
			game_over();
		else if (cell.get_adjacent_mines() == 0) //if there's no mines around, reveal more recursively
			reveal_adjacent(cell);
		else
			cell.setText(Integer.toString(cell.get_adjacent_mines()));
		
		remaining_cells--;
		
		if (remaining_cells == total_mines)
			game_win();
		
	}
	
	public void reveal_adjacent(e_mine_cell cell) {
		int row = cell.get_row();
		int col = cell.get_col();
		
		for (int r=row-1; r<=row+1; r++) {
			for (int c=col-1; c<=col+1; c++) {
				if (r<0 || c<0 || r>grid_dimension.width-1 || c>grid_dimension.height-1)
					continue;
				else if (grid[r][c].isEnabled())
					reveal_cell(grid[r][c]); //reveal the cells surrounding the 0 cell
			}
		}
		
	}

	public void game_over() {
		show_mines();
		JOptionPane.showMessageDialog(this,"You Lose!");
	}
	public void game_win() {
		JOptionPane.showMessageDialog(this,"You Win!");
	}
	
	public void show_mines() {
		for (int row=0; row<grid_dimension.height; row++) {
			for (int col=0; col<grid_dimension.width; col++) {
				if (grid[row][col].get_mine()) {
					try {
						Image img = ImageIO.read(getClass().getResource("bomb.png"));
						grid[row][col].setIcon(new ImageIcon(img));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public int get_flags() {
		int flags = 0;
		for (int row=0; row<grid_dimension.height; row++) {
			for (int col=0; col<grid_dimension.width; col++) {
				if (grid[row][col].get_flag()) {
					flags++;
				}
			}
		}
		return flags;
	}
}
