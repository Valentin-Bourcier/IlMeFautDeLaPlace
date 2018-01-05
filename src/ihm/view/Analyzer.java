package ihm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import ihm.core.FileTreeModel;
import ihm.core.Settings;
import model.ServiceNode;

public class Analyzer extends JFrame implements View {

	private static final long serialVersionUID = 1L;

	private JSplitPane pane;
	public static JTree tree;

	public Analyzer(String aTitle) {
		this.setTitle(aTitle);
	}

	@Override
	public void initComponents() {

		pane = new JSplitPane();

		TreeModel treeModel = new FileTreeModel(Settings.SERVICE.getTreeAsDefaultMutableTreeNode(null).getRoot());

		tree = new JTree(treeModel);
		tree.setShowsRootHandles(true);
	}

	@Override
	public void setLayout() {

		// Main Layout
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(tree), BorderLayout.WEST);
		this.add(pane, BorderLayout.CENTER);

		pane.add(tree, JSplitPane.LEFT);
		pane.add(TabsManager.getManager().getPane(), JSplitPane.RIGHT);

		tree.setPreferredSize(new Dimension(180, getHeight()));

	}

	@Override
	public void bind() {

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) Analyzer.tree.getLastSelectedPathComponent();
				Settings.SERVICE = (ServiceNode) node.getUserObject();
				JPanel vPanel = TabsManager.getManager().selectedTab();
				((View) vPanel).refresh();
				TabsManager.getManager().getPane().repaint();
				System.out.println("Selection changed: " + Settings.SERVICE.filename());
			}
		});

	}

	@Override
	public void render() {
		new Launch(this, "IMFDLP", true);

		build();

		this.setPreferredSize(new Dimension(800, 600));
		this.pack();

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Analyzer view = new Analyzer("IMFDLP");
		view.render();
	}
}
