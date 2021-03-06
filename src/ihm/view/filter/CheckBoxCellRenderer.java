package ihm.view.filter;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import ihm.model.CheckableItem;

public class CheckBoxCellRenderer<E extends CheckableItem> implements ListCellRenderer<E> {
	private final JLabel label = new JLabel(" ");
	private final JCheckBox check = new JCheckBox(" ");

	@Override
	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, CheckableItem value, int index, boolean isSelected,
	        boolean cellHasFocus) {
		if (index < 0)
		{
			label.setText(getCheckedItemString(list.getModel()));
			return label;
		}
		else
		{
			check.setText(Objects.toString(value, ""));
			check.setSelected(value.selected);
			if (isSelected)
			{
				check.setBackground(list.getSelectionBackground());
				check.setForeground(list.getSelectionForeground());
			}
			else
			{
				check.setBackground(list.getBackground());
				check.setForeground(list.getForeground());
			}
			return check;
		}
	}

	private static String getCheckedItemString(ListModel<?> model) {
		List<String> sl = new ArrayList<>();
		for (int i = 0; i < model.getSize(); i++)
		{
			Object o = model.getElementAt(i);
			if (o instanceof CheckableItem && ((CheckableItem) o).selected)
			{
				sl.add(o.toString());
			}
		}
		return sl.stream().sorted().collect(Collectors.joining(", "));
	}
}