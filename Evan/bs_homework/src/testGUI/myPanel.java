package testGUI;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class myPanel extends JPanel {
	int red = 0;
	int green = 0;
	int blue = 0;
	public myPanel() {
		setBackground(Color.YELLOW);
		
		JButton btnHello = new JButton("Hello");
		btnHello.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// write your code here
			}
		});
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JProgressBar progressBar = new JProgressBar();
		
		
		JSlider rslider = new JSlider();
		JSlider gslider = new JSlider();
		JSlider bslider = new JSlider();
		rslider.setMaximum(255);
		gslider.setMaximum(255);
		bslider.setMaximum(255);
		
		rslider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {	
				JSlider source = (JSlider)arg0.getSource();
		        red = (int)source.getValue();
		        progressBar.setBackground(new Color(red, green, blue));
			}
		});
		gslider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {	
				JSlider source = (JSlider)arg0.getSource();
		        green = (int)source.getValue();
		        progressBar.setBackground(new Color(red, green, blue));
			}
		});
		bslider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {	
				JSlider source = (JSlider)arg0.getSource();
		        blue = (int)source.getValue();
		        progressBar.setBackground(new Color(red, green, blue));
			}
		});
		add(rslider);
		add(gslider);
		add(bslider);
		
		
		progressBar.setBackground(new Color(102, 255, 102));
		progressBar.setForeground(new Color(102, 102, 255));
		add(progressBar);
		add(btnHello);
	}

}
