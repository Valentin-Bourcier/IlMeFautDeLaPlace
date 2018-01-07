package ihm.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ihm.core.Settings;
import model.MyNodeInterface;
import model.NodeDirectory;
import model.ServiceNode;

public class ScanView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private JPanel graph;
	private GridBagConstraints vGBagConstraints;

	public ScanView() {
		render();
	}

	@Override
	public void initComponents() {
		graph = new JPanel();
	}

	@Override
	public void setLayout() {
		this.setLayout(new BorderLayout());
		this.add(graph, BorderLayout.NORTH);

		graph.setLayout(new BoxLayout(graph, BoxLayout.X_AXIS));
		graph.setPreferredSize(new Dimension(getWidth(), 200));

	}

	public JPanel paintChild(ServiceNode aNode, double vPercent) {
		JPanel vPanel = new JPanel();
		// Color vColorMax = new Color(255, 146, 60);
		// Color vColorMin = new Color(200, 255, 60);
		Color vColorMax = new Color(94, 61, 255);
		Color vColorMin = new Color(61, 224, 255);
		int vRed = vColorMin.getRed() + (int) ((vColorMax.getRed() - vColorMin.getRed()) * vPercent);
		int vGreen = vColorMin.getGreen() - (int) (vColorMax.getGreen() * vPercent);
		int vBlue = vColorMin.getBlue();

		// vRed = vPercent >= 0.50 ? vRed + 40 : vRed;
		vPanel.setBackground(new Color(vRed, vGreen, vBlue));
		vPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		vPanel.setPreferredSize(new Dimension((int) (getWidth() * vPercent), getHeight()));
		vPanel.setMaximumSize(new Dimension((int) (getWidth() * vPercent), getHeight()));
		return vPanel;
	}

	public int getPercent(ServiceNode aNode) {
		long vWeight = Settings.SERVICE.weight();
		long vChildWeight = aNode.weight();
		double vPercent = (double) vChildWeight / vWeight;

		if (vPercent < 0.1 && vPercent > 0.0)
		{
			vPercent = 0.01;
		}

		return (int) (vPercent * 100);
	}

	@Override
	public void load() {

		ArrayList<MyNodeInterface> vChilds = ((NodeDirectory) Settings.SERVICE).getSons();
		int x = 0;
		for (MyNodeInterface vChild : vChilds)
		{
			graph.add(paintChild(vChild, getPercent(vChild) / 100.0), vGBagConstraints);
		}
	}

	@Override
	public void refresh() {
		removeAll();
		render();
	}

}
