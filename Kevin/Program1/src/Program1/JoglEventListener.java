package Program1;

import com.jogamp.opengl.*;
import com.jogamp.opengl.GLAutoDrawable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class JoglEventListener implements GLEventListener, KeyListener {

//	float backrgb[] = new float[4]; 
//	float rot; 
	float radius = 0.5f;
	float cx = 0.0f;
    float cy = 0.0f;

    	private GLU glu = new GLU();

	
	 public void displayChanged(GLAutoDrawable gLDrawable, 
	            boolean modeChanged, boolean deviceChanged) {
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {
	        GL2 gl = gLDrawable.getGL().getGL2();
	        //gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        // Really Nice Perspective Calculations
	        //gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	        glu.gluPerspective(45.0f, h, 1.0, 20.0);
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	    }

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

//			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	        gl.glTranslatef(0.0f, 0.0f, -6.0f);                        
	        
	        //Draw Circle
	        gl.glBegin(GL.GL_LINE_LOOP);        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);   
	        float lineSegments = 20f;
	        for (int i = 0; i < lineSegments; i++) {
	        	float theta = 2.0f * 3.14f * (float) i / lineSegments;
	        	float x = radius * (float)Math.cos(theta);
	        	float y = radius * (float)Math.sin(theta);
	        	gl.glVertex2f(x + cx, y + cy);
	        }
	        gl.glEnd();
	        
	        gl.glLoadIdentity();

	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		 public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_A) {
		      cx -= 1f;
		      System.out.println("Horizontal Coordinate:" + cx);
		    }
		    if (e.getKeyCode() == KeyEvent.VK_F) {
		      cx += 1f;
		      System.out.println("Horizontal Coordinate:" + cx);
		    }
		    if (e.getKeyCode() == KeyEvent.VK_Z) {
		      cy -= 1f;
		      System.out.println("Vertical Coordinate:" + cy);
		      
		    }
		    if (e.getKeyCode() == KeyEvent.VK_W) {
		      cy += 1f;
		      System.out.println("Vertical Coordinate:" + cy);
		    }
		    if (e.getKeyCode() == KeyEvent.VK_P) {
		      radius += 1f;
		      System.out.println("Circe Radius:" + radius);
		    }
		    if (e.getKeyCode() == KeyEvent.VK_O) {   
		      if (radius > 1f) {
		    	  radius -= 1f;
		      }
		      System.out.println("Circle Radius:" + radius);
		    }

		  }
		 
		 public void keyReleased(KeyEvent e) {
		 }

		 public void keyTyped(KeyEvent e) {
		 }
		
		
}
