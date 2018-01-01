package ihm.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ihm.core.Settings;
import model.ServiceNode;
public class GlobalAnalyzeView extends JSplitPane implements View{
	
	private static final long serialVersionUID = 1L;

	private JPanel center;
	private JButton search;
	private JScrollPane scroll;
	private JList<ArrayList<ServiceNode>> duplicates;
	private JPanel bottom;
	private JList<ServiceNode> bottomList;
	
	private DefaultListModel<ArrayList<ServiceNode>> model;
	
	
	public GlobalAnalyzeView() {
		render();
	}
	
	@Override
	public void initComponents() {
		
		center = new JPanel();
		search = new JButton("Search duplicates");
		model = new DefaultListModel<>();
		
		duplicates = new JList<>(model);
		duplicates.setCellRenderer(new FileRenderer());
		
		scroll = new JScrollPane(duplicates);
		bottom = new JPanel();
	}

	@Override
	public void setLayout() {
		
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.add(center, JSplitPane.TOP);
		
		center.setLayout(new BorderLayout());
		center.add(scroll, BorderLayout.CENTER);
		center.add(search, BorderLayout.SOUTH);
		
		this.add(bottom, JSplitPane.BOTTOM);
		this.setDividerSize(0);
		bottom.setVisible(false);
		
	}
	
	@Override
	public void bind() {
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, ArrayList<ServiceNode>> vDuplicatesFiles = Settings.SERVICE.getDoublons();
				for (ArrayList<ServiceNode> vDuplicate: vDuplicatesFiles.values()) {
					model.addElement(vDuplicate);
				}
			}
		});
		
		duplicates.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ArrayList<ServiceNode> vSelection = duplicates.getSelectedValue();
				for (ServiceNode vServiceNode : vSelection) {
					
				}
				setDividerSize(10);
				bottom.setVisible(true);
			}
		});
	}	

}
