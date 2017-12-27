package ihm.view;

import javax.swing.JFrame;

public abstract class Window extends JFrame{

	private static final long serialVersionUID = 1L;

	public abstract void initComponents();
	public abstract void setLayout();
	public abstract void bind();
	
	public void render() {
	    
		initComponents();
		setLayout();
		bind();
		
	}
	
}
