# Recherche du Plus Court Chemin - TP7 TP8

Cet exercice implémente les algorithmes de Dijkstra et A* pour trouver le plus court chemin sur une carte grille. L'objectif est de lire une carte à partir d'un fichier texte, de la représenter sous forme de graphe pondéré, et de trouver le chemin le plus court entre un point de départ et un point d'arrivée en utilisant l'un de ces deux algorithmes.

## Structure du Projet

Le programme est organisé en plusieurs classes Java :

1. `WeightedGraph.java` : Cette classe définit une structure de graphe pondéré. Elle contient des classes internes pour représenter les arêtes et les sommets du graphe.

2. `Board.java` : Cette classe gère l'affichage de la carte, du déroulement des algorithmes et du chemin trouvé. Elle utilise la bibliothèque Swing pour créer une fenêtre graphique.

3. `Loader.java` : Cette classe est responsable de la lecture du fichier texte de la carte et de la conversion en données exploitables.

4. `InitGraph.java` : Cette classe initialise le graphe à partir des données de la carte.

5. `App.java` : Cette classe contient la méthode `main` où le programme principal s'exécute. Vous y trouverez les méthodes `AStar` et `Dijkstra` pour l'exécution des algorithmes.

## Exécution du Programme

Pour exécuter le programme, placez-vous à la racine du projet, là où se trouve le script `run.sh`. Exécutez le script en tapant la commande suivante dans votre terminal :

```bash
./run.sh


## Format de la Carte

La carte est fournie sous forme de fichier texte avec un format spécifique. Le fichier doit contenir des métadonnées telles que la taille de la carte, les types de cases, les couleurs, et le graphe lui-même. Un exemple de format de carte est fourni dans le fichier ```graph.txt```.

## Exécution du Programme

Pour exécuter le programme, exécutez la méthode `main` de la classe `App.java`. Le programme affichera une boîte de dialogue permettant de choisir entre les algorithmes Dijkstra et A*.

## Algorithme de Dijkstra

L'algorithme de Dijkstra cherche à assigner la plus courte distance depuis le point de départ à tous les sommets du graphe. Il utilise une approche gloutonne en choisissant les voisins du nœud courant qui ont la plus petite distance temporaire. Lorsque le nœud d'arrivée est atteint, le chemin le plus court est déroulé.

## Algorithme A*

L'algorithme A* est une extension de Dijkstra qui choisit les nœuds à explorer en fonction d'une somme entre la distance temporaire et une heuristique (estimation de la distance au nœud d'arrivée). Cela permet généralement d'accélérer la recherche du chemin le plus court.


## Auteur

Réalisé par FEKIH HASSEN Yassine dans le cadre du cours "Algorithmique Avancée" à l'Université Paris-Cité.

