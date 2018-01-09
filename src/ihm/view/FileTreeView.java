package ihm.view;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import ihm.model.FileTreeModel;
import ihm.model.Settings;
import ihm.view.main.View;
import ihm.view.tabs.TabsManager;
import model.ServiceNode;

public class FileTreeView extends JTree implements View {

	private static final long serialVersionUID = 1L;
	private static FileTreeView instance;

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
		setShowsRootHandles(true);
	}

	@Override
	public void setLayout() {
		setPreferredSize(new Dimension(180, getHeight()));
	}

	@Override
	public void load() {
		TreeModel vTreeModel = new FileTreeModel(Settings.service.getTreeAsDefaultMutableTreeNode(null).getRoot());
		setModel(vTreeModel);
	}

	@Override
	public void bind() {
		addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
				if (node != null)
				{
					Settings.service = (ServiceNode) node.getUserObject();
					TabsManager.getManager().selectedTab().refresh();
					TabsManager.getManager().getPane().repaint();
				}
			}
		});
	}

	public void setSelection(ServiceNode aNode) {
		FileTreeModel vModel = (FileTreeModel) getModel();
		DefaultMutableTreeNode vNode = vModel.find(aNode);
		setSelectionPath(new TreePath(vNode.getPath()));
	}

	@Override
	public void refresh() {
		removeAll();
		revalidate();
		repaint();
	}

}
