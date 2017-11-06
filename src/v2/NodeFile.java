package v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;

public class NodeFile implements MyNodeInterface {
	private File file;
	private ServiceNode father;
	private String hash;
	private String extension;

	// BUILDERS
	NodeFile() {
		file = null;
		father = null;

	}

	NodeFile(File f) {
		file = f;

	}

	NodeFile(String filename) {
		file = new File(filename);

	}

	NodeFile(String filename, ServiceNode father) {
		file = new File(filename);

		this.father = father;
	}

	NodeFile(File f, ServiceNode father) {
		file = f;
		this.father = father;

	}

	private File getFile() {
		return file;
	}

	private ServiceNode getFather() {
		return father;
	}

	private String getHash() {
		return hash;
	}

	private String getExtension() {
		return extension;
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	public void setExtension(String extension) {
		this.extension = extension.substring(1).trim();
	}

	@Override
	public ServiceNode tree(String path) {
		return new NodeFile(path);
	}

	@Override
	public ServiceNode tree(String path, int depth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<File> doublons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTreeModel treeModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String filename() {

		return file.getName();
	}

	@Override
	public String hash() {

		return getHash();
	}

	@Override
	public long weight() {

		return file.getTotalSpace();
	}

	@Override
	public String absolutePath() {

		return file.getAbsolutePath();
	}

	@Override
	public ArrayList<ServiceNode> child() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceNode filter(String[] filtres) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceNode createINode(File f) {
		return new NodeFile(f);
	}

	@Override
	public String toString() {
		return "" + filename();
	}

	// MyInterfaceNode
	@Override
	public void computHash() {
		// System.out.println("Hash en cours : " + filename());
		DigestInputStream dis;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			dis = new DigestInputStream(new FileInputStream(getFile()), md);
			while (dis.read(new byte[8192]) > 0)
				;
			byte[] hash = md.digest();
			dis.close();
			this.setHash(new String(hash, StandardCharsets.UTF_8));
		} catch (IOException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

	}

	// Renvoie .txt
	public String computeExtension() {
		setExtension(filename().substring(filename().lastIndexOf(".")));
		return getExtension();
	}

	public String[] extension() {
		String[] result = new String[1];
		result[1] = getExtension();
		return result;
	}

	@Override
	public boolean isThatKind(String kind) {

		return getExtension().equals(kind);
	}

	@Override
	public void addSon(ServiceNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsOneOfThose(String[] filtres) {
		for (String currentFiltre : filtres) {
			if (isThatKind(currentFiltre))
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public NodeFile clone() {
		NodeFile result = null;
		try {
			result = (NodeFile) super.clone();

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.file = new File(this.file.getPath());
		// result.father = this.father.clone();
		result.hash = new String(this.hash());
		result.extension = new String(this.extension);

		return result;
	}

	/*
	public static void main(String[] args) {
	//Test clone
	NodeFile n1 = new NodeFile("C:\\Users\\val-5\\Pictures\\OIM.jpg");
	n1.computHash();
	n1.computeExtension();
	System.out.println("ref n1: " + n1);
	NodeFile n2 = n1.clone();
	System.out.println("ref n2: " + n2);
	
	// File différent OK
	System.out.println("n1 file: " + n1.file);
	System.out.println("n2 file: " + n2.file);
	n1.file = new File("test");
	System.out.println("n1 file: " + n1.file);
	System.out.println("n2 file: " + n2.file);
	
	// Hash différent ok
	System.out.println("n1 hash: " + n1.hash());
	System.out.println("n2 hash: " + n2.hash());
	n1.hash = "test";
	System.out.println("n1 hash: " + n1.hash());
	System.out.println("n2 hash: " + n2.hash());
	
	// Extension différente ok
	System.out.println("n1 extension: " + n1.extension);
	System.out.println("n2 extension: " + n2.extension);
	n1.extension = "test";
	System.out.println("n1 extension: " + n1.extension);
	System.out.println("n2 extension: " + n2.extension);
	
	// TEST FINAL OK, le node d'origine et son clone sont indépendants
	try {
	    n1.finalize();
	} catch (Throwable e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(n2);
	System.out.println("n2 file: " + n2.file);
	System.out.println("n2 hash: " + n2.hash());
	System.out.println("n2 extension: " + n2.extension);
	}*/

}
