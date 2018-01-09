package ihm.view.duplicates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;

import ihm.model.FileTreeModel;
import ihm.model.Settings;
import ihm.view.FileTreeView;
import ihm.view.main.Analyzer;
import ihm.view.main.View;
import ihm.view.tabs.TabsManager;
import model.NodeFile;
import model.ServiceNode;

public class DuplicatesRenderer extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private ArrayList<ServiceNode> duplicates;
	private JPanel pathPanel;
	private JPanel menuPanel;
	private JLabel icon;
	private Dimension dimension;
	private static DuplicatesRenderer selected;

	public DuplicatesRenderer(ArrayList<ServiceNode> aDuplicate, Dimension aDimension) {
		duplicates = aDuplicate;
		dimension = aDimension;
		render();
	}

	@Override
	public void initComponents() {
		pathPanel = new JPanel();
		menuPanel = new JPanel();
		icon = new JLabel();

		setBackground(Color.WHITE);
		pathPanel.setBackground(Color.WHITE);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
	}

	@Override
	public void setLayout() {

		this.setLayout(new BorderLayout());
		this.setMaximumSize(new Dimension(dimension.width, 30 * duplicates.size()));
		this.add(icon, BorderLayout.WEST);
		this.add(pathPanel, BorderLayout.CENTER);
		this.add(menuPanel, BorderLayout.EAST);

		pathPanel.setLayout(new GridLayout(duplicates.size(), 1));
		pathPanel.setMaximumSize(new Dimension(dimension.width - 60, getHeight()));
		menuPanel.setLayout(new GridLayout(duplicates.size(), 2));
		menuPanel.setPreferredSize(new Dimension(60, getHeight()));

	}

	public void hoverColor(boolean aHovered) {
		if (aHovered)
		{
			setBackground(new Color(167, 182, 201));
			pathPanel.setBackground(new Color(167, 182, 201));
		}
		else
		{
			setBackground(Color.WHITE);
			pathPanel.setBackground(Color.WHITE);
		}
	}

	public void gainFocus() {
		if (selected != null)
		{
			selected.hoverColor(false);
		}
		hoverColor(true);
		selected = this;

	}

	@Override
	public void bind() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				gainFocus();
			}
		});

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void load() {
		SwingWorker sw = new SwingWorker() {

			@Override
			protected Object doInBackground() throws Exception {
				Analyzer.progress.setVisible(true);
				for (ServiceNode vNodeFile : duplicates)
				{

					if (!vNodeFile.isDirectory())
					{
						if (vNodeFile.absolutePath().equals(Settings.service.absolutePath()))
						{
							gainFocus();
						}
						File vFile = new File(vNodeFile.absolutePath());
						Icon vCurrentIcon = FileSystemView.getFileSystemView().getSystemIcon(vFile);
						if (icon.getIcon() == null || icon.getIcon() == vCurrentIcon)
						{
							icon.setIcon(vCurrentIcon);
						}
						JLabel vPath = new JLabel(vFile.getPath());
						pathPanel.add(vPath); // "wmin 0"
						try
						{
							setButtons(vNodeFile, vPath);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}

				}
				return null;
			}

			@Override
			public void done() {
				if (SwingUtilities.isEventDispatchThread())
				{
					Analyzer.progress.setVisible(false);
				}
			}

		};
		sw.execute();

	}

	private void setButtons(ServiceNode aNode, JLabel aPath) throws IOException {

		JButton vDelete = new JButton();
		JButton vInfo = new JButton();
		vInfo.setPreferredSize(new Dimension(30, 30));
		vDelete.setPreferredSize(new Dimension(30, 30));
		BufferedImage vInfoImage = ImageIO.read(new File("resources/info.png"));
		vInfo.setIcon(new ImageIcon(vInfoImage));
		BufferedImage vDeleteImage = ImageIO.read(new File("resources/delete.png"));
		vDelete.setIcon(new ImageIcon(vDeleteImage));
		vInfo.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.WHITE));
		vDelete.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.WHITE));
		menuPanel.add(vInfo);
		menuPanel.add(vDelete);

		vInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileTreeView.getView().setSelection(aNode);
				TabsManager.getManager().open(TabsManager.INFORMATIONS);
			}
		});

		vDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileTreeModel vModel = (FileTreeModel) FileTreeView.getView().getModel();
				vModel.remove(aNode);
				duplicates.remove(aNode);
				((NodeFile) aNode).getFile().delete();
				Settings.service.remove(aNode);
				removeAll();
				render();
				repaint();
				FileTreeView.getView().refresh();
			}
		});

	}

	@Override
	public void render() {
		// If there is no more duplicates in this view we don't show the panel
		// It avoid a new call to the service and a new calculation of the duplicates
		// for the same result
		if (duplicates.size() > 1)
		{
			build();
		}
		else
		{
			setVisible(false);
		}
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	@Override
	public void refresh() {
		removeAll();
		render();
	}

}
