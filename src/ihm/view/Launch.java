package ihm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import ihm.core.LaunchModel;
import ihm.core.Settings;
import model.NodeDirectory;

public class Launch extends JDialog implements View{

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
	private JList<String> list;
	protected static boolean customClose = true; 
	
	public Launch(JFrame aParentFrame, String aTitle, Boolean aModal) {
		super(aParentFrame, aTitle, aModal);
		render();
		customClose = false;
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
		list = new JList<>(LaunchModel.getModel());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
		center.add(new JLabel("Recent paths analyzed"), BorderLayout.NORTH);
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
				
				if(list.isSelectionEmpty() && !filePath.getText().equals(""))
				{
					LaunchModel.getModel().add(filePath.getText());
					Settings.PATH = filePath.getText();
				}
				else if(!list.isSelectionEmpty()) 
				{
					LaunchModel.getModel().add(list.getSelectedValue());
					Settings.PATH = list.getSelectedValue();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select a directory path with search button, or on history path list.", "Warning", JOptionPane.INFORMATION_MESSAGE);					
				}
				if(!list.isSelectionEmpty() || !filePath.getText().equals(""))
				{	
					Settings.SERVICE = NodeDirectory.NodeFactory.createINode(new File(Settings.PATH));
					LaunchModel.getModel().serialize();
					dispose();
				}
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				LaunchModel.getModel().remove(list.getSelectedIndices());
			}
		});
		
		
		if(customClose) {
			this.addWindowListener(new WindowAdapter() {

			    @Override
				public void windowClosing(WindowEvent e) {
			    	System.out.println("Lancement annulé.");
			    	System.exit(0);
				}
			});
		}
				
	}
	
	@Override
	public void render() {
		build();
		
		this.setPreferredSize(new Dimension(300, 200));
	    this.pack();
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
	
}
