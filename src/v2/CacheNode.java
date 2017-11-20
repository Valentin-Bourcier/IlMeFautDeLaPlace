package v2;

import java.util.HashMap;
import java.util.Map;

public class CacheNode {
	MyNodeInterface root;
	public HashMap<String, String> map = new HashMap<String, String>();

	public CacheNode(MyNodeInterface root) {
		this.root = root;
		map = new HashMap<String, String>(root.getNbNode());
		this.launchBuildMap();
	}

	public void launchBuildMap() {
		buildMap(root);
	}

	private void buildMap(ServiceNode currentNode) {
		this.map.put(currentNode.absolutePath(), currentNode.hash());
		if (currentNode.isDirectory()) {
			for (ServiceNode current : currentNode.child()) {
				this.buildMap(current);
			}
		}
	}

	public String toString() {
		String s = "";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			s += entry.getKey() + " " + entry.getValue();
			s += "\n";
		}
		return s;
	}

}
