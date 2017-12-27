package ihm.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GlobalAnalyzeView extends Window{
	
	private static final long serialVersionUID = 1L;

	private JPanel center;
	
	@Override
	public void initComponents() {
		
		center = new JPanel();
		
	}

	@Override
	public void setLayout() {
		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		
		center.setLayout(new BorderLayout());
		
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}	

}
