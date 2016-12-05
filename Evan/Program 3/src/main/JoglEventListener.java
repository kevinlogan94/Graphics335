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
	
	float zoom = 0.0f;

	//diffuse light color variables
	float dlr = 1.0f;
	float dlg = 1.0f;
	float dlb = 0.8f;
	float dla = 1.0f;

	//ambient light color variables
	float alr = 1.0f;
	float alg = 1.0f;
	float alb = 1.0f;
	float ala = 1.0f;
	
	//specular light color variables
	float slr = 1.0f;
	float slg = 1.0f;
	float slb = 1.0f;
	float sla = 1.0f;

	//light position variables
	float lx_0 = 0.0f;
	float ly_0 = 0.0f;
	float lz_0 = 0.0f; // light0 is at the origin
	float lw_0 = 1.0f;
	
	float earth_angle_around_sun = 0.0f;
	float moon_angle_around_earth = 0.0f;
	
	float [] drag_amount = {0.0f, 0.0f};
	float [] mouse_last = {0.0f, 0.0f};
	
	boolean animate = true;
	
	private GLUT glut = new GLUT();
	
	
	/*** Define material property  ***/ 
	
	float whiteSpecularLight []    = { 1.0f, 1.0f, 1.0f }; //set the light specular to white
	
	float mShininess[]        = { 50 }; //set the shininess of the material
		
	boolean smooth_flag = true;

    private GLU glu = new GLU();
	
	public void drawSphere(final GL2 gl,float radius, float[] material){
		gl.glPushMatrix();
		
		// set the shading model GL_SMOOTH /GL_FLAT
		if(smooth_flag){
			gl.glShadeModel(GL2.GL_SMOOTH);
		}
		else{
			gl.glShadeModel(GL2.GL_FLAT);
		}
		
		// set the material property
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularLight, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);	
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, material, 0);
		
//		drawCustomSphere(radius, 4, gl);
		glut.glutSolidSphere(radius,50,50);
		
		gl.glPopMatrix();
	}
	
		public void drawCustomSphere(float radius, int iter, final GL2 gl) {
			float z = radius * (float)Math.sin(45); 
			float x = radius * (float)Math.cos(45);
			
			divide(radius, -x, 0,  z, 0,  radius, 0,  x, 0,  z, iter, gl);
			divide(radius, -x, 0, -z, 0,  radius, 0, -x, 0,  z, iter, gl);
			divide(radius,  x, 0,  z, 0,  radius, 0,  x, 0, -z, iter, gl);
			divide(radius,  x, 0, -z, 0,  radius, 0, -x, 0, -z, iter, gl);
			divide(radius, -x, 0,  z, 0, -radius, 0,  x, 0,  z, iter, gl);
			divide(radius, -x, 0, -z, 0, -radius, 0, -x, 0,  z, iter, gl);
			divide(radius,  x, 0,  z, 0, -radius, 0,  x, 0, -z, iter, gl);
			divide(radius,  x, 0, -z, 0, -radius, 0, -x, 0, -z, iter, gl);
		}
		
		public void divide(float radius, 	float x1, float y1, float z1,
											float x2, float y2, float z2,
											float x3, float y3, float z3,
											int iter, final GL2 gl) {
			if (iter == 0) {
				gl.glBegin(GL.GL_TRIANGLES);
//				float[] normal = getNormal(x1, y1, z1, x2, y2, z2, x3, y3, z3);
//				gl.glNormal3f(normal[0], normal[1], normal[2]);
				gl.glVertex3f(x1, y1, z1);
				gl.glVertex3f(x2, y2, z2);
				gl.glVertex3f(x3, y3, z3);
				gl.glEnd();
			} else { //subdivide
				float x12 = (x1+x2)/2;
				float y12 = (y1+y2)/2;
				float z12 = (z1+z2)/2;
				float norm12 = (float)(radius / Math.sqrt(x12*x12 + y12*y12 + z12*z12)); 
				x12 *= norm12;
				y12 *= norm12;
				z12 *= norm12;
				
				float x13 = (x1+x3)/2;
				float y13 = (y1+y3)/2;
				float z13 = (z1+z3)/2;
				float norm13 = (float)(radius / Math.sqrt(x13*x13 + y13*y13 + z13*z13)); 
				x13 *= norm13;
				y13 *= norm13;
				z13 *= norm13;
				
				float x23 = (x2+x3)/2;
				float y23 = (y2+y3)/2;
				float z23 = (z2+z3)/2;
				float norm23 = (float)(radius / Math.sqrt(x23*x23 + y23*y23 + z23*z23)); 
				x23 *= norm23;
				y23 *= norm23;
				z23 *= norm23;
				
				divide(radius, x1, y1, z1, x12, y12, z12, x13, y13, z13, iter-1, gl);
				divide(radius, x12, y12, z12, x2, y2, z2, x23, y23, z23, iter-1, gl);
				divide(radius, x13, y13, z13, x23, y23, z23, x3, y3, z3, iter-1, gl);
				divide(radius, x12, y12, z12, x13, y13, z13, x23, y23, z23, iter-1, gl);
			}
			
			
		}
		
		public float[] getNormal(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
			float Ux = x2 - x1;
			float Uy = y2 - y1;
			float Uz = z2 - z1;
			float Vx = x3 - x1;
			float Vy = y3 - y1;
			float Vz = z3 - z1;
			
			float[] normal = {Uy*Vz - Uz*Vy, Uz*Vx - Ux*Vz, Ux*Vy - Uy*Vx};
			
			return normal;
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
	        glu.gluPerspective(45.0f, h, 1, 100);

	        glu.gluLookAt(0.0, 0.0, 20, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        System.out.println("hello!!!");
	    }
	    
		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();
			
			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
			
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
//	    	gl.glLoadIdentity();
	    	
	    	gl.glPushMatrix();
			float lightPosition_0[] = {lx_0, ly_0, lz_0, lw_0}; // light position
			float ambientLight[] = {alr, alg, alb, ala}; // ambient light property
			float diffuseLight[] = {dlr, dlg, dlb, dla}; // diffuse light property
			float specularLight[] = {slr, slg, slb, sla}; // specular light property
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition_0, 0); // set light0 position
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0); // set light0 as diffuse light with related property
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);
			gl.glPopMatrix();
			
			 
			
			transform(gl);
			
			drawAxes(gl);
	    	drawSun(gl);
	    	drawEarth(gl);
	    	drawMoon(gl);
	    	
	    	if (animate) {
	    		moon_angle_around_earth+=10;
	    		earth_angle_around_sun++;
	    	}
	    	
	    	gl.glFlush();
	    	
		}
		
		public void transform(final GL2 gl) {
			if (drag_amount[0] != 0 || drag_amount[1] != 0) {
				gl.glRotatef(-2*drag_amount[0], 0, 1, 0);
				gl.glRotatef(2*drag_amount[1], 1, 0, 0);
			}
			
			drag_amount[0] = 0;
			drag_amount[1] = 0;
		}
		
		public void drawAxes(final GL2 gl) {
			gl.glPushMatrix();
			
			gl.glLineWidth(2); 
			
			float red[] = {1, 0, 0};
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, red, 0);
			gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(-10, 0, 0);
			gl.glVertex3f( 10, 0, 0);
			gl.glEnd();
			
			float green[] = {0, 1, 0};
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, green, 0);
			gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0, -10, 0);
			gl.glVertex3f(0,  10, 0);
			gl.glEnd();
			
			float blue[] = {0, 0, 1};
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, blue, 0);
			gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0, 0, -10);
			gl.glVertex3f(0, 0,  10);
			gl.glEnd();
			
			gl.glPopMatrix();
		}
		
		public void drawSun(final GL2 gl) {
			gl.glPushMatrix();
			float[] material = {1f, 1f, 0f};
			drawSphere(gl, 1f, material);
			gl.glPopMatrix();
		}
		
		public void drawEarth(final GL2 gl) {
			gl.glPushMatrix();
			gl.glRotatef(-12f, 0f, 0f, 1f);	
	    	gl.glRotatef(earth_angle_around_sun, 0f, 1f, 0f);
	    	gl.glTranslatef(5f, 0f, 0f);
			
			float[] material = {0f, 1f, 0f};
			drawSphere(gl, 1f/3f, material);
			gl.glPopMatrix();
		}
		
		public void drawMoon(final GL2 gl) {
			gl.glPushMatrix();
			
			gl.glRotatef(-12f, 0f, 0f, 1f);
	    	gl.glRotatef(earth_angle_around_sun, 0f, 1f, 0f);
	    	gl.glTranslatef(5f, 0f, 0f);
	    	
	    	gl.glRotatef(-earth_angle_around_sun, 0, 1, 0);
	    	
	    	gl.glRotatef(24f, 0f, 0f, 1f);
	    	gl.glRotatef(moon_angle_around_earth, 0f, 1f, 0f);
	    	
	    	gl.glTranslatef(1f, 0f, 0f);
	    	
			float[] material = {0.7f, 0.7f, 0.7f};
			drawSphere(gl, 1f/9f, material);
			gl.glPopMatrix();
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
			
			case 't':
				animate = !animate;
				break;
			case 'T':
				animate = !animate;
				break;
			case 'z':
				zoom++;
				glu.gluLookAt(0, 0, zoom, 0, 0, 0, 0, 1, 0);
				break;
			case 'Z':
				zoom--;
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
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			
			drag_amount[0] = XX - mouse_last[0];
			drag_amount[1] = YY - mouse_last[1];		
			mouse_last[0] = XX;
			mouse_last[1] = YY;
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
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			mouse_last[0] = XX;
			mouse_last[1] = YY;
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



