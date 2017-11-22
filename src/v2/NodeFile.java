package v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import convert_to_default_mutable_tree_node.AbstractStrategyConvert;
import convert_to_default_mutable_tree_node.StrategyConvertFile;

public class NodeFile implements MyNodeInterface {

	private static final long serialVersionUID = -7609107586723401900L;
	private static final TikaFileTypeDetector fileDetector = new TikaFileTypeDetector();

	private File file;

	private String hash = "";
	private String type = "";

	private AbstractStrategyConvert strategyConversion = new StrategyConvertFile(this);

	long lastModificationDate = 0;

	// BUILDERS
	NodeFile() {
		file = null;

	}

	NodeFile(File f) {
		file = f;

	}

	NodeFile(String filename) {
		file = new File(filename);
	}

	NodeFile(File f, long date) {
		file = f;
		lastModificationDate = date;
	}

	private File getFile() {
		return file;
	}

	private String getHash() {
		if (hash.isEmpty())
			this.computHash();
		return hash;
	}

	private String getType() {
		return type;
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	public void ChangerHash(String cachedHash) {
		this.setHash(cachedHash);
	}

	public void setType(String extension) {
		this.type = extension.trim();
	}

	private long getLastModificationDate() {
		return lastModificationDate;
	}

	void setLastModificationDate(long date) {
		lastModificationDate = date;
	}

	@Override
	public ServiceNode tree(String path) {
		return new NodeFile(path);
	}

	@Override
	public DefaultMutableTreeNode getTreeAsDefaultMutableTreeNode(DefaultMutableTreeNode pere) {
		return strategyConversion.convertToDefaultMutableTreeNode(pere);
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
		//System.out.println(filename() + " ==> " + file.length());
		return file.length();
	}

	@Override
	public long lastModificationDate() {
		return getLastModificationDate();
	}

	@Override
	public String absolutePath() {

		return file.getAbsolutePath();
	}

	@Override
	public ArrayList<ServiceNode> child() {
		throw new UnsupportedOperationException("Une feuille n'a pas de fils");

	}

	@Override
	public ServiceNode filter(String[] filtres) {
		throw new UnsupportedOperationException("On ne peut filtrer que des dossiers");
	}

	@Override
	public String[] types() {
		return containedTypes();
	}

	@Override
	public MyNodeInterface updateTree() {
		//TODO
		return null;
	}

	/**** MyNodeInterface ****/

	@Override
	public MyNodeInterface createINode(File f) {
		return new NodeFile(f);
	}

	@Override
	public String toString() {
		return "" + filename();
	}

	// MyInterfaceNode
	@Override
	public ArrayList<MyNodeInterface> getChildAsMyNodeInterface() {
		throw new UnsupportedOperationException();
	}

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
	public String computeFileType() {
		String resul = "";
		try {
			resul = fileDetector.probeContentType(file.toPath());
		} catch (IOException e) {
			System.out.println(e);
		}

		return resul;

	}

	public String[] containedTypes() {
		String[] result = new String[1];
		result[0] = getType();
		return result;
	}

	@Override
	public boolean isThatKind(String kind) {

		return getType().equals(kind);
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
			e.printStackTrace();
		}
		result.file = new File(this.file.getPath());
		result.hash = new String(this.hash());
		result.type = new String(this.type);

		return result;
	}

	@Override
	public void addSon(MyNodeInterface node) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void effectiveFilter(String[] filtres) {
	}

	public NodeFile deserialize() {
		NodeFile nf = null;
		try {
			FileInputStream fileIn = new FileInputStream("tmp.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			nf = (NodeFile) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Node directory class not found");
			c.printStackTrace();
			return null;
		}
		return nf;
	}

	public void computeDoublons() {
		if (NodeDirectory.doublons.containsKey(getHash()))
			NodeDirectory.doublons.get(getHash()).add(this);
		else {
			ArrayList<ServiceNode> tmp = new ArrayList<ServiceNode>();
			tmp.add(this);
			NodeDirectory.doublons.put(getHash(), tmp);
		}
	}

	public HashMap<String, ArrayList<ServiceNode>> getDoublons() {
		return NodeDirectory.doublons;
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
	
	// File diff�rent OK
	System.out.println("n1 file: " + n1.file);
	System.out.println("n2 file: " + n2.file);
	n1.file = new File("test");
	System.out.println("n1 file: " + n1.file);
	System.out.println("n2 file: " + n2.file);
	
	// Hash diff�rent ok
	System.out.println("n1 hash: " + n1.hash());
	System.out.println("n2 hash: " + n2.hash());
	n1.hash = "test";
	System.out.println("n1 hash: " + n1.hash());
	System.out.println("n2 hash: " + n2.hash());
	
	// Extension diff�rente ok
	System.out.println("n1 extension: " + n1.extension);
	System.out.println("n2 extension: " + n2.extension);
	n1.extension = "test";
	System.out.println("n1 extension: " + n1.extension);
	System.out.println("n2 extension: " + n2.extension);
	
	// TEST FINAL OK, le node d'origine et son clone sont ind�pendants
	try {
	    n1.finalize();
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	System.out.println(n2);
	System.out.println("n2 file: " + n2.file);
	System.out.println("n2 hash: " + n2.hash());
	System.out.println("n2 extension: " + n2.extension);
	}*/

}
