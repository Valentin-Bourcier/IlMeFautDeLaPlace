package v2;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

public class NodeDirectory implements MyNodeInterface {
	static final protected MyNodeInterface NodeFactory = new NodeDirectory();

	private File directory;
	private ArrayList<MyNodeInterface> sons = new ArrayList<MyNodeInterface>();

	private String hash = null;

	public HashMap<String, String> extension = new HashMap<String, String>();

	// BUILDERS
	NodeDirectory() {
		directory = null;

	}

	NodeDirectory(File f) {
		directory = f;

	}

	NodeDirectory(String filename) {

		directory = new File(filename);

	}

	// SETTERS ET GETTERS
	private File getDirectory() {
		return directory;
	}

	private void setDirectory(File directory) {
		this.directory = directory;
	}

	private ArrayList<MyNodeInterface> getSons() {
		return sons;
	}

	private void setSons(ArrayList<MyNodeInterface> sons) {
		this.sons = sons;
	}

	// Retourne le hash, le calcul si il n'est pas déjà fait
	private String getHash() {
		if (hash == null)
			this.computHash();
		return hash;
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	private HashMap<String, String> getExtension() {
		return extension;
	}

	private void setExtension(HashMap<String, String> extension) {
		this.extension = extension;
	}

	// FABRIQUE
	public MyNodeInterface createINode(File f) {
		if (f.isDirectory()) {
			//NodeDirectory result = new NodeDirectory(f);
			NodeDirectory result = new NodeDirectory(f);
			// System.out.println(result.filename());
			for (File currentFile : f.listFiles()) {
				result.sons.add(NodeFactory.createINode(currentFile));
			}
			result.computHash();
			return result;
		} else {
			NodeFile result = new NodeFile(f);
			// System.out.println(result.filename());
			result.computHash();
			return result;
		}

	}

	// ServiceNode
	@Override
	public ServiceNode tree(String path) {
		File f = new File(path);
		MyNodeInterface tree = NodeFactory.createINode(f);
		for (File currentFile : f.listFiles()) {
			sons.add(NodeFactory.createINode(currentFile));
		}
		tree.computHash();
		return tree;
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

		return directory.getName();
	}

	@Override
	public String hash() {

		return this.getHash();
	}

	@Override
	public long weight() {

		return directory.getTotalSpace();
	}

	@Override
	public String absolutePath() {

		return directory.getAbsolutePath();
	}

	@Override
	public ArrayList<ServiceNode> child() {
		ArrayList<ServiceNode> sons = new ArrayList<ServiceNode>();
		for (ServiceNode currentNode : getSons()) {
			sons.add(currentNode);
		}
		return sons;
	}

	public ServiceNode filter(String[] filtres) {
		NodeDirectory result = this.clone();
		result.effectiveFilter(filtres);
		return result;
	}

	@Override
	public void effectiveFilter(String[] filtres) {

		// On liste les fils éligibles
		ArrayList<MyNodeInterface> eligibleSons = new ArrayList<MyNodeInterface>();
		for (MyNodeInterface currentNode : getSons()) {
			if (currentNode.containsOneOfThose(filtres) == Boolean.TRUE) {
				eligibleSons.add(currentNode.clone());
			}
		}
		//on remplace alors la liste de fils, par la liste ne contenant que les fils éligibles
		this.setSons(eligibleSons);

		// On filtre les fils éligibles
		for (MyNodeInterface currentNode : getSons()) {
			currentNode.effectiveFilter(filtres);
		}

	}

	@Override
	public boolean isThatKind(String kind) {
		return getExtension().containsKey(kind);
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
	public void addSon(MyNodeInterface node) {
		this.getSons().add(node);
	}

	public String toString() {
		String s = filename().toUpperCase() + " ";
		for (ServiceNode currentNode : child()) {
			s += currentNode.toString() + " ";
		}

		return s;

	}

	// MyNodeInterface
	@Override
	/*
	 * Le hash d'un dossier sera le hash de la concatenation des hash
	 * fichiers/sous dossiers(non-Javadoc)
	 * 
	 * @see v2.MyNodeInterface#computHash()
	 */
	public void computHash() {
		// System.out.println("Hash en cours : " + filename());
		String stringToHash = "";
		for (ServiceNode CurrentNode : getSons()) {
			// Si fils deja hash on le recup
			if (!(CurrentNode.hash() == null)) {
				stringToHash += CurrentNode.hash();
			}
			// Sinon on le calcule puis on le recup
			else {
				((MyNodeInterface) CurrentNode).computHash();
				stringToHash += CurrentNode.hash();
			}
		}

		byte[] bytesOfMessage;
		byte[] thedigest = null;
		try {
			bytesOfMessage = stringToHash.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		this.setHash(new String(thedigest, StandardCharsets.UTF_8));

	}

	// retourne la liste de tous les types de fichier contenus dans le dossier
	public String computeExtension() {
		String listeExtension = "";
		for (ServiceNode currentNode : sons) {
			listeExtension += ((MyNodeInterface) currentNode).computeExtension() + " ";
		}
		String[] tabExtension = listeExtension.trim().split(" ");
		for (String currentExtension : tabExtension) {
			extension.put(currentExtension.trim(), currentExtension.trim());
		}
		System.out.println(extension.size());
		return listeExtension;
	}

	// Sera utilisé pour connaitre tous les types de fichier contenus dans le
	// dossier
	public String[] extension() {
		String[] type = new String[1];
		return extension.keySet().toArray(type);
	}

	@Override
	public NodeDirectory clone() {
		NodeDirectory result = null;
		try {
			result = (NodeDirectory) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		result.setDirectory(new File(this.getDirectory().getPath()));

		result.setSons(new ArrayList<MyNodeInterface>());
		for (MyNodeInterface currentNode : this.getSons()) {
			result.addSon(currentNode.clone());
		}

		result.setHash(new String(this.getHash()));

		result.setExtension(new HashMap<String, String>());
		for (Map.Entry<String, String> currentEntry : this.getExtension().entrySet()) {
			String key = new String(currentEntry.getKey());
			String value = new String(currentEntry.getValue());
			result.extension.put(key, value);
		}
		return result;
	}

	/*
	public static void main(String[] args) {
		// test clone
		NodeDirectory r1 = (NodeDirectory) NodeDirectory.NodeFactory
				.createINode(new File("C:\\Users\\val-5\\Pictures\\test"));
		long end = System.currentTimeMillis();
		r1.computeExtension();
	
		NodeDirectory r2 = r1.clone();
		System.out.println("ref r1: " + r1);
		System.out.println("ref r2: " + r2);
	
		//dir ok
		System.out.println("dir r1: " + r1.directory);
		System.out.println("dir r1: " + r2.directory);
		r1.directory = new File("test");
		System.out.println("dir r1: " + r1.directory);
		System.out.println("dir r1: " + r2.directory);
	
		//sons ok
		System.out.println("sons r1: " + r1.sons);
		System.out.println("sons r2: " + r2.sons);
		r1.sons = null;
		System.out.println("sons r1: " + r1.sons);
		System.out.println("sons r2: " + r2.sons);
		//Hash ok cf NodeFile
	
		//Extension OK
		System.out.println("extension r1: " + r1.extension);
		System.out.println("extension r2: " + r2.extension);
		r1.extension = null;
		System.out.println("extension r1: " + r1.extension);
		System.out.println("extension r2: " + r2.extension);
	
		//Clone ok
		try {
			r1.finalize();
		} catch (Throwable e) {
	
			e.printStackTrace();
		}
		System.out.println(r2);
	
	}*/

}
