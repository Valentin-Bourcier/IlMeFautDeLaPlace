package ihm.view.scan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ihm.model.Settings;
import ihm.view.main.View;
import model.MyNodeInterface;
import model.NodeDirectory;
import model.ServiceNode;

class ScanView extends JPanel implements View {

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
		ServiceNode vSelection = Settings.service;
		if (!vSelection.isDirectory())
		{
			vSelection = Settings.root.findFather(vSelection);
		}
		ArrayList<MyNodeInterface> vChilds = ((NodeDirectory) vSelection).getSons();
		long vWeight = vSelection.weight();
		for (ServiceNode vChild : vChilds)
		{

			graph.add(new FileGraphView(vWeight, getSize(), vChild));
		}

	}

	@Override
	public void refresh() {
		removeAll();
		render();
	}

}
