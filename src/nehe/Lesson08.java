package nehe.Lesson08;

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

public class Render implements GLEventListener {
	private static GraphicsEnvironment graphicsEnviorment;
	private static boolean isFullScreen = false;
	public static DisplayMode dm, dm_old;
	private static Dimension xgraphic;
	private static Point point = new Point(0, 0);
	
	private GLU glu = new GLU();
	
	private float xrot, yrot,zrot;
	private int [] texture = new int [3];
	/*
	 * New Code
	 *
	 */
	private float[] lightAmbient = { 0.5f, 0.5f, 0.5f, 1.0f };
	private float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float[] lightPosition = { 0.0f, 0.0f, 2.0f, 1.0f };
	private int filter;
	private float xspeed, yspeed;
	private boolean light;
	/*
	 * New Code
	 *
	 */
	private boolean blend;
	
	/*
	 * (non-Javadoc)
	 * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
		 gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
		    gl.glLoadIdentity();                       // Reset The View
		    gl.glTranslatef(0f, 0f, -5.0f);
		    gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		    gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		    gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
		    gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[filter]);
		    if(light)
		    	gl.glEnable(GL2.GL_LIGHTING);
		    else
		    	gl.glDisable(GL2.GL_LIGHTING);
		    if(blend){
		    	gl.glEnable(GL2.GL_BLEND);
		    	gl.glDisable(GL2.GL_DEPTH_TEST);
		    }else{
		    	gl.glDisable(GL2.GL_BLEND);
		    	gl.glEnable(GL2.GL_DEPTH_TEST);
		    }
		    gl.glBegin(GL2.GL_QUADS);
	        // Front Face
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
	        // Back Face
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
	        // Top Face
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
	        // Bottom Face
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
	        // Right face
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
	        // Left Face
	        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
	        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
	        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
	      gl.glEnd();
		    gl.glFlush();
		    xrot+=.3f;
		    yrot+=.2f;
		    zrot+=.4;
		
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
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		try{
			File im = new File("data/Glass.bmp");
			Texture t = TextureIO.newTexture(im, true);
			texture[0]= t.getTextureObject(gl);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			gl.glBindTexture(GL2.GL_TEXTURE_2D,	 texture[0]);
			
			 t = TextureIO.newTexture(im, true);
			texture[1]= t.getTextureObject(gl);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,  GL2.GL_LINEAR);
			gl.glBindTexture(GL2.GL_TEXTURE_2D,	 texture[1]);
			
			 t = TextureIO.newTexture(im, true);
			texture[2]= t.getTextureObject(gl);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_NEAREST);
			gl.glBindTexture(GL2.GL_TEXTURE_2D,	 texture[2]);
			/**
			 * Lighting
			 */
			
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, this.lightAmbient,0);
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, this.lightDiffuse,0);
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, this.lightPosition,0);
		
			gl.glEnable(GL2.GL_LIGHT1);
			gl.glEnable(GL2.GL_LIGHTING);
			
			this.light=true;
			
			gl.glColor4f(1f, 1f, 1f, 0.5f);//50% Alpha
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
			
		}catch(IOException e){
			e.printStackTrace();
		}
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
		
		final JFrame frame = new JFrame ("nehe: Lesson 08");
		
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
		InputMap inputMap = p.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		/*
		 * 
		 */
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
		/**
		 * 
		 */
		/*
		 * 
		 */
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
		actionMap.put("UP", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				r.xspeed+=0.1f;
			}});
		/**
		 * 
		 */
		/*
		 * 
		 */
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
		actionMap.put("DOWN", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				r.xspeed-=0.1f;
			}});
		/**
		 * 
		 */
		/*
		 * 
		 */
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
		actionMap.put("LEFT", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				r.yspeed+=0.1f;
			}});
		/**
		 * 
		 */
		/*
		 * 
		 */
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
		actionMap.put("RIGHT", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				r.yspeed-=0.1f;
			}});
		/**
		 * 
		 */
		/*
		 * 
		 */
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "F");
		actionMap.put("F", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				r.filter++;
				if(r.filter>r.texture.length-1)
					r.filter=0;
			}});
		/**
		 * 
		 */
		/*
		 * 
		 */
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "L");
		actionMap.put("L", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				if(r.light)
					r.light=false;
				else
					r.light=true;
			}});
		/**
		 * 
		 */
		/*
		 * 
		 */
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "B");
		actionMap.put("B", new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6576101918414437189L;

			@Override
			public void actionPerformed(ActionEvent drawable) {
				// TODO Auto-generated method stub
				r.blend= r.blend?false:true;
				
			}});
		/**
		 * 
		 */
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