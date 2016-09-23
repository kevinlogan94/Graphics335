package helloOpenGL;

import java.awt.BorderLayout;
import java.awt.Frame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas; 

import com.jogamp.opengl.util.Animator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;





public class HelloOpenGL extends Frame {

	private static final long serialVersionUID = 1L;
	static Animator anim = null;
	private void setupJOGL(){
	    GLCapabilities caps = new GLCapabilities(null);
	    caps.setDoubleBuffered(true);
	    caps.setHardwareAccelerated(true);
	    
	    GLCanvas canvas = new GLCanvas(caps); 
        add(canvas);

        JoglEventListener jgl = new JoglEventListener();
        canvas.addGLEventListener(jgl); 
        canvas.addKeyListener(jgl); 

        anim = new Animator(canvas);
        anim.start();

	}
	
    public HelloOpenGL() {
        super("Basic JOGL Demo");

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
        HelloOpenGL demo = new HelloOpenGL();
        

        demo.setVisible(true);
    }
}


class MyWin extends WindowAdapter {
	 public void windowClosing(WindowEvent e)
   {
       System.exit(0);
   }
}