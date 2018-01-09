package ihm.view.filter;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.TreeModel;

import ihm.model.CheckableItem;
import ihm.model.FileTreeModel;
import ihm.model.FilterModel;
import ihm.model.Settings;
import ihm.view.FileTreeView;
import ihm.view.main.View;
import ihm.view.tabs.TabsManager;
import model.ServiceNode;

public class FilterView extends JPanel implements View {

	private static final long serialVersionUID = 1L;

	private JComboBox<CheckableItem> extensions;
	private JButton filter;
	private JButton reset;

	public FilterView() {
		render();
	}

	@Override
	public void initComponents() {
		extensions = new CheckedComboBox<>();
		filter = new JButton("Filter");
		reset = new JButton("Reset");
	}

	@Override
	public void setLayout() {
		setLayout(new GridLayout(4, 1));
		add(new JLabel("Select extensions: "));
		add(extensions);
		add(filter);
		add(reset);
	}

	@Override
	public void load() {
		FilterModel vFilterModel = new FilterModel();
		DefaultComboBoxModel<CheckableItem> vModel = new DefaultComboBoxModel<CheckableItem>(vFilterModel.getModel());
		extensions.setModel(vModel);
	}

	@Override
	public void bind() {
		filter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ComboBoxModel<CheckableItem> vModel = extensions.getModel();

				ArrayList<String> sl = new ArrayList<>();
				String[] vTypes = Settings.service.types();
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
				if (!sl.isEmpty())
				{
					ServiceNode vSelection = Settings.service;
					if (!vSelection.isDirectory())
					{
						vSelection = Settings.root.findFather(vSelection);
					}
					Settings.service = vSelection.filter(Arrays.copyOf(sl.toArray(), sl.size(), String[].class));
					TreeModel vTreeModel = new FileTreeModel(
					        Settings.service.filter(Arrays.copyOf(sl.toArray(), sl.size(), String[].class))
					                .getTreeAsDefaultMutableTreeNode(null).getRoot());

					FileTreeView.getView().setModel(vTreeModel);
					FileTreeView.getView().revalidate();
					FileTreeView.getView().repaint();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select one or several file types.", "Warning",
					        JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.service = Settings.root;
				FileTreeView.getView().refresh();
				FileTreeView.getView().render();
				TabsManager.getManager().selectedTab().refresh();
			}
		});
	}

}
