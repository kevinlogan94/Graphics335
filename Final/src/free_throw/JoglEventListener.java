package free_throw;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.gl2.GLUT;


public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	float rot;
	float y_lookat;
	int windowWidth;
	int windowHeight;
	float orthoX=40;
	int[] movement_state = {0, 0};
	float[] movement= {0, 0};
	float [] mouse_last = {0, 0};
	float [] drag_amount = {0, 0};
	boolean ballVisible = false;
	Texture mytex = null; 
	Texture myHUD = null;
	Texture myGround = null;
	Texture myCourt = null;
	Texture myBuildingFace1 = null;
	Texture myBuildingFace2 = null;
	Texture myBuildings = null;
	Texture myGoal = null;
	Texture myUDLR = null;
	Texture myLR = null;

    private GLU glu = new GLU();
    private GLUT glut = new GLUT();

	
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
	        
	        // load the texture;
	        
	        try {
	        	 mytex = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/basketball_textures/textures/skybox2.jpg"), false);
	        	 myHUD = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/basketball_textures/textures/HUD2.png"), false);
	        	 myGround = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/basketball_textures/textures/grass.png"), false);
	        	 myCourt = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/Workspace/basketball_textures/textures/court2.png"), false);
	        	 myBuildingFace1 = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/workspace/basketball_textures/textures/building1.png"), false);
	        	 myBuildingFace2 = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/workspace/basketball_textures/textures/building2.png"), false);
	        	 myBuildings = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/workspace/basketball_textures/textures/buildings_small.png"), false);
	        	 myGoal = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/workspace/basketball_textures/textures/goal.jpg"), false);
	        	 myUDLR = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/workspace/basketball_textures/textures/udlr.png"), false);
	        	 myLR = TextureIO.newTexture(new File("/Users/kevinlogan/Desktop/workspace/basketball_textures/textures/lr.png"), false);
	        	 
//	        	 int texID = mytex.getTextureObject();
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             myGoal.bind(gl);
	             myUDLR.bind(gl);
	             mytex.bind(gl);
	             //gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	        	 gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE);
	             
	             myHUD.bind(gl);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             gl.glEnable(GL2.GL_BLEND);
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
	             
	             myGround.bind(gl);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             myCourt.bind(gl);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             
	             myBuildingFace1.bind(gl);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             gl.glEnable(GL2.GL_BLEND);
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
	             
	             myBuildingFace2.bind(gl);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             gl.glEnable(GL2.GL_BLEND);
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
	             
	             myBuildings.bind(gl);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	             gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	             gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	             gl.glEnable(GL2.GL_BLEND);
	             gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
	             
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	       
	    }
 
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, 
	            int height) {
	        final GL2 gl = gLDrawable.getGL().getGL2();
	        
	        windowWidth = width;
	    	windowHeight = height;

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(width/2*0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	        glu.gluPerspective(50.0f, h, 0.5f, 80);
	        
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        
	        gl.glLoadIdentity();

			glu.gluLookAt(0, 1.5, 0, 0, 0.5, -1, 0, 1, 0);
	    }
	    public void renderSkyBox(final GL2 gl) {
	    	gl.glPushMatrix();
	    	gl.glRotatef(-rot, 0.0f, 1.0f, 0.0f);
	    	gl.glEnable(GL.GL_TEXTURE_2D);
	    	mytex.bind(gl);
	    	drawSkyBox(gl);
	    	gl.glDisable(GL.GL_TEXTURE_2D);
	    	gl.glPopMatrix();
	    }
	    public void drawSkyBox(final GL2 gl) {
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(0, 1f/3f);
	    	gl.glVertex3f(-40, -40, 40);
	    	gl.glTexCoord2f(0, 2f/3f);
	    	gl.glVertex3f(-40, 40, 40);
	    	gl.glTexCoord2f(1f/4f, 2f/3f);
	    	gl.glVertex3f(-40, 40, -40);
	    	gl.glTexCoord2f(1f/4f, 1f/3f);
	    	gl.glVertex3f(-40, -40, -40);
	    	gl.glEnd();
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(1f/4f, 1f/3f);
	    	gl.glVertex3f(-40, -40, -40);
	    	gl.glTexCoord2f(1f/4f, 2f/3f);
	    	gl.glVertex3f(-40, 40, -40);
	    	gl.glTexCoord2f(1f/2f, 2f/3f);
	    	gl.glVertex3f(40, 40, -40);
	    	gl.glTexCoord2f(1f/2f, 1f/3f);
	    	gl.glVertex3f(40, -40, -40);
	    	gl.glEnd();
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(1f/2f, 1f/3f);
	    	gl.glVertex3f(40, -40, -40);
	    	gl.glTexCoord2f(1f/2f, 2f/3f);
	    	gl.glVertex3f(40, 40, -40);
	    	gl.glTexCoord2f(3f/4f, 2f/3f);
	    	gl.glVertex3f(40, 40, 40);
	    	gl.glTexCoord2f(3f/4f, 1f/3f);
	    	gl.glVertex3f(40, -40, 40);
	    	gl.glEnd();
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(3f/4f, 1f/3f);
	    	gl.glVertex3f(40, -40, 40);
	    	gl.glTexCoord2f(3f/4f, 2f/3f);
	    	gl.glVertex3f(40, 40, 40);
	    	gl.glTexCoord2f(1f, 2f/3f);
	    	gl.glVertex3f(-40, 40, 40);
	    	gl.glTexCoord2f(1f, 1f/3f);
	    	gl.glVertex3f(-40, -40, 40);
	    	gl.glEnd();
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(1f/4f, 2f/3f);
	    	gl.glVertex3f(-40, 40, -40);
	    	gl.glTexCoord2f(1f/4f, 1f);
	    	gl.glVertex3f(-40, 40, 40);
	    	gl.glTexCoord2f(1f/2f, 1f);
	    	gl.glVertex3f(40, 40, 40);
	    	gl.glTexCoord2f(1f/2f, 2f/3f);
	    	gl.glVertex3f(40, 40, -40);
	    	gl.glEnd();
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(1f/4f, 1f/3f);
	    	gl.glVertex3f(-40, -40, -40);
	    	gl.glTexCoord2f(1f/4f, 0);
	    	gl.glVertex3f(-40, -40, 40);
	    	gl.glTexCoord2f(1f/2f, 0);
	    	gl.glVertex3f(40, -40, 40);
	    	gl.glTexCoord2f(1f/2f, 1f/3f);
	    	gl.glVertex3f(40, -40, -40);
	    	gl.glEnd();
	    }	    
	    public void drawAxes(final GL2 gl) {
	    	gl.glPushMatrix();
	    	
	    	gl.glBegin(GL.GL_LINES);
			
			gl.glColor3f(1.0f, 0, 0);
			gl.glVertex2f(0, 0);
			gl.glVertex2f(10, 0);
			
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex2f(0, 0);
			gl.glVertex2f(0, 10);
			
			
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex2f(0, 0);
			gl.glVertex3f(0, 0, 10);
			
			gl.glEnd();
			
	    	gl.glPopMatrix();
	    }
	    public void drawQuad(final GL2 gl) {
	    	gl.glPushMatrix();
	        gl.glEnable(GL.GL_TEXTURE_2D);
	        mytex.bind(gl);
	        //gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glBegin(GL2.GL_QUADS);
	        gl.glColor3f(1.0f, 1.0f, 1.0f);     // white
	        gl.glTexCoord2f(0,0); 
	        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	        
	        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        gl.glTexCoord2f(1,0);   
	        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // 
	        
	        gl.glColor3f(1.0f, 0.0f, 0.0f);     
	        gl.glTexCoord2f(1,1);   
	        gl.glVertex3f(1.0f, 1.0f, -1.0f);   
	        
	        gl.glColor3f(0.0f, 0.0f, 1.0f);     
	        gl.glTexCoord2f(0,1);   
	        gl.glVertex3f(-1.0f, 1.0f, -1.0f);   
	        gl.glEnd(); 
	        
	        gl.glDisable(GL.GL_TEXTURE_2D);
	        gl.glPopMatrix();
	    }
	    public void drawPyramid(final GL2 gl, float x, float y, float z) {
	    	gl.glPushMatrix();
			gl.glBegin(GL.GL_TRIANGLE_STRIP);
	        gl.glColor3f(1, 0, 0); //bottom of pyramid
	        gl.glVertex3f(x, y, z);
	        gl.glColor3f(0, 1, 0);
	        gl.glVertex3f(x+2, y, z);
	        gl.glColor3f(0, 0, 1);
	        gl.glVertex3f(x+1, y, (float)(z+Math.sqrt(3)));
	        
	        gl.glColor3f(1, 1, 1); //top of pyramid
	        gl.glVertex3f(x+1, (float)(Math.sqrt(3))+y, (float)( (z+z+z+Math.sqrt(3))/3  ));
	        
	        gl.glColor3f(1, 0, 0);
	        gl.glVertex3f(x, y, z);
	        
	        gl.glColor3f(0, 1, 0);
	        gl.glVertex3f(x+2, y, z);
	        gl.glEnd();
	        gl.glPopMatrix();
	    }
	    public void drawHUD(final GL2 gl) {
	    	gl.glPushMatrix();
	    	gl.glEnable(GL.GL_TEXTURE_2D);
	    	/*
	        myHUD.bind(gl);
	        gl.glBegin(GL2.GL_QUADS);
	        
	        gl.glTexCoord2f(0, 0);
	        gl.glVertex3f(0, -0.5f, -1);
	        
	        gl.glTexCoord2f(0, 1);
	        gl.glVertex3f(0, 0, -1);
	        
	        gl.glTexCoord2f(1, 1);
	        gl.glVertex3f(0.5f, 0, -1);
	        
	        gl.glTexCoord2f(1, 0);
	        gl.glVertex3f(0.5f, -0.5f, -1);
	        
	        gl.glEnd();
	        */
	        gl.glDisable(GL.GL_TEXTURE_2D);
	        
	        
	        gl.glBegin(GL2.GL_LINES);
	        gl.glColor3f(200, 40, 40);
	        gl.glVertex3f(-0.02f, 0, -1);
	        gl.glVertex3f(0.02f, 0, -1);
	        gl.glEnd();
	        
	        gl.glBegin(GL2.GL_LINES);
	        gl.glVertex3f(0, -0.02f, -1);
	        gl.glVertex3f(0, 0.02f, -1);
	        gl.glEnd();
	    	
	    	gl.glPopMatrix();
	    }
	    public void drawGround(final GL2 gl) {
	    	gl.glPushMatrix();
	    	gl.glEnable(GL.GL_TEXTURE_2D);
	    	myGround.bind(gl);
	    	
	    	float[] vertices = {	-16, 	1.5f, 	-20.5f,
	    							-6, 	0f, 	-10.5f,
	    							-16, 	1.5f,	20.5f,
	    							-6, 	0, 		10.5f,
	    							16, 	1.5f,	20.5f,
	    							6, 		0, 		10.5f,
	    							16, 	1.5f, 	-20.5f,
	    							6, 		0, 		-10.5f		};
	    	
	    	int[] indices = {	1, 0, 2, 3,
	    						3, 2, 4, 5,
	    						5, 4, 6, 7,
	    						7, 6, 0, 1		};
	    	
	    	for (int i=0; i<4; i++) { // for each quad
	    		gl.glBegin(GL2.GL_QUADS);
	    		for (int j=i*4; j<i*4+4; j++) { // for each vertex
	    			if (j%4==0) {gl.glTexCoord2f(2, 0);}
	    			else if (j%4==1) {gl.glTexCoord2f(0, 2);}
	    			else if (j%4==2) {gl.glTexCoord2f(6, 2);}
	    			else {gl.glTexCoord2f(4, 0);}
	    			gl.glVertex3f(vertices[3*indices[j]], vertices[3*indices[j]+1], vertices[3*indices[j]+2]);
	    		}
	    		gl.glEnd();
	    	}
	    	
	    	gl.glDisable(GL.GL_TEXTURE_2D);
	    	gl.glPopMatrix();
	    }
		public void drawBuildings(final GL2 gl) {
			gl.glPushMatrix();
	    	gl.glEnable(GL.GL_TEXTURE_2D);
	    	myBuildingFace2.bind(gl);
	    	
	    	float[] vertices = {
	    							-20, 0, -25,
	    							-20, 0, -15,
	    							-30, 0, -15,
	    							-30, 0, -5,
	    							-20, 0, -5,
	    							-20, 0, 5,
	    							-30, 0, 5,
	    							-30, 0, 15,
	    							-20, 0, 15,
	    							-20, 0, 25,
	    							
	    							-20, 15, -25,
	    							-20, 15, -15,
	    							-30, 15, -15,
	    							-30, 15, -5,
	    							-20, 15, -5,
	    							-20, 15, 5,
	    							-30, 15, 5,
	    							-30, 15, 15,
	    							-20, 15, 15,
	    							-20, 15, 25		
	    											};
	    	int[] indices = {
	    							0, 10, 11, 1,
	    							1, 11, 12, 2,
	    							2, 12, 13, 3,
	    							3, 13, 14, 4,
	    							4, 14, 15, 5,
	    							5, 15, 16, 6,
	    							6, 16, 17, 7,
	    							7, 17, 18, 8,
	    							8, 18, 19, 9
	    											};
	    	
	    	float[] vertices2 = {
	    							23, 0, 25,
	    							23, 0, 15,
	    							23, 0, 5,
	    							13, 0, 5,
	    							13, 0, -5,
	    							23, 0, -5,
	    							23, 0, -15,
	    							23, 0, -25,
	    							
	    							23, 15, 25,
	    							23, 15, 15,
	    							23, 15, 5,
	    							13, 15, 5,
	    							13, 15, -5,
	    							23, 15, -5,
	    							23, 15, -15,
	    							23, 15, -25,
	    											};
	    	int[] indices2 = {
	    							0, 8, 9, 1,
	    							1, 9, 10, 2,
	    							2, 10, 11, 3,
	    							4, 12, 13, 5,
	    							5, 13, 14, 6,
	    							6, 14, 15, 7
	    											};
	    	
	    	for (int i=0; i<9; i++) { // for each quad
	    		gl.glBegin(GL2.GL_QUADS);
	    		for (int j=i*4; j<i*4+4; j++) { // for each vertex
	    			if (j%4==0) {gl.glTexCoord2f(0, 0);}
	    			else if (j%4==1) {gl.glTexCoord2f(0, 1);}
	    			else if (j%4==2) {gl.glTexCoord2f(2, 1);}
	    			else {gl.glTexCoord2f(2, 0);}
	    			gl.glVertex3f(vertices[3*indices[j]], vertices[3*indices[j]+1], vertices[3*indices[j]+2]);
	    		}
	    		gl.glEnd();
	    	}
	    	for (int i=0; i<6; i++) { // for each quad
	    		gl.glBegin(GL2.GL_QUADS);
	    		for (int j=i*4; j<i*4+4; j++) { // for each vertex
	    			if (j%4==0) {gl.glTexCoord2f(0, 0);}
	    			else if (j%4==1) {gl.glTexCoord2f(0, 1);}
	    			else if (j%4==2) {gl.glTexCoord2f(2, 1);}
	    			else {gl.glTexCoord2f(2, 0);}
	    			gl.glVertex3f(vertices2[3*indices2[j]], vertices2[3*indices2[j]+1], vertices2[3*indices2[j]+2]);
	    		}
	    		gl.glEnd();
	    	}
	    	
	    	myBuildingFace1.bind(gl);
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(0, 0);
	    	gl.glVertex3f(vertices2[3*3], vertices2[3*3+1], vertices2[3*3+2]);
	    	gl.glTexCoord2f(0, 1);
	    	gl.glVertex3f(vertices2[3*11], vertices2[3*11+1], vertices2[3*11+2]);
	    	gl.glTexCoord2f(1, 1);
	    	gl.glVertex3f(vertices2[3*12], vertices2[3*12+1], vertices2[3*12+2]);
	    	gl.glTexCoord2f(1, 0);
	    	gl.glVertex3f(vertices2[3*4], vertices2[3*4+1], vertices2[3*4+2]);
	    	gl.glEnd();
	    	
	    	myBuildings.bind(gl);
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(0, 0);
	    	gl.glVertex3f(-30, 0, -30);
	    	gl.glTexCoord2f(0, 1);
	    	gl.glVertex3f(-30, 15, -30);
	    	gl.glTexCoord2f(1, 1);
	    	gl.glVertex3f(30, 15, -30);
	    	gl.glTexCoord2f(1, 0);
	    	gl.glVertex3f(30, 0, -30);
	    	gl.glEnd();
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glTexCoord2f(0, 0);
	    	gl.glVertex3f(-30, 0, 30);
	    	gl.glTexCoord2f(0, 1);
	    	gl.glVertex3f(-30, 15, 30);
	    	gl.glTexCoord2f(1, 1);
	    	gl.glVertex3f(30, 15, 30);
	    	gl.glTexCoord2f(1, 0);
	    	gl.glVertex3f(30, 0, 30);
	    	gl.glEnd();
	    	
	    	
	    	gl.glDisable(GL.GL_TEXTURE_2D);
	    	gl.glPopMatrix();
		}
		public void drawCourt(final GL2 gl) {
			gl.glPushMatrix();
			gl.glEnable(GL.GL_TEXTURE_2D);
			myCourt.bind(gl);
			gl.glBegin(GL2.GL_QUADS);

			gl.glTexCoord2f(0, 0);
			gl.glVertex3f(-6, 0, -10.5f);

			gl.glTexCoord2f(0, 1);
			gl.glVertex3f(-6, 0, 10.5f);

			gl.glTexCoord2f(1, 1);
			gl.glVertex3f(6, 0, 10.5f);

			gl.glTexCoord2f(1, 0);
			gl.glVertex3f(6, 0, -10.5f);



			gl.glEnd();
			gl.glDisable(GL.GL_TEXTURE_2D);
			gl.glPopMatrix();
		}
	   public void drawGoal(final GL2 gl){
		   gl.glPushMatrix();
		   gl.glTranslated(0,4,-10);
		   gl.glPushMatrix();
		   	gl.glRotated(90, 1, 0, 0);
		   	gl.glColor3d(0, 0, 0);
		   	glut.glutSolidCylinder(0.1f, 4, 20, 20);
		   	gl.glPushMatrix();
		   	    gl.glTranslated(0, 0.8, 0.04);
		   	    gl.glColor3d(255, 0, 0); 
		    	glut.glutSolidTorus(0.02, 0.4, 5, 25);
		   	gl.glPopMatrix();
		   gl.glPopMatrix();	   
		   //glut.glutSolidCube(1);
		   gl.glPopMatrix();
		   drawBackBoard(gl);
	   }
	   public void drawBackBoard(final GL2 gl){
		   gl.glPushMatrix();
		   gl.glEnable(GL.GL_TEXTURE_2D);
		   myGoal.bind(gl);
		   gl.glBegin(GL2.GL_QUADS);

		   //front
		   gl.glTexCoord2f(0, 0);
		   gl.glVertex3f(-1, 3.8f, -9.6f);
		   gl.glTexCoord2f(0, 1);
		   gl.glVertex3f(-1, 5, -9.6f);
		   gl.glTexCoord2f(1, 1);
		   gl.glVertex3f(1, 5, -9.6f);
		   gl.glTexCoord2f(1, 0);
		   gl.glVertex3f(1, 3.8f, -9.6f);

		   //right
		   gl.glVertex3f(1, 3.8f, -9.6f);
		   gl.glVertex3f(1, 5, -9.6f);
		   gl.glVertex3f(1, 5, -9.7f);
		   gl.glVertex3f(1, 3.8f, -9.7f);

		   //back
		   gl.glVertex3f(-1, 3.8f, -9.7f);
		   gl.glVertex3f(-1, 5, -9.7f);
		   gl.glVertex3f(1, 5, -9.7f);
		   gl.glVertex3f(1, 3.8f, -9.7f);

		   //left
		   gl.glVertex3f(-1, 3.8f, -9.6f);
		   gl.glVertex3f(-1, 5, -9.6f);
		   gl.glVertex3f(-1, 5, -9.7f);
		   gl.glVertex3f(-1, 3.8f, -9.7f);

		   gl.glEnd();
		   gl.glDisable(GL.GL_TEXTURE_2D);
		   gl.glPopMatrix();
	   }
	   public void drawArrows(final GL2 gl){
		   gl.glPushMatrix();
		   gl.glEnable(GL.GL_TEXTURE_2D);
		   myUDLR.bind(gl);
		   gl.glBegin(GL2.GL_QUADS);
		   //front
		   gl.glTexCoord2f(0, 0);
		   gl.glVertex3f(-0.7f, -0.45f, -1);
		   gl.glTexCoord2f(0, 1);
		   gl.glVertex3f(-0.7f, -0.05f, -1);
		   gl.glTexCoord2f(1, 1);
		   gl.glVertex3f(-0.3f, -0.05f, -1);
		   gl.glTexCoord2f(1, 0);
		   gl.glVertex3f(-0.3f, -0.45f, -1);
		   gl.glEnd();
		   
		   myLR.bind(gl);
		   gl.glBegin(GL2.GL_QUADS);
		   //front
		   gl.glTexCoord2f(0, 0);
		   gl.glVertex3f(0.35f, -0.4f, -1);
		   gl.glTexCoord2f(0, 1);
		   gl.glVertex3f(0.35f, -0.1f, -1);
		   gl.glTexCoord2f(1, 1);
		   gl.glVertex3f(0.65f, -0.1f, -1);
		   gl.glTexCoord2f(1, 0);
		   gl.glVertex3f(0.65f, -0.4f, -1);
		   gl.glEnd();
		   
		   gl.glDisable(GL.GL_TEXTURE_2D);
		   gl.glPopMatrix();
	   }

		
	    @Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();
			
			movement[0] += (movement_state[0]*0.04)*Math.cos(rot/180*Math.PI) + (movement_state[1]*0.04)*Math.sin(-rot/180*Math.PI);
			movement[1] += (movement_state[1]*0.04)*Math.cos(rot/180*Math.PI) + (movement_state[0]*0.04)*Math.sin(rot/180*Math.PI);
			rot += drag_amount[0];
			y_lookat -= drag_amount[1]/10;
			
			gl.glLoadIdentity();
			
			gl.glPushMatrix();
			glu.gluLookAt(0, 1.5, 0, 0, y_lookat, -10, 0, 1, 0);
			
			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			
			renderSkyBox(gl);
	        gl.glRotatef(-rot, 0.0f, 1.0f, 0.0f);
	        gl.glTranslated(movement[1], -0.5, movement[0]);
	        
	        drawGoal(gl);
	        drawGround(gl);
	        drawCourt(gl);
	        drawBuildings(gl);
			
			gl.glPopMatrix();	
			drawArrows(gl);
	        drawHUD(gl);
	        
	        if(ballVisible){
	          gl.glTranslated(0,0,-2);
	          gl.glColor3d(255, 140, 0);
	          glut.glutSolidSphere(0.5f, 30, 30);
	        }	        
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			
			drag_amount[0] = mouse_last[0] - XX;
			drag_amount[1] = mouse_last[1] - YY;
			
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
			drag_amount[0] = 0;
			drag_amount[1] = 0;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			char key= e.getKeyChar();
			if (key == ' ') {
				ballVisible=true;
		    }
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			char key= e.getKeyChar();
			System.out.printf("Key pressed: %c\n", key);
			
			if (key == 'w') {
				movement_state[0] = 1;
			} else if (key == 'a') {
				movement_state[1] = 1;
			} else if (key == 's') {
				movement_state[0] = -1;
			} else if (key == 'd') {
				movement_state[1] = -1;
			} else if (key == 'r') {
				System.out.println(rot);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			char key= e.getKeyChar();
			System.out.printf("Key released: %c\n", key);
			
			if (key == 'w') {
				movement_state[0] = 0;
			} else if (key == 'a') {
				movement_state[1] = 0;
			} else if (key == 's') {
				movement_state[0] = 0;
			} else if (key == 'd') {
				movement_state[1] = 0;
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
