package program4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.*;



public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	int windowWidth, windowHeight;
	float focalLength = 20.0f;
	float orthoX=40;
	float Xclick, Yclick;
	float backrgb[] = new float[4]; 
	float rot; 
	float [] clickDif = {0.0f, 0.0f};
	Texture mytex = null; 
	Texture mytexY = null;
	Texture mytexX = null;
	Texture mytexy = null;
	Texture mytexZ = null;
	Texture mytexx = null;
	Texture mytexHUD = null;
	Texture mytexB = null;
	float forward = 0.0f;
	float toward = 0.0f;
	float turn = 0.0f;
	float up = 0.0f;
	float sidetoside = 0.0f;
	float oldXdrag = 0.0f;
	float oldYdrag = 0.0f;

    	private GLU glu = new GLU();
    	//private GLUT glut = new GLUT();

	
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
	        
	        // load the texture;
	        
	        try {
	        	 //mytex = TextureIO.newTexture(new File("C:/Users/ruigang/workspace/helloOpenGL/hp.png"), false);
	        	 mytex = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/-Z.png"), false);
	        	 mytexY = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/Y.png"), false);
	        	 mytexy = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/-Y.png"), false);
	        	 mytexZ = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/Z.png"), false);
	        	 mytexX = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/X.png"), false);
	        	 mytexx = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/-X.png"), false);
	        	 mytexHUD = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/skybox1.png"), false);
	        	 mytexB = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/skybox/Building.jpg"), false);

	        	 //int texID = mytex.getTextureObject();
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             mytex.bind(gl);
	             mytexY.bind(gl);
	             mytexy.bind(gl);
	             mytexZ.bind(gl);
	             mytexx.bind(gl);
	             mytexHUD.bind(gl);
	             mytexB.bind(gl);
	             //gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	        	 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             //gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE);
	             
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	       
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
	        glu.gluPerspective(45.0f, h, 1.0, 200);
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        //glu.gluLookAt(0, 0, 35, 0, 0, 0, 0, 1, 0);
	    }

		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();

			gl.glClearColor(backrgb[0], 0, 1, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			//backrgb[0]+=0.0005;
			if (backrgb[0]> 1) backrgb[0] = 0; 
			gl.glLoadIdentity();
			glu.gluLookAt(0, 0, 1, 0, 0, 0, 0, 1, 0);
			
			gl.glPushMatrix();
			//Camera for skybox and 3d scene
			glu.gluLookAt(0, 0, 1, 0, 0+up, -1, 0, 1, 0);

			gl.glPushMatrix();
			
			//Adjust eye position
			gl.glRotated(turn, 0, 1, 0);
			drawSkyBox(gl, 10.0f);
			
			gl.glPopMatrix();

			//Draw 3D Graphic
			gl.glRotated(turn, 0, 1, 0);	
			gl.glTranslatef(sidetoside,0,forward);
			drawScene(gl, 2);
			
			gl.glPopMatrix();
	        
			//Draw HUD
			drawHUD(gl);
			if (turn==360){
				turn = 0;
			}
			//rot += 0.0001;
	        //gl.glLoadIdentity();

	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {}
		
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			char key= e.getKeyChar();
			System.out.printf("Key typed: %c\n", key); 

			if (key == 'w'){
				System.out.println(turn);
				if((turn > 67.5 && turn <= 112.5) || (turn > -292.5 && turn <= -247.5)){ //90
					sidetoside-=0.5f;
				}
				else if(turn > 247.5 && turn <= 292.5 || (turn > -112.5 && turn <= -67.5)){//270
					sidetoside+=0.5f;
				}
				else if(turn > 157.5 && turn <= 202.5 || (turn > (157.5-360) && turn <= (202.5-360))){//180
					forward-=0.5f;
				}
				else if(turn > 22.5 && turn <= 67.5 || (turn > (22.5-360) && turn <= (67.5-360))){//45
					sidetoside-=0.5f;
					forward+=0.5f;
				}
				else if(turn > 112.5 && turn <= 157.5 || (turn > (112.5-360) && turn <= (157.5-360))){//135
					sidetoside-=0.5f;
					forward-=0.5f;
				}
				else if(turn > 202.5 && turn <= 247.5 || (turn > (202.5-360) && turn <= (247.5-360))){//225
					sidetoside+=0.5f;
					forward-=0.5f;
				}
				else if(turn > 292.5 && turn <= 337.5 || (turn > (292.5-360) && turn <= (337.5-360))){ //315
					sidetoside+=0.5f;
					forward+=0.5f;
				}
				else{
					forward+=0.5f;
				}
			}
			if (key == 's'){
				System.out.println(turn);
				if((turn > 67.5 && turn <= 112.5) || (turn > -292.5 && turn <= -247.5)){ //90
					sidetoside+=0.5f;
				}
				else if(turn > 247.5 && turn <= 292.5 || (turn > -112.5 && turn <= -67.5)){//270
					sidetoside-=0.5f;
				}
				else if(turn > 157.5 && turn <= 202.5 || (turn > (157.5-360) && turn <= (202.5-360))){//180
					forward+=0.5f;
				}
				else if(turn > 22.5 && turn <= 67.5 || (turn > (22.5-360) && turn <= (67.5-360))){//45
					sidetoside+=0.5f;
					forward-=0.5f;
				}
				else if(turn > 112.5 && turn <= 157.5 || (turn > (112.5-360) && turn <= (157.5-360))){//135
					sidetoside+=0.5f;
					forward+=0.5f;
				}
				else if(turn > 202.5 && turn <= 247.5 || (turn > (202.5-360) && turn <= (247.5-360))){//225
					sidetoside-=0.5f;
					forward+=0.5f;
				}
				else if(turn > 292.5 && turn <= 337.5 || (turn > (292.5-360) && turn <= (337.5-360))){ //315
					sidetoside-=0.5f;
					forward-=0.5f;
				}
				else{
					forward-=0.5f;
				}
			}
			if (key == 'a'){
				System.out.println(turn);
				if((turn > 67.5 && turn <= 112.5) || (turn > -292.5 && turn <= -247.5)){ //90
					forward+=0.5f;
				}
				else if(turn > 247.5 && turn <= 292.5 || (turn > -112.5 && turn <= -67.5)){//270
					forward-=0.5f;
				}
				else if(turn > 157.5 && turn <= 202.5 || (turn > (157.5-360) && turn <= (202.5-360))){//180
					sidetoside-=0.5f;
				}
				else if(turn > 22.5 && turn <= 67.5 || (turn > (22.5-360) && turn <= (67.5-360))){//45
					sidetoside+=0.5f;
					forward+=0.5f;
				}
				else if(turn > 112.5 && turn <= 157.5 || (turn > (112.5-360) && turn <= (157.5-360))){//135
					sidetoside-=0.5f;
					forward+=0.5f;
				}
				else if(turn > 202.5 && turn <= 247.5 || (turn > (202.5-360) && turn <= (247.5-360))){//225
					sidetoside-=0.5f;
					forward-=0.5f;
				}
				else if(turn > 292.5 && turn <= 337.5 || (turn > (292.5-360) && turn <= (337.5-360))){ //315
					sidetoside+=0.5f;
					forward-=0.5f;
				}
				else{
					sidetoside+=0.5f;
				}
			}
			if (key == 'd'){
				System.out.println(turn);
				if((turn > 67.5 && turn <= 112.5) || (turn > -292.5 && turn <= -247.5)){ //90
					forward-=0.5f;
				}
				else if(turn > 247.5 && turn <= 292.5 || (turn > -112.5 && turn <= -67.5)){//270
					forward+=0.5f;
				}
				else if(turn > 157.5 && turn <= 202.5 || (turn > (157.5-360) && turn <= (202.5-360))){//180
					sidetoside+=0.5f;
				}
				else if(turn > 22.5 && turn <= 67.5 || (turn > (22.5-360) && turn <= (67.5-360))){//45
					sidetoside-=0.5f;
					forward-=0.5f;
				}
				else if(turn > 112.5 && turn <= 157.5 || (turn > (112.5-360) && turn <= (157.5-360))){//135
					sidetoside+=0.5f;
					forward-=0.5f;
				}
				else if(turn > 202.5 && turn <= 247.5 || (turn > (202.5-360) && turn <= (247.5-360))){//225
					sidetoside+=0.5f;
					forward+=0.5f;
				}
				else if(turn > 292.5 && turn <= 337.5 || (turn > (292.5-360) && turn <= (337.5-360))){ //315
					sidetoside-=0.5f;
					forward+=0.5f;
				}
				else{
					sidetoside-=0.5f;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			float Xdrag = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float Ydrag = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			clickDif[0] = Xdrag - Xclick;
			clickDif[1] = Ydrag - Yclick;
			System.out.printf("Point difference: (%.3f, %.3f)\n", oldXdrag, oldYdrag);

			
			if(oldXdrag > Xdrag){
				turn-=1.0f;
			}
			else if (oldXdrag < Xdrag){
				turn+=1.0f;
			}
			
			if(oldYdrag > Ydrag){
				up-=0.05f;
			}
			else if (oldYdrag < Ydrag){
				up+=0.05f;
			}
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {// Right button

			}
			
			oldXdrag = Xdrag;
			oldYdrag = Ydrag;
			
			
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Xclick = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			Yclick = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point pressed: (%.3f, %.3f)\n", Xclick, Yclick);
			System.out.println();
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
		public void drawScene(final GL2 gl, float size){
			gl.glTranslatef(0.0f, 2*size-3, -10.0f);  
			
			gl.glEnable(GL.GL_TEXTURE_2D);
			mytexB.bind(gl);
			gl.glBegin(GL2.GL_QUADS);
		        //Back
		        gl.glTexCoord2f(0,0);
		        gl.glVertex3f(-size, -2*size, -size);    

		        gl.glTexCoord2f(1,0);   
		        gl.glVertex3f(size, -2*size, -size);  
		             
		        gl.glTexCoord2f(1,2);   
		        gl.glVertex3f(size, size, -size);   
		           
		        gl.glTexCoord2f(0,2);   
		        gl.glVertex3f(-size, size, -size);
		        //--
		        //Right
		        gl.glTexCoord2f(0,0);
		        gl.glVertex3f(size, -2*size, size);    

		        gl.glTexCoord2f(1,0);   
		        gl.glVertex3f(size, -2*size, -size);  
		             
		        gl.glTexCoord2f(1,2);   
		        gl.glVertex3f(size, size, -size);   
		           
		        gl.glTexCoord2f(0,2);   
		        gl.glVertex3f(size, size, size);   
		        
		        //Front
		        gl.glTexCoord2f(0,0);
		        gl.glVertex3f(-size, -2*size, size);    

		        gl.glTexCoord2f(1,0);   
		        gl.glVertex3f(size, -2*size, size);  
		             
		        gl.glTexCoord2f(1,2);   
		        gl.glVertex3f(size, size, size);   
		           
		        gl.glTexCoord2f(0,2);   
		        gl.glVertex3f(-size, size, size);
		        
		        //Left
		        
		        gl.glTexCoord2f(0,0);
		        gl.glVertex3f(-size, -2*size, size);    

		        gl.glTexCoord2f(1,0);   
		        gl.glVertex3f(-size, -2*size, -size);  
		             
		        gl.glTexCoord2f(1,2);   
		        gl.glVertex3f(-size, size, -size);   
		           
		        gl.glTexCoord2f(0,2);   
		        gl.glVertex3f(-size, size, size); 
		        
		        gl.glEnd();
		    gl.glDisable(GL.GL_TEXTURE_2D);
		}
		
		public void drawSkyBox(final GL2 gl, float size){
			gl.glTranslatef(0.0f, size-3, 0.0f);
	        //-Z
	        gl.glEnable(GL.GL_TEXTURE_2D);
	        mytex.bind(gl); //Apply the texture to the shape.
	        gl.glBegin(GL2.GL_QUADS);
	        //putting mytext.bind won't work here  
	        gl.glTexCoord2f(0,0);
	        gl.glVertex3f(-size, -size, -size);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(size, -size, -size);  
	             
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(size, size, -size);   
	           
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-size, size, -size);   
	        gl.glEnd(); 
	        
	        //Y
	        mytexY.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);
	        //putting mytext.bind won't work here 
	        gl.glTexCoord2f(0,0);
	        gl.glVertex3f(-size, size, -size);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(size, size, -size);  
	             
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(size, size, size);   
	           
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-size, size, size);   
	        gl.glEnd();
	        
	        //-Y
	        mytexy.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);
	        //putting mytext.bind won't work here 
	        gl.glTexCoord2f(0,0);
	        gl.glVertex3f(-size, -size, -size);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(size, -size, -size);  
	             
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(size, -size, size);   
	           
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-size, -size, size);   
	        gl.glEnd();
	        
	        //Z
	        mytexZ.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);
	        //putting mytext.bind won't work here 
	        gl.glTexCoord2f(0,0);
	        gl.glVertex3f(-size, -size, size);    

	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(size, -size, size);  
	             
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(size, size, size);   
	           
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-size, size, size);   
	        gl.glEnd();
	        
	        //X
	        mytexX.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);
	        //putting mytext.bind won't work here 
	        gl.glTexCoord2f(0,0);
	        gl.glVertex3f(size, -size, -size);     

	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(size, size, -size);  
	             
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(size, size, size);     
	           
	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(size, -size, size);    
	        gl.glEnd();
	        
	        //-X
	        mytexx.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);
	        //putting mytext.bind won't work here 
	        gl.glTexCoord2f(1,0);
	        gl.glVertex3f(-size, -size, -size);     

	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(-size, size, -size);  
	             
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-size, size, size);     
	           
	        gl.glTexCoord2f(0,0);   
	        gl.glVertex3f(-size, -size, size);    
	        gl.glEnd();
	      
	        gl.glDisable(GL.GL_TEXTURE_2D);
		}
		
		public void drawHUD(final GL2 gl){
			gl.glTranslatef(2.3f, 1.8f, -5.0f);

			gl.glEnable(GL.GL_TEXTURE_2D);

			mytexHUD.bind(gl);
			gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(0,0);
			gl.glVertex3f(-0.5f, -0.5f, 0.0f); 
			gl.glTexCoord2f(1,0);
			gl.glVertex3f(0.5f, -0.5f, 0.0f); 
			gl.glTexCoord2f(1,1);
			gl.glVertex3f(0.5f, 0.5f, 0.0f); 
			gl.glTexCoord2f(0,1);
			gl.glVertex3f(-0.5f, 0.5f, 0.0f); 
			gl.glEnd();

			gl.glDisable(GL.GL_TEXTURE_2D);
		}

}
