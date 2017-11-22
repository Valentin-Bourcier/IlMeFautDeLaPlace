package v2;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public interface ServiceNode {
	/**
	 * 
	 * @param path
	 * @return un arbre de ServiceNode ayant path comme origine
	 */
	public ServiceNode tree(String path);

	/**
	 * @param pere
	 *            doit être null quand la methode est excutée sur la racine de
	 *            l'arbre
	 * @return l'arbre de ServiceNode courant sous forme de DefaultMutableTreeNode
	 */
	public DefaultMutableTreeNode getTreeAsDefaultMutableTreeNode(DefaultMutableTreeNode pere);

	public DefaultTreeModel treeModel();

	/**
	 * 
	 * @return le nom du fichier porté par le noeud courant
	 */
	public String filename();

	/**
	 * 
	 * @return le hash du fichier porté par le noeud courant
	 */
	public String hash();

	/**
	 * 
	 * @return le poids du fichier porté par le noeud courant
	 */
	public long weight();

	/**
	 * 
	 * @return la date de dernière modif du fichier porté par le noeud courant
	 */
	public long lastModificationDate();

	/**
	 * 
	 * @return le chemin absolu du fichier porté par le noeud courant
	 */
	public String absolutePath();

	/**
	 * 
	 * @return la liste des fils du noeud courant
	 */
	public ArrayList<ServiceNode> child();

	/**
	 * 
	 * @return la liste des types du noeud courant
	 */
	public String[] types();

	/**
	 * 
	 * @param filtres
	 * @return un arbre ne contenant que des noeud ayant un type contenu dans
	 *         filtres
	 */
	public ServiceNode filter(String[] filtres);

	/**
	 * 
	 * @return true si le noeud courant symbolise un dossier
	 */
	default public boolean isDirectory() {
		return false;
	}

	/**
	 * 
	 * @return une hash map de doublons
	 */
	public HashMap<String, ArrayList<ServiceNode>> getDoublons();

	/**
	 * sauvegarde l'état de l'arbre courant dans le fichier tmp.ser situé à la
	 * racine du projet
	 */
	public void saveTreeIntoCacheFile();

	/**
	 * 
	 * @return un arbre à partir du fichier de cache
	 */
	public ServiceNode LoadTreeFromCacheFile();

	/**
	 * 
	 * @return le nombre de noeud dans l'arbre courant
	 */
	default public int getNbNode() {
		return 1;
	}

	public ServiceNode updateTree();

}
