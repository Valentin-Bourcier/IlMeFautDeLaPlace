package ihm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import ihm.core.LaunchManager;

public class Launch extends Window{

	private static final long serialVersionUID = 1L;

	private JLabel label;
	private JFileChooser chooser;
	private JButton buttonFileChooser;
	private JTextField filePath;
	private JPanel top;
	private JPanel center;
	private JScrollPane pathPanel;
	private JPanel bottom;
	private JButton delete;
	private JButton launch;
	private JList list;
	
	public Launch(String aTitle) {
		this.setTitle(aTitle);
	}
	
	@Override
	public void initComponents() {
		
		// Top components
		top = new JPanel();
		label = new JLabel("Choose a directory or a disk to analyze");
		filePath = new JTextField();
		buttonFileChooser = new JButton("Select");
		
		// Center components
		center = new JPanel();
		list = new JList<>(LaunchManager.getManager());
		pathPanel = new JScrollPane(list);
		
		// Bottom components
		bottom = new JPanel();
		delete = new JButton("Delete");
		launch = new JButton("Launch");
	}
	
	@Override
	public void setLayout() {
		
		// View Layout
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		
		// Top Layout
		top.setLayout(new BorderLayout());
		top.add(label, BorderLayout.NORTH);
		top.add(filePath, BorderLayout.CENTER);		
		top.add(buttonFileChooser, BorderLayout.EAST);
		
		// Center Layout
		center.setLayout(new BorderLayout());
		center.add(new JLabel("Path history"), BorderLayout.NORTH);
		center.add(pathPanel, BorderLayout.CENTER);
		
		// Bottom Layout
		bottom.setLayout(new GridLayout(1, 2));
		bottom.add(delete);
		bottom.add(launch);
	}
	
	@Override
	public void bind() {
		chooser = new JFileChooser(System.getProperty("user.home"));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		buttonFileChooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				if(file.getAbsolutePath() != null) {
					filePath.setText(file.getAbsolutePath());					
				}
			}
		
		});
		launch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(list.isSelectionEmpty() && !filePath.getText().equals("")) {
					LaunchManager.getManager().addPath(filePath.getText());
					
					list.repaint();
				}else {
					System.out.println(list.getSelectedValue().toString());
				}
				
			}
		});
		
		
		
	}
	
	@Override
	public void render() {
	    super.render();
		
		this.setPreferredSize(new Dimension(400, 280));
		this.pack();
	    
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Launch launch = new Launch("IMFDLP");
		launch.render();
	}
	
}
