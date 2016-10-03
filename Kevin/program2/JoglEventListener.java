package program2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.jogamp.opengl.*;
//import com.jogamp.opengl.glu.GLU;




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
	float [] clickDif = {0.0f, 0.0f, 0.0f};
	char transCom = 't';
	char mouseButt = 'l';
	float Xclick, Yclick;
	
	/*
	 * Custom variables for mouse drag operations 
	 */
	int windowWidth, windowHeight;
	float orthoX=40;
	float tVal_x, tVal_y, rVal_x, rVal_y, rVal;
	double rtMat[] = new double[16];
	int saveRTnow=0, mouseDragButton=0;
	
	float focalLength = 10.0f;
	float r11 = 1.0f, r12 = 0.0f, r13 = 0.0f, tx = 0.0f,
	      r21 = 0.0f, r22 = 1.0f, r23 = 0.0f, ty = 0.0f,
	      r31 = 0.0f, r32 = 0.0f, r33 = 1.0f, tz = 0.0f;

    //private GLU glu = new GLU();

	
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
	        //final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	       gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
	        //glu.gluPerspective(45.0f, h, 1, 100000.0);

	    }
	    
	    public void project(float[] vertices_in, float[] vertices_out) {
	    
	        
	    	float[] projMatrix = new float[] 
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
	    	if(transCom == 't' && (clickDif[0] != 0 || clickDif[1] != 0)){
	    		translation(clickDif);
	    	}
	    	else if (transCom == 'r' && (clickDif[0] != 0 || clickDif[1] != 0)){
	    		if(mouseButt == 'l'){
	    			if(clickDif[0] > 5.0f){
	    				clickDif[0] = 5.0f;
	    			}
	    			else if(clickDif[0] < -5.0f){
	    				clickDif[0] = -5.0f;
	    			}
	    			
	    			if(clickDif[1] > 5.0f){
	    				clickDif[1] = 5.0f;
	    			}
	    			else if(clickDif[1] < -5.0f){
	    				clickDif[1] = -5.0f;
	    			}
	    			//rotation around y based on x coord.
	    			rotation('y', (float)Math.toRadians(-clickDif[0]));
	    			//rotation around x based on y coord.
	    			rotation('x', (float)Math.toRadians(clickDif[1]));
	    		}
	    		else if(mouseButt == 'r'){
	    			//float rotationFactor = (float) Math.sqrt((clickDif[0] * clickDif[0]) + (clickDif[1] * clickDif[1]));
	    			float rotationFactor = 5.0f;
	    			// if click Difference from P to drag point is negative make it negative.
	    			if(clickDif[0] < 0 || clickDif[1] < 0){
	    				rotationFactor = -rotationFactor;
	    			}
	    			rotation('z', (float)Math.toRadians(rotationFactor));
	    		}
	    	}
	    	else if (transCom == 's' && (clickDif[0] != 0 || clickDif[1] != 0)){
	    		scale(vertices);
	    	}
	    	clickDif = new float[] {0.0f, 0.0f, 0.0f};	
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
			// transform(...)
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
//		    char key= e.getKeyChar();
//			System.out.printf("Key typed: %c\n", key); 
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_T) {
				//Switch to translation
				transCom='t';
				System.out.println("Transformation Switched to: Translation");
			}
			if (e.getKeyCode() == KeyEvent.VK_R) {
				//Switch to rotation
				transCom='r';
				System.out.println("Transformation Switched to: Rotation");
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				//Switch to scale
				transCom='s';
				System.out.println("Transformation Switched to: Scale");
			}
			if (e.getKeyCode() == KeyEvent.VK_G) {
				//Zoom in
				focalLength += 5.0f;
				System.out.println("Zoom in");
			}
			if (e.getKeyCode() == KeyEvent.VK_H) {
				//Zoom out
				focalLength -= 5.0f;
				System.out.println("Zoom out");
			}
			if (e.getKeyCode() == KeyEvent.VK_0) {
				//Reset UK LOGO
				vertices= new float[] {5.97994f, -0.085086f, -0.010798f, 
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
				System.out.println("UK LOGO RESET");
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {	
     		float Xdrag = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float Ydrag = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			clickDif[0] = Xdrag - Xclick;
			clickDif[1] = Ydrag - Yclick;
			//System.out.printf("Point difference: (%.3f, %.3f)\n", clickDif[0], clickDif[1]);
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				mouseButt = 'l';
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				mouseButt = 'r';
			}
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
			 //Coordinates 
			Xclick = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			Yclick = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
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

		public void translation(float[] trans){
			float[][] matrix = {{1.0f, 0.0f, 0.0f, trans[0]},
		                       {0.0f, 1.0f, 0.0f, trans[1]},
		                       {0.0f, 0.0f, 1.0f, trans[2]},
		                       {0.0f, 0.0f, 0.0f, 1.0f}};
			matrixMult(matrix);
			System.out.println("Translation by (" + trans[0] + ", " + trans[1] + ", " + trans[2] + ")");
		}
		
		public void scale(float[] vertices){
			float scaleFactor;
			if (clickDif[0] > 1 || clickDif[1] > 1){
				scaleFactor = 1.1f;
			}
			else if(clickDif[0] < -1 || clickDif[1] < -1) {
				scaleFactor = 0.9f;
			}
			else {
				scaleFactor = 1.0f;
			}
			
			float[][] matrix = {{scaleFactor, 0.0f, 0.0f, 0.0f},
					            {0.0f, scaleFactor, 0.0f, 0.0f},
					            {0.0f, 0.0f, scaleFactor, 0.0f},
					            {0.0f, 0.0f, 0.0f, 1.0f}};
			
			//multiple each vertex with the scale matrix
			matrixMult(matrix);
			
    		System.out.println("Scale: " + scaleFactor);
		}
		 //function that performs a rotation on a 3d shape.
		 public void rotation(char rotType, float theta) {
			 float [][] matrix = new float [4][4];
			 if(rotType == 'z'){
			   matrix = new float[][] {{(float) Math.cos(theta), (float) -Math.sin(theta), 0.0f, 0.0f},
					                  {(float) Math.sin(theta), (float) Math.cos(theta), 0.0f, 0.0f},
					                  {0.0f, 0.0f, 1.0f, 0.0f},
					                  {0.0f, 0.0f, 0.0f, 1.0f}};
			 }
			 else if(rotType == 'x') {
			   matrix = new float[][] {{1.0f, 0.0f, 0.0f, 0.0f},
			                          {0.0f, (float) Math.cos(theta), (float) -Math.sin(theta), 0.0f},
			                          {0.0f, (float) Math.sin(theta), (float) Math.cos(theta), 0.0f},
			                          {0.0f, 0.0f, 0.0f, 1.0f}};
			 }
			 else if(rotType == 'y'){
			   matrix = new float[][] {{(float) Math.cos(theta), 0.0f, (float) Math.sin(theta), 0.0f},
                                     {0.0f, 1.0f, 0.0f, 0.0f},
                                     {(float) -Math.sin(theta), 0.0f, (float) Math.cos(theta), 0.0f},
                                     {0.0f, 0.0f, 0.0f, 1.0f}};		 
			 }
			 
			 matrixMult(matrix);
			 System.out.println("Rotation type " + rotType + " by degrees:" + Math.toDegrees(theta) + " complete");
		 }
		 
		 //Matrix multiplication function
		 public void matrixMult(float [][] matrix){
			 float[] newvertex = new float [4];
			 float[] samplevertex = new float [4];
			 
			 //apply to each vertex in the array of vertices
			 for (int k=0; k < vertices.length; k+=3){
				 float [] vertex = {vertices[k], vertices[k+1], vertices[k+2], 1};
				 
				 //perform the matrix multiplication on the singular vertex
				 for (int i=0; i < matrix.length; i++){
					 for (int j=0; j < matrix.length; j++){
						 samplevertex[j] = matrix[i][j] * vertex[j];
					 } 
					 newvertex[i] = samplevertex[0] + samplevertex[1] + samplevertex[2] + samplevertex[3];
				 }
			     
				 //enter the result into the array of vertices
				 vertices[k] = newvertex[0];
				 vertices[k+1] = newvertex[1];
				 vertices[k+2] = newvertex[2];
			     //System.out.println(corners[t] + ": " + vertices[k] + ", " + vertices[k+1] + ", " + vertices[k+2]);
			 }
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


