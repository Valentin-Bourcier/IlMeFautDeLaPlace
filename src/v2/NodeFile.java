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

}
