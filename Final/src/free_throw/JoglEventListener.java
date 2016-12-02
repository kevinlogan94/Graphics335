package free_throw;

import java.awt.BorderLayout;
import java.awt.Frame;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.util.Animator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class EntryClass extends Frame {

	private static final long serialVersionUID = 1L;
	static Animator anim = null;
	
	private void setupJOGL(){
	    GLCapabilities caps = new GLCapabilities(null);
	    caps.setDoubleBuffered(true);  // request double buffer display mode
	    caps.setHardwareAccelerated(true);
	    
	    GLCanvas canvas = new GLCanvas(caps); 
        add(canvas);

        JoglEventListener jgl = new JoglEventListener();
        
        canvas.addGLEventListener(jgl); 
        canvas.addKeyListener(jgl); 
        canvas.addMouseListener(jgl);
        canvas.addMouseMotionListener(jgl);

        anim = new Animator(canvas);
        anim.start();

	}
	
    public EntryClass() {
        super("Free Throw");

        setLayout(new BorderLayout());
        
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        setSize(1200, 800);
        setLocation(40, 40);

        setVisible(true);

        setupJOGL();
    }

    public static void main(String[] args) {
    	EntryClass demo = new EntryClass();
        demo.setVisible(true);
        JoglEventListener.playSound("birds.wav");
    }
}


class MyWin extends WindowAdapter {
	 public void windowClosing(WindowEvent e)
   {
       System.exit(0);
   }
}
