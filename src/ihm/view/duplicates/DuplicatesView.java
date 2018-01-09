package ihm.view.duplicates;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ihm.model.DuplicatesCache;
import ihm.model.Settings;
import ihm.view.main.View;
import model.ServiceNode;

public class DuplicatesView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private JPanel header;
	private JScrollPane scroll;
	private JPanel duplicates;
	private ArrayList<DuplicatesRenderer> renderers;
	private JButton search;

	public DuplicatesView() {
		render();
	}

	@Override
	public void initComponents() {

		header = new JPanel();
		header.add(new JLabel("Paths", JLabel.CENTER));
		header.add(new JLabel("Actions", JLabel.CENTER));

		duplicates = new JPanel();
		renderers = new ArrayList<>();
		scroll = new JScrollPane(duplicates);

		search = new JButton("Search duplicates");
	}

	@Override
	public void setLayout() {

		this.setLayout(new BorderLayout());
		this.add(header, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
		this.add(search, BorderLayout.SOUTH);

		header.setLayout(new GridLayout(1, 2));
		duplicates.setLayout(new BoxLayout(duplicates, BoxLayout.Y_AXIS));

	}

	@Override
	public void bind() {
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				duplicates.removeAll();
				ServiceNode vSelection = Settings.service;
				if (!vSelection.isDirectory())
				{
					vSelection = Settings.root.findFather(vSelection);
				}
				vSelection.computeDoublons();
				HashMap<String, ArrayList<ServiceNode>> vDuplicatesFiles = vSelection.getDoublons();
				DuplicatesCache.getCache().add(vSelection.absolutePath(), vDuplicatesFiles.size());
				DuplicatesCache.getCache().serialize();
				for (ArrayList<ServiceNode> vDuplicate : vDuplicatesFiles.values())
				{
					DuplicatesRenderer vRenderer = new DuplicatesRenderer(vDuplicate, getSize());
					renderers.add(vRenderer);
					duplicates.add(vRenderer);
				}

				if (vDuplicatesFiles.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "No duplicates founded in " + vSelection.filename(), "Warning",
					        JOptionPane.INFORMATION_MESSAGE);

				}
				duplicates.revalidate();
				duplicates.repaint();
			}
		});
	}

	@Override
	public void refresh() {
		if (!renderers.isEmpty())
		{
			for (DuplicatesRenderer duplicatesRenderer : renderers)
			{
				duplicatesRenderer.setDimension(getSize());
				duplicatesRenderer.refresh();
			}
		}
	}

}
