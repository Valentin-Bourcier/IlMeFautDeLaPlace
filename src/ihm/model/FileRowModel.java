package ihm.model;

import java.util.Date;

import org.netbeans.swing.outline.RowModel;

import model.ServiceNode;

public class FileRowModel implements RowModel {
	@Override
	@SuppressWarnings("rawtypes")
	public Class getColumnClass(int column) {
		switch (column) {
			case 0:
				return Date.class;
			case 1:
				return Long.class;
			default:
				assert false;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int column) {
		String vName = "";
		switch (column) {
			case 0:
				vName = "Path";
				break;
			case 1:
				vName = "Modification date";
				break;
		}
		return vName;
	}

	@Override
	public Object getValueFor(Object aObject, int aColumn) {
		ServiceNode vNode = (ServiceNode) aObject;
		switch (aColumn) {
			case 0:
				return vNode.absolutePath();
			case 1:
				long vTime = vNode.lastModificationDate();
				return new Date(vTime);
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
