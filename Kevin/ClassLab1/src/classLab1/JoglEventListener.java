package classLab1;

import com.jogamp.opengl.*;
import com.jogamp.opengl.GLAutoDrawable;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class JoglEventListener implements GLEventListener {

	float backrgb[] = new float[4]; 
	float rot; 
	

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

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			backrgb[0]+=0.0005;
			if (backrgb[0]> 1) backrgb[0] = 0; 
			
			
			gl.glLoadIdentity();
	        gl.glTranslatef(0.0f, 0.0f, -6.0f);
	        gl.glRotatef(rot, 0.0f, 1.0f, 0.0f);
	        gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Front)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Left Of Triangle (Front)
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Right Of Triangle (Front)
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Right)
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Left Of Triangle (Right)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Right Of Triangle (Right)
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Back)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Left Of Triangle (Back)
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Right Of Triangle (Back)
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        gl.glVertex3f(0.0f, 1.0f, 0.0f);    // Top Of Triangle (Left)
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Left Of Triangle (Left)
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Right Of Triangle (Left)
	        gl.glEnd();                         // Finished Drawing The Triangle
	        
	        rot += 0.01;
	        gl.glLoadIdentity();

	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

	  /*  
	public void init(GLDrawable gLDrawable) {
		final GL gl = glDrawable.getGL();
        final GLU glu = glDrawable.getGLU();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0f, 1.0f, -1.0f, 1.0f); // drawing square
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }*/
	
}
