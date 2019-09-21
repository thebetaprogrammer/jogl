/**
 * 
 */
package nehe.Lesson09;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Render allows java to talk to the graphics card via openGL 

 * Implements{@link GLEventListener}
 * @author Beta
 *
 */
public class Render implements GLEventListener{

	private static GraphicsEnvironment graphicsEnviorment;
	private static boolean isFullScreen = false;
	public static DisplayMode dm, dm_old;
	private static Dimension xgraphic;
	private static Point point = new Point(0, 0);
	
	/*
	 * Added at start of tutorial two...
	 */
	private GLU glu = new GLU();
	
	private boolean twinkle;
	final int num=50;
	stars star[] = new stars[num];
	class stars{
		int r,g,b;
		float dist, angle;
	}
	float zoom =-15.0f, tilt=90f, spin;
	int [] texture = new int [1];
	
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		final GL2 gl = drawable.getGL().getGL2();
		
		 gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
	      gl.glBindTexture(gl.GL_TEXTURE_2D, texture[0]);     // Select Our Texture
	      
	      for(int loop=num-1; loop>=0; loop--){                   // Loop Through All The Stars
		        gl.glLoadIdentity();                              // Reset The View Before We Draw Each Star
		        gl.glTranslatef(0.0f,0.0f,zoom);                  // Zoom Into The Screen (Using The Value In 'zoom')
		        gl.glRotatef(tilt,1.0f,0.0f,0.0f);                // Tilt The View (Using The Value In 'tilt')
		        gl.glRotatef(star[loop].angle,0.0f,1.0f,0.0f);    // Rotate To The Current Stars Angle
		        gl.glTranslatef(star[loop].dist,0.0f,0.0f);       // Move Forward On The X Plane
		        gl.glRotatef(-star[loop].angle,0.0f,1.0f,0.0f);   // Cancel The Current Stars Angle
		        gl.glRotatef(-tilt,1.0f,0.0f,0.0f);               // Cancel The Screen Tilt

		        if(twinkle){
		          gl.glColor4ub((byte)star[(num-loop)-1].r,(byte)star[(num-loop)-1].g,(byte)star[(num-loop)-1].b,(byte)255);
		          gl.glBegin(gl.GL_QUADS);
		            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,-1.0f, 0.0f);
		            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,-1.0f, 0.0f);
		            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f, 1.0f, 0.0f);
		            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, 1.0f, 0.0f);
		          gl.glEnd();
		        }

		        gl.glRotatef(spin,0.0f,0.0f,1.0f);
		        gl.glColor4ub((byte)star[loop].r,(byte)star[loop].g,(byte)star[loop].b,(byte)255);
		        gl.glBegin(gl.GL_QUADS);
		          gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,-1.0f, 0.0f);
		          gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,-1.0f, 0.0f);
		          gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f, 1.0f, 0.0f);
		          gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, 1.0f, 0.0f);
		        gl.glEnd();

		        spin+=0.01f;
		        star[loop].angle+=(float)loop/num;
		        star[loop].dist-=0.01f;

		        if(star[loop].dist<0.0f){
		           star[loop].dist+=5.0f;
		           star[loop].r = (int)(Math.random()*1000)%256;
		           star[loop].g = (int)(Math.random()*1000)%256;
		           star[loop].b = (int)(Math.random()*1000)%256;
		        }
		      }
	      
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(gl.GL_TEXTURE_2D);                              // Enable Texture Mapping
	      gl.glShadeModel(gl.GL_SMOOTH);                              // Enable Smooth Shading
	      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);                    // Black Background
	      gl.glClearDepth(1.0f);                                      // Depth Buffer Setup
	      gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT,gl.GL_NICEST);  // Really Nice Perspective Calculations
	      gl.glBlendFunc(gl.GL_SRC_ALPHA,gl.GL_ONE);                  // Set The Blending Function For Translucency
	      gl.glEnable(gl.GL_BLEND);
	      
	    // Not code is messing after this line
    }

}