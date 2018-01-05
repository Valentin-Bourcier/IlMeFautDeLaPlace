package ihm.view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabsManager implements View {

	private static TabsManager instance;

	private JTabbedPane tabs;

	public static int HOME = 0;
	public static int SCAN = -1;
	public static int DUPLICATES = -2;
	public static int INFORMATIONS = -3;

	private TabsManager() {
		render();
	}

	public static TabsManager getManager() {
		if (instance == null)
		{
			instance = new TabsManager();
		}
		return instance;
	}

	@Override
	public void initComponents() {
		tabs = new JTabbedPane();
		tabs.setUI(new TabbedPaneUI());
		open(HOME);
		open(SCAN);
		open(DUPLICATES);
		open(INFORMATIONS);
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bind() {
		tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
			}
		});

	}

	public void open(int aId) {

		if (aId == HOME && !isOpen(HOME))
		{
			tabs.addTab("Home" + TabbedPaneUI.CLOSE_WIDTH, new JPanel());
		}
		if (aId == SCAN && !isOpen(SCAN))
		{
			tabs.addTab("Scan" + TabbedPaneUI.CLOSE_WIDTH, new ScanView());
			SCAN = tabs.getTabCount() - 1;
		}
		if (aId == DUPLICATES && !isOpen(DUPLICATES))
		{
			tabs.addTab("Duplicates scan" + TabbedPaneUI.CLOSE_WIDTH, new DuplicatesView());
			DUPLICATES = tabs.getTabCount() - 1;
		}
		if (aId == INFORMATIONS && !isOpen(INFORMATIONS))
		{
			tabs.addTab("Informations" + TabbedPaneUI.CLOSE_WIDTH, new FileInformationsView());
			INFORMATIONS = tabs.getTabCount() - 1;
		}

		setActive(tabs.getTabCount() - 1);

	}

	public boolean isOpen(int aId) {
		return aId > 0 && aId < tabs.getTabCount();
	}

	public void setActive(int aId) {
		tabs.setSelectedIndex(aId);
	}

	public JTabbedPane getPane() {
		return tabs;
	}

	public JPanel selectedTab() {
		return (JPanel) tabs.getSelectedComponent();
	}

}
