package MainApp;

import MainApp.WeightedGraph.Graph;
import MainApp.WeightedGraph.Vertex;

/**
 * Cette classe initialise un graphe avec des arêtes pondérées en fonction de la
 * géométrie de la grille.
 */
public class InitGraph {

    /**
     * Initialise le graphe en ajoutant des arêtes en fonction de la géométrie de la
     * grille.
     *
     * @param graph      Le graphe à initialiser.
     * @param nbLignes   Le nombre de lignes de la grille.
     * @param nbColonnes Le nombre de colonnes de la grille.
     */
    public static void initGraph(Graph graph, int nbLignes, int nbColonnes) {
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                int source = (ligne * nbColonnes) + colonne;

                // 8-connexité
                int[] dx = { 0, 1, 0, -1, 1, -1, 1, -1 };
                int[] dy = { 1, 0, -1, 0, 1, 1, -1, -1 };

                for (int i = 0; i < 8; i++) {
                    int new_x = colonne + dx[i];
                    int new_y = ligne + dy[i];

                    if (new_x >= 0 && new_x < nbColonnes && new_y >= 0 && new_y < nbLignes) {
                        int dest = (new_y * nbColonnes) + new_x;
                        // on ajoute une arête au graphe entre la source et la destination
                        addEdgeToGraph(graph, source, dest);
                    }
                }
            }
        }
    }

    /**
     * Ajoute une arête au graphe avec le poids calculé comme la moyenne des temps
     * individuels
     * des sommets source et destination.
     *
     * @param graph  Le graphe auquel ajouter l'arête.
     * @param source L'indice du sommet source.
     * @param dest   L'indice du sommet de destination.
     */
    private static void addEdgeToGraph(Graph graph, int source, int dest) {
        Vertex sourceVertex = graph.vertexlist.get(source);
        Vertex destinationVertex = graph.vertexlist.get(dest);

        double poids = (sourceVertex.indivTime + destinationVertex.indivTime) / 2;

        graph.addEgde(source, dest, poids);
    }

    /**
     * Calcule la distance euclidienne entre deux sommets dans le graphe.
     *
     * @param vertexSource      Le sommet source.
     * @param vertexDestination Le sommet de destination.
     * @param graph             Le graphe dans lequel effectuer le calcul.
     * @return La distance euclidienne entre les deux sommets.
     */
    public static double euclidieanDistance(int vertexSource, int vertexDestination, Graph graph) {
        double distanceBetweenSourceDestination;

        int lineDestination = vertexSource / 100;
        int colDestination = vertexSource % 100;

        int lineSource = vertexDestination / 100;
        int colSource = vertexDestination % 100;

        distanceBetweenSourceDestination = Math
                .sqrt(Math.pow(lineSource - lineDestination, 2) + Math.pow(colSource - colDestination, 2));

        return distanceBetweenSourceDestination;
    }

    /**
     * Calcule la distance de Manhattan entre deux sommets dans le graphe.
     *
     * @param vertexSource      Le sommet source.
     * @param vertexDestination Le sommet de destination.
     * @param graph             Le graphe dans lequel effectuer le calcul.
     * @return La distance de Manhattan entre les deux sommets.
     */
    public static double manhattanDistance(int vertexSource, int vertexDestination, Graph graph) {
        int startX = vertexSource % 100;
        int startY = vertexSource / 100;
        int endX = vertexDestination % 100;
        int endY = vertexDestination / 100;

        return Math.abs(endX - startX) + Math.abs(endY - startY);
    }

    /**
     * Calcule la distance octilienne entre deux sommets dans le graphe.
     *
     * @param vertexSource      Le sommet source.
     * @param vertexDestination Le sommet de destination.
     * @param graph             Le graphe dans lequel effectuer le calcul.
     * @return La distance octilienne entre les deux sommets.
     */
    public static double octileHeuristic(int vertexSource, int vertexDestination, Graph graph) {
        int startX = vertexSource % 100;
        int startY = vertexSource / 100;
        int endX = vertexDestination % 100;
        int endY = vertexDestination / 100;

        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        double D = 1.0;
        double D2 = Math.sqrt(2.0);

        return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
    }

}
