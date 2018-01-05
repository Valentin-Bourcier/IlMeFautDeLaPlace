package ihm.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ihm.core.Settings;
import ihm.core.Style;
import model.NodeFile;

public class FileInformationsView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	public FileInformationsView() {
		render();
	}

	@Override
	public void initComponents() {
		this.add(new JLabel("File name", JLabel.CENTER));
		this.add(new JLabel(Settings.SERVICE.filename(), JLabel.CENTER));

		if (Settings.SERVICE instanceof NodeFile)
		{
			NodeFile vFile = (NodeFile) Settings.SERVICE;
			this.add(new JLabel("Hash", JLabel.CENTER));
			this.add(new JLabel(vFile.hashCode() + "", JLabel.CENTER));
		}

		this.add(new JLabel("Type", JLabel.CENTER));
		String vTypes = "";
		for (String vType : Settings.SERVICE.types())
		{
			vTypes += vTypes.equals("") ? vType : ", " + vType;
		}
		this.add(new JLabel(vTypes, JLabel.CENTER));
		this.add(new JLabel("Path", JLabel.CENTER));
		this.add(new JLabel(Settings.SERVICE.absolutePath(), JLabel.CENTER));

		this.add(new JLabel("Weight", JLabel.CENTER));
		this.add(new JLabel(Style.printWeight(Settings.SERVICE), JLabel.CENTER));

		this.add(new JLabel("Modification date", JLabel.CENTER));
		SimpleDateFormat vFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date vDate = new Date(Settings.SERVICE.lastModificationDate());
		this.add(new JLabel(vFormatter.format(vDate), JLabel.CENTER));

	}

	@Override
	public void refresh() {
		this.removeAll();
		this.render();
	}

	@Override
	public void setLayout() {
		this.setLayout(new GridLayout(6, 2));
		this.setMaximumSize(new Dimension(500, 500));
	}

}
