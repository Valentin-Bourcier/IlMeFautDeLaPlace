package ihm.view.scan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ihm.model.Settings;
import ihm.view.FileTreeView;
import ihm.view.main.View;
import model.ServiceNode;

public class FileGraphView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	// private static Color maxColor = new Color(255, 146, 60);
	// private static Color minColor = new Color(200, 255, 60);
	private static Color maxColor = new Color(94, 61, 255);
	private static Color minColor = new Color(61, 224, 255);
	private ServiceNode node;
	private long parentWeight;
	private Dimension dimension;
	private static FileGraphView focused;

	public FileGraphView(long aParentWeight, Dimension aDimension, ServiceNode aNode) {
		node = aNode;
		parentWeight = aParentWeight;
		dimension = aDimension;
		render();
	}

	@Override
	public void initComponents() {
		double vPercent = getPercent();
		int vRed = minColor.getRed() + (int) ((maxColor.getRed() - minColor.getRed()) * vPercent);
		int vGreen = minColor.getGreen() - (int) (maxColor.getGreen() * vPercent);
		int vBlue = minColor.getBlue();

		setBackground(new Color(vRed, vGreen, vBlue));
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));

		setPreferredSize(new Dimension((int) (dimension.width * vPercent), dimension.height));
	}

	public double getPercent() {
		long vChildWeight = node.weight();
		double vPercent = (double) vChildWeight / parentWeight;

		if (vPercent < 0.1 && vPercent > 0.0)
		{
			vPercent = 0.01;
		}

		return vPercent;
	}

	public void gainFocus() {
		if (focused != null)
		{
			focused.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		}
		focused = this;
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.YELLOW));

	}

	@Override
	public void load() {
		if (Settings.service.equals(node))
		{
			gainFocus();
		}
	}

	@Override
	public void bind() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Settings.service = node;
				FileTreeView.getView().setSelection(node);
			}

		});
	}

}
