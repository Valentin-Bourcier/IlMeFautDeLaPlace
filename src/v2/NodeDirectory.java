package v2;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;

public class NodeDirectory implements MyNodeInterface {
    static final protected ServiceNode NodeFactory = new NodeDirectory();

    private File directory;
    private ServiceNode father;
    private ArrayList<ServiceNode> sons;

    private String hash;

    // BUILDERS
    NodeDirectory() {
	directory = null;
	father = null;
	sons = new ArrayList<ServiceNode>();
    }

    NodeDirectory(File f) {
	directory = f;
	sons = new ArrayList<ServiceNode>();
    }

    NodeDirectory(String filename) {
	directory = new File(filename);
	sons = new ArrayList<ServiceNode>();
    }

    NodeDirectory(String filename, ServiceNode father) {
	directory = new File(filename);
	sons = new ArrayList<ServiceNode>();
	this.father = father;
    }

    NodeDirectory(File f, ServiceNode father) {
	directory = f;
	this.father = father;
	sons = new ArrayList<ServiceNode>();
    }

    // SETTERS ET GETTERS
    private File getDirectory() {
	return directory;
    }

    private void setDirectory(File directory) {
	this.directory = directory;
    }

    private ServiceNode getFather() {
	return father;
    }

    private void setFather(ServiceNode father) {
	this.father = father;
    }

    private ArrayList<ServiceNode> getSons() {
	return sons;
    }

    private void setSons(ArrayList<ServiceNode> sons) {
	this.sons = sons;
    }

    // Retourne le hash, le calcul si il n'est pas déjà fait
    private String getHash() {
	return hash;
    }

    private void setHash(String hash) {
	this.hash = hash;
    }

    // FABRIQUE
    public ServiceNode createINode(File f) {
	if (f.isDirectory()) {
	    NodeDirectory result = new NodeDirectory(f);
	    for (File currentFile : f.listFiles()) {
		result.sons.add(NodeFactory.createINode(currentFile));
	    }
	    result.computHash();
	    return result;
	} else {
	    NodeFile result = new NodeFile(f);
	    result.computHash();
	    return result;
	}

    }

    // ServiceNode
    @Override
    public ServiceNode tree(String path) {
	File f = new File(path);
	ServiceNode tree = NodeFactory.createINode(f);
	for (File currentFile : f.listFiles()) {
	    sons.add(NodeFactory.createINode(currentFile));
	}
	((MyNodeInterface) tree).computHash();
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
	// TODO Auto-generated method stub
	return this.getSons();
    }

    @Override
    public ServiceNode filter(String[] filtres) {
	// TODO Auto-generated method stub
	return null;
    }

    public String toString() {
	String s = filename() + " ";
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
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	this.setHash(new String(thedigest, StandardCharsets.UTF_8));

    }

}
