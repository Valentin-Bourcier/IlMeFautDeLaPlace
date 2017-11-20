package v2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultTreeModel;

public interface ServiceNode {
	public ServiceNode tree(String path);

	public ServiceNode tree(String path, int depth);

	public ArrayList<File> doublons();

	public DefaultTreeModel treeModel();

	public String filename();

	public String hash();

	public long weight();

	public long lastModificationDate();

	public String absolutePath();

	public ArrayList<ServiceNode> child();

	public String[] types();

	public ServiceNode filter(String[] filtres);

	default public boolean isDirectory() {
		return false;
	}

	//LARACHE
	public HashMap<String, ArrayList<ServiceNode>> getDoublons();

}
