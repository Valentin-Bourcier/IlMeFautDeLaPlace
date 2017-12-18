package convert_to_default_mutable_tree_node;

import javax.swing.tree.DefaultMutableTreeNode;

import model.ServiceNode;

/**
 * Conversion pour les NodeFiles
 * 
 * @author valentin
 *
 */
public class StrategyConvertFile extends AbstractStrategyConvert {
	public StrategyConvertFile(ServiceNode n) {
		node = n;
	}

	@Override
	public DefaultMutableTreeNode convertToDefaultMutableTreeNode(DefaultMutableTreeNode pere) {
		//TODO
		DefaultMutableTreeNode resul = new DefaultMutableTreeNode();
		resul.setAllowsChildren(false);
		resul.setUserObject(node);
		//resul.setParent(pere);
		return resul;
	}
}
