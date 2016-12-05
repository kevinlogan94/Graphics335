package main;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;




public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	
	float backrgb[] = new float[4]; 

	int windowWidth, windowHeight;
	float orthoX=40;

	int mouseX0, mouseY0;	
	float picked_x = 0.0f, picked_y = 0.0f;
	
	float focalLength = 30.0f;
	
	//angle of rotation
	float rotateAngleX = 0.0f;
	float rotateAngleY = 0.0f;
	float rotateAngleZ = 0.0f;
	
	//translation
	float translateX = 0.0f;
	float translateY = 0.0f;
	float translateZ = 0.0f;
	
	//scale
	float scaleX = 1.0f;
	float scaleY = 1.0f;
	float scaleZ = 1.0f;

	//diffuse light color variables
	float dlr = 1.0f;
	float dlg = 1.0f;
	float dlb = 1.0f;
	float dlw = 0.0f;

	//ambient light color variables
	float alr = 1.0f;
	float alg = 0.0f;
	float alb = 1.0f;

	//light position variables
	float lx_0 = 0.0f;
	float ly_0 = 0.0f;
	float lz_0 = 0.0f; // light0 is at the origin
	float lw_0 = 0.0f;
	
	//light position variables
	float lx_1 = 0.0f;
	float ly_1 = 5.0f;
	float lz_1 = 0.0f;
	float lw_1 = 0.0f;
	
	
	/*** Define material property  ***/ 
	
	float redDiffuseMaterial []    = { 1.0f, 0.0f, 0.0f }; //set the material to red
	float whiteSpecularMaterial [] = { 1.0f, 1.0f, 1.0f }; //set the material to white
	float greenEmissiveMaterial [] = { 0.0f, 1.0f, 0.0f }; //set the material to green
	float whiteSpecularLight []    = { 1.0f, 1.0f, 1.0f }; //set the light specular to white
	float redSpecularLight []    = { 1.0f, 0.1f, 0.1f }; //set the light specular to white
	
	float blankMaterial[]     = { 0.0f, 0.0f, 0.0f }; //set the material to black
	float grayMaterial[]     = { 0.7f, 0.7f, 0.7f }; //set the material to gray
	float mShininess[]        = { 20 }; //set the shininess of the material

	
	
	boolean diffuse_flag  = false;
	boolean specular_flag = false;
	
	boolean smooth_flag = true;

    private GLU glu = new GLU();
	
    private GLUT glut = new GLUT();
	
	public void drawTeapot(final GL2 gl){
		gl.glPushMatrix();
		
		// set the shading model GL_SMOOTH /GL_FLAT
		if(smooth_flag){
			gl.glShadeModel(GL2.GL_SMOOTH);
		}
		else{
			gl.glShadeModel(GL2.GL_FLAT);
		}
		
		
		// set the material property
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, grayMaterial, 0);
	
		
		
		glut.glutSolidTeapot(5);
		
		gl.glPopMatrix();
		
	}
	
	public void drawCube(final GL2 gl){
		gl.glPushMatrix();
		
		// set the shading model GL_SMOOTH /GL_FLAT
		if(smooth_flag){
			gl.glShadeModel(GL2.GL_SMOOTH);
		}
		else{
			gl.glShadeModel(GL2.GL_FLAT);
		}
		
		
		// set the material property
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, grayMaterial, 0);
	
		
		
		glut.glutSolidCube(5);
		
		gl.glPopMatrix();

	}
	
	public void drawSphere(final GL2 gl){
		gl.glPushMatrix();
		
		// set the shading model GL_SMOOTH /GL_FLAT
		if(smooth_flag){
			gl.glShadeModel(GL2.GL_SMOOTH);
		}
		else{
			gl.glShadeModel(GL2.GL_FLAT);
		}
		
		
		// set the material property
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, grayMaterial, 0);
	
		
		
		glut.glutSolidSphere(5, 20, 20);
		
		gl.glPopMatrix();
	
	}

	
	    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
		 
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {
	        GL2 gl = gLDrawable.getGL().getGL2();
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        // Really Nice Perspective Calculations
	        //gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	        
	        gl.glEnable(GL2.GL_LIGHTING); // enable lighting
	        
	        gl.glEnable(GL2.GL_LIGHT0); // enable light0
	        gl.glEnable(GL2.GL_LIGHT1); 
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        
	        
	        
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	    	windowWidth = width;
	    	windowHeight = height;
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	       // gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
	        glu.gluPerspective(45.0f, h, 1, 100);

	    }
	    
	    

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
			
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
	    	gl.glLoadIdentity();
	    	
			float diffuseLight[] = {dlr, dlg, dlb}; // diffuse light property
			//float ambientLight[] = {alr, alg, alb}; // ambient light property
			
			float ligthtPosition_0[] = {lx_0, ly_0, lz_0, lw_0}; // light position
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0); // set light0 as diffuse light with related property
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, ligthtPosition_0, 0); // set light0 position
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteSpecularLight, 0);
			
			
			
			
			glu.gluLookAt(0.0, 0.0, 10, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 

	    	drawSphere(gl);
	    	
	    	gl.glFlush();
	    	
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
			
			switch(key)
			{
			// rotations
			case 'r':
				rotateAngleX += 5.0f;
				if(rotateAngleX >= 360.0f)
					rotateAngleX -= 360.0f;
				break;
			case 'R':
				rotateAngleX -= 5.0f;
				if(rotateAngleX <= 0)
					rotateAngleX += 360;
				break;
				
			case 't':
				rotateAngleY += 5.0f;
				if(rotateAngleY >= 360.0f)
					rotateAngleY -= 360.0f;
				break;
			case 'T':
				rotateAngleY -= 5.0f;
				if(rotateAngleY <= 0)
					rotateAngleY += 360;
				break;
			
			case 'y':
				rotateAngleZ += 5.0f;
				if(rotateAngleZ >= 360.0f)
					rotateAngleZ -= 360.0f;
				break;
			case 'Y':
				rotateAngleZ -= 5.0f;
				if(rotateAngleZ <= 0)
					rotateAngleZ += 360;
				break;
			
			// translations
			case 'q':
				translateX += 2.0f;
				break;
			case 'Q':
				translateX -= 2.0f;
				break;
			case 'w':
				translateY += 2.0f;
				break;
			case 'W':
				translateY -= 2.0f;
				break;
			case 'e':
				translateZ += 2.0f;
				break;
			case 'E':
				translateZ -= 2.0f;
				break;
			
			// scales
			case 'a':
				scaleX *= 1.2f;
				break;
			case 'A':
				scaleX /= 1.2f;
				break;
			case 's':
				scaleY *= 1.2f;
				break;
			case 'S':
				scaleY /= 1.2f;
				break;
			case 'd':
				scaleZ *= 1.2f;
				break;
			case 'D':
				scaleZ /= 1.2f;
				break;
				
				
				
			
			case 'g':
			case 'G':
				focalLength += 5;
				
				break;
				
			case 'h':
			case 'H':
				focalLength -= 5;
				
				break;
				
			default:
				
				break;
			
			
			
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
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			
			
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Your window get focus."); 
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			picked_x = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			picked_y = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			
			System.out.printf("Point clicked: (%.3f, %.3f)\n", picked_x, picked_y);
			
			mouseX0 = e.getX();
			mouseY0 = e.getY();
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				
				
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) { // cursor enter the window
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) { // cursor exit the window
			// TODO Auto-generated method stub
			
		}


	
}



