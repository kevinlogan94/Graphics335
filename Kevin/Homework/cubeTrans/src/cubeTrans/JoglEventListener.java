package cubeTrans;

import com.jogamp.opengl.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class JoglEventListener implements GLEventListener, KeyListener {
	float[] vertices={
			//front
			2.0f, 2.0f, 1.0f,//bottom left A 
			3.0f, 2.0f, 1.0f,//bottom right B
			3.0f, 3.0f, 1.0f,//top right C    
			2.0f, 3.0f, 1.0f,//top left  D
			//back
			2.0f, 2.0f, 0.0f, //bottom left E
			3.0f, 2.0f, 0.0f,//bottom right F
			3.0f, 3.0f, 0.0f,//top right G
			2.0f, 3.0f, 0.0f,//top left H 
	};
	
	String[] corners={"A","B","C","D","E","F","G","H"};
	
	int[] indices={
			3, 2, 1, 0,
			7, 6, 2, 3,
			2, 6, 5, 1,
			7, 3, 0, 4,
			4, 5, 1, 0,
			7, 4, 5, 4};
	
	float alpha;
	float beta;
	float[] trans;
	
    float rot; //rotation
    float backrgb[] = new float[4];

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

			gl.glClearColor(0, 0, 0, 0);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);//clear the screen

			backrgb[0]+=0.0005;
			if (backrgb[0]> 1) backrgb[0] = 0; 

			//gl.glClearColor(0, 0, 0, 0); //set screen to clear to black
			gl.glTranslatef(-2.0f, -2.0f, -10.0f);//Set up the grid 
			
			gl.glColor3f(1.0f, 1.0f, 1.0f); //Set color to white
			gl.glBegin(GL2.GL_LINES); //Set drawing method to line loop  
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(10.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 10.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 10.0f);
			gl.glEnd();
	         
	        //Draw Cube
			//gl.glRotatef(rot, rot, rot, 0.0f); //Allow shape to rotate
	        gl.glBegin(GL2.GL_QUADS); //Set drawing method to line loop    
	        gl.glColor3f(1.0f, 0.0f, 1.0f); //Set color to white
	        for(int i = 0; i < indices.length; i++){
	        	gl.glVertex3f(vertices[indices[i]*3], vertices[indices[i]*3+1], vertices[indices[i]*3+2]);
	        }
	        
	        gl.glEnd();
	        
	        rot += 0.5;
	        gl.glLoadIdentity();//Replace matrix with identity matrix. Load the circle.

	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_1) {
				//perform translation so A is {0,0,0}
				trans = new float [] {-2, -2, -1};
				translation(vertices, trans);
			}
			if (e.getKeyCode() == KeyEvent.VK_2) {
				//attain alpha then rotate Cube
				alpha = (float) Math.atan(vertices[18]/vertices[19]);
				rotation('z', alpha, vertices);
			}
			if (e.getKeyCode() == KeyEvent.VK_3) {
				//attain beta then rotate Cube
				beta = (float) Math.asin(1/Math.sqrt(3));
				rotation('x', beta, vertices);
			}
			if (e.getKeyCode() == KeyEvent.VK_4) {
				//Rotate Cube 45 degrees on AG.
				rotation('y', (float) Math.toRadians(45), vertices);
			}
			if (e.getKeyCode() == KeyEvent.VK_5) {
				//undo beta
				rotation('x', -beta, vertices);
			}
			if (e.getKeyCode() == KeyEvent.VK_6) {
				//undo alpha
				rotation('z', -alpha, vertices);
			}
			if (e.getKeyCode() == KeyEvent.VK_7) {
				//perform translation so A is {0,0,0}
				trans = new float [] {2, 2, 1};
				translation(vertices, trans);
			}
			if (e.getKeyCode() == KeyEvent.VK_9) {
				
				//DO ALL!!!!!!
				
				//perform translation so A is {0,0,0}
				trans = new float [] {-2, -2, -1};
				translation(vertices, trans);
				
				//attain alpha then rotate Cube
				alpha = (float) Math.atan(1);
				rotation('z', alpha, vertices);
				
				//attain beta then rotate Cube
				beta = (float) Math.asin(1/Math.sqrt(3));
				rotation('x', beta, vertices);
				
				//Rotate Cube 45 degrees on AG.
				rotation('y', (float) Math.toRadians(45), vertices);
				
				//undo beta
				rotation('x', -beta, vertices);
				
				//undo alpha
				rotation('z', -alpha, vertices);
				
				//perform translation so A is {0,0,0}
				trans = new float [] {2, 2, 1};
				translation(vertices, trans);
				
			}
			

		}
		 
		 public void keyReleased(KeyEvent e) {
		 }

		 public void keyTyped(KeyEvent e) {
		 }
		 
		 public void translation(float[] vertices, float[] trans){
			 
		      for (int i = 0, j = 0; i < vertices.length; i+=3, j++){ 
		    	  vertices[i] = (vertices[i]+trans[0]);
		    	  vertices[i+1] = (vertices[i+1]+trans[1]);
		    	  vertices[i+2] = (vertices[i+2]+trans[2]);
		    	  System.out.println(corners[j] + ": " + vertices[i] + ", " + vertices[i+1] + ", " + vertices[i+2]);
		      }
		      System.out.println("Translation by " + trans[0] + ", " + trans[1] + ", " + trans[2]);
			 
		 }
		 
		 //function that performs a rotation on a 3d shape.
		 public void rotation(char rotType, float theta, float[] vertices) {
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
			 
			 float[] newvertex = new float [4];
			 float[] samplevertex = new float [4];
			 
			 //apply to each vertex in the array of vertices
			 for (int k=0, t=0; k < vertices.length; k+=3, t++){
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
			     System.out.println(corners[t] + ": " + vertices[k] + ", " + vertices[k+1] + ", " + vertices[k+2]);
			 }
			 System.out.println("Rotation type " + rotType + " by degrees:" + Math.toDegrees(theta) + " complete");
		 }
			
}
