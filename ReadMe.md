# Sources accessibles via le lien suivant: https://github.com/Valentin-Bourcier/IlMeFautDeLaPlace
Je n'arrive pas à uploader les sources du fait de la librairie utilisée qui est assez lourde. Veuillez m'excuser pour le retard qui en résulte.

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

# Liste des fonctionnalités

- Affichage d'une arborescence sous forme d'arbre + tableau détaillé.
- Possibilité de naviguer dans l'arborescence via un JTree (arbre graphique).
- Visualisation graphique des tailles de fichiers dans un répertoire sélectionné via l'arbre.
- Suppression, ou récolte d'informations sur tous les fichiers.
- Listage des doublons contenus dans un répertoire sélectionné via l'arborescence.
- Détection et filtrage par type de fichiers de l'arborescence et de la vue synthétique.
- Estimation du nombre de doublons en fonction des précédentes recherches.
- Enregistrement des précédents lancements à la manière du lanceur de workspace d'Eclipse.


# Structuration du code

Le code est basé sur le patron de conception Modèle, Vue, Contrôleurs et enrichis par une interface qui facilite le rendu et la communication entre les composants:

- View : Est l'interface qui contient l'ensemble des méthodes permettant le rendu de n'importe quel composant de l'interface.
    - initComponents permet d'initialisé les sous-composants.
    - setLayout définit leur agencement.
    - bind, et load permettent de charger les données et d'attacher les bons listener entre eux, c'est la partie Contrôleurs.
    - refresh, build et render sont les méthodes qui construisent la vue.

- Avantages:
    - Bon découpage de l'interface, peut de code est utilisé dans les listener et on limite de nombre d'argument dans ces derniers.
    - Facilité de lecture et d'identification des erreurs. En effet chaque méthode a un rôle bien définit comme en MVC.

- Inconvénients:
    - Pour un découpage optimal, de nombreux fichiers sont souvent créés. 

# Difficultés et améliorations

Lors de ce projet les principales difficultés que j'ai pu rencontrés étaient principalement dûent au caractères inhabituel de ce que je souhaitais faire. En effet, afficher par exemple plusieurs fichiers doublons de façon à ce qu'on puisse les identifier facilement, ou bien créer un spectre de l'architecture d'un dossier de façon graphique demandais la création de composants spécifiques car il n'existe pas de composants natifs pour cela. J'ai donc dû créer tous les rendus moi même, ce qui est assez difficile pour un rendu correct. De plus la manipulation du code récupéré après la première phase m'a aussi ralentis, j'ai rencontré quelques erreurs bloquantes.

Pour améliorer le rendu final, il faudrait ajouter des possibilités de filtrage. En effet actuellement basé sur le code de la première phase, le filtre fonctionne uniquement avec des types de fichiers. Il serait intéressant d'ajouter des filtres par tailles ou dates de modifications.

# Installation du logiciel

Récupérer le fichier .jar dans le dossier /rendu et l'exécuter avec la commande suivante:
```
java -jar IMFDLP.jar
```


Valentin BOURCIER - IMT Atlantique FIL A1
