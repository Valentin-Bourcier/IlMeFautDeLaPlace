package ihm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

import org.netbeans.swing.outline.RowModel;

import model.NodeFile;
import model.ServiceNode;

public class FileRowModel implements RowModel {
	@Override
	@SuppressWarnings("rawtypes")
	public Class getColumnClass(int column) {
		switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			default:
				assert false;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int aColumn) {
		switch (aColumn) {
			case 0:
				return "Path";
			case 1:
				return "Size";
			case 2:
				return "Modification date";
			case 3:
				return "Estimated duplicates";
		}
		return null;
	}

	@Override
	public Object getValueFor(Object aObject, int aColumn) {
		DefaultMutableTreeNode vMutableTreeNode = (DefaultMutableTreeNode) aObject;
		ServiceNode vNode = (ServiceNode) vMutableTreeNode.getUserObject();
		switch (aColumn) {
			case 0:
				return vNode.absolutePath();
			case 1:
				return vNode.weight();
			case 2:
				long vTime = vNode.lastModificationDate();
				SimpleDateFormat vFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				return vFormatter.format(new Date(vTime));
			case 3:
				if (vNode instanceof NodeFile)
				{
					return "Undefined";
				}
				else
				{
					return DuplicatesCache.getCache().getDuplicatesNumber(vNode.absolutePath());
				}
		}
		return null;
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		return false;
	}

	@Override
	public void setValueFor(Object node, int column, Object value) {
		// do nothing for now
	}

}
