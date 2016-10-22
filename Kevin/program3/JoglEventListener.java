package program3;


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
	float PI = 3.141592f;
	float Xclick, Yclick;
	float [] clickDif = {0.0f, 0.0f};
	boolean animate = true;
	
	float focalLength = 20.0f;
	
	//angle of rotation
	float rotate = 0.0f;
	float RotationExtra = 0.0f;
	
	/*** Define material property  ***/ 
	
	float redDiffuseMaterial []    = { 1.0f, 0.0f, 0.0f }; //set the material to red
	float whiteSpecularMaterial [] = { 1.0f, 1.0f, 1.0f }; //set the material to white
	float Blacklight[] = {0.0f, 0.0f, 0.0f};
	
	float Goldlight[]     = { 255.0f, 215.0f, 0.0f }; //set the material to gold
	float Darkgoldlight[] = { 218.0f, 165.0f, 32.0f};
	float blueMaterial[]     = { 0.0f, 0.0f, 255.0f }; //set the material to blue
	float royalblueMaterial[]     = { 65.0f, 105.0f, 255.0f }; //set the material to blue
	float mShininess[]        = { 20 }; //set the shininess of the material
	float eShininess[]       = {120};
	float sShininess[]		 = 	{ 30 };
	float moShininess[]	     =  { 120 };
	float lShininess[]	     =  { 120 };
	float grayMaterial[]     = { 64.0f, 64.f, 64.0f }; //set the material to gray
	float silverMaterial[]    ={192, 192, 192 };


    private GLU glu = new GLU();
    private GLUT glut = new GLUT();
	
	public void drawTeapot(final GL2 gl){
		gl.glPushMatrix();
		
		// set the shading model GL_SMOOTH /GL_FLAT
			gl.glShadeModel(GL2.GL_SMOOTH);
			//gl.glShadeModel(GL2.GL_FLAT);
		
		
		// set the material property
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, blueMaterial, 0);
	
		glut.glutSolidTeapot(5);
		
		gl.glPopMatrix();
		
	}
	
	public void drawAxes(final GL2 gl){
		gl.glPushMatrix();
		gl.glShadeModel(GL2.GL_SMOOTH);
//		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, whiteSpecularMaterial, 0);	
//		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, whiteSpecularMaterial, 0);	
//		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);	
//		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);
		gl.glBegin(GL2.GL_LINES); //Set drawing method to line loop  
		gl.glVertex3f(-50.0f, 0.0f, 0.0f);
		gl.glVertex3f(50.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, -50.0f, 0.0f);
		gl.glVertex3f(0.0f, 50.0f, 0.0f);
		gl.glVertex3f(0.0f, 0.0f, -50.0f);
		gl.glVertex3f(0.0f, 0.0f, 50.0f);
		gl.glEnd();
		
		gl.glPopMatrix();
	}
	
	public void drawEarth(float radius, final GL2 gl){
		gl.glPushMatrix();
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, blueMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, eShininess, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, blueMaterial, 0);

		
		gl.glRotatef(-12.0f, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotate, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(-5.0f, 0.0f, 0.0f);
		
		gl.glRotatef(RotationExtra, 0.0f, 1.0f, 0.0f);//Rotation Extra Credit
		
		//glut.glutSolidSphere(0.33,10,10);
		drawSphere(0.33f, 5, gl);
		
		gl.glPopMatrix();
	}
	
	public void drawMoon(float radius, final GL2 gl){
		gl.glPushMatrix();
		gl.glShadeModel(GL2.GL_SMOOTH);

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, grayMaterial, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, moShininess, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, grayMaterial, 0);

		//Match the Earth
		gl.glRotatef(-12.0f, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotate, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(-5.0f, 0.0f, 0.0f);

		//Moon Rotation
		gl.glRotatef(12.0f, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(rotate, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(1.0f, 0.0f, 0.0f);

		gl.glRotatef(RotationExtra, 0.0f, 1.0f, 0.0f);//Rotation Extra Credit

		//glut.glutSolidSphere(0.11,10,10);
		drawSphere(0.11f, 5, gl);

		gl.glPopMatrix();
	}
	
	public void drawSun(float radius, final GL2 gl){
        gl.glPushMatrix();
        gl.glShadeModel(GL2.GL_SMOOTH);
		
		// set the material property
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, Goldlight, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, Goldlight, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, sShininess, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, Goldlight, 0);
	
		gl.glRotatef(RotationExtra, 0.0f, 1.0f, 0.0f);
		drawSphere(1.0f, 5, gl);
		
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
	        gl.glEnable(GL2.GL_NORMALIZE);
	        
	        gl.glEnable(GL2.GL_LIGHT0); // enable light0
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
	        glu.gluPerspective(45.0f, h, 1, 100000.0);

	    }
	    
		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
			
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
	    	gl.glLoadIdentity();
	    	
			glu.gluLookAt(0.0, 0.0, focalLength, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 

	    	
			float diffuseLight[] = {1.0f, 1.0f, 1.0f}; // diffuse light property
			float ambientLight[] = {1.0f, 1.0f, 0.0f}; // ambient light property
			float whiteSpecularLight [] = { 1.0f, 1.0f, 1.0f }; //set the light specular to white

//			gl.glPushMatrix();
			float lightPosition_0[] = {0.0f, 0.0f, 0.0f, 1.0f}; // light position
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0); // set light0 as diffuse light with related property
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteSpecularLight, 0); // set light0 specular light
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0); // set light0 ambient light
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition_0, 0); // set light0 position
//			gl.glPopMatrix();
			
					
			drawSun(5.0f, gl);
			drawEarth(0.33f, gl);
			drawMoon(0.11f, gl);	
			drawAxes(gl);
			RotationExtra+=0.01f;
			
			if(animate == true){
				if(rotate < 360){
					rotate += 0.5f;
				}
				else{
					rotate = 0.0f;
				}
			}
	    	
	    	//drawTeapot(gl);
	    	
	    	gl.glFlush();
	    	
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {}

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			 char key= e.getKeyChar();
				System.out.printf("Key typed: %c\n", key); 
				if(key == 't') {
					animate = !animate;
				}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {		
			float Xdrag = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float Ydrag = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			clickDif[0] = Xdrag - Xclick;
			clickDif[1] = Ydrag - Yclick;
			//System.out.printf("Point difference: (%.3f, %.3f)\n", clickDif[0], clickDif[1]);
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				if(Xdrag > Xclick){
					  rotate += 1;
					}
					else {
					  rotate -= 1;
					}
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {// Right button
				//Zoom in/out	
				if(Ydrag > Yclick){
				  focalLength += 1;
				}
				else {
				  focalLength -= 1;
				}
				
			}
			
			
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Xclick = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			Yclick = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}	
		
		public void drawSphere(float radius, int level, final GL2 gl){
			//Get X and Z Coord for the octahedron vertices
			float Vz = radius * (float)Math.sin(45); 
			float Vx = radius * (float)Math.cos(45);
			
        	//Draw Octahedron
			//top front
			subdivide(radius, -Vx, 0.0f, Vz, 0.0f, radius, 0.0f, Vx, 0.0f, Vz, level, gl);
			//top left
			subdivide(radius, -Vx, 0.0f, -Vz, 0.0f, radius, 0.0f, -Vx, 0.0f, Vz, level, gl);
			//top right
			subdivide(radius, Vx, 0.0f, Vz, 0.0f, radius, 0.0f, Vx, 0.0f, -Vz, level, gl);
			//top back
			subdivide(radius, Vx, 0.0f, -Vz, 0.0f, radius, 0.0f, -Vx, 0.0f, -Vz, level, gl);
			//bottom front
			subdivide(radius, -Vx, 0.0f, Vz, 0.0f, -radius, 0.0f, Vx, 0.0f, Vz, level, gl);
			//bottom left
			subdivide(radius, -Vx, 0.0f, -Vz, 0.0f, -radius, 0.0f, -Vx, 0.0f, Vz, level, gl);
			//bottom right
			subdivide(radius, Vx, 0.0f, Vz, 0.0f, -radius, 0.0f, Vx, 0.0f, -Vz, level, gl);
			//bottom back
			subdivide(radius, Vx, 0.0f, -Vz, 0.0f, -radius, 0.0f, -Vx, 0.0f, -Vz, level, gl);


		}
		
		void subdivide(float radius, float v1x, float v1y, float v1z,
	               float v2x, float v2y, float v2z,
	               float v3x, float v3y, float v3z,
	               int level, final GL2 gl) {
			if (level == 0) {
					gl.glBegin(GL.GL_TRIANGLES);
					gl.glVertex3f(v1x, v1y, v1z);
					gl.glVertex3f(v2x, v2y, v2z);
					gl.glVertex3f(v3x, v3y, v3z);
					gl.glEnd();
				
			} else {
				
				// Calculate middle of first edge
				float v12x = 0.5f * (v1x + v2x);
				float v12y = 0.5f * (v1y + v2y);
				float v12z = 0.5f * (v1z + v2z);
				// Normalize the vertices
				float norm = radius / (float)Math.sqrt((v12x * v12x) + (v12y * v12y) + (v12z * v12z));
				v12x *= norm;
				v12y *= norm;
				v12z *= norm;

				// Same thing for the middle of the other two edges.
				float v13x = 0.5f * (v1x + v3x);
				float v13y = 0.5f * (v1y + v3y);
				float v13z = 0.5f * (v1z + v3z);
				norm = radius / (float)Math.sqrt((v13x * v13x) + (v13y * v13y) + (v13z * v13z));
				v13x *= norm;
				v13y *= norm;
				v13z *= norm;

				float v23x = 0.5f * (v2x + v3x);
				float v23y = 0.5f * (v2y + v3y);
				float v23z = 0.5f * (v2z + v3z);
				norm = radius / (float)Math.sqrt((v23x * v23x) + (v23y * v23y) + (v23z * v23z));
				v23x *= norm;
				v23y *= norm;
				v23z *= norm;

				// Make the recursive calls.
				subdivide(radius, v1x, v1y, v1z,
						v12x, v12y, v12z,
						v13x, v13y, v13z,
						level - 1, gl);
				subdivide(radius, v12x, v12y, v12z,
						v2x, v2y, v2z,
						v23x, v23y, v23z,
						level - 1, gl);
				subdivide(radius, v13x, v13y, v13z,
						v23x, v23y, v23z,
						v3x, v3y, v3z,
						level - 1, gl);
				subdivide(radius, v12x, v12y, v12z,
						v23x, v23y, v23z,
						v13x, v13y, v13z,
						level - 1, gl);
			}
	}
}



