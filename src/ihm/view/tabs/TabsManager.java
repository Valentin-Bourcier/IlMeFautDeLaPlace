package ihm.view.tabs;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ihm.view.duplicates.DuplicatesView;
import ihm.view.main.View;
import ihm.view.scan.FileInformationsView;
import ihm.view.scan.ScanView;

public class TabsManager implements View {

	private static TabsManager instance;

	private JTabbedPane tabs;

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
		open(SCAN);
	}

	@Override
	public void bind() {
		tabs.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				((View) tabs.getSelectedComponent()).refresh();
			}
		});

	}

	public void open(int aId) {

		if (aId == SCAN && !isOpen(SCAN))
		{
			tabs.addTab("Scan" + TabbedPaneUI.CLOSE_WIDTH, new ScanView());
			SCAN = tabs.getTabCount() - 1;
			setActive(tabs.getTabCount() - 1);
		}
		else if (aId == DUPLICATES && !isOpen(DUPLICATES))
		{
			tabs.addTab("Duplicates scan" + TabbedPaneUI.CLOSE_WIDTH, new DuplicatesView());
			DUPLICATES = tabs.getTabCount() - 1;
			setActive(tabs.getTabCount() - 1);
		}
		else if (aId == INFORMATIONS && !isOpen(INFORMATIONS))
		{
			tabs.addTab("Informations" + TabbedPaneUI.CLOSE_WIDTH, new FileInformationsView());
			INFORMATIONS = tabs.getTabCount() - 1;
			setActive(tabs.getTabCount() - 1);
		}
		else
		{
			setActive(aId);
		}

	}

	public boolean isOpen(int aId) {
		return aId >= 0;
	}

	public void close() {
		int aId = 0;
		if (TabsManager.getManager().getPane().getTabCount() > 1)
		{
			aId = TabsManager.getManager().getPane().getSelectedIndex() + 1;
		}
		if (aId == SCAN)
		{
			SCAN = -1;
		}
		else if (aId == DUPLICATES)
		{
			DUPLICATES = -2;
		}
		else if (aId == INFORMATIONS)
		{
			INFORMATIONS = -3;
		}
	}

	public void setActive(int aId) {
		tabs.setSelectedIndex(aId);
	}

	public JTabbedPane getPane() {
		return tabs;
	}

	public View selectedTab() {
		return (View) tabs.getSelectedComponent();
	}

}
