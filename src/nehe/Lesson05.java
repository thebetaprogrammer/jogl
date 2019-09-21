package nehe.Lesson05;



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
	
	private float rquad=0.0f, rtri =0.0f;
	
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
		 gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
		    gl.glLoadIdentity();                       // Reset The View
			
		    gl.glTranslatef(-1.5f,0.0f,-6.0f);                 // Move Left 1.5 Units And Into The Screen 6.0
		   
		    gl.glRotatef(rtri, 0.0f, 1.0f, 0.0f);//(NEW!)
		 
		    gl.glBegin(GL2.GL_TRIANGLES);                      // Drawing Using Triangl.gles
		    /*
			 * Front
			 */
			gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
			gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Front)
			gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
			gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Left Of Triangle (Front)
			gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
			gl.glVertex3f(1.0f, -1.0f, 1.0f); // Right Of Triangle (Front)
			/*
			 * Right
			 */
			gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
			gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Right)
			gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
			gl.glVertex3f(1.0f, -1.0f, 1.0f); // Left Of Triangle (Right)
			gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
			gl.glVertex3f(1.0f, -1.0f, -1.0f); // Right Of Triangle (Right)
			/*
			 * Left
			 */
			gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
			gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Back)
			gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
			gl.glVertex3f(1.0f, -1.0f, -1.0f); // Left Of Triangle (Back)
			gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
			gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Right Of Triangle (Back)
			/*
			 * 
			 */
			gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
			gl.glVertex3f(0.0f, 1.0f, 0.0f); // Top Of Triangle (Left)
			gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
			gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Left Of Triangle (Left)
			gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
			gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Right Of Triangle (Left)

			gl.glEnd(); // Done Drawing The Pyramid

			/**
			 * Cube
			 */

			gl.glLoadIdentity();
			gl.glTranslatef(1.5f, 0.0f, -7.0f); // Move Right And Into The Screen

			gl.glRotatef(rquad, 1.0f, 1.0f, 1.0f); // Rotate The Cube On X, Y & Z

			gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube

			gl.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
			gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
			gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
			gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
			gl.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)

			gl.glColor3f(1.0f, 0.5f, 0.0f); // Set The Color To Orange
			gl.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad (Bottom)
			gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad (Bottom)
			gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Bottom)
			gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Bottom)

			gl.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
			gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
			gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
			gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Front)
			gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Front)

			gl.glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
			gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Back)
			gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Back)
			gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Back)
			gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Back)

			gl.glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
			gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
			gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
			gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
			gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Left)

			gl.glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
			gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
			gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
			gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Right)
			gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Right)
			gl.glEnd(); // Done Drawing The Quad
	    gl.glFlush();
	    rtri +=0.2f;
	    rquad -=0.15f;
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
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
		
		final JFrame frame = new JFrame ("nehe: Lesson 5");
		
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