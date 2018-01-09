package ihm.view.filter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.accessibility.Accessible;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.ComboPopup;

import ihm.model.CheckableItem;

public class CheckedComboBox<E extends CheckableItem> extends JComboBox<E> {

	private static final long serialVersionUID = 1L;

	private boolean keepOpen;
	private transient ActionListener listener;

	public CheckedComboBox() {
		super();
	}

	protected CheckedComboBox(ComboBoxModel<E> aModel) {
		super(aModel);
	}

	protected CheckedComboBox(E[] m) {
		super(m);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 20);
	}

	@Override
	public void updateUI() {
		setRenderer(null);
		removeActionListener(listener);
		super.updateUI();
		listener = e -> {
			if (e.getModifiers() == InputEvent.BUTTON1_MASK)
			{
				updateItem(getSelectedIndex());
				keepOpen = true;
			}
		};
		setRenderer(new CheckBoxCellRenderer<>());
		addActionListener(listener);
		getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "checkbox-select");
		getActionMap().put("checkbox-select", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Accessible a = getAccessibleContext().getAccessibleChild(0);
				if (a instanceof ComboPopup)
				{
					ComboPopup pop = (ComboPopup) a;
					updateItem(pop.getList().getSelectedIndex());
				}
			}
		});
	}

	protected void updateItem(int index) {
		if (isPopupVisible())
		{
			E item = getItemAt(index);
			item.selected ^= true;
			setSelectedIndex(-1);
			setSelectedItem(item);
		}
	}

	@Override
	public void setPopupVisible(boolean v) {
		if (keepOpen)
		{
			keepOpen = false;
		}
		else
		{
			super.setPopupVisible(v);
		}
	}
}