package ihm.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ihm.core.Settings;
import model.ServiceNode;

public class DuplicatesView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private JPanel header;
	private JScrollPane scroll;
	private JPanel duplicates;
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
				HashMap<String, ArrayList<ServiceNode>> vDuplicatesFiles = Settings.SERVICE.getDoublons();
				for (ArrayList<ServiceNode> vDuplicate : vDuplicatesFiles.values())
				{
					System.out.println("Récupération liste: ");
					for (ServiceNode serviceNode : vDuplicate)
					{
						System.out.println(serviceNode.getClass());
					}
					duplicates.add(new DuplicatesRenderer(vDuplicate));
				}
				duplicates.revalidate();
				duplicates.repaint();
			}
		});
	}

}
