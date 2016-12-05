package e_minesweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private String[] dropdown_options = {"Menu", "Beginner", "Intermediate", "Expert", "Custom", "Exit"};
	private JButton reset;
	private e_mine_grid grid;
	private JComboBox dropdown;
	private JLabel time_remaining;
	private JLabel mines_remaining;
	private Timer timer;
	
	private int total_mines = 10;
	private int remaining_mines = 10;
	private int board_width = 8;
	private int board_height = 8;
	private int seconds = 0;
	
	public Main() {
		super("Minesweeper");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel subpanel = new JPanel();
		subpanel.setLayout(new FlowLayout());
		
		
		grid = new e_mine_grid(total_mines, board_height, board_width);
		dropdown = new JComboBox(dropdown_options);
		dropdown.addActionListener(this);
		
		time_remaining = new JLabel();
		mines_remaining = new JLabel();
		reset = new JButton();
		reset.setText("Reset");
		reset.addActionListener(this);
		
		
		subpanel.add(dropdown);
		subpanel.add(reset);
		subpanel.add(time_remaining);
		mines_remaining.setText(Integer.toString(total_mines));
		subpanel.add(mines_remaining);
		
		add(subpanel, BorderLayout.PAGE_START);
		add(grid, BorderLayout.CENTER);
		
		timer = new Timer(1000, timer_listener);
		timer.start();
		
		setSize(600, 650);
		setVisible(true);
	}
	
	public static void main(String args[]) {
		System.out.println("hello");
		new Main();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == reset) {
			reset();
		} else if (e.getSource() == dropdown) {
			JComboBox temp = (JComboBox)(e.getSource());
			String option = (String)(temp.getSelectedItem());
			if (option == "Menu") {
				//do nothing
			}
			else if (option == "Exit") {
				System.exit(0);
			}
			else {
				create_new_game(option);
			}
		}
		
	}
	
	public void create_new_game(String option) {
		if (option == "Beginner") {
			total_mines = 10;
			board_height = 8;
			board_width = 8;
		} else if (option == "Intermediate") {
			total_mines = 20;
			board_height = 10;
			board_width = 10;
		} else if (option == "Expert") {
			total_mines = 40;
			board_height = 15;
			board_width = 15;
		} else if (option == "Custom") {
			Scanner in = new Scanner(System.in);
			System.out.print("Mines: ");
			total_mines = in.nextInt();
			System.out.print("Board Size: ");
			int dim = in.nextInt();
			board_height = dim;
			board_width = dim;
		} else {
			System.out.println("wut");
		}
		seconds = 0;
		remove(grid);
		grid = new e_mine_grid(total_mines, board_height, board_width);
		add(grid, BorderLayout.CENTER);
		revalidate();
	}
	
	public void reset() {
		seconds = 0;
		remove(grid);
		grid = new e_mine_grid(total_mines, board_height, board_width);
		add(grid, BorderLayout.CENTER);
		revalidate();
	}

	
	ActionListener timer_listener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			time_remaining.setText(Integer.toString(seconds));
			seconds++;
			update_mine_count();
		}
	};
	
	public void update_mine_count() {
		remaining_mines = total_mines - grid.get_flags();
		mines_remaining.setText(Integer.toString(remaining_mines));
		
	}

}
