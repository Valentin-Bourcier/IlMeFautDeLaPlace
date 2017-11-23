# Il Me Faut De La Place
### Valentin Quiedeville

## Contexte
Ce projet est réalisé dans le cadre du cours Actualisation des Compétences
en Développement et Conception en première année du département Filière Ingénierie Logicielle de l'Institut Mines Télécom.

## Sujet du projet
Le projet va s’articuler autour de deux étapes.
La première va consister à fournir une vue synthétique à l’utilisateur. Concrètement, il va s’agir d’analyser une unité
de stockage, renseignée par l’utilisateur, en listant la totalité des entrées référencées (répertoire, fichier) et en
associant pour chacune d’entre elle la taille, le type, l’auteur (i.e. la personne qui a créé l’entrée) et également la
dernière fois que cette entrée a été accédée. Lors de la construction de cette liste, il sera nécessaire de générer
une empreinte unique (md5, …) pour chaque entrée afin de pouvoir identifier facilement des doublons. Une fois
une telle vue construite, l’utilisateur devra être en mesure d’appliquer différents filtres afin de visualiser
uniquement les entrées qu’il souhaite (lister les doublons, lister les fichiers de type \*.avi, lister les entrées selon leur
taille, lister les entrées créées par un utilisateur X ou Y, …). Il sera également pertinent de pouvoir permettre la
combinaison de ces différents filtres (je souhaite voir dans un ordre antéchronologique les fichiers \*.avi).
Dans un second temps, le projet s’attardera sur la mise en œuvre d’une IHM (JAVA swing) permettant d’interagir
avec les mécanismes mis en œuvre préalablement. Il sera donc impératif de documenter l’API des composants
développés lors de la première partie.

## Dépendance
Ce projet nécessite la librairie Apache Tika qu'il est possible de récupérer à cette adresse : https://tika.apache.org/download.html  disponible sous APACHE LICENSE, VERSION 2.0 (http://www.apache.org/licenses/LICENSE-2.0)

## Prise en main
La classe TestAsFinalClient fournit des exemples simples d'utilisation couvrant l'ensemble des fonctionnalités proposées.
