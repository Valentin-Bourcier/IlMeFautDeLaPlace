package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		System.out.println(args.length);
		boolean hashe = false;
		boolean type = false;
		boolean continuer = true;

		if (args.length != 1) {
			System.out.println("java -classpath ./bin/ model.Main cheminMenantALaRacineDeLarbo");
			System.exit(1);
		}
		File rootFile = new File(args[0]);
		if (!rootFile.exists()) {
			System.out.println("Le chemin fourni est incorrect");
			System.exit(1);
		}

		System.out.println("Création de l'arbre à partir du path passé en paramètre");
		ServiceNode root = (MyNodeInterface) NodeDirectory.NodeFactory.tree(args[0]);
		Scanner sc = new Scanner(System.in);
		while (continuer) {
			System.out.println("L'arbre courant contient " + root.getNbNode() + " noeuds");
			System.out.println("0) afficher l'arborescence");
			System.out.println("1) hasherl'arborescence");
			System.out.println("2) Afficher la liste de tous les types de fichiers présents dans l'arbre");
			System.out.println("3) Consulter le poids de l'arbre");
			if (hashe) {
				System.out.println("4) Consulter les doublons");
			}
			if (type) {
				System.out.println("5) Filtrer l'arbre");
			}
			System.out.println("6) Exit");

			String input = sc.nextLine();
			int value = Integer.parseInt(input.substring(0, 1));

			switch (value) {
			case 0:
				System.out.println(root.cliPrint(0));
				System.out.println("NB : Pour faciliter la lecture les nom des dossiers sont en caps");
				break;
			case 1:
				System.out.println("Hash de l'arborescence en cours");
				root.hash();
				System.out.println("Hash terminé");
				hashe = true;
				break;
			case 2:
				System.out.println("Recupération de l'ensemble des types de fichiers en cours");
				printType(root);
				type = true;
				break;
			case 3:
				System.out.println(root.weight());

				break;
			case 4:
				if (hashe) {
					HashMap<String, ArrayList<ServiceNode>> doublons = root.getDoublons();
					for (Map.Entry<String, ArrayList<ServiceNode>> entry : doublons.entrySet()) {
						System.out.println("Fichier : " + entry.getValue().get(0).absolutePath());
						System.out.println("doublons détecté : ");
						for (ServiceNode sn : entry.getValue()) {
							System.out.println("    " + sn.absolutePath());
						}
					}
					//printDoublons(root);
				}
				break;
			case 5:
				printFilteredTree(root);
				break;
			case 6:
				System.out.println("Exit");
				System.exit(0);
			default:
				System.out.println("Valeur incorrecte");
			}

		}

	}

	public static void printType(ServiceNode root) {
		for (String s : root.types()) {
			System.out.println(s);
		}
	}

	public static void printFilteredTree(ServiceNode root) {
		System.out.println("Les types suivants sont présents dans l'arbre :");
		printType(root);
		Scanner sc = new Scanner(System.in);
		ArrayList<String> filters = new ArrayList<>();
		boolean continuer = true;
		while (continuer) {
			for (int i = 0; i < root.types().length; i++) {
				System.out.println(i + ") " + root.types()[i]);
			}
			System.out.println("Entrez l'index du filtre que vous souhaitez ajouter");
			String input = sc.nextLine();
			int value = Integer.parseInt(input.substring(0, input.length()));
			if (value < root.types().length) {
				filters.add(root.types()[value]);
			} else {
				continuer = false;
			}

			String rappel = "Les filtres suivants sont déja pris en compte : -";
			for (String s : filters) {
				rappel += s + "-";
			}
			System.out.println(rappel);
		}
		String[] filtersToApply = (String[]) filters.toArray(new String[0]);
		root = root.filter(filtersToApply);
		System.out.println(root.cliPrint(0));
		System.out.println("L'arbre filtré contient " + root.getNbNode() + " noeuds");
	}

	/*public static void printDoublons(ServiceNode root) {
		HashMap<String, ArrayList<ServiceNode>> doublons = root.getDoublons();
		String s = "";
		for (Map.Entry<String, ArrayList<ServiceNode>> entry : doublons.entrySet()) {
	
			String key = entry.getKey();
			ArrayList<ServiceNode> value = entry.getValue();
			s = key + " : ";
	
			for (ServiceNode sn : value) {
				s = s + sn.absolutePath() + " ";
			}
	
			s = s + "\n";
	
		}
		System.out.println(s);
	}*/

}
