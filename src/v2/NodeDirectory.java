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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import convert_to_default_mutable_tree_node.AbstractStrategyConvert;
import convert_to_default_mutable_tree_node.StrategyConvertDirectory;

public class NodeDirectory implements MyNodeInterface {

	private static final long serialVersionUID = -115364763555925482L;

	public static final MyNodeInterface NodeFactory = new NodeDirectory();

	private File directory;
	private ArrayList<MyNodeInterface> sons = new ArrayList<MyNodeInterface>();
	private String hash = "";
	public HashMap<String, String> containedTypes = new HashMap<String, String>();
	long lastModificationDate = 0;
	static HashMap<String, ArrayList<ServiceNode>> doublons = new HashMap<String, ArrayList<ServiceNode>>();

	private AbstractStrategyConvert strategyConversion = new StrategyConvertDirectory(this);

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

	NodeDirectory(File f, long date) {
		directory = f;
		lastModificationDate = date;
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
		if (hash.isEmpty())
			this.computHash();
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
		return directory.lastModified();
	}

	private void setLastModificationDate(long date) {
		lastModificationDate = date;
	}

	@Override
	public ArrayList<MyNodeInterface> getChildAsMyNodeInterface() {
		return this.sons;

	}

	// FABRIQUE
	public MyNodeInterface createINode(File f) {
		try {
			if (f.isDirectory()) {

				NodeDirectory result = new NodeDirectory(f);
				result.setLastModificationDate(f.lastModified());

				for (File currentFile : f.listFiles()) {
					MyNodeInterface tmpNode = NodeFactory.createINode(currentFile);
					if (tmpNode != null)
						result.sons.add(tmpNode);
				}

				return result;
			} else {// ie f est un fichier
				NodeFile result = new NodeFile(f);
				result.setLastModificationDate(f.lastModified());

				return result;
			}
		} catch (Exception e) {//Dossier verouille ect...
			System.out.println(e);
			return null;
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
		//tree.computHash();
		return tree;
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

		return directory.getName();
	}

	@Override
	public String hash() {

		return this.getHash();
	}

	@Override
	public long weight() {
		long poids = 0;
		for (MyNodeInterface currentNode : getChildAsMyNodeInterface())
			poids = poids + currentNode.weight();
		return poids + this.directory.length();
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
		NodeDirectory result = this.clone();
		result.effectiveFilter(filtres);
		return result;
	}

	@Override
	public String[] types() {
		return containedTypes();
	}

	@Override
	public MyNodeInterface updateTree() {
		//TODO
		MyNodeInterface updatedTree = this.clone();
		File f = new File(this.absolutePath());
		return null;
	}

	/**** MyNodeInterface ****/
	@Override
	public boolean isDirectory() {
		return true;
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

		// On filtre les fils �ligibles
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

	@Override
	public void saveTreeIntoCacheFile() {
		this.serialize();
	}

	@Override
	public ServiceNode LoadTreeFromCacheFile() {
		return this.deserialize();
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
		//System.out.println("Hash en cours : " + filename());
		StringBuffer stringToHashBuffer = new StringBuffer("");
		for (MyNodeInterface CurrentNode : getSons()) {
			// Si fils deja hash on le recup
			if (!(CurrentNode.hash().isEmpty())) {
				stringToHashBuffer.append(CurrentNode.hash());
			}
			// Sinon on le calcule puis on le recup
			else {
				CurrentNode.computHash();
				stringToHashBuffer.append(CurrentNode.hash());
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

		}

	}

	// retourne la liste de tous les types de fichier contenus dans le dossier
	public String computeFileType() {
		String listeTypes = "";
		String guessedType = "";
		for (MyNodeInterface currentNode : sons) {
			guessedType = currentNode.computeFileType();
			if (guessedType != null)//Si la detection de type �choue, elle renvoie null
				listeTypes += guessedType + " ";
		}
		String[] tabTypes = listeTypes.trim().split(" ");
		for (String currentTypes : tabTypes) {
			containedTypes.put(currentTypes.trim(), currentTypes.trim());
		}

		return listeTypes;
	}

	// Sera utilisé pour connaitre tous les types de fichier contenus dans le
	// dossier
	public String[] containedTypes() {
		String[] type = new String[1];
		if (containedTypes.size() == 0)
			this.computeFileType();
		return containedTypes.keySet().toArray(type);
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

	/**
	 * Remplie la HashMap Doublons
	 */
	public void computeDoublons() {
		if (doublons.containsKey(getHash()))
			doublons.get(getHash()).add(this);
		else {
			ArrayList<ServiceNode> tmp = new ArrayList<ServiceNode>();
			tmp.add(this);
			doublons.put(getHash(), tmp);

		}
		for (MyNodeInterface currentNode : getChildAsMyNodeInterface()) {
			currentNode.computeDoublons();
		}
	}

	/**
	 * @Return une hashmap de doublons
	 */
	public HashMap<String, ArrayList<ServiceNode>> getDoublons() {
		HashMap<String, ArrayList<ServiceNode>> clean = new HashMap<>();
		for (Map.Entry<String, ArrayList<ServiceNode>> entry : doublons.entrySet()) {
			if (entry.getValue().size() > 1) {
				clean.put(entry.getKey(), entry.getValue());
			}
		}
		doublons = clean;
		return doublons;
	}

}
