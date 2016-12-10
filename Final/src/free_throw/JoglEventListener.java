package free_throw;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	float power = 0.0f;
	float velocity = 0.0f;
	int hitRightMeter = 0;
	int hitLeftMeter = 0;
	boolean powerGrowth = false;
	boolean gameTrigger = false;
	boolean gameover = false;
	boolean backboardHit = false;
	boolean exploding = false;
	int ground_bounces = 0;
	
	float[] ball_initial = {0, 0, 0};
	float[] ball_translation = {0, 0, 0};
	float[] initial_velocity = {0, 0, 0};
	float new_y_velocity = 0;
	float ball_locationX = 0.0f;
	float ball_alpha = 0;
	float ball_size = 0.3f;
	float time_passed = 0;
	float time_bounced_y = 0;
	float time_bounced_z = 0;
	float time_increment = 0;
	float replay_increment = 0;
	float time_passed2 = 0;
	float[] new_ball_position = {0, 0, 0};
	
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
	Texture myMeter = null;
	Texture mySpeedSelection = null;
	
	float whiteSpecularLight [] = { 1.0f, 1.0f, 1.0f }; //set the light specular to white
	float ambientLight [] = { 0.3f, 0.3f, 0.35f }; //set ambient light for light bounce off court
	float diffuseLight [] = { 1.0f, 1.0f, 1.0f, 1.0f}; //set diffuse light intensity
	
	float orangeDiffuseMaterial [] = { 1.0f, 0.45f, 0.0f }; //set the material to orange
	float mShininess[] = { 20 }; //set the shininess of the material
	float whiteSpecularMaterial [] = { 1.0f, 1.0f, 1.0f }; //set the material to white
	
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
	        
	        float lightPosition_0[] = {0.0f, 10.0f, 0.0f, 1.0f}; // light position
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0); // set light0 as diffuse light with related property
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteSpecularLight, 0); // set light0 specular light
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0); // set light0 ambient light
			gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition_0, 0); // set light0 position
			
	        //gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        gl.glEnable(GL2.GL_LIGHTING); // enable lighting    
	        gl.glEnable(GL2.GL_LIGHT0); // enable light0

	        // load the texture;
	        
	        try {
	        	
	        	// "/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/skybox2.jpg"
	        	// "/Users/kevinlogan/Desktop/Workspace/basketball_textures/textures/skybox2.jpg"
	        	mytex = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/skybox2.jpg"), false);
	        	myHUD = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/HUD2.png"), false);
	        	myGround = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/grass.png"), false);
	        	myCourt = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/court2.png"), false);
	        	myBuildingFace1 = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/building1.png"), false);
	        	myBuildingFace2 = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/building2.png"), false);
	        	myBuildings = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/buildings_small.png"), false);
	        	myGoal = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/goal.jpg"), false);
	        	myUDLR = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/udlr.png"), false);
	        	myLR = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/lr.png"), false);
	        	myMeter = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/meter.png"), false);
	        	mySpeedSelection = TextureIO.newTexture(new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_textures/textures/speedSelection.png"), false);
	        	
//	        	 int texID = mytex.getTextureObject();
	        	 gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  
	             gl.glEnable(GL.GL_TEXTURE_2D);
	             myGoal.bind(gl);
	             myUDLR.bind(gl);
	             mytex.bind(gl);
	             myMeter.bind(gl);
	             mySpeedSelection.bind(gl);
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
	    	gl.glRotatef(rot, 0.0f, 1.0f, 0.0f);
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
//	    	// set the material property
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);	
			
			//Rim
			https://www.opengl.org/sdk/docs/man2/xhtml/glColorMaterial.xml
//			this seems to imply that this glColorMaterial call is necessary
	    	gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
	    	gl.glEnable(GL2.GL_COLOR_MATERIAL);
	    	gl.glColor3f(1.0f, 0.0f, 0.1f);	
	    	
			gl.glPushMatrix();
			gl.glTranslated(0, 4, -9);//-9.6+.6
			gl.glRotated(90, 1, 0, 0);
			gl.glColor3d(255, 0, 0); 
			glut.glutSolidTorus(0.028, 0.6, 5, 25);//rim radius is 0.6
			gl.glPopMatrix();

			//Stand
			https://www.opengl.org/sdk/docs/man2/xhtml/glColorMaterial.xml
//		   	this seems to imply that this glColorMaterial call is necessary
	    	gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
	    	gl.glEnable(GL2.GL_COLOR_MATERIAL);
	    	gl.glColor3f(0.8f, 0.8f, 0.8f);	
	    	
			gl.glPushMatrix();
			gl.glTranslated(0,4,-10);
			gl.glRotated(90, 1, 0, 0);
			gl.glColor3d(0, 0, 0);
			glut.glutSolidCylinder(0.1f, 4, 20, 20);   
		   	gl.glPopMatrix();
		   	
		   	//Backboard
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
	   public void drawMeter(final GL2 gl){
		   gl.glPushMatrix();
		   
		   gl.glEnable(GL.GL_TEXTURE_2D);
		   myMeter.bind(gl);
		   gl.glBegin(GL2.GL_QUADS);

		   gl.glTexCoord2f(0, 0);
		   gl.glVertex3f(-0.2f, -0.3f, -1);
		   gl.glTexCoord2f(0, 1);
		   gl.glVertex3f(-0.2f, -0.2f, -1);
		   gl.glTexCoord2f(1, 1);
		   gl.glVertex3f(0.2f, -0.2f, -1);
		   gl.glTexCoord2f(1, 0);
		   gl.glVertex3f(0.2f, -0.3f, -1);
		   gl.glEnd();
		   gl.glDisable(GL.GL_TEXTURE_2D);
		   
		   gl.glTranslated(power, 0, 0);
		   
		   gl.glEnable(GL2.GL_LINE_SMOOTH);
		   gl.glColor3f(0.0f, 0.0f, 0.0f);
		   gl.glBegin(GL2.GL_LINES);
		   gl.glVertex3f(-0.195f, -0.3f, -1);
		   gl.glVertex3f(-0.195f, -0.2f, -1);
		   gl.glEnd();
		   
		   gl.glPopMatrix();
	   }
	   public void drawVelocityMeter(final GL2 gl){
		   if(gameTrigger && !gameover){
			   drawMeter(gl);
			   if(powerGrowth){
				   //Determine which direction for the line to translate
				   if(hitRightMeter >= 2 && hitLeftMeter >= 1){
					   if(gameover==false){
						   System.out.println("You Lose");
						   playSound("lose.wav");
						   gameover=true;
					   }
				   }
				   else if((hitRightMeter == 0 && hitLeftMeter == 0) || (hitRightMeter == 1 && hitLeftMeter == 1))  
					   power += 0.01f;
				   else
					   power -= 0.01f;

				   //Increment if right or left side of bar is hit
				   if(power >= 2*(0.195f))
					   hitRightMeter++;
				   if(power <= 0)
					   hitLeftMeter++;
			   }
			   //		          gl.glColor3d(255, 140, 0);
			   //		          glut.glutSolidSphere(0.5f, 30, 30);
		   }	        
	   }

	    public void resetGame() {
	    	movement[0] = 0;
	    	movement[1] = 0;
	    	initial_velocity[0] = 0;
	    	initial_velocity[1] = 0;
	    	initial_velocity[2] = 0;
	    	ball_translation[0] = 0;
	    	ball_translation[1] = 0;
	    	ball_translation[2] = 0;
	    	rot = 0;
	    	y_lookat = 0;
	    	gameTrigger = false;
	    	powerGrowth = false;
	    	power = 0;
	    	hitRightMeter = 0;
	    	hitLeftMeter = 0;
	    	velocity = 0;
	    	ball_alpha = 0;
	    	time_passed = 0;
	    	time_increment = 0;
	    	ball_size = 0.3f;
	    	backboardHit=false;
	    	ground_bounces = 0;
	    	gameover=false;
	    	exploding=false;
	    	
	    	time_bounced_y = 0;
	    	time_bounced_z = 0;
	    	
	    	System.out.println("reset");
	    }
	    public void shootBall() {
	    	//move ball to player location
	    	ball_initial[0] = -movement[1];
	    	ball_initial[1] = 1.6f;
	    	ball_initial[2] = movement[0];
	    	
	    	//get initial velocity
			velocity = ((power *(200))/3.9f);
	    	
	    	//make ball visible
	    	ball_alpha = 1;
	    	time_increment = 0.01f;
	    
	    	//calculate initial velocities
	    	float ud_angle = (float)Math.atan(y_lookat/10) + 0.2f; // adding 0.2 so the angle is a little higher
	    	initial_velocity[1] = (float) (velocity * Math.sin(ud_angle));
	    	new_y_velocity = initial_velocity[1];
	    	float v_xz = (float) (velocity * Math.cos(ud_angle));
	    	float lr_angle = (float)(rot*Math.PI/180);
	    	initial_velocity[0] = (float) (v_xz * Math.sin(lr_angle));
	    	initial_velocity[2] = (float) (-v_xz * Math.cos(lr_angle));
	    }
	    
	    public void drawBall(final GL2 gl) {
	    	gl.glEnable(gl.GL_BLEND);
	    	gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_ONE_MINUS_SRC_ALPHA);
	    	
//	    	// set the material property
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, whiteSpecularMaterial, 0);
			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mShininess, 0);	
			
			// found a better way for the diffuse light than this. glColorMaterial
//			gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, orangeDiffuseMaterial, 0);
	    	
	    	gl.glPushMatrix();
	    	
	    	updateBallLocation();
	    	
	    	gl.glTranslatef(ball_initial[0] + ball_translation[0], ball_initial[1] + ball_translation[1], ball_initial[2] + ball_translation[2]);
	    	
	    	//ball exploding
	    	if(exploding && ball_size < 10 && ball_alpha > 0){
	    	  ball_size+=.15f;
	    	  ball_alpha-=.04f;
	    	}
	    	
//	    	https://www.opengl.org/sdk/docs/man2/xhtml/glColorMaterial.xml
//	    	this seems to imply that this glColorMaterial call is necessary
	    	gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
	    	gl.glEnable(GL2.GL_COLOR_MATERIAL);
	    	gl.glColor4f(1, 0.45f, 0, ball_alpha);	
	    	
	    	glut.glutSolidSphere(ball_size, 20, 20);
	    	
	    	gl.glPopMatrix();
	    }
	    public void updateBallLocation() {
	    	//if ground is hit
	    	if(ball_translation[1] < (-1.6 + ball_size) && gameover==false){
	    		new_y_velocity = 0.75f * -(new_y_velocity - 9.8f * (time_passed - time_bounced_y) );
	    		new_ball_position[1] = ball_translation[1] + 0.2f;
	    		time_bounced_y = time_passed;
	    		playSound("backboard.wav");
	    		
	    		ground_bounces++;
	    		if (ground_bounces > 3) {
	    			//explode
	    			System.out.println("You Lose");
	    			playSound("lose.wav");
	    			exploding = true;
	    			gameover = true;
	    		}
	    	}
	    	
	    	//if board is hit
	    	if(((ball_locationX >= (-1 - ball_size) && ball_locationX <= (1 + ball_size)) 
	    			&& (ball_translation[1] >= (3.8f - 1.6f - ball_size) && ball_translation[1] < (5 - 1.6f + ball_size)) 
	    			&& (ball_translation[2] >= -10f + ball_size &&  ball_translation[2] <= -9.6f + ball_size))
	    			&& backboardHit == false){
	    		System.out.println("Backboard Hit");
	    		playSound("backboard.wav");
	    		time_passed2=0;

	    		new_ball_position[2] = ball_translation[2];
	    		time_bounced_z = time_passed;
	    		System.out.println("new_ball_position[2] = " + new_ball_position[2]);
	    		backboardHit=true;
	    	}
	    	
	    	//If rim isn't hit
	    	if(((ball_locationX * ball_locationX) + (ball_translation[2] + 9) * (ball_translation[2] + 9)) <= (0.4f*0.4f) 
	    			&& (ball_translation[1] >= 3.8f - 1.6f && ball_translation[1] <= 4.1f - 1.6f)
	    			&& gameover == false){
	    		gameover = true;
	    		playSound("win.wav");
	    		System.out.println("You Win");
	    	}
	    	
	    	else if(((ball_locationX * ball_locationX) + (ball_translation[2] + 9) * (ball_translation[2] + 9)) <= (0.8f*0.8f) 
	    			&& (ball_translation[1] >= 3.8f - 1.6f && ball_translation[1] <= 4.1f - 1.6f)
	    			&& gameover == false){
	    		gameover = true;
	    		exploding = true;
	    		playSound("lose.wav");
	    		System.out.println("Rim hit");
	    		System.out.println("You Lose");
	    	}
	    	
	    	//if game isn't over
	    	if (gameover == false){
	    		
	    		ball_translation[0] = initial_velocity[0] * time_passed;
	    		
	    		if (ground_bounces > 0)
	    			ball_translation[1] = new_ball_position[1] + (new_y_velocity * (time_passed - time_bounced_y)) - (9.8f/2 * (time_passed - time_bounced_y) * (time_passed - time_bounced_y));
	    		else
	    			ball_translation[1] = (initial_velocity[1] * time_passed) - (9.8f/2 * time_passed * time_passed);
	    		
	    		if(backboardHit){
	    			// These 2 lines will have to be adjusted for actual physics.
	    			// I gotchu
	    			ball_translation[2] = new_ball_position[2] - initial_velocity[2]*0.9f * (time_passed - time_bounced_z); // multiply by 0.9 to reduce speed a little bit
	    		}
	    		else {
	    			ball_translation[2] = initial_velocity[2] * time_passed;
	    		}
	    		
	    		ball_locationX = ball_initial[0] + ball_translation[0];
			}
	    		    	
	    	time_passed += time_increment;
	    	time_passed2 += time_increment;
	    }
	    
	    public void drawReplay(final GL2 gl) {
	    	if(gameover){
	    		drawSpeedSelection(gl);
	    	}
	    }
	    
	    public void replay(){
	    	ball_translation[0] = 0;
	    	ball_translation[1] = 0;
	    	ball_translation[2] = 0;
	    	new_y_velocity = initial_velocity[1];
	    	time_passed = 0;
	    	ball_alpha=1;
	    	ball_size = 0.3f;
	    	exploding=false;
	    	backboardHit=false;
	    	gameover=false;
	    	ground_bounces = 0;
	    	time_bounced_y = 0;
	    }

	    public void drawSpeedSelection(final GL2 gl){
	    	gl.glPushMatrix();

	    	gl.glEnable(GL.GL_TEXTURE_2D);
	    	mySpeedSelection.bind(gl);
	    	gl.glBegin(GL2.GL_QUADS);

	    	gl.glTexCoord2f(0, 0);
	    	gl.glVertex3f(-0.2f, -0.35f, -1);
	    	gl.glTexCoord2f(0, 1);
	    	gl.glVertex3f(-0.2f, -0.2f, -1);
	    	gl.glTexCoord2f(1, 1);
	    	gl.glVertex3f(0.2f, -0.2f, -1);
	    	gl.glTexCoord2f(1, 0);
	    	gl.glVertex3f(0.2f, -0.35f, -1);
	    	gl.glEnd();
	    	gl.glDisable(GL.GL_TEXTURE_2D);

	    	gl.glPopMatrix();
	    }

	    public void cheat(){
	    	movement[0] = 0;
	    	movement[1] = 0;
	    	rot = 0;
	    	y_lookat = 8.59997f;
	    	power = 0.210002f;
	    	ball_translation[0] = 0;
	    	ball_translation[1] = 0;
	    	ball_translation[2] = 0;
	    	new_y_velocity = initial_velocity[1];
	    	time_passed = 0;
	    	ball_alpha=1;
	    	ball_size = 0.3f;
	    	exploding=false;
	    	backboardHit=false;
	    	gameover=false;
	    	gameTrigger=true;
	    	ground_bounces = 0;
	    	time_bounced_y = 0;
	    	shootBall();
	    }
	    public void lose(){
	    	movement[0] = 0;
	    	movement[1] = 0;
	    	rot = 0;
	    	y_lookat = 3.29999f;
	    	power = 0.27f;
	    	ball_translation[0] = 0;
	    	ball_translation[1] = 0;
	    	ball_translation[2] = 0;
	    	new_y_velocity = initial_velocity[1];
	    	time_passed = 0;
	    	ball_alpha=1;
	    	ball_size = 0.3f;
	    	exploding=false;
	    	backboardHit=false;
	    	gameover=false;
	    	gameTrigger=true;
	    	ground_bounces = 0;
	    	time_bounced_y = 0;
	    	shootBall();
	    }
	    
	    @Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();
			
			
			
//			movement[0] -= (movement_state[0]*0.04)*Math.cos(rot/180*Math.PI) + (movement_state[1]*0.04)*Math.sin(-rot/180*Math.PI);
//			movement[1] -= (movement_state[1]*0.04)*Math.cos(rot/180*Math.PI) + (movement_state[0]*0.04)*Math.sin(rot/180*Math.PI);
			if(movement_state[1] == 1)
				movement[1] += 0.1;
			if(movement_state[1] == -1)
				movement[1] -= 0.1;
			rot -= drag_amount[0];
			y_lookat -= drag_amount[1]/10;
			
			gl.glLoadIdentity();
			
			gl.glPushMatrix();
			glu.gluLookAt(0, 1.5, 0, 0, y_lookat, -10, 0, 1, 0);
			
			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			
			renderSkyBox(gl);
	        gl.glRotatef(rot, 0.0f, 1.0f, 0.0f);
	        gl.glTranslated(movement[1], -0.5, movement[0]);
	        
	        drawGoal(gl);
	        drawGround(gl);
	        drawCourt(gl);
	        drawBuildings(gl);
	        drawBall(gl);
			
			gl.glPopMatrix();	
			drawArrows(gl);
	        drawHUD(gl);
	        drawVelocityMeter(gl);
	        drawReplay(gl);
	        
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
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.println(XX);
			System.out.println(YY);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			
			mouse_last[0] = XX;
			mouse_last[1] = YY;
			
			//look up
			if(YY > -5.45 && YY < -1.5 && XX > -15 && XX < -11.4)
				drag_amount[1] = -1;
			//look right
			if(XX > -12 && XX < -8 && YY < -4.85 && YY > -8.6)
				drag_amount[0] = -1;
			//look left
			if(XX > -18.7 && XX < -14.7 && YY < -4.85 && YY > -8.6)
				drag_amount[0] = 1;
			//look down
			if(YY > -12 && YY < -8.15 && XX > -15 && XX < -11.4)
				drag_amount[1] = 1;
			//left
			if(XX < 13.5 && XX > 9.8 && YY < -4.6 && YY > -8.3)
				movement_state[1] = 1;
			//right
			if(XX < 17 && XX > 13.5 && YY < -4.6 && YY > -8.3)
				movement_state[1] = -1;
			
			if(XX > -5.2 && XX < -2.5 && YY < -7 && YY > -9){
				time_increment=0.01f;
				replay();
			}
			if(XX > -2.5 && XX < 1.8 && YY < -7 && YY > -9){
				time_increment=(float)1/4*(0.01f);
				System.out.println(time_increment);
				replay();
			}
			if(XX > 1.8 && XX < 5.1 && YY < -7 && YY > -9){
				time_increment=(float)1/10*(0.01f);
				replay();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			drag_amount[0] = 0;
			drag_amount[1] = 0;
			movement_state[1] = 0;
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
				if(!gameTrigger){
					gameTrigger=true;
					powerGrowth=true;
				}
				else{
					powerGrowth=false;
					playSound("shoot.wav");
					shootBall();
				}
		    } else if (key == 'r') {
		    	resetGame();
		    } else if (key == 'f') {
		    	updateBallLocation();
		    }
			
			if(key == 'c')
				cheat();
			if(key == 'l')
				lose();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			char key= e.getKeyChar();
			System.out.printf("Key pressed: %c\n", key);
			
			if (key == 'w') {
				//movement_state[0] = 1;
				drag_amount[1] = -1;
			} else if (key == 'a') {
				//movement_state[1] = 1;
				drag_amount[0] = 1;
			} else if (key == 's') {
				//movement_state[0] = -1;
				drag_amount[1] = 1;
			} else if (key == 'd') {
				//movement_state[1] = -1;
				drag_amount[0] = -1;
			} else if (key == 'r') {
				System.out.println(rot);
			}
			
			 if (e.getKeyCode()  == KeyEvent.VK_RIGHT )
		            movement_state[1] = -1;
			 if (e.getKeyCode()  == KeyEvent.VK_LEFT )
		            movement_state[1] = 1;
			 
//			 if (e.getKeyCode()  == KeyEvent.VK_UP ) movement_state[0] = 1;
//			 if (e.getKeyCode()  == KeyEvent.VK_DOWN ) movement_state[0] = -1;
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			char key= e.getKeyChar();
			System.out.printf("Key released: %c\n", key);
			
			if (key == 'w') {
				//movement_state[0] = 0;
				drag_amount[1] = 0;
			} else if (key == 'a') {
				//movement_state[1] = 0;
				drag_amount[0] = 0;
			} else if (key == 's') {
				//movement_state[0] = 0;
				drag_amount[1] = 0;
			} else if (key == 'd') {
				//movement_state[1] = 0;
				drag_amount[0] = 0;
			}
			
			 if (e.getKeyCode()  == KeyEvent.VK_RIGHT )
		            movement_state[1] = 0;
			 if (e.getKeyCode()  == KeyEvent.VK_LEFT )
		            movement_state[1] = 0;
//			 if (e.getKeyCode()  == KeyEvent.VK_UP ) movement_state[0] = 0;
//			 if (e.getKeyCode()  == KeyEvent.VK_DOWN ) movement_state[0] = 0;
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

		public static synchronized void playSound(final String url) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Clip clip = AudioSystem.getClip();
						File soundFile = new File("/Users/epheat07/Documents/git_repositories_fa2016/Graphics335/Final/basketball_sounds/" + url);						AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);
						clip.open(inputStream);
						clip.start(); 
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			}).start();
		}

}
