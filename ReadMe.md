# Il me faut de la place

## Partie 1

Pour cette première phase du projet, toute la partie fonctionnelle est implémentée. Elle offre les services suivants:

- Création d'une arborescence, avec option de hashage des fichiers à la construction, et option de mise en cache des fichiers.
- Récupération d'informations sur les différents fichiers et dossiers de l'arborescence, taille, date de modification...
- Collecte et suppression des doublons.
- Filtrage des fichiers, selon plusieurs critères, taille, date de modification, nom de fichier...
- Ecoute des changements sur les fichiers du système. Nécessite l'activation de l'option de mise en cache des fichiers à la création de l'arbre. Ainsi que l'implémentation de la classe FileTreeListener.

- Points positifs: Le fait d'exécuter les fonctionnalitées à la demande permet d'être plus efficace dans les premiers lancements du logiciel
- Points négatifs: Le même aspect implique un plus grand nombre d'opération pour enrichir la mise en cache.

- Problèmes rencontrés: implémentation du multithreading, et support multiplateforme.


# Partie 2 - Sources de : Valentin Quiedéville

- L'interface graphique comporte une vue pour la visualisation des doublons, pour détailler les informations des fichiers, et une vue permettant d'analyser graphiquement, et de façon visuelle la composition d'un répertoire.

- Points positifs: la navigation visuelle est facile à appréhender et plutôt intuitive.
- Points négatifs: Elle nécessite parfois des rechargements lourds puisque toutes les vues sont liées à au panneau de navigation dans l'arborescence.




# Installation du logiciel

Récupérer le fichier .jar dans le dossier /rendu et l'exécuter avec la commande suivante:
```
java -jar IMFDLP.jar
```

## Rapport de phase deux disponible via le lien suivant [Rapport](/Rendu/designBackground.md)

Valentin BOURCIER - IMT Atlantique FIL A1
