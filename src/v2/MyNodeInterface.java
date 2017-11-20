package v2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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

	public MyNodeInterface deserialize();

	default public int getNbNode() {
		return 1;
	}

	//DOUBLONS
	public void computeDoublons();
}
