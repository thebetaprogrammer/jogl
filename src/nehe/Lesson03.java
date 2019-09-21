package nehe.Lesson03;


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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * @author Beta
 *
 */
public class Render implements GLEventListener{

	private static GraphicsEnvironment graphicsEnviorment;
	private static boolean isFullScreen = false;
	public static DisplayMode dm, dm_old;
	private static Dimension xgraphic;
	private static Point point = new Point(0, 0);
	
	private GLU glu = new GLU();
	
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
		 gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
		    gl.glLoadIdentity();                       // Reset The View
			
		    gl.glTranslatef(-1.5f,0.0f,-6.0f);                 // Move Left 1.5 Units And Into The Screen 6.0
		 gl.glBegin(GL2.GL_TRIANGLES);                      // Drawing Using Triangl.gles
		 gl.glColor3f(1.0f, 0.0f, 0.0f); //Red
		    gl.glVertex3f( 0.0f, 1.0f, 0.0f);              // Top
		    gl.glColor3f(0.0f, 1.0f, 0.0f); //Green
		    gl.glVertex3f(-1.0f,-1.0f, 0.0f);              // Bottom Left
		    gl.glColor3f(0.0f, 0.0f, 1.0f); //Blue
		    gl.glVertex3f( 1.0f,-1.0f, 0.0f);              // Bottom Right
		gl.glEnd();                            // Finished Drawing The Triangl.gle
		gl.glTranslatef(3.0f,0.0f,0.0f);                   // Move Right 3 Units
		
		gl.glColor3f(0.5f,0.5f,1.0f);              // Set The Color To Blue One Time Only
		 gl.glBegin(GL2.GL_QUADS);                      // Draw A Quad
	        gl.glVertex3f(-1.0f, 1.0f, 0.0f);              // Top Left
	        gl.glVertex3f( 1.0f, 1.0f, 0.0f);              // Top Right
	        gl.glVertex3f( 1.0f,-1.0f, 0.0f);              // Bottom Right
	        gl.glVertex3f(-1.0f,-1.0f, 0.0f);              // Bottom Left
	    gl.glEnd();                            // Done Drawing The Quad
	    gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
		 
		if(height <=0)
			height =1;
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// setUp open GL version 2
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		
		// The canvas 
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Render r = new Render();
		glcanvas.addGLEventListener(r);
		glcanvas.setSize(400, 400);
		
		final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true );
		
		final JFrame frame = new JFrame ("nehe: Lesson 2");
		
		frame.getContentPane().add(glcanvas);
		
		//Shutdown
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(animator.isStarted())
					animator.stop();
				System.exit(0);
			}
		});
		
		frame.setSize(frame.getContentPane().getPreferredSize());
		/**
		 * Centers the screen on start up
		 * 
		 */
		graphicsEnviorment = GraphicsEnvironment.getLocalGraphicsEnvironment();

		GraphicsDevice[] devices = graphicsEnviorment.getScreenDevices();

		dm_old = devices[0].getDisplayMode();
		dm = dm_old;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int windowX = Math.max(0, (screenSize.width - frame.getWidth()) / 2);
		int windowY = Math.max(0, (screenSize.height - frame.getHeight()) / 2);

		frame.setLocation(windowX, windowY);
		/**
				 * 
				 */
		frame.setVisible(true);
		/*
		 * Time to add Button Control
		 */
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(0,0));
		frame.add(p, BorderLayout.SOUTH);
		
		keyBindings(p, frame, r);
		animator.start();
	}

	private static void keyBindings(JPanel p, final JFrame frame, final Render r) {
	
		ActionMap actionMap = p.getActionMap();
		InputMap inputMap = p.getInputMap();
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
		actionMap.put("F1", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				fullScreen(frame);
			}});
	}

	protected static void fullScreen(JFrame f) {
		// TODO Auto-generated method stub
		if(!isFullScreen){
			f.dispose();
			f.setUndecorated(true);
			f.setVisible(true);
			f.setResizable(false);
			xgraphic = f.getSize();
			point = f.getLocation();
			f.setLocation(0, 0);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			f.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
			isFullScreen=true;
		}else{
			f.dispose();
			f.setUndecorated(false);
			f.setResizable(true);
			f.setLocation(point);
			f.setSize(xgraphic);
			f.setVisible(true);
			
			isFullScreen =false;
		}
	}


}