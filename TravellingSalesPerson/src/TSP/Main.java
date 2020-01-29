package TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

public class Main extends Canvas {
	// Create Instance of TSP to initialise static arraylist and totalDistance
	// variable, so the instance object is the same for every class that uses it
	
	static TSPClass tsp = new TSPClass();
	
	// rSearch Parameter - Iterations
	static RandomSearch rSearch = new RandomSearch(1000);
	static greedySearch gSearch = new greedySearch();

	// geSearch parameters - Iterations, population, k , parents
	static geneticSearch geSearch = new geneticSearch(100, 20, 5, 10);

	public static void main(String[] args) {

		// rSearch.search();

		// gSearch.search();
		//geSearch.search();

		JFrame frame = new JFrame("My Drawing");
		frame.setResizable(false);

		Canvas canvas = new Main();

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		canvas.setSize(dim.width, dim.height);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		canvas.setBackground(Color.ORANGE);

	}

	public void paint(Graphics g) {

		String distance = "";

		for (int i = 0; i < tsp.getRoute().size(); i++) {

			int x = (int) tsp.getRoute().get(i).getXpos();
			int y = (int) tsp.getRoute().get(i).getYpos();

			g.fillOval((int) x, (int) y, 20, 20);
			g.drawString(tsp.getRoute().get(i).getName(), x + 2, y);

			if (i < tsp.getRoute().size() - 1) {
				g.setColor(Color.BLUE);
				g.drawLine((int) tsp.getRoute().get(i).getXpos() + 10, (int) tsp.getRoute().get(i).getYpos() + 10,
						(int) tsp.getRoute().get(i + 1).getXpos() + 10, (int) tsp.getRoute().get(i + 1).getYpos() + 10);
				g.setColor(Color.BLACK);
				g.drawString("(" + i + ")", x - 10, y - 10);

				distance = " " + tsp.calcDistance(tsp.getRoute().get(i), tsp.getRoute().get(i + 1));
				g.drawString(distance,
						(int) (((tsp.getRoute().get(i).getXpos() + tsp.getRoute().get(i + 1).getXpos()) / 2)),
						(int) (((tsp.getRoute().get(i).getYpos() + tsp.getRoute().get(i + 1).getYpos()) / 2)));

			} else {

				distance = " " + tsp.calcDistance(tsp.getRoute().get(0), tsp.getRoute().get(tsp.getRoute().size() - 1));
				g.setColor(Color.BLUE);
				g.drawLine((int) tsp.getRoute().get(0).getXpos() + 10, (int) tsp.getRoute().get(0).getYpos() + 10,
						(int) tsp.getRoute().get(tsp.getRoute().size() - 1).getXpos() + 10,
						(int) tsp.getRoute().get(tsp.getRoute().size() - 1).getYpos() + 10);
				g.setColor(Color.BLACK);
				g.drawString("(" + i + ")", x - 10, y - 10);
				g.drawString(distance,
						(int) (((tsp.getRoute().get(tsp.getRoute().size() - 1).getXpos()
								+ tsp.getRoute().get(0).getXpos()) / 2)),
						(int) (((tsp.getRoute().get(tsp.getRoute().size() - 1).getYpos()
								+ tsp.getRoute().get(0).getYpos()) / 2)));

			}

		}

		g.drawString("Total Distance of this route : " + tsp.getTotalDistance(), 500, 10);

	}

}
