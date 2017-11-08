package v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

import com.sun.istack.internal.Nullable;

public class NodeDirectory implements MyNodeInterface {

	private static final long serialVersionUID = -115364763555925482L;

	static final protected MyNodeInterface NodeFactory = new NodeDirectory();

	private File directory;
	private ArrayList<MyNodeInterface> sons = new ArrayList<MyNodeInterface>();
	private String hash = "";
	public HashMap<String, String> containedTypes = new HashMap<String, String>();
	long lastModificationDate = 0;
	NodeDirectory father = null;

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

	NodeDirectory(File f, long date, NodeDirectory father) {
		directory = f;
		lastModificationDate = date;
		this.father = father;
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

	private String getHash() {

		return hash;
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	protected HashMap<String, String> getContainedTypes() {
		return containedTypes;
	}

	private void setContainedTypes(HashMap<String, String> extension) {
		this.containedTypes = extension;
	}

	private long getLastModificationDate() {
		return lastModificationDate;
	}

	private void setLastModificationDate(long date) {
		lastModificationDate = date;
	}

	protected NodeDirectory getFather() {
		return father;
	}

	private void setFather(NodeDirectory father) {
		this.father = father;
	}

	// FABRIQUE
	public MyNodeInterface createINode(File f, @Nullable ServiceNode pere) {
		try {
			if (f.isDirectory()) {
				//NodeDirectory result = new NodeDirectory(f);
				NodeDirectory result = new NodeDirectory(f);
				result.setLastModificationDate(f.lastModified());
				result.setFather((NodeDirectory) pere);
				// System.out.println(result.filename());
				for (File currentFile : f.listFiles()) {
					MyNodeInterface tmpNode = NodeFactory.createINode(currentFile, result);
					if (tmpNode != null)
						result.sons.add(tmpNode);
				}
				//result.computHash();
				return result;
			} else {
				NodeFile result = new NodeFile(f);
				result.setLastModificationDate(f.lastModified());
				result.setFather((NodeDirectory) pere);
				// System.out.println(result.filename());
				//result.computHash();
				return result;
			}
		} catch (Exception e) {//Dossier verouille ect...
			System.out.println(e);
			return null;
		}

	}

	// ServiceNode
	@Override
	public ServiceNode tree(String path, @Nullable ServiceNode pere) {
		File f = new File(path);
		MyNodeInterface tree = NodeFactory.createINode(f, pere);
		for (File currentFile : f.listFiles()) {
			sons.add(NodeFactory.createINode(currentFile, tree));
		}
		//tree.computHash();
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
	public long lastModificationDate() {
		return getLastModificationDate();
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

	@Override
	public ServiceNode filter(String[] filtres) {
		NodeDirectory result = this.clone(null);
		result.effectiveFilter(filtres);
		return result;
	}

	@Override
	public String[] types() {
		return containedTypes();
	}

	/**** MyNodeInterface ****/
	@Override
	public void effectiveFilter(String[] filtres) {

		// On liste les fils éligibles
		ArrayList<MyNodeInterface> eligibleSons = new ArrayList<MyNodeInterface>();
		for (MyNodeInterface currentNode : getSons()) {
			if (currentNode.containsOneOfThose(filtres) == Boolean.TRUE) {
				eligibleSons.add(currentNode.clone(this));
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
		return getContainedTypes().containsKey(kind);
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
		StringBuffer stringToHashBuffer = new StringBuffer("");
		for (MyNodeInterface CurrentNode : getSons()) {
			// Si fils deja hash on le recup
			if (!(CurrentNode.hash() == null)) {
				stringToHashBuffer.append(CurrentNode.hash());
			}
			// Sinon on le calcule puis on le recup
			else {
				CurrentNode.computHash();
				stringToHashBuffer.append(CurrentNode.hash());
			}
			//On répercute le changement de hash sur l'ensemble de l'arbo
			if (getFather() != null) {
				getFather().computHash();
			}
		}
		String stringToHash = stringToHashBuffer.toString();
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
		if (thedigest != null) {
			this.setHash(new String(thedigest, StandardCharsets.UTF_8));

			//On répercute le changement de hash sur l'ensemble de l'arbo
			if (getFather() != null) {
				getFather().computHash();
			}
		}

	}

	// retourne la liste de tous les types de fichier contenus dans le dossier
	public String computeFileType() {
		String listeTypes = "";
		String guessedType = "";
		for (MyNodeInterface currentNode : sons) {
			guessedType = currentNode.computeFileType();
			if (guessedType != null)//Si la detection de type échoue, elle renvoie null
				listeTypes += guessedType + " ";
		}
		String[] tabTypes = listeTypes.trim().split(" ");
		for (String currentTypes : tabTypes) {
			containedTypes.put(currentTypes.trim(), currentTypes.trim());
		}
		//System.out.println(extension.size());
		return listeTypes;
	}

	// Sera utilisé pour connaitre tous les types de fichier contenus dans le
	// dossier
	public String[] containedTypes() {
		String[] type = new String[1];
		return containedTypes.keySet().toArray(type);
	}

	@Override
	public NodeDirectory clone(MyNodeInterface pere) {
		NodeDirectory result = null;
		try {
			result = (NodeDirectory) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		result.setDirectory(new File(this.getDirectory().getPath()));

		result.setSons(new ArrayList<MyNodeInterface>());
		result.setFather((NodeDirectory) pere);
		for (MyNodeInterface currentNode : this.getSons()) {
			result.addSon(currentNode.clone(result));
		}

		result.setHash(new String(this.getHash()));

		result.setContainedTypes(new HashMap<String, String>());
		for (Map.Entry<String, String> currentEntry : this.getContainedTypes().entrySet()) {
			String key = new String(currentEntry.getKey());
			String value = new String(currentEntry.getValue());
			result.containedTypes.put(key, value);
		}
		return result;
	}

	public NodeDirectory deserialize() {
		NodeDirectory nd = null;
		try {
			FileInputStream fileIn = new FileInputStream("tmp.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			nd = (NodeDirectory) in.readObject();
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
		return nd;
	}

	@Override
	public int getNbNode() {
		int somme = 1;
		for (MyNodeInterface currentNode : getSons()) {
			somme = somme + currentNode.getNbNode();
		}
		return somme;
	}

	/*public static void main(String[] args) {
		// test clone
		NodeDirectory r1 = (NodeDirectory) NodeDirectory.NodeFactory
				.createINode(new File("C:\\Users\\val-5\\Pictures\\test"), null);
		long end = System.currentTimeMillis();
		r1.computeExtension();
	
		NodeDirectory r2 = r1.clone(null);
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
	
		System.out.println("father r1: " + r1.father);
		System.out.println("father r2: " + r2.father);
		r1.father = new NodeDirectory();
		System.out.println("father r1: " + r1.father);
		System.out.println("father r2: " + r2.father);
	
		//Clone ok
		try {
			r1.finalize();
		} catch (Throwable e) {
	
			e.printStackTrace();
		}
		System.out.println(r2);
		System.out.println(r2.directory);
		System.out.println(r2.sons);
		System.out.println(r2.extension);
		System.out.println(r2.father);
	
	}*/

}
