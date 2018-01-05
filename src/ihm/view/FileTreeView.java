package ihm.view;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import ihm.core.FileTreeModel;
import ihm.core.Settings;

public class FileTreeView extends JTree implements View {

	private static final long serialVersionUID = 1L;
	private static FileTreeView instance;

	private JTree tree;

	public FileTreeView() {
		render();
	}

	public static FileTreeView getView() {
		if (instance == null)
		{
			instance = new FileTreeView();
		}
		return instance;
	}

	@Override
	public void initComponents() {
		TreeModel treeModel = new FileTreeModel(Settings.SERVICE.getTreeAsDefaultMutableTreeNode(null).getRoot());
		tree = new JTree(treeModel);

		tree.setShowsRootHandles(true);
	}

	@Override
	public void setLayout() {
		tree.setPreferredSize(new Dimension(180, getHeight()));
	}

}
