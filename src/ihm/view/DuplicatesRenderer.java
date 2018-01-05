package ihm.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import ihm.core.FileTreeModel;
import ihm.core.Settings;
import model.NodeFile;
import model.ServiceNode;

public class DuplicatesRenderer extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private ArrayList<ServiceNode> duplicates;
	private JPanel pathPanel;
	private JPanel menuPanel;
	private JLabel icon;

	public DuplicatesRenderer(ArrayList<ServiceNode> aDuplicate) {
		duplicates = aDuplicate;
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
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20 * duplicates.size()));
		this.add(icon, BorderLayout.WEST);
		this.add(pathPanel, BorderLayout.CENTER);
		this.add(menuPanel, BorderLayout.EAST);

		pathPanel.setLayout(new GridLayout(duplicates.size(), 2));
		menuPanel.setLayout(new GridLayout(duplicates.size(), 1));

	}

	@Override
	public void bind() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.WHITE);
				pathPanel.setBackground(Color.WHITE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(167, 182, 201));
				pathPanel.setBackground(new Color(167, 182, 201));
			}
		});
	}

	@Override
	public void load() {
		for (ServiceNode vNodeFile : duplicates)
		{

			if (!vNodeFile.isDirectory())
			{
				File vFile = ((NodeFile) vNodeFile).getFile();
				Icon vCurrentIcon = FileSystemView.getFileSystemView().getSystemIcon(vFile);
				if (icon.getIcon() == null || icon.getIcon() == vCurrentIcon)
				{
					icon.setIcon(vCurrentIcon);
				}
				JLabel vPath = new JLabel(vFile.getPath());
				pathPanel.add(vPath);
				setButtons(vNodeFile, vPath);
			}

		}

	}

	private void setButtons(ServiceNode aNode, JLabel aPath) {

		JButton vDelete = new JButton("Delete");
		JButton vInfo = new JButton("info");
		vInfo.setPreferredSize(new Dimension(80, 40));
		vDelete.setPreferredSize(new Dimension(80, 40));
		menuPanel.add(vInfo);
		menuPanel.add(vDelete);

		vInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileTreeModel vModel = (FileTreeModel) Analyzer.tree.getModel();
				DefaultMutableTreeNode vNode = vModel.find(aNode);
				Analyzer.tree.setSelectionPath(new TreePath(vNode.getPath()));
				TabsManager.getManager().open(TabsManager.INFORMATIONS);
			}
		});

		vDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileTreeModel vModel = (FileTreeModel) Analyzer.tree.getModel();
				vModel.remove(aNode);
				duplicates.remove(aNode);
				((NodeFile) aNode).getFile().delete();
				Settings.SERVICE.remove(aNode);
				removeAll();
				render();
				repaint();
				Analyzer.tree.repaint();
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

}
