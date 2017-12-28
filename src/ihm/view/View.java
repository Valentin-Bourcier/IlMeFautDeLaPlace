package ihm.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import model.MyNodeInterface;
import model.NodeDirectory;
import model.ServiceNode;

public class View extends Window {

	private static final long serialVersionUID = 1L;

	private JTabbedPane pane;
	private JTree tree;
	
	public View(String aTitle) {
		this.setTitle(aTitle);	
	}

	@Override
	public void initComponents() {
		pane = new JTabbedPane();
		JFrame vGlobalAnalyzePanel = new GlobalAnalyzeView();
		pane.addTab("test", vGlobalAnalyzePanel.getContentPane());
		ServiceNode root = (MyNodeInterface) NodeDirectory.NodeFactory
				.createINode(new File("/home/valentin/IMFDLP/"));
		TreeModel treeModel = new DefaultTreeModel(root.getTreeAsDefaultMutableTreeNode(null).getRoot());
		tree = new JTree(treeModel);
		tree.setShowsRootHandles(true);
	}

	@Override
	public void setLayout() {
		
		// Main Layout
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(tree), BorderLayout.WEST);
		this.add(pane, BorderLayout.CENTER);
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}
	
	public void render() {
		
		super.render();
		this.setPreferredSize(new Dimension(800, 600));
		this.pack();
	    
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    
	    JDialog launch = new JDialog(this, "Path", true);
	    new Launch("Path").render();
//	    Container pane = new Launch("Path").getContentPane();
//	    launch.setPreferredSize(new Dimension(600, 600));
//	    launch.pack();
//	    launch.setContentPane(pane);
//	    launch.setVisible(true);
	}
	
	public static void main(String[] args) {
		View view = new View("IMFDLP");
		view.render();
	}
}
