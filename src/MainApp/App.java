package MainApp;

//Par Sylvain Lobry, pour le cours "Algorithmique avancee"

// Université Paris-Cité

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JFrame;

import MainApp.WeightedGraph.Edge;
import MainApp.WeightedGraph.Graph;
import MainApp.WeightedGraph.Vertex;

import javax.swing.*;

/**
 * Classe principale pour trouver le PCC.
 */
public class App {

	/**
	 * Initialise l'affichage de la grille.
	 *
	 * @param board     La grille à afficher.
	 * @param nlines    Le nombre de lignes de la grille.
	 * @param ncols     Le nombre de colonnes de la grille.
	 * @param pixelSize La taille des pixels pour l'affichage.
	 */
	private static void drawBoard(Board board, int nlines, int ncols, int pixelSize) {
		JFrame frame = new JFrame("PLUS COURT CHEMIN :)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(board);
		frame.setSize((ncols * pixelSize) + pixelSize, (nlines * pixelSize) - 2 * pixelSize);

		frame.setVisible(true);
	}

	/**
	 * Méthode A* pour trouver le plus court chemin dans un graphe.
	 *
	 * @param graph Le graphe représentant la carte.
	 * @param start L'indice de la case de départ.
	 * @param end   L'indice de la case d'arrivée.
	 * @param board L'affichage de la grille.
	 * @return Une liste d'entiers correspondant au chemin trouvé.
	 */
	public static LinkedList<Integer> AStar(Graph graph, int start, int end, Board board) {
		// initialisation du temps du sommet de départ à 0
		graph.vertexlist.get(start).timeFromSource = 0;
		int number_tries = 0; // Compteur du nombre de nœuds explorés

		// création d'un ensemble contenant les numéros des sommets à visiter
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for (int i = 0; i < graph.num_v; i++) {
			to_visit.add(graph.vertexlist.get(i).num);
		}

		// calcul de l'heuristique pour tous les sommets du graphe
		for (int i = 0; i < graph.num_v; i++) {
			Vertex vertexSource = graph.vertexlist.get(graph.vertexlist.get(i).num);
			vertexSource.heuristic = InitGraph.manhattanDistance(vertexSource.num, end, graph);
		}

		to_visit.remove(start);

		double timeFromSource = 0;

		// initialisation des listes pour suivre les sommets visités
		ArrayList<Vertex> listVisitedVertex = new ArrayList<>();
		Vertex min_v = graph.vertexlist.get(start);

		ArrayList<Vertex> listVisitedVertexTree = new ArrayList<>();

		listVisitedVertexTree.add(min_v);

		ArrayList<Integer> exploredVertex = new ArrayList<>();
		exploredVertex.add(min_v.num);

		while (to_visit.contains(end)) {
			// parcours des arêtes voisines du sommet courant
			for (Edge edge : min_v.adjacencylist) {

				Vertex neighboVertex = graph.vertexlist.get(edge.destination);

				if (!listVisitedVertexTree.contains(neighboVertex) && !listVisitedVertex.contains(neighboVertex)) {
					// ajout du voisin à la liste des sommets à visiter
					listVisitedVertex.add(neighboVertex);
				}

				if (timeFromSource + edge.poids < neighboVertex.timeFromSource) {
					// mise à jour du temps de parcours du voisin si un chemin plus court est trouvé
					neighboVertex.timeFromSource = timeFromSource + edge.poids;
					neighboVertex.prev = min_v;
				}
			}

			// selection du prochain sommet à visiter en fonction de la somme du temps de
			// parcours et de l'heuristique
			min_v = listVisitedVertex.get(0);

			for (Vertex vertex : listVisitedVertex) {
				if (vertex.timeFromSource + vertex.heuristic < min_v.timeFromSource + min_v.heuristic) {
					min_v = vertex;
				}
			}

			timeFromSource = min_v.timeFromSource;

			listVisitedVertexTree.add(min_v);
			exploredVertex.add(min_v.num);
			listVisitedVertex.remove(min_v);

			to_visit.remove(min_v.num);
			number_tries += 1;

			// mise à jour de l'affichage du tableau si un tableau est passé en paramètre
			if (board != null) {
				try {
					board.update(graph, exploredVertex);
					Thread.sleep(10);

				} catch (InterruptedException e) {
					System.out.println("stop");
				}
			}
		}

		// affichage des résultats
		System.out.println("Done! Using A*:");
		System.out.println("source vertex : " + start);
		System.out.println(" destination vertex : " + end);
		System.out.println(" Number of nodes explored: " + number_tries);
		System.out.println(" Total time of the path:" + graph.vertexlist.get(end).timeFromSource);

		// reconstitution du chemin à partir du sommet d'arrêt
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addFirst(end);

		Vertex currentVertex = graph.vertexlist.get(end);

		while (currentVertex.prev != null) {
			int numCurrentVertex = currentVertex.prev.num;
			path.addFirst(numCurrentVertex);
			currentVertex = graph.vertexlist.get(numCurrentVertex);
		}

		// ajout du chemin à l'affichage du tableau si un tableau est passé en paramètre
		if (board != null) {
			board.addPath(graph, path);
		}

		return path;
	}

	/**
	 * Méthode Dijkstra pour trouver le plus court chemin dans un graphe.
	 *
	 * @param graph Le graphe représentant la carte.
	 * @param start L'indice de la case de départ.
	 * @param end   L'indice de la case d'arrivée.
	 * @param board L'affichage de la grille.
	 * @return Une liste d'entiers correspondant au chemin trouvé.
	 */
	public static LinkedList<Integer> Dijkstra(Graph graph, int start, int end, Board board) {
		// initialisation du temps du sommet de départ à 0
		graph.vertexlist.get(start).timeFromSource = 0;
		int number_tries = 0; // Compteur du nombre de nœuds explorés

		// creation d'un ensemble contenant les numéros des sommets à visiter
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for (int i = 0; i < graph.num_v; i++) {
			to_visit.add(graph.vertexlist.get(i).num);
		}

		to_visit.remove(start);

		double timeFromSource = 0;

		// initialisation des listes pour suivre les sommets visités
		ArrayList<Vertex> listVisitedVertex = new ArrayList<>();
		Vertex min_v = graph.vertexlist.get(start);

		ArrayList<Vertex> listVisitedVertexTree = new ArrayList<>();

		listVisitedVertexTree.add(min_v);

		ArrayList<Integer> exploredVertex = new ArrayList<>();
		exploredVertex.add(min_v.num);

		while (to_visit.contains(end)) {
			// parcours des arêtes voisines du sommet courant
			for (Edge edge : min_v.adjacencylist) {

				Vertex neighboVertex = graph.vertexlist.get(edge.destination);

				if (!listVisitedVertexTree.contains(neighboVertex) && !listVisitedVertex.contains(neighboVertex)) {
					// ajout du voisin à la liste des sommets à visiter
					listVisitedVertex.add(neighboVertex);
				}

				if (timeFromSource + edge.poids < neighboVertex.timeFromSource) {
					// mise à jour du temps de parcours du voisin si un chemin plus court est trouvé
					neighboVertex.timeFromSource = timeFromSource + edge.poids;
					neighboVertex.prev = min_v;
				}
			}

			// selection du prochain sommet à visiter en fonction du temps de parcours
			min_v = listVisitedVertex.get(0);

			for (Vertex vertex : listVisitedVertex) {
				if (vertex.timeFromSource < min_v.timeFromSource) {
					min_v = vertex;
				}
			}

			timeFromSource = min_v.timeFromSource;

			listVisitedVertexTree.add(min_v);
			exploredVertex.add(min_v.num);
			listVisitedVertex.remove(min_v);

			to_visit.remove(min_v.num);
			number_tries += 1;

			// mise à jour de l'affichage du tableau si un tableau est passé en paramètre
			if (board != null) {
				try {
					board.update(graph, exploredVertex);
					Thread.sleep(10);

				} catch (InterruptedException e) {
					System.out.println("Stop");
				}
			}
		}

		// affichage des résultats
		System.out.println("Done! Using Dijkstra : ");
		System.out.println("  Vertex Source : " + start);
		System.out.println("  Vertex Destination : " + end);
		System.out.println("  Number of nodes explored: " + number_tries);
		System.out.println("  Total time of the path:	" + graph.vertexlist.get(end).timeFromSource);

		// reconstitution du chemin à partir du sommet d'arrêt
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addFirst(end);

		Vertex currentVertex = graph.vertexlist.get(end);

		while (currentVertex.prev != null) {
			int numCurrentVertex = currentVertex.prev.num;
			path.addFirst(numCurrentVertex);
			currentVertex = graph.vertexlist.get(numCurrentVertex);
		}

		// ajout du chemin à l'affichage du tableau si un tableau est passé en paramètre
		if (board != null) {
			board.addPath(graph, path);
		}

		return path;
	}

	/**
	 * Méthode principale.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Object> listInformationGraph = Loader.readFromFileGraph();

		int nlines = (Integer) listInformationGraph.get(0);
		int ncols = (Integer) listInformationGraph.get(1);
		Graph graph = (Graph) listInformationGraph.get(2);

		HashMap<Integer, String> groundColor = (HashMap<Integer, String>) listInformationGraph.get(5);

		InitGraph.initGraph(graph, nlines, ncols);
		System.out.println("Nombre de lignes : " + nlines);
		System.out.println("Nombre de colonnes : " + ncols);

		int startV = (Integer) listInformationGraph.get(3);
		int endV = (Integer) listInformationGraph.get(4);

		int pixelSize = 10;
		Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
		drawBoard(board, nlines, ncols, pixelSize);
		board.repaint();

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			System.out.println("stop");
		}

		// creation d'une boite de dialogue pour choisir l'algorithme
		String[] options = { "A*", "Dijkstra" };
		int selectedOption = JOptionPane.showOptionDialog(null, "Choisissez un algorithme:", "Selection algorithme",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (selectedOption == 0) {
			App.AStar(graph, startV, endV, board);
		} else if (selectedOption == 1) {
			App.Dijkstra(graph, startV, endV, board);
		}
	}
}
