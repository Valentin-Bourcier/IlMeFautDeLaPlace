package ihm.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import ihm.model.FileTreeModel;
import ihm.model.Settings;
import ihm.model.Style;
import ihm.view.main.View;
import ihm.view.tabs.TabsManager;
import model.NodeFile;
import model.ServiceNode;

public class FileTreeView extends JTree implements View {

	private static final long serialVersionUID = 1L;
	private static FileTreeView instance;
	private DefaultMutableTreeNode root;

	private JPopupMenu menu;
	private JMenuItem informations;
	private JMenuItem duplicates;
	private JMenuItem delete;
	private static boolean menuVisible = false;

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

		menu = new JPopupMenu();
		informations = new JMenuItem("More informations");
		duplicates = new JMenuItem("Duplicates Scan");
		delete = new JMenuItem("Delete");

		menu.add(informations);
		menu.add(duplicates);
		menu.add(delete);
		setComponentPopupMenu(menu);
	}

	@Override
	public void setLayout() {
		setPreferredSize(new Dimension(Style.DEFAULT_TREE_WIDTH, getHeight()));
	}

	@Override
	public void load() {
		root = (DefaultMutableTreeNode) Settings.service.getTreeAsDefaultMutableTreeNode(null).getRoot();
		TreeModel vTreeModel = new FileTreeModel(root);
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
					if (Settings.service.isDirectory())
					{
						menuVisible = true;
					}
					else
					{
						menuVisible = false;
					}
					TabsManager.getManager().selectedTab().refresh();
					TabsManager.getManager().getPane().repaint();
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e))
				{
					if (Settings.service.isDirectory())
					{
						menu.setVisible(true);
					}
					else
					{
						menu.setVisible(false);
					}
					menu.show((JComponent) e.getSource(), e.getX(), e.getY());
				}
			}
		});

		informations.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				TabsManager.getManager().open(TabsManager.INFORMATIONS);
			}
		});

		duplicates.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TabsManager.getManager().open(TabsManager.DUPLICATES);
			}
		});

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
				if (node != null)
				{
					FileTreeModel vModel = (FileTreeModel) getModel();
					NodeFile vNode = (NodeFile) node.getUserObject();
					setSelection((ServiceNode) vModel.findFather(vNode).getUserObject());
					vModel.remove(vNode);
					vNode.getFile().delete();
					Settings.service.remove(vNode);
				}
				refresh();
				TabsManager.getManager().selectedTab().refresh();
			}
		});
	}

	public void setSelection(ServiceNode aNode) {
		FileTreeModel vModel = (FileTreeModel) getModel();
		DefaultMutableTreeNode vNode = vModel.find(aNode);
		setSelectionPath(new TreePath(vNode.getPath()));
	}

	public DefaultMutableTreeNode getRoot() {
		return root;
	}

	@Override
	public void refresh() {
		removeAll();
		revalidate();
		repaint();
	}

}
