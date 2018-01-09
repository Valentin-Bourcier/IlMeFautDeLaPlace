package ihm.view.scan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ihm.model.DuplicatesCache;
import ihm.model.Settings;
import ihm.model.Style;
import ihm.view.NodeIcon;
import ihm.view.main.View;
import model.NodeFile;

public class FileInformationsView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private JPanel informations;

	public FileInformationsView() {
		render();
	}

	@Override
	public void initComponents() {
		informations = new JPanel();
		JLabel vIcon = new NodeIcon(new Dimension(100, 100));
		informations.add(vIcon);
		JLabel vLabel = new JLabel(Settings.service.filename(), JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		informations.add(vLabel);

		vLabel = new JLabel("Type", JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		informations.add(vLabel);
		String vTypes = "";
		for (String vType : Settings.service.types())
		{
			vTypes += vTypes.equals("") ? vType : ", " + vType;
		}
		vLabel = new JLabel(vTypes, JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		informations.add(vLabel);

		if (Settings.service instanceof NodeFile)
		{
			NodeFile vFile = (NodeFile) Settings.service;
			vLabel = new JLabel("Checksum", JLabel.CENTER);
			// vLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
			informations.add(vLabel);
			vLabel = new JLabel(vFile.hashCode() + "", JLabel.CENTER);
			// vLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
			informations.add(vLabel);
		}

		vLabel = new JLabel("Path", JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		informations.add(vLabel);
		vLabel = new JLabel(Settings.service.absolutePath(), JLabel.CENTER);
		JScrollPane vScrollPane = new JScrollPane(vLabel);
		vScrollPane.setBorder(BorderFactory.createEmptyBorder());
		// vScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1,
		// Color.BLACK));
		informations.add(vScrollPane);

		vLabel = new JLabel("Weight", JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		informations.add(vLabel);
		vLabel = new JLabel(Style.printWeight(Settings.service), JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		informations.add(vLabel);

		vLabel = new JLabel("Modification date", JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		informations.add(vLabel);
		SimpleDateFormat vFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date vDate = new Date(Settings.service.lastModificationDate());
		vLabel = new JLabel(vFormatter.format(vDate), JLabel.CENTER);
		// vLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		informations.add(vLabel);

		Integer vNbOfDuplicates = DuplicatesCache.getCache().getDuplicatesNumber(Settings.service.absolutePath());
		if (vNbOfDuplicates != null)
		{
			vLabel = new JLabel("Estimated duplicated files", JLabel.CENTER);
			// vLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
			informations.add(vLabel);
			vLabel = new JLabel(vNbOfDuplicates.toString(), JLabel.CENTER);
			// vLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
			informations.add(vLabel);
		}

	}

	@Override
	public void refresh() {
		removeAll();
		render();
	}

	@Override
	public void setLayout() {
		setLayout(new BorderLayout());
		add(informations, BorderLayout.CENTER);
		informations.setLayout(new GridLayout(6, 2));

	}

}
