package convert_to_default_mutable_tree_node;

import javax.swing.tree.DefaultMutableTreeNode;

import model.ServiceNode;

/**
 * Conversion pour les NodeDirectory
 * 
 * @author valentin
 *
 */
public class StrategyConvertDirectory extends AbstractStrategyConvert {

	public StrategyConvertDirectory(ServiceNode n) {
		node = n;
	}

	@Override
	public DefaultMutableTreeNode convertToDefaultMutableTreeNode(DefaultMutableTreeNode pere) {
		DefaultMutableTreeNode resul = new DefaultMutableTreeNode();
		resul.setAllowsChildren(true);
		resul.setUserObject(node);
		//resul.setParent(pere);
		int index = 0;
		for (ServiceNode currentNode : node.child()) {
			resul.insert(currentNode.getTreeAsDefaultMutableTreeNode(resul), index);
			index = index + 1;
		}

		return resul;

	}

}
