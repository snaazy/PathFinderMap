package MainApp;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Cette classe définit un graphe pondéré.
 */
public class WeightedGraph {

    /**
     * Classe interne représentant une arête dans le graphe.
     */
    static class Edge {
        int source;
        int destination;
        double poids;

        /**
         * Constructeur de la classe Edge.
         *
         * @param source      Le sommet source de l'arête.
         * @param destination Le sommet de destination de l'arête.
         * @param poids       Le poids de l'arête.
         */
        public Edge(int source, int destination, double poids) {
            this.source = source;
            this.destination = destination;
            this.poids = poids;
        }

        public String toString() {
            return "(" + source + "," + destination + "," + poids + ")";
        }
    }

    /**
     * Classe interne représentant un sommet dans le graphe.
     */
    static class Vertex {
        double indivTime;
        double timeFromSource;
        double heuristic;
        Vertex prev;
        LinkedList<Edge> adjacencylist;
        int num;

        /**
         * Constructeur de la classe Vertex.
         *
         * @param num Le numéro unique attribué au sommet.
         */
        public Vertex(int num) {
            this.indivTime = Double.POSITIVE_INFINITY;
            this.timeFromSource = Double.POSITIVE_INFINITY;
            this.heuristic = -1;
            this.prev = null;
            this.adjacencylist = new LinkedList<Edge>();
            this.num = num;
        }

        /**
         * Ajoute une arête voisine à ce sommet.
         *
         * @param e L'arête à ajouter.
         */
        public void addNeighbor(Edge e) {
            this.adjacencylist.addFirst(e);
        }

        public String toString() {
            String content = "";
            content += " {" + num + "," + indivTime + "," + timeFromSource + "," + heuristic + "," + prev + "}";
            return content;
        }
    }

    /**
     * Classe représentant le graphe.
     */
    static class Graph {
        ArrayList<Vertex> vertexlist;
        int num_v = 0;

        /**
         * Constructeur de la classe Graph.
         */
        Graph() {
            vertexlist = new ArrayList<Vertex>();
        }

        /**
         * Ajoute un sommet au graphe avec un temps donné.
         *
         * @param indivTime Le temps du sommet.
         */
        public void addVertex(double indivTime) {
            Vertex v = new Vertex(num_v);
            v.indivTime = indivTime;
            vertexlist.add(v);
            num_v = num_v + 1;
        }

        /**
         * Ajoute une arête au graphe avec la source, la destination et le poids donnés.
         *
         * @param source      La source de l'arête.
         * @param destination La destination de l'arête.
         * @param poids       Le poids de l'arête.
         */
        public void addEgde(int source, int destination, double poids) {
            Edge edge = new Edge(source, destination, poids);
            vertexlist.get(source).addNeighbor(edge);

        }

        public String toString() {
            String content = "";
            for (int i = 1; i < 2; i++) {
                content += "vertex : " + vertexlist.get(i).num +
                        vertexlist.get(i).toString() + " [";
                for (Edge edge : vertexlist.get(i).adjacencylist) {
                    content += edge.toString();
                }
                if (i == 1) {
                    System.out.println("size=" + vertexlist.get(i).adjacencylist.size());
                }
                content += "]\n";
            }
            return content;
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addEgde(0, 1, 4);
        graph.addEgde(0, 2, 3);
        graph.addEgde(1, 3, 2);
        graph.addEgde(1, 2, 5);
        graph.addEgde(2, 3, 7);
        graph.addEgde(3, 4, 2);
        graph.addEgde(4, 0, 4);
        graph.addEgde(4, 1, 4);
        graph.addEgde(4, 5, 6);
        System.out.println(graph.toString());
    }
}