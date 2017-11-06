package v2;

import java.io.File;

public class Test {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		MyNodeInterface root = (MyNodeInterface) NodeDirectory.NodeFactory
				.createINode(new File("C:\\Users\\val-5\\Pictures\\test"));
		long end = System.currentTimeMillis();
		root.computeExtension();
		System.out.println(root);
		String[] filtres = new String[] { "png" };
		NodeDirectory fsFiltre = (NodeDirectory) root.filter(filtres);
		System.out.println(fsFiltre);
		System.out.println(root);

	}

}
