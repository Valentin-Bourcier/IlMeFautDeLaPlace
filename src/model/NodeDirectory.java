package model;

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

import convert_to_default_mutable_tree_node.AbstractStrategyConvert;
import convert_to_default_mutable_tree_node.StrategyConvertDirectory;

/**
 * Noeud de l'arbre portant un dossier
 * 
 * @author valentin
 *
 * 
 */

public class NodeDirectory implements MyNodeInterface {

	private static final long serialVersionUID = -115364763555925482L;

	public static final MyNodeInterface NodeFactory = new NodeDirectory();

	/**
	 * Le dossier que réprésente le noeud
	 */
	private File directory;
	/**
	 * La liste des fichiers et sous dossiers du noeud courant
	 */
	private ArrayList<MyNodeInterface> sons = new ArrayList<MyNodeInterface>();
	/**
	 * Le hash du noeud, correspond au hash de l'ensemble de ses fils
	 */
	private String hash = "";
	/**
	 * Une Map de tous les types de fichiers contenus par le dossier courant
	 */
	public HashMap<String, String> containedTypes = new HashMap<String, String>();
	/**
	 * La dernière date de modif du dossier
	 */
	long lastModificationDate = 0;
	/**
	 * La liste des doublons
	 */
	public static HashMap<String, ArrayList<ServiceNode>> doublons = new HashMap<String, ArrayList<ServiceNode>>();

	/**
	 * La stratégie de conversion
	 */
	private AbstractStrategyConvert strategyConversion = new StrategyConvertDirectory(this);

	// BUILDERS
	public NodeDirectory() {
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

	public ArrayList<MyNodeInterface> getSons() {
		return sons;
	}

	public void setSons(ArrayList<MyNodeInterface> sons) {
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

	@Override
	public void ChangerHash(String cachedHash) {
		this.setHash(cachedHash);
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
	/**
	 * Construit une arborescence de Noeud à partir du fichier/dossier passé en
	 * paramètre
	 * 
	 * @param f
	 *            fichier à partir duquel l'arbo est construite
	 * 
	 * @return {@link MyNodeInterface} la racine de l'arbo construite
	 * 
	 */
	@Override
	public MyNodeInterface createINode(File f) {
		try
		{
			if (f.isDirectory())
			{

				NodeDirectory result = new NodeDirectory(f);
				result.setLastModificationDate(f.lastModified());

				for (File currentFile : f.listFiles())
				{
					MyNodeInterface tmpNode = NodeFactory.createINode(currentFile);
					if (tmpNode != null)
						result.sons.add(tmpNode);
				}

				return result;
			}
			else
			{// ie f est un fichier
				NodeFile result = new NodeFile(f);
				result.setLastModificationDate(f.lastModified());

				return result;
			}
		}
		catch (Exception e)
		{// Dossier verouille ect...
			System.out.println(e);
			return null;
		}

	}

	// ServiceNode
	/**
	 * Construit une arborescence de Noeud à partir du fichier/dossier passé en
	 * paramètre
	 * 
	 * @param path
	 *            path du fichier à partir duquel l'arbo est construite
	 * 
	 * @return {@link ServiceNode} la racine de l'arbo construite
	 * 
	 */
	@Override
	public ServiceNode tree(String path) {
		File f = new File(path);
		MyNodeInterface tree = NodeFactory.createINode(f);
		for (File currentFile : f.listFiles())
		{
			sons.add(NodeFactory.createINode(currentFile));
		}
		// tree.computHash();
		return tree;
	}

	@Override
	public DefaultMutableTreeNode getTreeAsDefaultMutableTreeNode(DefaultMutableTreeNode pere) {
		return strategyConversion.convertToDefaultMutableTreeNode(pere);
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
		for (ServiceNode currentNode : getSons())
		{
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

	/**** MyNodeInterface ****/
	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public void effectiveFilter(String[] filtres) {

		// On liste les fils éligibles
		ArrayList<MyNodeInterface> eligibleSons = new ArrayList<MyNodeInterface>();
		for (MyNodeInterface currentNode : getSons())
		{
			System.out.println("Filtre, fils testé : " + currentNode.filename());
			if (currentNode.containsOneOfThose(filtres) == Boolean.TRUE)
			{
				eligibleSons.add(currentNode.clone());
			}
		}
		// on remplace alors la liste de fils, par la liste ne contenant que les fils
		// éligibles
		this.setSons(eligibleSons);

		// On filtre les fils éligibles
		for (MyNodeInterface currentNode : getSons())
		{
			currentNode.effectiveFilter(filtres);
		}

	}

	@Override
	public boolean isThatKind(String kind) {
		return getContainedTypes().containsKey(kind);
	}

	@Override
	public boolean containsOneOfThose(String[] filtres) {
		for (String currentFiltre : filtres)
		{
			if (isThatKind(currentFiltre))
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public void addSon(MyNodeInterface node) {
		this.getSons().add(node);
	}

	// @Override
	// public String toString() {
	// String s = filename().toUpperCase() + " ";
	// for (ServiceNode currentNode : child())
	// {
	// s += currentNode.toString() + " ";
	// }
	//
	// return s;
	//
	// }
	@Override
	public String toString() {
		return filename();
	}

	// MyNodeInterface
	@Override
	/**
	 * Le hash d'un dossier sera le hash de la concatenation des hash fichiers/sous
	 * dossiers
	 * 
	 * @see v2.MyNodeInterface#computHash()
	 */
	public void computHash() {
		// System.out.println("Hash en cours : " + filename());
		StringBuffer stringToHashBuffer = new StringBuffer("");
		for (MyNodeInterface CurrentNode : getSons())
		{
			// Si fils deja hash on le recup
			if (!(CurrentNode.hash().isEmpty()))
			{
				stringToHashBuffer.append(CurrentNode.hash());
			}
			// Sinon on le calcule puis on le recup
			else
			{
				CurrentNode.computHash();
				stringToHashBuffer.append(CurrentNode.hash());
			}

		}
		String stringToHash = stringToHashBuffer.toString();
		byte[] bytesOfMessage;
		byte[] thedigest = null;
		try
		{
			bytesOfMessage = stringToHash.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);
		}
		catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{

			e.printStackTrace();
		}
		if (thedigest != null)
		{
			this.setHash(new String(thedigest, StandardCharsets.UTF_8));

		}

	}

	// retourne la liste de tous les types de fichier contenus dans le dossier
	@Override
	public String computeFileType() {
		String listeTypes = "";
		String guessedType = "";
		for (MyNodeInterface currentNode : sons)
		{
			guessedType = currentNode.computeFileType();
			if (guessedType != null)// Si la detection de type �choue, elle renvoie null
				listeTypes += guessedType + " ";
		}
		String[] tabTypes = listeTypes.trim().split(" ");
		for (String currentTypes : tabTypes)
		{
			containedTypes.put(currentTypes.trim(), currentTypes.trim());
		}

		return listeTypes;
	}

	// Sera utilisé pour connaitre tous les types de fichier contenus dans le
	// dossier
	@Override
	public String[] containedTypes() {
		String[] type = new String[1];
		if (containedTypes.size() == 0)
			this.computeFileType();
		return containedTypes.keySet().toArray(type);
	}

	@Override
	public NodeDirectory clone() {
		NodeDirectory result = null;
		try
		{
			result = (NodeDirectory) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}

		result.setDirectory(new File(this.getDirectory().getPath()));

		result.setSons(new ArrayList<MyNodeInterface>());
		result.strategyConversion = new StrategyConvertDirectory(result);
		for (MyNodeInterface currentNode : this.getSons())
		{
			result.addSon(currentNode.clone());
		}

		result.setHash(new String(this.getHash()));

		result.setContainedTypes(new HashMap<String, String>());
		for (Map.Entry<String, String> currentEntry : this.getContainedTypes().entrySet())
		{
			String key = new String(currentEntry.getKey());
			String value = new String(currentEntry.getValue());
			result.containedTypes.put(key, value);
		}
		return result;
	}

	@Override
	public NodeDirectory deserialize() {
		NodeDirectory nd = null;
		try
		{
			FileInputStream fileIn = new FileInputStream("tmp.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			nd = (NodeDirectory) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException c)
		{
			System.out.println("Node directory class not found");
			c.printStackTrace();
			return null;
		}
		return nd;
	}

	@Override
	public int getNbNode() {
		int somme = 1;
		for (MyNodeInterface currentNode : getSons())
		{
			somme = somme + currentNode.getNbNode();
		}
		return somme;
	}

	/**
	 * Remplie la HashMap Doublons
	 */
	@Override
	public void computeDoublons() {
		if (doublons.containsKey(getHash()))
			doublons.get(getHash()).add(this);
		else
		{
			ArrayList<ServiceNode> tmp = new ArrayList<ServiceNode>();
			tmp.add(this);
			doublons.put(getHash(), tmp);

		}
		for (MyNodeInterface currentNode : getChildAsMyNodeInterface())
		{
			currentNode.computeDoublons();
		}
	}

	/**
	 * @return une hashmap de doublons
	 */
	@Override
	public HashMap<String, ArrayList<ServiceNode>> getDoublons() {
		HashMap<String, ArrayList<ServiceNode>> clean = new HashMap<>();
		if (doublons.isEmpty())
		{
			this.computeDoublons();
		}
		for (Map.Entry<String, ArrayList<ServiceNode>> entry : doublons.entrySet())
		{
			if (entry.getValue().size() > 1)
			{
				clean.put(entry.getKey(), entry.getValue());
			}
		}
		doublons = clean;
		return doublons;
	}

	@Override
	public String cliPrint(int shift) {
		String resul = "";
		for (int i = 0; i < shift; i++)
		{
			resul += "-";
		}
		resul += this.filename().toUpperCase() + "\n";
		for (MyNodeInterface son : this.getSons())
		{
			resul = resul + son.cliPrint(shift + 1);
		}
		return resul;
	}

}
