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

    private void setHash(String hash) {
	this.hash = hash;
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
	// TODO Auto-generated method stub
	return file.getName();
    }

    @Override
    public String hash() {
	// TODO Auto-generated method stub
	return getHash();
    }

    @Override
    public long weight() {
	// TODO Auto-generated method stub
	return file.getTotalSpace();
    }

    @Override
    public String absolutePath() {
	// TODO Auto-generated method stub
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

    public String toString() {
	return "" + filename();
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
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
