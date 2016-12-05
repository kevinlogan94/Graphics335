package testGUI;

import javax.swing.JFrame;

public class myFrame extends JFrame {
	public myFrame() {
	}
	static myFrame frame; 
	public static void main(String[] args) {
		frame = new myFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(400, 300);
		myPanel p = new myPanel();
		
		frame.setContentPane(p);
		frame.setVisible(true);
		
	}
}
