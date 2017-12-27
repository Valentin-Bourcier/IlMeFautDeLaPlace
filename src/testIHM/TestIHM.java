package testIHM;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import model.MyNodeInterface;
import model.NodeDirectory;
import model.ServiceNode;

public class TestIHM {

	public static void main(String[] args) {
		ServiceNode root = (MyNodeInterface) NodeDirectory.NodeFactory
				.createINode(new File("/home/valentin/IMFDLP/"));
		JFrame frame = new JFrame("File Browser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TreeModel treeModel = new DefaultTreeModel(root.getTreeAsDefaultMutableTreeNode(null).getRoot());

		JTree jtree = new JTree(treeModel);
		jtree.setShowsRootHandles(true);
		JScrollPane scrollPane = new JScrollPane(jtree);

		frame.add(scrollPane);
		frame.setLocationByPlatform(true);
		frame.setSize(640, 480);
		frame.setVisible(true);

	}

}
