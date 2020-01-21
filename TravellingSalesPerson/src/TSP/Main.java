package TSP;

import java.util.ArrayList;
import java.util.Arrays;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

public class Main extends Canvas {
	static TSPClass2 boop = new TSPClass2();

	public static void main(String[] args) {
		//boop.randomSearch(500);
		boop.greedySearch(); 
		JFrame frame = new JFrame("My Drawing");

		frame.setResizable(false);

		Canvas canvas = new Main();

		canvas.setSize(900, 900);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		canvas.setBackground(Color.ORANGE);
		

		

	
		
		
	}

	public void paint(Graphics g) {
		
		String distance = "";
		for (int i = 0; i < boop.getList().size(); i++) {

			int x = (int) boop.getList().get(i).getXpos();
			int y = (int) boop.getList().get(i).getYpos();
			
			
			
			g.fillOval((int) x, (int) y, 20, 20);
			g.drawString(boop.getList().get(i).getName(), x + 2, y);


			if (i < boop.getList().size()-1) {
				g.setColor(Color.BLUE);
				g.drawLine((int) boop.getList().get(i).getXpos()+ 10, (int) boop.getList().get(i).getYpos()+10,
						(int) boop.getList().get(i + 1).getXpos() +10 , (int) boop.getList().get(i + 1).getYpos()+10);
				g.setColor(Color.BLACK);
				g.drawString("(" + i+")", x-10, y -10 );
				
			
			distance = " " + boop.calcDistance(boop.getList().get(i), boop.getList().get(i+1));
			g.drawString(distance, (int) (((boop.getList().get(i).getXpos() + boop.getList().get(i + 1).getXpos()) / 2)), (int) (((boop.getList().get(i).getYpos() + boop.getList().get(i + 1).getYpos()) / 2)));

			} else {
				
				distance = " " + boop.calcDistance(boop.getList().get(0), boop.getList().get(boop.getList().size()-1));
				g.setColor(Color.BLUE);
				g.drawLine((int) boop.getList().get(0).getXpos()+10, (int) boop.getList().get(0).getYpos()+10,
						(int) boop.getList().get(boop.getList().size()-1).getXpos()+10, (int) boop.getList().get(boop.getList().size()-1).getYpos()+10);
				g.setColor(Color.BLACK);
				g.drawString("(" + i+")", x-10, y - 10);
				g.drawString(distance, (int) (((boop.getList().get(boop.getList().size()-1).getXpos() + boop.getList().get(0).getXpos()) / 2)), (int) (((boop.getList().get(boop.getList().size()-1).getYpos() + boop.getList().get(0).getYpos()) / 2)));
				
				
			}
			
		}
		
		g.drawString("Total Distance of this route : "+boop.getTotalDistance(), 500, 10);

	
	}

}
