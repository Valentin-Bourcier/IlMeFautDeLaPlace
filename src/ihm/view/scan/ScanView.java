package ihm.view.scan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import ihm.model.FileTreeModel;
import ihm.view.FileTreeView;
import ihm.view.main.View;
import model.ServiceNode;

public class ScanView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private JPanel graph;
	private JScrollPane scroll;
	private ChildFilesView file;

	public ScanView() {
		render();
	}

	@Override
	public void initComponents() {
		graph = new JPanel();
		file = new ChildFilesView();
		scroll = new JScrollPane(file);
	}

	@Override
	public void setLayout() {
		this.setLayout(new BorderLayout());
		this.add(graph, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);

		graph.setLayout(new BoxLayout(graph, BoxLayout.X_AXIS));
		graph.setPreferredSize(new Dimension(getWidth(), 100));
	}

	@Override
	public void load() {
		DefaultMutableTreeNode vDMTnode = (DefaultMutableTreeNode) FileTreeView.getView()
		        .getLastSelectedPathComponent();
		FileTreeModel vModel = (FileTreeModel) FileTreeView.getView().getModel();
		if (vDMTnode == null)
		{
			vDMTnode = FileTreeView.getView().getRoot();
		}
		ServiceNode vSelection = (ServiceNode) vDMTnode.getUserObject();
		if (!vSelection.isDirectory())
		{
			vDMTnode = vModel.findFather(vSelection);
		}
		long vWeight = ((ServiceNode) vDMTnode.getUserObject()).weight();

		Enumeration<?> vEnumeration = vDMTnode.children();
		while (vEnumeration.hasMoreElements())
		{
			DefaultMutableTreeNode vChild = (DefaultMutableTreeNode) vEnumeration.nextElement();
			if (vChild != null)
			{
				ServiceNode vNode = (ServiceNode) vChild.getUserObject();
				graph.add(new FileGraphView(vWeight, getSize(), vNode));
			}

		}

		file.setSelection(vSelection);
		file.render();

	}

	@Override
	public void refresh() {
		removeAll();
		render();
	}

}
