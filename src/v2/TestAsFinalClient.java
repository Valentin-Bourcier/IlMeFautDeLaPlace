package v2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public class TestAsFinalClient {

	public static void main(String[] args) {
		//1 Construire un Arbre à partir d'un path
		ServiceNode root = (MyNodeInterface) NodeDirectory.NodeFactory
				.createINode(new File("/home/valentin/Documents/Cours/retroconception/RetroConcept"));

		//2 Obtenir le hash d'un noeud /!\ calcul le hash si cela n'a pas été fait précedemment
		System.out.println(root.hash());

		//3 Obtenir la liste de tous les types de fichiers à partir du noeud courant
		System.out.println(root.types().length);

		//4 Obtenir un arbre ne contenant que des fichiers d'un ou plusieurs types specifiques
		ServiceNode arbreFiltre = root.filter(new String[] { root.types()[0], root.types()[1] });

		//5 Sauvegarder dans un fichier l'arbre courant
		root.saveTreeIntoCacheFile();

		//6 Charger un arbre depuis un fichier de cache
		ServiceNode loadedTree = new NodeDirectory();
		loadedTree = loadedTree.LoadTreeFromCacheFile();

		//7 Compter le nombre de noeuds d'un arbre
		System.out.println(root.getNbNode());
		System.out.println(loadedTree.getNbNode());

		//8 Consulter le poids d'un fichier/dossier
		System.out.println(root.weight());

		//9 Convertir un arbre de ServiceNode en arbre de DefaultMutableTreeNode
		DefaultMutableTreeNode rootAsDefaultMutableTreeNode = root.getTreeAsDefaultMutableTreeNode(null);

		//10 Obtenir la HashMap des doublons de l'arbre /!\ va calculer le hash de tous les fichiers de l'arbre
		HashMap<String, ArrayList<ServiceNode>> doublons = root.getDoublons();
		System.out.println(doublons.size());

	}

}
