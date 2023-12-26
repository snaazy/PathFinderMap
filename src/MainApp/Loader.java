package MainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import MainApp.WeightedGraph.Graph;

/**
 * Cette classe gère la lecture et l'écriture de fichiers pour l'application.
 */
public class Loader {

    private static final String FILE_GRAPH = "graph.txt";

    /**
     * Lit les informations du fichier graph.txt et les retourne dans une liste.
     *
     * @return Une liste d'informations lues à partir du fichier graph.txt.
     */
    public static ArrayList<Object> readFromFileGraph() {

        ArrayList<Object> listInformationGraph = new ArrayList<>();

        try {

            File myObj = new File(FILE_GRAPH);

            Scanner myReader = new Scanner(myObj);
            String data = "";

            for (int i = 0; i < 3; i++) {
                data = myReader.nextLine();
            }

            int nlines = Integer.parseInt(data.split("=")[1]);
            listInformationGraph.add(nlines);

            data = myReader.nextLine();
            int ncols = Integer.parseInt(data.split("=")[1]);
            listInformationGraph.add(ncols);

            Graph graph = new Graph();

            HashMap<String, Integer> groundTypes = new HashMap<String, Integer>();
            HashMap<Integer, String> groundColor = new HashMap<Integer, String>();
            data = myReader.nextLine();
            data = myReader.nextLine();

            while (!data.equals("==Graph==")) {
                String name = data.split("=")[0];
                int time = Integer.parseInt(data.split("=")[1]);
                data = myReader.nextLine();
                String color = data;
                groundTypes.put(name, time);
                groundColor.put(time, color);
                data = myReader.nextLine();
            }

            for (int line = 0; line < nlines; line++) {
                data = myReader.nextLine();
                for (int col = 0; col < ncols; col++) {
                    graph.addVertex(groundTypes.get(String.valueOf(data.charAt(col))));
                }
            }

            listInformationGraph.add(graph);

            data = myReader.nextLine();
            data = myReader.nextLine();
            int startV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols
                    + Integer.parseInt(data.split("=")[1].split(",")[1]);
            listInformationGraph.add(startV);

            data = myReader.nextLine();
            myReader.close();
            int endV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols
                    + Integer.parseInt(data.split("=")[1].split(",")[1]);
            listInformationGraph.add(endV);

            listInformationGraph.add(groundColor);
        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est apparu.");
            e.printStackTrace();
        }

        return listInformationGraph;
    }
}
