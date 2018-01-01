package ihm.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileSystemView;

import model.NodeFile;
import model.ServiceNode;

public class FileRenderer extends JLabel implements ListCellRenderer<ArrayList<ServiceNode>>{

	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getListCellRendererComponent(JList<? extends ArrayList<ServiceNode>> list, ArrayList<ServiceNode> value,
	        int index, boolean isSelected, boolean cellHasFocus) {
		
		
		JPanel vPanel = new JPanel(new BorderLayout());
		JPanel vItem = new JPanel(new GridLayout(value.size(), 3));
		Icon vIcon = null;
		for (ServiceNode vNodeFile : value) {
			
			if(!vNodeFile.isDirectory()) {
				File vFile = ((NodeFile) vNodeFile).getFile();
				Icon vCurrentIcon = FileSystemView.getFileSystemView().getSystemIcon(vFile);
				if(vIcon == null || vIcon == vCurrentIcon) {
					vIcon = vCurrentIcon;					
				}
				vItem.add(new JLabel(vFile.getPath()));
				Date vDate = new Date(vFile.lastModified());
				SimpleDateFormat vDateFormat = new SimpleDateFormat("yyyy-MM-dd ", Locale.FRANCE);
				vItem.add(new JLabel(vDateFormat.format(vDate)));
				DecimalFormat vFormatter = new DecimalFormat("#.##");
				vItem.add(new JLabel(vFormatter.format(vFile.length()/Math.pow(1024, 2)) +" MiB"));
			}
			
		}
		vPanel.add(new JLabel(vIcon), BorderLayout.WEST);
		vPanel.add(vItem, BorderLayout.CENTER);
		
		if (isSelected) {
		    vItem.setBackground(new Color(167, 182, 201));
		} else {
			vItem.setBackground(Color.WHITE);
		}
		
		return vPanel;
	}

}
