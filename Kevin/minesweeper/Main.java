package minesweeper;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;



public class Main extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JButton ResetBtn;
	MinePanel Board;
	int seconds=000;
	int mineAmount=10;
	int boardWidth=8;
	int boardHeight=8;
	JLabel Timerlabel;
	JLabel Minelabel;
	String[] menuOptions = {"Menu", "New", "Beginner", "Intermediate", "Expert", "Custom", "Exit"};
	JComboBox Menu;

	public Main() {
		super("Program 5: Minesweeper");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Board = new MinePanel(mineAmount, boardWidth, boardHeight);
		
		Menu = new JComboBox(menuOptions);
		Menu.addActionListener(this);
		
        setupTimer();
        Minelabel = new JLabel();
        if(mineAmount < 100){
        	Minelabel.setText("0" + Integer.toString(mineAmount)); 
        }
        else{
        	Minelabel.setText(Integer.toString(mineAmount));    
        }
        
        ResetBtn = new JButton("Reset");
        ResetBtn.addActionListener(this);
        ResetBtn.setBounds(10,30,80,30);
        
//        try {
//            Image img = ImageIO.read(getClass().getResource("Reset.jpeg"));
//            ResetBtn.setIcon(new ImageIcon(img));
//          } catch (IOException ex) {
//          }
        
        add(Menu, BorderLayout.PAGE_START);
        add(Minelabel, BorderLayout.AFTER_LINE_ENDS);
        add(Timerlabel, BorderLayout.LINE_START);
        add(ResetBtn, BorderLayout.PAGE_END);
        add(Board, BorderLayout.CENTER);
        
        
		setSize(750, 500);
		setLocation(100, 100);
		setVisible(true);
	}
	
	public static void main(String[] args) {
			new Main();	
	}
	
	public void actionPerformed( ActionEvent e ){
		//JButton btn = (JButton) e.getSource();
		//If a Resetbtn is pressed
		if(e.getSource() == ResetBtn){
			//Board.reset();
			createDifGame("");
		}
		
		//If menu is pressed
		if(e.getSource() == Menu){
			JComboBox cb = (JComboBox)e.getSource();
			String option = (String)cb.getSelectedItem();
			
			//Create a new game
			if(option != "Exit" && option != "Menu")
				createDifGame(option);
			
			if(option=="Exit")
				System.exit(0);
		}
	}
	
	//Setup timer label
	public void setupTimer(){
		Timerlabel = new JLabel();
        Timerlabel.setText("000");    
        ActionListener listener = new ActionListener(){
  		  public void actionPerformed(ActionEvent event){
  			seconds++;
  			if(seconds<10)
  			  Timerlabel.setText("00" + Integer.toString(seconds));
  			else if(seconds<100)
  			  Timerlabel.setText("0" + Integer.toString(seconds));
  			else
  			  Timerlabel.setText(Integer.toString(seconds));
  		  }
  		}; 
        Timer timer = new Timer(1000, listener);
  	    timer.start();
	}
	
	public void createDifGame(String option){
		if(option=="Beginner"){
			mineAmount=10;
			boardWidth=8;
			boardHeight=8;
		}
		else if(option=="Intermediate"){
			mineAmount=40;
			boardWidth=16;
			boardHeight=16;
		}
		else if(option=="Expert"){
			mineAmount=99;
			boardWidth=32;
			boardHeight=32;
		}
		else if(option=="Custom"){
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);

			//Request mines for game.
			System.out.println("How many Mines?");
			mineAmount = scanner.nextInt();

			//Request size.
			System.out.println("Board Width?");
			boardWidth = scanner.nextInt();

			System.out.println("Board Height?");
			boardHeight = scanner.nextInt();
		}
		
		//Set up new custom game
		seconds=0;
		remove(Board);
		Board = new MinePanel(mineAmount, boardWidth, boardHeight);
		add(Board, BorderLayout.CENTER);
		
		//Set mineAmount
		if(mineAmount < 100){
        	Minelabel.setText("0" + Integer.toString(mineAmount)); 
        }
        else{
        	Minelabel.setText(Integer.toString(mineAmount));    
        }
	}
}
