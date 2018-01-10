package ihm.view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ihm.model.Style;
import ihm.view.FileTreeView;
import ihm.view.filter.FilterView;
import ihm.view.launch.LaunchView;
import ihm.view.tabs.TabsManager;

public class Analyzer extends JFrame implements View {

	private static final long serialVersionUID = 1L;

	private JSplitPane pane;
	private JPanel treePanel;
	private JPanel filterPanel;
	private JTree tree;
	public static JProgressBar progress;

	public Analyzer(String aTitle) {
		this.setTitle(aTitle);
	}

	@Override
	public void initComponents() {

		pane = new JSplitPane();
		treePanel = new JPanel();

		tree = FileTreeView.getView();

		filterPanel = new FilterView();

		progress = new JProgressBar();
		progress.setVisible(false);
		progress.setIndeterminate(true);
	}

	@Override
	public void setLayout() {

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(tree), BorderLayout.WEST);
		this.add(pane, BorderLayout.CENTER);
		this.add(progress, BorderLayout.SOUTH);

		pane.add(treePanel, JSplitPane.LEFT);
		pane.add(TabsManager.getManager().getPane(), JSplitPane.RIGHT);

		treePanel.setLayout(new BorderLayout());
		treePanel.add(tree, BorderLayout.CENTER);
		treePanel.add(filterPanel, BorderLayout.SOUTH);

		tree.setPreferredSize(new Dimension(180, getHeight()));

	}

	@Override
	public void bind() {
		addComponentListener(new ComponentAdapter() {

			@SuppressWarnings("rawtypes")
			@Override
			public void componentResized(ComponentEvent e) {
				SwingWorker sw = new SwingWorker() {

					@Override
					protected Object doInBackground() throws Exception {
						progress.setVisible(true);
						TabsManager.getManager().selectedTab().refresh();
						return null;
					}

					@Override
					public void done() {
						if (SwingUtilities.isEventDispatchThread())
						{
							progress.setVisible(false);
						}
					}

				};
				sw.execute();
			}
		});

	}

	@Override
	public void render() {
		new LaunchView(this, "IMFDLP", true);

		build();

		this.setPreferredSize(new Dimension(Style.DEFAULT_MAIN_WIDTH, Style.DEFAULT_MAIN_HEIGHT));
		this.pack();

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		TabsManager.getManager().open(TabsManager.SCAN);
	}

	public static void main(String[] args) {

		Analyzer view = new Analyzer("IMFDLP");
		view.render();
	}
}
