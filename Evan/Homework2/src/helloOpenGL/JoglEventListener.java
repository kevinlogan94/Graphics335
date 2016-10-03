package helloOpenGL;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.DoubleBuffer;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;




public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
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
	float backrgb[] = new float[4]; 
	float rot; 
	
	float [] drag_amount = {0.0f, 0.0f};
	float [] mouse_initial = {0.0f, 0.0f};
	float [] mouse_last = {0.0f, 0.0f};
	char current_transformation = 's';
	char mouse_button = 'l';
	
	/*
	 * Custom variables for mouse drag operations 
	 */
	int windowWidth, windowHeight;
	float orthoX=40;
	float tVal_x, tVal_y, rVal_x, rVal_y, rVal;
	double rtMat[] = new double[16];
	int mouseX0, mouseY0;
	int saveRTnow=0, mouseDragButton=0;
	
	float focalLength = 10.0f;
	float r11 = 1.0f, r12 = 0.0f, r13 = 0.0f, tx = 0.0f,
	      r21 = 0.0f, r22 = 1.0f, r23 = 0.0f, ty = 0.0f,
	      r31 = 0.0f, r32 = 0.0f, r33 = 1.0f, tz = 0.0f;

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

	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        
	        
	        
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	    	windowWidth = width;
	    	windowHeight = height;
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	       gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
	        //glu.gluPerspective(45.0f, h, 1, 100000.0);

	    }
	    
	    public void project(float[] vertices_in, float[] vertices_out) {
	    
	        
	    	float[] projMatrix = 
		    	{
		    	   1, 0, 0, 0,
		    	   0, 1, 0, 0,
		    	   0, 0, 0, 0,
		    	   0,   0,   1.0f/focalLength, 1
		    	};
		    	
		    	for(int i = 0; i < vertices_in.length; i += 3){
		    		float tempZ = vertices_in[i+2] + 10;  // this translation in Z is needed to pull the camera away from the object. 
		    		// don't change the above line unless you are sure about what you are doing.
		    		
		    		vertices_out[i] = (projMatrix[0] * vertices_in[i] + projMatrix[1] * vertices_in[i + 1] + projMatrix[2] * tempZ + projMatrix[3]);
		    		vertices_out[i + 1] = projMatrix[4] * vertices_in[i] + projMatrix[5] * vertices_in[i + 1] + projMatrix[6] * tempZ+ projMatrix[7];
		    		vertices_out[i + 2] = projMatrix[8] * vertices_in[i] + projMatrix[9] * vertices_in[i + 1] + projMatrix[10] * tempZ+ projMatrix[11];
		    	   
		    		float temp = projMatrix[12] * vertices_in[i] + projMatrix[13] * vertices_in[i + 1] + projMatrix[14] * tempZ+ projMatrix[15];
		    	   
		    	    vertices_out[i]   = vertices_out[i]   / temp;
		    	    vertices_out[i+1] = vertices_out[i+1] / temp;
		    	    vertices_out[i+2] = vertices_out[i+2] / temp;
	    	
		    	}
	    }
	    
	    public void transform(){
	    	
	    	// perform your transformation
	    	
	    	if (current_transformation == 's' && (drag_amount[0] != 0 || drag_amount[1] != 0)) {
	    		scale(vertices, drag_amount);
	    	} else if (current_transformation == 't' && (drag_amount[0] != 0 || drag_amount[1] != 0)) {
	    		translate(vertices, drag_amount);
	    	} else if (current_transformation == 'r' && (drag_amount[0] != 0 || drag_amount[1] != 0)) {
	    		if (mouse_button == 'l') 	
	    			rotate_xy(vertices, drag_amount);	// rotate about x or y
				else 
					rotate_z(vertices, drag_amount);	// rotate about z
	    	}
	    	
	    
//	    	int length = vertices_in.length;
	    	float[] transformMatrix = 
		    	{
		    	   r11, r12, r13, tx,
		    	   r21, r22, r23, ty,
		    	   r31, r32, r33, tz,
		    	   0,   0,   0, 1
		    	};
	    	

	    	drag_amount[0] = 0f;
	    	drag_amount[1] = 0f;
	    }

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
			
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
	    	gl.glLoadIdentity();
	 	
			float[] vertices_new = new float[vertices.length];

			// call the transform function here
			transform();
			
			project(vertices, vertices_new);
	        
			gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
        	for(int i=0; i<44; i++) {
        		gl.glColor3f(0.7f, 0.7f, 0.7f);
        		
        		gl.glVertex3f(vertices_new[(indices[i*4+1])*3],
        				vertices_new[(indices[i*4+1])*3+1],
        				vertices_new[(indices[i*4+1])*3+2]);
        		gl.glVertex3f(vertices_new[(indices[i*4+2])*3],
        				vertices_new[(indices[i*4+2])*3+1],
        				vertices_new[(indices[i*4+2])*3+2]);
        		gl.glVertex3f(vertices_new[(indices[i*4+3])*3],
        				vertices_new[(indices[i*4+3])*3+1],
        				vertices_new[(indices[i*4+3])*3+2]);
        		
        		/*gl.glVertex3f(vertices[(indices[i*4+1])*3],
        					  vertices[(indices[i*4+1])*3+1],
        					  vertices[(indices[i*4+1])*3+2]);
        		gl.glVertex3f(vertices[(indices[i*4+2])*3],
  					  		  vertices[(indices[i*4+2])*3+1],
  					  		  vertices[(indices[i*4+2])*3+2]);
        		gl.glVertex3f(vertices[(indices[i*4+3])*3],
  					  		  vertices[(indices[i*4+3])*3+1],
  					  		  vertices[(indices[i*4+3])*3+2]);*/
        	}
	        gl.glEnd();                         // Finished Drawing The Triangle
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
			System.out.printf("Key typed: %c\n", key); 
			
			if (key == 's') {			// Scale
				current_transformation = 's';
				System.out.println("Now Scaling");
			} else if (key == 't') {	// Translate
				current_transformation = 't';
				System.out.println("Now Translating");
			} else if (key == 'r') {	// Rotate
				current_transformation = 'r';
				System.out.println("Now Rotating");
			} else if (key == '0') {	// Reset
				reset_logo();
			} else if (key == 'g') {	// Zoom in
				focalLength *= 2;
			} else if (key == 'h') {	// Zoom out
				focalLength /= 2;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			
			
			
			drag_amount[0] = XX - mouse_last[0];
			drag_amount[1] = YY - mouse_last[1];
			
			mouse_last[0] = XX;
			mouse_last[1] = YY;
			
			System.out.println("changeX: " + drag_amount[0] + " changeY: " + drag_amount[1]);
			
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			
			mouse_initial[0] = XX; mouse_last[0] = XX;
			mouse_initial[1] = YY; mouse_last[1] = YY;
			
			System.out.printf("Point clicked: (%.3f, %.3f)\n", XX, YY);
			
			mouseX0 = e.getX();
			mouseY0 = e.getY();
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				mouse_button = 'l';
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				mouse_button = 'r';
			}
		}
		
		//resets the UK logo
		public void reset_logo() {
			vertices = new float[] {5.97994f, -0.085086f, -0.010798f, 
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
		}
		
		public void apply_transformation(float [][] transformation_matrix) {
			
			//for each vertex in 'vertices'
			for (int i=0; i<vertices.length; i+=3) {
				
				float[] current_vertex = {vertices[i], vertices[i+1], vertices[i+2], 1};	// homogenized vertex
				
				for (int j=0; j<transformation_matrix.length; j++) {
					float post_transformation_point = 0.0f;
					for (int k=0; k<transformation_matrix[j].length; k++) {
						post_transformation_point += transformation_matrix[j][k] * current_vertex[k];	
					}
					if (j != 3)
						vertices[i+j] = post_transformation_point;
				}
			}
		}
		
		public void translate(float[] vertices, float[] drag_amount) {
			float[][] translation_matrix = {{1f, 0f, 0f, drag_amount[0]},
											{0f, 1f, 0f, drag_amount[1]},
											{0f, 0f, 1f, 0f}, // might need to change that last 0f to translate in z direction
											{0f, 0f, 0f, 1f}};
			apply_transformation(translation_matrix);
			System.out.println("translated");
		}
		
		public void scale(float[] vertices, float[] drag_amount) {
			float scale_factor = (float)Math.sqrt((double)(drag_amount[0]*drag_amount[0] + drag_amount[1]*drag_amount[1]))/8;
			//make the scale factor negative if you drag negative
			if (drag_amount[0] < 0 || drag_amount[1] < 0)
				scale_factor = 1f - scale_factor;
			else
				scale_factor = 1f + scale_factor;
			
			float[][] translation_matrix = {{scale_factor, 0f, 0f, 0f},
											{0f, scale_factor, 0f, 0f},
											{0f, 0f, scale_factor, 0f},
											{0f, 0f, 0f, 1f}};
			apply_transformation(translation_matrix);
		}
		
		public void rotate_xy(float[] vertices, float[] drag_amount) {
			float theta_x = drag_amount[1]/8;
			float theta_y = -drag_amount[0]/8;

			//	rotate about x
			float[][] transformation_matrix_x = 	{{1f, 0f, 0f, 0f},
													{0f, (float)Math.cos(theta_x), -(float)Math.sin(theta_x), 0f},
													{0f, (float)Math.sin(theta_x), (float)Math.cos(theta_x), 0f},
													{0f, 0f, 0f, 1f}};
			apply_transformation(transformation_matrix_x);
			//	rotate about y
			float[][] transformation_matrix_y = 	{{(float)Math.cos(theta_y), 0f, (float)Math.sin(theta_y), 0f},
													{0f, 1f, 0f, 0f},
													{-(float)Math.sin(theta_y), 0f, (float)Math.cos(theta_y), 0f},
													{0f, 0f, 0f, 1f}};
			apply_transformation(transformation_matrix_y);
			
		}
		public void rotate_z(float[] vertices, float[] drag_amount) {
			float theta_z = drag_amount[0]/8;
			
			//rotate about z
			float[][] transformation_matrix_z = 	{{(float)Math.cos(theta_z), -(float)Math.sin(theta_z), 0f, 0f},
													{(float)Math.sin(theta_z), (float)Math.cos(theta_z), 0f, 0f},
													{0f, 0f, 1f, 0f},
													{0f, 0f, 0f, 1f}};
			apply_transformation(transformation_matrix_z);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
}



