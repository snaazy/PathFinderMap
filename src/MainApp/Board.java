package MainApp;

import MainApp.WeightedGraph.Graph;
import MainApp.WeightedGraph.Vertex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

class Board extends JComponent {

	private static final long serialVersionUID = 1L;
	Graph graph;
	int pixelSize;
	int ncols;
	int nlines;
	HashMap<Integer, String> colors;
	int start;
	int end;
	double max_distance;
	int current;
	LinkedList<Integer> path;
	private static Graphics2D g2d;
	ArrayList<Integer> exploredVertex;

	public Board(Graph graph, int pixelSize, int ncols, int nlines, HashMap<Integer, String> colors,
			int start,
			int end) {
		super();
		this.graph = graph;
		this.pixelSize = pixelSize;
		this.ncols = ncols;
		this.nlines = nlines;
		this.colors = colors;
		this.start = start;
		this.end = end;
		this.max_distance = ncols * nlines;
		this.current = -1;
		this.path = null;
		Board.g2d = null;
		this.exploredVertex = null;
	}

	public void initBoard(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;

		int width = getWidth();
		int height = getHeight();

		int cellWidth = (int) Math.floor((double) width / ncols);
		int cellHeight = (int) Math.floor((double) height / nlines);

		for (int i = 0; i < nlines; i++) {
			int y = i * cellHeight;
			g2d.drawLine(0, y, width, y);
		}

		for (int i = 0; i < ncols; i++) {
			int x = i * cellWidth;
			g2d.drawLine(x, 0, x, height);
		}
	}

	protected void paintComponent(Graphics g) {

		initBoard(g);

		int width = getWidth();
		int height = getHeight();

		int cellWidth = (int) Math.floor((double) width / ncols);
		int cellHeight = (int) Math.floor((double) height / nlines);

		setPreferredSize(new Dimension((ncols * cellWidth), (nlines * cellHeight) - 2 * cellHeight));

		if (this.path != null) {
			for (Integer num : this.path) {

				int xCell = (num % ncols) * cellWidth;
				int yCell = (num / ncols) * cellHeight;

				g2d.setColor(Color.BLACK);

				g2d.fillRect(xCell, yCell, cellWidth, cellHeight);
			}
		}

		if (this.exploredVertex != null && this.path == null) {
			for (Integer num : this.exploredVertex) {

				Vertex vertex = graph.vertexlist.get(num);
				double individualTime = vertex.indivTime;
				String color = this.colors.get((int) individualTime);

				int xCell = (num % ncols) * cellWidth;
				int yCell = (num / ncols) * cellHeight;

				switch (color) {
					case "green":
						g2d.setColor(Color.GREEN);

						break;
					case "gray":
						g2d.setColor(Color.GRAY);
						break;
					case "blue":
						g2d.setColor(Color.BLUE);
						break;
					default:
						g2d.setColor(Color.YELLOW);
						break;
				}

				g2d.fillRect(xCell, yCell, cellWidth, cellHeight);
			}
		}

		if (this.path != null && this.exploredVertex != null) {
			List<Integer> result = this.exploredVertex.stream()

					.filter(num -> !this.path.contains(num))
					.collect(Collectors.toList());

			for (Integer num : result) {

				Vertex vertex = graph.vertexlist.get(num);
				double individualTime = vertex.indivTime;
				String color = this.colors.get((int) individualTime);

				int xCell = (num % ncols) * cellWidth;
				int yCell = (num / ncols) * cellHeight;

				switch (color) {
					case "green":
						g2d.setColor(Color.GREEN);

						break;
					case "gray":
						g2d.setColor(Color.GRAY);
						break;
					case "blue":
						g2d.setColor(Color.BLUE);
						break;
					default:
						g2d.setColor(Color.YELLOW);
						break;
				}

				g2d.fillRect(xCell, yCell, cellWidth, cellHeight);
			}

		}

	}

	public void update(Graph graph, ArrayList<Integer> exploredVertex) {
		this.graph = graph;
		this.exploredVertex = exploredVertex;
		repaint();
	}

	public void addPath(Graph graph, LinkedList<Integer> path) {
		this.graph = graph;
		this.path = path;
		this.current = -1;
		repaint();
	}

}
