import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileSystem {
	Node root;
	HashMap<String, File> hashMap;
	ArrayList<File[]> doublons;

	FileSystem() {
		root = new Node();
		hashMap = new HashMap<String, File>();
		doublons = new ArrayList<File[]>();
	}

	FileSystem(String filename) {
		root = new Node(filename);
		hashMap = new HashMap<String, File>();
		doublons = new ArrayList<File[]>();
	}

	FileSystem(File file) {
		root = new Node(file);
		hashMap = new HashMap<String, File>();
		doublons = new ArrayList<File[]>();
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public HashMap<String, File> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<String, File> hashMap) {
		this.hashMap = hashMap;
	}

	public ArrayList<File[]> getDoublons() {
		return doublons;
	}

	public void setDoublons(ArrayList<File[]> doublons) {
		this.doublons = doublons;
	}

	public static void fillHashMap(FileSystem fs, Node n) {
		if (n.type == false) {//n est une feuille/fichier
			//fs.HashFile(n.value);
		} else {
			for (Node currentNode : n.sons) {
				fillHashMap(fs, currentNode);
			}
		}

	}

	public void HashFile(File f) {
		DigestInputStream dis;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			dis = new DigestInputStream(new FileInputStream(f), md);

			while (dis.read(new byte[8192]) > 0)
				;

			byte[] hash = md.digest();
			File doubl = hashMap.put(new String(hash), f);
			dis.close();
			if (doubl != null) {
				this.doublons.add(new File[] { doubl, f });
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FileSystem fs = new FileSystem("/home");
		System.out.println(fs.root.toGraphviz());
		fillHashMap(fs, fs.root);
		//System.out.println(fs.hashMap.size());
		//System.out.println(fs.doublons.size());
	}

}
