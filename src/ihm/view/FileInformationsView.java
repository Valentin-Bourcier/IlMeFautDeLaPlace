package ihm.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ihm.core.Settings;
import ihm.core.Style;
import model.NodeFile;

public class FileInformationsView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private JPanel pane;

	public FileInformationsView() {
		render();
	}

	@Override
	public void initComponents() {
		pane = new JPanel();
		pane.add(new JLabel("File name", JLabel.CENTER));
		pane.add(new JLabel(Settings.SERVICE.filename(), JLabel.CENTER));

		if (Settings.SERVICE instanceof NodeFile)
		{
			NodeFile vFile = (NodeFile) Settings.SERVICE;
			pane.add(new JLabel("Hash", JLabel.CENTER));
			pane.add(new JLabel(vFile.hashCode() + "", JLabel.CENTER));
		}

		pane.add(new JLabel("Type", JLabel.CENTER));
		String vTypes = "";
		for (String vType : Settings.SERVICE.types())
		{
			vTypes += vTypes.equals("") ? vType : ", " + vType;
		}
		pane.add(new JLabel(vTypes, JLabel.CENTER));
		pane.add(new JLabel("Path", JLabel.CENTER));
		JScrollPane vScrollPane = new JScrollPane(new JLabel(Settings.SERVICE.absolutePath(), JLabel.CENTER));
		vScrollPane.setBorder(BorderFactory.createEmptyBorder());
		pane.add(vScrollPane);

		pane.add(new JLabel("Weight", JLabel.CENTER));
		pane.add(new JLabel(Style.printWeight(Settings.SERVICE), JLabel.CENTER));

		pane.add(new JLabel("Modification date", JLabel.CENTER));
		SimpleDateFormat vFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date vDate = new Date(Settings.SERVICE.lastModificationDate());
		pane.add(new JLabel(vFormatter.format(vDate), JLabel.CENTER));

	}

	@Override
	public void refresh() {
		removeAll();
		render();
	}

	@Override
	public void setLayout() {
		this.setLayout(new GridBagLayout());
		this.add(pane);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pane.setLayout(new GridLayout(6, 2));
		pane.setPreferredSize(new Dimension(500, 500));
		pane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
	}

}
