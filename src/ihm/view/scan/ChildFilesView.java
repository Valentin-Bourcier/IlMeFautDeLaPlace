package ihm.view.scan;

import javax.swing.tree.DefaultTreeModel;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;

import ihm.model.FileRowModel;
import ihm.model.Settings;
import ihm.view.main.View;

public class ChildFilesView extends Outline implements View {

	private static final long serialVersionUID = 1L;

	public ChildFilesView() {
		render();
	}

	@Override
	public void load() {
		DefaultTreeModel vTreeModel = new DefaultTreeModel(
		        Settings.service.getTreeAsDefaultMutableTreeNode(null).getRoot());

		OutlineModel vOutlineModel = DefaultOutlineModel.createOutlineModel(vTreeModel, new FileRowModel(), true,
		        "File System");

		setRootVisible(false);
		setModel(vOutlineModel);
	}

}
