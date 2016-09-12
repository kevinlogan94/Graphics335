package Program1;

import java.awt.BorderLayout;
import java.awt.Frame;

import com.jogamp.opengl.util.Animator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;


public class Main extends Frame{
	
	static Animator anim = null;
	private void setupJOGL(){
	    GLCapabilities caps = new GLCapabilities(null);
	    caps.setDoubleBuffered(true);
	    caps.setHardwareAccelerated(true);
	    
	    GLCanvas canvas = new GLCanvas(caps); 
        add(canvas);

        
        canvas.addGLEventListener(new JoglEventListener());
        canvas.addKeyListener(new JoglEventListener());

        anim = new Animator(canvas);
        anim.start();

	}
	
    public Main() {
        super("Program 1 CS335");

        setLayout(new BorderLayout());
        
        


        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        setSize(600, 600);
        setLocation(40, 40);

        setVisible(true);

        setupJOGL();
    }

    public static void main(String[] args) {
        Main demo = new Main();
        

        demo.setVisible(true);
    }




}
