package v2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public class TestAsFinalClient {

	public static void main(String[] args) {
		//1 Construire un Arbre à partir d'un path
		String path = "/home/valentin/Documents/Cours/acdc/testArbo";
		ServiceNode root = (MyNodeInterface) NodeDirectory.NodeFactory.createINode(new File(path));

		//2 Obtenir le hash d'un noeud /!\ calcul le hash si cela n'a pas été fait précedemment
		System.out.println("Hash de la racine de l'arbre :" + root.hash());

		//3 Obtenir la liste de tous les types de fichiers à partir du noeud courant
		//System.out.println("Nombre de types de fichier dans l'arbre : " + root.types().length);

		//4 Obtenir un arbre ne contenant que des fichiers d'un ou plusieurs types specifiques
		//ServiceNode arbreFiltre = root.filter(new String[] { root.types()[0], root.types()[1] });

		//5 Sauvegarder dans un fichier l'arbre courant
		root.saveTreeIntoCacheFile();

		//6 Charger un arbre depuis un fichier de cache
		ServiceNode loadedTree = new NodeDirectory();
		loadedTree = loadedTree.LoadTreeFromCacheFile(path);

		//7 Compter le nombre de noeuds d'un arbre
		System.out.println("Nombre de noeuds dans l'arbre : " + root.getNbNode());
		System.out.println("Nombre de noeuds dans l'arbre regenéré depuis le cache : " + loadedTree.getNbNode());

		//8 Consulter le poids d'un fichier/dossier
		System.out.println("Poids de l'arbre en octect : " + root.weight());

		//9 Convertir un arbre de ServiceNode en arbre de DefaultMutableTreeNode
		DefaultMutableTreeNode rootAsDefaultMutableTreeNode = root.getTreeAsDefaultMutableTreeNode(null);

		//10 Obtenir la HashMap des doublons de l'arbre 
		((MyNodeInterface) root).computeDoublons(); // /!\ va calculer le hash de tous les fichiers de l'arbre
		HashMap<String, ArrayList<ServiceNode>> doublons = root.getDoublons();
		System.out.println(doublons.size());

	}

}
