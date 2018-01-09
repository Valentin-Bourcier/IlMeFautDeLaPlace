package ihm.view.scan;

import javax.swing.tree.DefaultTreeModel;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;

import ihm.model.FileRowModel;
import ihm.model.Settings;
import ihm.view.main.View;
import model.ServiceNode;

public class ChildFilesView extends Outline implements View {

	private static final long serialVersionUID = 1L;

	private DefaultTreeModel treeModel;
	private OutlineModel outlineModel;
	private ServiceNode selection;

	public void setSelection(ServiceNode aSelection) {
		selection = aSelection;
	}

	@Override
	public void initComponents() {

	}

	@Override
	public void load() {
		if (!selection.isDirectory())
		{
			selection = Settings.root.findFather(selection);
		}
		treeModel = new DefaultTreeModel(selection.getTreeAsDefaultMutableTreeNode(null).getRoot());
		outlineModel = DefaultOutlineModel.createOutlineModel(treeModel, new FileRowModel(), true, "File System");

		setRootVisible(false);
		setModel(outlineModel);
		if (selection.isDirectory())
		{
			int vIndex = selection.child().lastIndexOf(Settings.service);
			if (vIndex >= 0)
			{
				setRowSelectionInterval(vIndex, vIndex);
			}
		}
	}

}
