package e_minesweeper;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class e_mine_cell extends JButton {
	private static final long serialVersionUID = 1L;
	private int row, col, adjacent_mines; // location of cell, adjacent mines
	private boolean mine; // is there a mine in this square?
	private boolean flag = false; // is there a flag in this square?
	
	public e_mine_cell(int r, int c) {
		setPreferredSize(new Dimension(60,60));
		setMargin(new Insets(1,1,1,1));
		this.row = r;
		this.col = c;
		this.adjacent_mines = 0;
		this.mine = false;
		
		JButton button = this;
		this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) { // if right click
                	if (!flag) {
                		button.setText("F");
                		flag = true;
                	} else {
                		button.setText("");
                		flag = false;
                	}
                }
            }
        });
	}
	
	public void add_mine() { this.mine = true; }
	public boolean get_mine() { return this.mine; }
	public boolean get_flag() { return this.flag; }
	public int get_adjacent_mines() { return this.adjacent_mines; }
	public void inc_adjacent() { this.adjacent_mines++; }
	public int get_row() { return this.row; }
	public int get_col() { return this.col; }	
}
