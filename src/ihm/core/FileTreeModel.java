package ihm.core;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import model.ServiceNode;

public class FileTreeModel extends DefaultTreeModel{

	private static final long serialVersionUID = 1L;

	public FileTreeModel(TreeNode aRootNode) {
		super(aRootNode);
	}
	
	public void remove(ServiceNode aNode) {
		DefaultMutableTreeNode vNode = find(aNode);
		if(vNode != null) {
			removeNodeFromParent(vNode);
		}
		
	}
	
	public DefaultMutableTreeNode find(ServiceNode aNode) {
		DefaultMutableTreeNode vNode = (DefaultMutableTreeNode) root;
		Enumeration<?> vEnumeration = vNode.breadthFirstEnumeration();
		while (vEnumeration.hasMoreElements()) {
			DefaultMutableTreeNode vNext = (DefaultMutableTreeNode) vEnumeration.nextElement();
			if(vNext != null) {
				if(vNext.getUserObject().equals(aNode)){
					return vNext;
				}
			}
		}
		return null;
	}
	
}
