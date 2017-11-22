package v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import lightweight_cache_system.CacheNode;

public interface MyNodeInterface extends ServiceNode, Cloneable, Serializable {
	/**
	 * Calcul le hash des fichiers de l'arbre. Le point de départ est le noeud
	 * appelant
	 */
	public void computHash();

	/**
	 * 
	 * @param f
	 * @return L'arbre à partir de f
	 */
	public MyNodeInterface createINode(File f);

	//Getters
	public ArrayList<MyNodeInterface> getChildAsMyNodeInterface();

	//Setters (nécessaire pour reprendre le cache)
	public void ChangerHash(String cachedHash);

	//DebutFiltres
	public String computeFileType();

	public String[] containedTypes();

	public MyNodeInterface clone();

	public void effectiveFilter(String[] filtres);

	public boolean containsOneOfThose(String[] filtres);

	/**
	 * 
	 * @param kind
	 *            un type de fichier
	 * @return true si le fichier porté par le noeud courant est de type kind
	 */
	public boolean isThatKind(String kind);

	//FinFiltres
	/**
	 * 
	 * @param node
	 *            Ajoute le param node à la liste des fils du noeud courant
	 */
	public void addSon(MyNodeInterface node);

	/**
	 * Serialise dans le fichier tmp.ser l'état de l'arbre
	 */
	default public void serialize() {
		try {
			FileOutputStream fos = new FileOutputStream("tmp.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		} catch (IOException i) {
			i.printStackTrace();

		} finally {

		}
	}

	@Override
	default public void saveTreeIntoCacheFile() {
		//this.serialize();
		CacheNode cache = new CacheNode(this);
		cache.serialize();
	}

	@Override
	default public ServiceNode LoadTreeFromCacheFile(String path) {
		CacheNode cache = new CacheNode();
		cache = cache.deserialize();
		MyNodeInterface root = (MyNodeInterface) NodeDirectory.NodeFactory.createINode(new File(path));
		if (cache.contains(root.absolutePath())
				&& cache.getAssociatedDate(root.absolutePath()) == (root.lastModificationDate())) {
			root.ChangerHash(cache.getAssociatedHash(path));
		}

		for (MyNodeInterface currentNode : root.getChildAsMyNodeInterface()) {
			if (cache.contains(currentNode.absolutePath())) {
				if (cache.getAssociatedDate(currentNode.absolutePath()) == currentNode.lastModificationDate()) {
					currentNode.ChangerHash(cache.getAssociatedHash(path));
				}
			}
		}

		return root;
		//return this.deserialize();
	}

	public MyNodeInterface deserialize();

	//DOUBLONS
	public void computeDoublons();
}
