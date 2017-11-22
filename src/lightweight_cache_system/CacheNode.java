package efficient_cache_system;

import java.util.HashMap;
import java.util.Map;

import v2.MyNodeInterface;
import v2.ServiceNode;

public class CacheNode {
	MyNodeInterface root;
	public HashMap<String, CacheObject> map = new HashMap<String, CacheObject>();

	public CacheNode(MyNodeInterface root) {
		this.root = root;
		map = new HashMap<String, CacheObject>(root.getNbNode());
		this.launchBuildMap();
	}

	public void launchBuildMap() {
		buildMap(root);
	}

	private void buildMap(ServiceNode currentNode) {
		this.map.put(currentNode.absolutePath(),
				new CacheObject(currentNode.hash(), currentNode.lastModificationDate()));
		if (currentNode.isDirectory()) {
			for (ServiceNode current : currentNode.child()) {
				this.buildMap(current);
			}
		}
	}

	public String toString() {
		String s = "";
		for (Map.Entry<String, CacheObject> entry : map.entrySet()) {
			s += entry.getKey() + " " + entry.getValue().getHash() + " " + entry.getValue().getLasteModificationDate();
			s += "\n";
		}
		return s;
	}

}
