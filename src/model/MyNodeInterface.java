package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import lightweight_cache_system.CacheNode;

/**
 * Interface perso, commune a NodeFile et NodeDirectory Elle me permet de
 * definir des methodes internes qui sont nécessaires au traitement mais pas au
 * réalisateur de la partie graphique
 * 
 * @author valentin
 *
 */

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

	// Getters
	public ArrayList<MyNodeInterface> getChildAsMyNodeInterface();

	// Setters (nécessaire pour reprendre le cache)
	public void ChangerHash(String cachedHash);

	// DebutFiltres
	/**
	 * Calcule tous les types de fichiers portés par le noeud courant
	 * 
	 * @return une string contenant le type de fichier pour un NodeFile et une
	 *         string contenant tous les types de fichiers contenus dans le dossier
	 *         pour un Node Directory
	 * 
	 */
	public String computeFileType();

	/**
	 * Si un appel à la fonction computeFileType() a déja été effectué cette methode
	 * ce comporte comme un getter, sinon, computeFileType est appelé puis le
	 * tableau de string est retourné.
	 * 
	 * @return un tableau de string contenant les types de fichiers portés par le
	 *         noeud courant
	 */
	public String[] containedTypes();

	/**
	 * 
	 * @return un clone du noeud courant
	 */
	public MyNodeInterface clone();

	/**
	 * Cette methode élague l'arbre courant pour ne garder que les noeuds portant
	 * des fichiers dont le type est un de ceux passés en paramètres.
	 * 
	 * @param filtres
	 *            la liste des types de fichier que l'on souhaite récupérer
	 */
	public void effectiveFilter(String[] filtres);

	/**
	 * 
	 * @param filtres
	 *            la liste des types de fichier que l'on souhaite tester.
	 * @return true si le noeud porte un fichier dont le type est un de ceux passés
	 *         en paramètres.
	 */
	public boolean containsOneOfThose(String[] filtres);

	/**
	 * 
	 * @param kind
	 *            un type de fichier
	 * @return true si le fichier porté par le noeud courant est de type kind
	 */
	public boolean isThatKind(String kind);

	// FinFiltres
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
		try
		{
			FileOutputStream fos = new FileOutputStream("tmp.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			fos.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();

		}
		finally
		{

		}
	}

	/**
	 * sauvegarde l'arborescence à partir du noeud courant dans le fichier de cache
	 */
	@Override
	default public void saveTreeIntoCacheFile() {
		// this.serialize();
		CacheNode cache = new CacheNode(this);
		cache.serialize();
	}

	/**
	 * Recrée une arborescence depuis le fichier de cache
	 * 
	 * @return l'arbre reconstitué à partir du fichier de cache
	 */
	@Override
	default public ServiceNode LoadTreeFromCacheFile(String path) {
		CacheNode cache = new CacheNode();
		cache = cache.deserialize();
		MyNodeInterface root = NodeDirectory.NodeFactory.createINode(new File(path));
		if (cache.contains(root.absolutePath())
		        && cache.getAssociatedDate(root.absolutePath()) != (root.lastModificationDate()))
		{
			root.ChangerHash(cache.getAssociatedHash(path));
		}

		for (MyNodeInterface currentNode : root.getChildAsMyNodeInterface())
		{
			if (cache.contains(currentNode.absolutePath()))
			{
				if (cache.getAssociatedDate(currentNode.absolutePath()) != currentNode.lastModificationDate())
				{
					currentNode.ChangerHash(cache.getAssociatedHash(path));
				}
			}
		}

		return root;
		// return this.deserialize();
	}

	public MyNodeInterface deserialize();

	// DOUBLONS
	/**
	 * Parcours l'arborescence à partir du noeud courant afin d'identifier les
	 * doublons.
	 */
	@Override
	public void computeDoublons();
}
