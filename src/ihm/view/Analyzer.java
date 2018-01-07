package ihm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import ihm.core.FileTreeModel;
import ihm.core.Settings;
import model.ServiceNode;

public class Analyzer extends JFrame implements View {

	private static final long serialVersionUID = 1L;

	private JSplitPane pane;
	private JPanel treePanel;
	private JPanel filterPanel;
	private JComboBox<CheckableItem> extensions;
	private CheckableItem[] model;
	private JButton filter;

	public static JTree tree;

	public Analyzer(String aTitle) {
		this.setTitle(aTitle);
	}

	@Override
	public void initComponents() {

		pane = new JSplitPane();
		treePanel = new JPanel();

		TreeModel treeModel = new FileTreeModel(Settings.SERVICE.getTreeAsDefaultMutableTreeNode(null).getRoot());

		tree = new JTree(treeModel);
		tree.setShowsRootHandles(true);

		filterPanel = new JPanel();
		extensions = new CheckedComboBox<>();
		filter = new JButton("Filter");
	}

	@Override
	public void setLayout() {

		// Main Layout
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(tree), BorderLayout.WEST);
		this.add(pane, BorderLayout.CENTER);

		pane.add(treePanel, JSplitPane.LEFT);
		pane.add(TabsManager.getManager().getPane(), JSplitPane.RIGHT);

		treePanel.setLayout(new BorderLayout());
		treePanel.add(tree, BorderLayout.CENTER);

		filterPanel.setLayout(new GridLayout(3, 1));
		filterPanel.add(new JLabel("Select extensions: "));
		filterPanel.add(extensions);
		filterPanel.add(filter);

		treePanel.add(filterPanel, BorderLayout.SOUTH);

		tree.setPreferredSize(new Dimension(180, getHeight()));

	}

	@Override
	public void load() {
		model = new CheckableItem[59];
		model[0] = new CheckableItem("avi", false);
		model[1] = new CheckableItem("bak", false);
		model[2] = new CheckableItem("bat", false);
		model[3] = new CheckableItem("bin", false);
		model[4] = new CheckableItem("bmp", false);
		model[5] = new CheckableItem("class", false);
		model[6] = new CheckableItem("com", false);
		model[7] = new CheckableItem("cpp", false);
		model[8] = new CheckableItem("css", false);
		model[9] = new CheckableItem("dat", false);
		model[10] = new CheckableItem("dbf", false);
		model[11] = new CheckableItem("dll", false);
		model[12] = new CheckableItem("exe", false);
		model[13] = new CheckableItem("flv", false);
		model[14] = new CheckableItem("gif", false);
		model[15] = new CheckableItem("htm", false);
		model[16] = new CheckableItem("html", false);
		model[17] = new CheckableItem("ico", false);
		model[18] = new CheckableItem("img", false);
		model[19] = new CheckableItem("ini", false);
		model[20] = new CheckableItem("iso", false);
		model[21] = new CheckableItem("java", false);
		model[22] = new CheckableItem("jpe", false);
		model[23] = new CheckableItem("jpeg", false);
		model[24] = new CheckableItem("jpg", false);
		model[25] = new CheckableItem("log", false);
		model[26] = new CheckableItem("mov", false);
		model[27] = new CheckableItem("m3u", false);
		model[28] = new CheckableItem("mp2", false);
		model[29] = new CheckableItem("mp3", false);
		model[30] = new CheckableItem("mp4", false);
		model[31] = new CheckableItem("mpg", false);
		model[32] = new CheckableItem("msi", false);
		model[33] = new CheckableItem("old", false);
		model[34] = new CheckableItem("pdf", false);
		model[35] = new CheckableItem("php", false);
		model[36] = new CheckableItem("png", false);
		model[37] = new CheckableItem("ppt", false);
		model[38] = new CheckableItem("psf", false);
		model[39] = new CheckableItem("rar", false);
		model[40] = new CheckableItem("reg", false);
		model[41] = new CheckableItem("rtf", false);
		model[42] = new CheckableItem("shc", false);
		model[43] = new CheckableItem("sql", false);
		model[44] = new CheckableItem("swf", false);
		model[45] = new CheckableItem("swp", false);
		model[46] = new CheckableItem("sys", false);
		model[47] = new CheckableItem("tar", false);
		model[48] = new CheckableItem("tgz", false);
		model[49] = new CheckableItem("theme", false);
		model[50] = new CheckableItem("thm", false);
		model[51] = new CheckableItem("tmp", false);
		model[52] = new CheckableItem("ttf", false);
		model[53] = new CheckableItem("txt", false);
		model[54] = new CheckableItem("url", false);
		model[55] = new CheckableItem("vbs", false);
		model[56] = new CheckableItem("wav", false);
		model[57] = new CheckableItem("wma", false);
		model[58] = new CheckableItem("xls", false);

		extensions.setModel(new DefaultComboBoxModel<CheckableItem>(model));
	}

	@Override
	public void bind() {

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) Analyzer.tree.getLastSelectedPathComponent();
				if (node != null)
				{
					Settings.SERVICE = (ServiceNode) node.getUserObject();
					JPanel vPanel = TabsManager.getManager().selectedTab();
					((View) vPanel).refresh();
					TabsManager.getManager().getPane().repaint();
					System.out.println("Selection changed: " + Settings.SERVICE.filename());
				}
			}
		});

		filter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ComboBoxModel<CheckableItem> vModel = extensions.getModel();
				ArrayList<String> sl = new ArrayList<>();
				String[] vTypes = Settings.SERVICE.types();
				for (int i = 0; i < vModel.getSize(); i++)
				{
					Object o = vModel.getElementAt(i);
					if (o instanceof CheckableItem && ((CheckableItem) o).selected)
					{
						for (String vType : vTypes)
						{
							if (vType.contains(o.toString()))
							{
								sl.add(vType);
							}
						}
					}
				}

				Settings.SERVICE = Settings.SERVICE.filter(Arrays.copyOf(sl.toArray(), sl.size(), String[].class));
				TreeModel treeModel = new FileTreeModel(
				        Settings.SERVICE.filter(Arrays.copyOf(sl.toArray(), sl.size(), String[].class))
				                .getTreeAsDefaultMutableTreeNode(null).getRoot());

				tree.setModel(treeModel);
				tree.revalidate();
				tree.repaint();
			}
		});

	}

	@Override
	public void render() {
		new Launch(this, "IMFDLP", true);

		build();

		this.setPreferredSize(new Dimension(800, 600));
		this.pack();

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Analyzer view = new Analyzer("IMFDLP");
		view.render();
	}
}
