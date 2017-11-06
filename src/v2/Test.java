package v2;

import java.io.File;

public class Test {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		MyNodeInterface root = (MyNodeInterface) NodeDirectory.NodeFactory.createINode(new File("C:\\Users\\val-5"));
		long endCrawl = System.currentTimeMillis();
		System.out.println("Temps de parcours(ms): " + (endCrawl - start));
		root.computeExtension();
		long endComputeExtension = System.currentTimeMillis();
		System.out.println("Temps de computeExtension(ms): " + (endComputeExtension - endCrawl));
		root.serialize();
		long endSerialization = System.currentTimeMillis();
		System.out.println("Temps de serialization(ms): " + (endSerialization - endComputeExtension));
		MyNodeInterface test = NodeDirectory.NodeFactory.deserialize();
		long endDeSerialization = System.currentTimeMillis();
		System.out.println("Temps de deserialization(ms): " + (endDeSerialization - endSerialization));
		System.out.println(root.child().size());
		System.out.println(test.child().size());
		System.out.println(root.toString().equals(test.toString()));
		System.out.println(root.getNbNode());
		System.out.println(test.getNbNode());
		System.out.println(root.lastModificationDate());
		System.out.println(test.lastModificationDate());

		/*for (ServiceNode currentNode : root.child()) {
			System.out.println(currentNode.filename());
		}
		/*
		for (String currentString : root.extension()) {
			System.out.println(currentString);
		}*/

		/*System.out.println(root);
		String[] filtres = new String[] { "png" };
		NodeDirectory fsFiltre = (NodeDirectory) root.filter(filtres);
		System.out.println(fsFiltre);
		System.out.println(root);
		*/

	}

}
