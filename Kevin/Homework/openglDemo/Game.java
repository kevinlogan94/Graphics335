package openglDemo;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;

 
 public class Game extends JFrame implements GLEventListener {
     private static final long serialVersionUID = 1L;
     
     //define height/width of window
     final private int width = 800;
     final private int height = 600;
     
     public Game() {
    	 //Created a swing window called opengl
    	 super("OpenGL Demo");
    	 GLProfile profile = GLProfile.get(GLProfile.GL4);
    	 GLCapabilities capabilities = new GLCapabilities(profile);
    	 
    	 GLCanvas canvas = new GLCanvas(capabilities);
    	 canvas.addGLEventListener(this);
    	 
    	 this.setName("OpenGL Demo");
    	 this.getContentPane().add(canvas);
    	 
    	 this.setSize(width, height);
    	 this.setLocationRelativeTo(null);
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 this.setVisible(true);
    	 this.setResizable(false);
    	 canvas.requestFocusInWindow();
     }
     
     public void play() {
     }
     
     @Override
     public void display(GLAutoDrawable drawable) {
    	 GL4 gl = drawable.getGL().getGL4();
    	 gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);

    	 // call your draw code here

    	 gl.glFlush();
     }
 
     @Override
     public void dispose(GLAutoDrawable drawable) {
     }
 
     @Override
     public void init(GLAutoDrawable drawable) {
    	 GL4 gl = drawable.getGL().getGL4();
    	 gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);
     }
 
     @Override
     public void reshape(GLAutoDrawable drawable, int x, int y, int width,
             int height) {
     }
 }
