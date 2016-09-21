package helloOpenGL;






import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;





public class JoglEventListener implements GLEventListener, KeyListener {
	
	float[] vertices={5.97994f, -0.085086f, -0.010798f, 
			5.97994f, 10.0043f, -0.010798f, 
			7.99077f, 10.0043f, -0.010798f, 
			7.99077f, 11.3449f, -0.010798f, 
			-0.405339f, 11.3449f, -0.010798f, 
			-0.405339f, 9.98083f, -0.010798f, 
			1.65252f, 9.98083f, -0.010798f, 
			1.65252f, 0.549879f, -0.010798f, 
			-0.722839f, 0.549879f, -0.010798f, 
			-0.722839f, -1.69612f, -0.010798f, 
			2.6168f, -1.69612f, -0.010798f, 
			-7.24925f, 0.42055f, -0.010798f, 
			-9.35415f, 0.42055f, -0.010798f, 
			-9.35415f, 10.0043f, -0.010798f, 
			-7.37859f, 10.0043f, -0.010798f, 
			-7.37859f, 11.3802f, -0.010798f, 
			-15.8217f, 11.3802f, -0.010798f, 
			-15.8217f, 9.99258f, -0.010798f, 
			-13.8109f, 9.99258f, -0.010798f, 
			-13.8109f, -0.061591f, -0.010798f, 
			-10.2361f, -1.73139f, -0.010798f, 
			-7.26099f, -1.73139f, -0.010798f, 
			-6.1909f, 0.855631f, -0.010798f, 
			-8.11942f, 0.855631f, -0.010798f, 
			-8.11942f, 2.31379f, -0.010798f, 
			0.217914f, 2.31379f, -0.010798f, 
			0.217914f, 0.926204f, -0.010798f, 
			-1.73415f, 0.926204f, -0.010798f, 
			-1.73415f, -4.10675f, -0.010798f, 
			9.23724f, 0.937952f, -0.010798f, 
			7.26169f, 0.937952f, -0.010798f, 
			7.26169f, 2.38434f, -0.010798f, 
			15.6696f, 2.38434f, -0.010798f, 
			15.6696f, 1.00851f, -0.010798f, 
			14.964f, 1.00851f, -0.010798f, 
			7.75558f, -2.44873f, -0.010798f, 
			14.4231f, -9.36318f, -0.010798f, 
			16.0576f, -9.36318f, -0.010798f, 
			16.0576f, -10.6685f, -0.010798f, 
			7.62625f, -10.6685f, -0.010798f, 
			7.62625f, -9.33965f, -0.010798f, 
			9.67236f, -9.33965f, -0.010798f, 
			4.49827f, -3.90687f, -0.010798f, 
			-1.35784f, -6.59973f, -0.010798f, 
			-1.35784f, -9.3279f, -0.010798f, 
			0.217914f, -9.3279f, -0.010798f, 
			0.217914f, -10.6919f, -0.010798f, 
			-8.22526f, -10.6919f, -0.010798f, 
			-8.22526f, -9.32786f, -0.010798f, 
			-6.20266f, -9.32786f, -0.010798f};
	int[] indices={3, 2, 3, 1, 
			3, 1, 3, 6, 
			3, 1, 6, 10, 
			3, 10, 6, 7, 
			3, 10, 7, 8, 
			3, 4, 5, 6, 
			3, 4, 6, 3, 
			3, 10, 8, 9, 
			3, 1, 10, 0, 
			3, 13, 14, 15, 
			3, 13, 15, 18, 
			3, 13, 18, 20, 
			3, 13, 20, 12, 
			3, 16, 17, 18, 
			3, 16, 18, 15, 
			3, 12, 20, 21, 
			3, 12, 21, 11, 
			3, 20, 18, 19, 
			3, 49, 22, 44, 
			3, 44, 22, 28, 
			3, 44, 28, 43, 
			3, 43, 28, 29, 
			3, 43, 29, 42, 
			3, 42, 29, 35, 
			3, 42, 35, 41, 
			3, 41, 35, 36, 
			3, 41, 36, 38, 
			3, 38, 36, 37, 
			3, 39, 40, 41, 
			3, 39, 41, 38, 
			3, 29, 30, 32, 
			3, 29, 32, 34, 
			3, 29, 34, 35, 
			3, 46, 47, 49, 
			3, 46, 49, 44, 
			3, 46, 44, 45, 
			3, 22, 23, 25, 
			3, 22, 25, 27, 
			3, 22, 27, 28, 
			3, 25, 23, 24, 
			3, 27, 25, 26, 
			3, 49, 47, 48, 
			3, 32, 30, 31, 
			3, 34, 32, 33};
	
	double backrgb[] = {0,0,1,1};
	double backrgb_rates[] = {0.0005, 0.0010, 0, 0};
	double circle_radius = 5;
	
	double x_translation = 0;
	double y_translation = 0;

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
	        glu.gluPerspective(45.0f, h, 1.0, 200.0);
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        gl.glTranslatef(0.0f, 0.0f, -40.0f);
	    }

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor((float)backrgb[0], (float)backrgb[1], 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			
			backrgb[0]+=backrgb_rates[0];
			backrgb[1]+=backrgb_rates[1];
			
			if (backrgb[0] > 1) backrgb_rates[0] = -0.0005;
			if (backrgb[0] < 0) backrgb_rates[0] = 0.0005;
			if (backrgb[1] > 1) backrgb_rates[1] = -0.0010;
			if (backrgb[1] < 0) backrgb_rates[1] = 0.0010;
			
			
			// =============================================
			// draw your content in this function
			//
			// =============================================
	        // =============================================
			
			gl.glBegin(GL.GL_LINE_LOOP); //Line loop to draw a circle
			gl.glColor3d(1,0.5,0);
			double circle_vertices = 45; //How many vertices in the circle? 45 should be good.
			for (int i=0; i<circle_vertices; i++) {
				double theta = 2 * Math.PI * i/circle_vertices;
				double x_coord = circle_radius * Math.sin(theta);
				double y_coord = circle_radius * Math.cos(theta);
				
				gl.glVertex2d(x_coord + x_translation, y_coord + y_translation);
			}
			gl.glEnd();
			
			gl.glBegin(GL.GL_TRIANGLES); // Predefined Shape
			gl.glColor3d(0,0.2,0.8); //UK blue?
			for (int i=1; i<indices.length; i++ ) {
				if (i%4 != 0) // dodge all the 3s
					gl.glVertex3f(vertices[3*indices[i]], vertices[3*indices[i]+1], vertices[3*indices[i]+2]);
			}
			gl.glEnd();
	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
		    
		    // To control the circle, use WASD.
		    // To decrease the circle's size, use O. To increase, P.
		    if (key == 's')
		    	y_translation -= 1;
		    else if (key == 'w')
		    	y_translation += 1;
		    else if (key == 'a')
		    	x_translation -= 1;
		    else if (key == 'd')
		    	x_translation += 1;
		    else if (key == 'o')
		    	circle_radius /= 1.5;
		    else if (key == 'p')
		    	circle_radius *= 1.5;
		    
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	
}



