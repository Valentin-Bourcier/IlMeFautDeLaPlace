// package ihm.view;
//
// import java.awt.BorderLayout;
// import java.awt.Component;
// import java.awt.Dimension;
// import java.awt.EventQueue;
// import java.awt.GridLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.InputEvent;
// import java.awt.event.KeyEvent;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Objects;
// import java.util.stream.Collectors;
//
// import javax.accessibility.Accessible;
// import javax.swing.AbstractAction;
// import javax.swing.BorderFactory;
// import javax.swing.Box;
// import javax.swing.ComboBoxModel;
// import javax.swing.DefaultComboBoxModel;
// import javax.swing.JCheckBox;
// import javax.swing.JComboBox;
// import javax.swing.JComponent;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JList;
// import javax.swing.JPanel;
// import javax.swing.KeyStroke;
// import javax.swing.ListCellRenderer;
// import javax.swing.ListModel;
// import javax.swing.UIManager;
// import javax.swing.UnsupportedLookAndFeelException;
// import javax.swing.WindowConstants;
// import javax.swing.plaf.basic.ComboPopup;
//
// public final class MainPanel extends JPanel {
// private MainPanel() {
// super(new BorderLayout());
//
// CheckableItem[] m = { new CheckableItem("aaa", false), new
// CheckableItem("bbbbb", true),
// new CheckableItem("111", false), new CheckableItem("33333", true), new
// CheckableItem("2222", true),
// new CheckableItem("ccccccc", false) };
//
// JPanel p = new JPanel(new GridLayout(0, 1));
// p.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
// p.add(new JLabel("Default:"));
// p.add(new JComboBox<>(m));
// p.add(Box.createVerticalStrut(20));
// p.add(new JLabel("CheckedComboBox:"));
// p.add(new CheckedComboBox<>(new DefaultComboBoxModel<>(m)));
// // p.add(new CheckedComboBox<>(new CheckableComboBoxModel<>(m)));
//
// add(p, BorderLayout.NORTH);
// setPreferredSize(new Dimension(320, 240));
// }
//
// public static void main(String... args) {
// EventQueue.invokeLater(new Runnable() {
// @Override
// public void run() {
// createAndShowGUI();
// }
// });
// }
//
// public static void createAndShowGUI() {
// try
// {
// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
// }
// catch (ClassNotFoundException | InstantiationException |
// IllegalAccessException
// | UnsupportedLookAndFeelException ex)
// {
// ex.printStackTrace();
// }
// JFrame frame = new JFrame("CheckedComboBox");
// frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
// frame.getContentPane().add(new MainPanel());
// frame.pack();
// frame.setLocationRelativeTo(null);
// frame.setVisible(true);
// }
// }
//
//// class CheckableComboBoxModel<E> extends DefaultComboBoxModel<E> {
//// protected CheckableComboBoxModel(E[] items) {
//// super(items);
//// }
//// public void fireContentsChanged(int index) {
//// super.fireContentsChanged(this, index, index);
//// }
//// }
//
// class CheckableItem {
// public final String text;
// public boolean selected;
//
// protected CheckableItem(String text, boolean selected) {
// this.text = text;
// this.selected = selected;
// }
//
// @Override
// public String toString() {
// return text;
// }
// }
//
// class CheckBoxCellRenderer<E extends CheckableItem> implements
// ListCellRenderer<E> {
// private final JLabel label = new JLabel(" ");
// private final JCheckBox check = new JCheckBox(" ");
//
// @Override
// public Component getListCellRendererComponent(JList list, CheckableItem
// value, int index, boolean isSelected,
// boolean cellHasFocus) {
// if (index < 0)
// {
// label.setText(getCheckedItemString(list.getModel()));
// return label;
// }
// else
// {
// check.setText(Objects.toString(value, ""));
// check.setSelected(value.selected);
// if (isSelected)
// {
// check.setBackground(list.getSelectionBackground());
// check.setForeground(list.getSelectionForeground());
// }
// else
// {
// check.setBackground(list.getBackground());
// check.setForeground(list.getForeground());
// }
// return check;
// }
// }
//
// private static String getCheckedItemString(ListModel model) {
// List<String> sl = new ArrayList<>();
// for (int i = 0; i < model.getSize(); i++)
// {
// Object o = model.getElementAt(i);
// if (o instanceof CheckableItem && ((CheckableItem) o).selected)
// {
// sl.add(o.toString());
// }
// }
// return sl.stream().sorted().collect(Collectors.joining(", "));
// }
// }
//
// class CheckedComboBox<E extends CheckableItem> extends JComboBox<E> {
// private boolean keepOpen;
// private transient ActionListener listener;
//
// protected CheckedComboBox() {
// super();
// }
//
// protected CheckedComboBox(ComboBoxModel<E> aModel) {
// super(aModel);
// }
//
// // protected CheckedComboBox(E[] m) {
// // super(m);
// // }
// @Override
// public Dimension getPreferredSize() {
// return new Dimension(200, 20);
// }
//
// @Override
// public void updateUI() {
// setRenderer(null);
// removeActionListener(listener);
// super.updateUI();
// listener = e -> {
// if (e.getModifiers() == InputEvent.BUTTON1_MASK)
// {
// updateItem(getSelectedIndex());
// keepOpen = true;
// }
// };
// setRenderer(new CheckBoxCellRenderer<>());
// addActionListener(listener);
// getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
// 0), "checkbox-select");
// getActionMap().put("checkbox-select", new AbstractAction() {
// @Override
// public void actionPerformed(ActionEvent e) {
// Accessible a = getAccessibleContext().getAccessibleChild(0);
// if (a instanceof ComboPopup)
// {
// ComboPopup pop = (ComboPopup) a;
// updateItem(pop.getList().getSelectedIndex());
// }
// }
// });
// }
//
// protected void updateItem(int index) {
// if (isPopupVisible())
// {
// E item = getItemAt(index);
// item.selected ^= true;
// // ComboBoxModel m = getModel();
// // if (m instanceof CheckableComboBoxModel) {
// // ((CheckableComboBoxModel) m).fireContentsChanged(index);
// // }
// // removeItemAt(index);
// // insertItemAt(item, index);
// setSelectedIndex(-1);
// setSelectedItem(item);
// }
// }
//
// @Override
// public void setPopupVisible(boolean v) {
// if (keepOpen)
// {
// keepOpen = false;
// }
// else
// {
// super.setPopupVisible(v);
// }
// }
// }
