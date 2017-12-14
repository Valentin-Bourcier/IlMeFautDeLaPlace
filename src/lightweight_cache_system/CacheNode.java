package lightweight_cache_system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import v2.MyNodeInterface;
import v2.ServiceNode;

/**
 * 
 * @author valentin Le but de cette classe est de gérer la mise en cache de
 *         l'arbre sous la forme d'une hashMap
 */
public class CacheNode implements Serializable {
	/**
	 * pour calmer java
	 */
	private static final long serialVersionUID = -7320632609738944128L;
	MyNodeInterface root;
	public HashMap<String, CacheObject> map = new HashMap<String, CacheObject>();

	public CacheNode() {
	}

	/**
	 * 
	 * 
	 * @param root
	 *            la racine de l'arbre que l'on souhaite mettre en cache
	 */
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

	public boolean contains(String path) {
		return map.containsKey(path);
	}

	public long getAssociatedDate(String path) {
		return map.get(path).getLasteModificationDate();
	}

	public String getAssociatedHash(String path) {
		return map.get(path).getHash();
	}

	public String toString() {
		String s = "";
		for (Map.Entry<String, CacheObject> entry : map.entrySet()) {
			s += entry.getKey() + " " + entry.getValue().getHash() + " " + entry.getValue().getLasteModificationDate();
			s += "\n";
		}
		return s;
	}

	public void serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("tmp.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		} catch (IOException i) {
			i.printStackTrace();

		} finally {

		}
	}

	public CacheNode deserialize() {
		CacheNode cn = null;
		try {
			FileInputStream fileIn = new FileInputStream("tmp.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			cn = (CacheNode) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("CacheNode class not found");
			c.printStackTrace();
			return null;
		}
		return cn;
	}
	/*
	public void serialize() {
	
	try {
		FileOutputStream fos = new FileOutputStream("tmp.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeInt(map.size());
		for (Map.Entry<String, CacheObject> entry : map.entrySet()) {
			oos.writeObject(entry.getKey());
			oos.writeObject(entry.getValue().getHash());
			oos.writeObject(entry.getValue().getLasteModificationDate());
			System.out.println(entry.getValue().getLasteModificationDate());
		}
	} catch (Exception e) {
		System.out.println(e);
	}
	
	}
	
	public CacheNode deserialize() {
	CacheNode cn = new CacheNode();
	try {
		FileInputStream fileIn = new FileInputStream("tmp.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			String path = (String) in.readObject();
			String hash = (String) in.readObject();
			Date date = (Date) in.readObject();
			cn.map.put(path, new CacheObject(hash, date));
		}
	
		in.close();
		fileIn.close();
	} catch (IOException i) {
		i.printStackTrace();
		return null;
	} catch (ClassNotFoundException c) {
		System.out.println("CacheNode class not found");
		c.printStackTrace();
		return null;
	}
	return cn;
	}
	
	/*public void writeObject(java.io.ObjectOutputStream stream) {
	try {
		stream.write(map.size());
		for (Map.Entry<String, CacheObject> entry : map.entrySet()) {
			stream.writeObject(entry.getKey());
			stream.writeObject(entry.getValue().getHash());
			stream.writeLong(entry.getValue().getLasteModificationDate());
		}
	} catch (Exception e) {
		System.out.println(e);
	}
	
	}
	
	public void readObject(java.io.ObjectInputStream stream) {
	
	try {
		String path = (String) stream.readObject();
		String hash
	
	} catch (Exception e) {
		System.out.println(e);
	}
	}*/

}
