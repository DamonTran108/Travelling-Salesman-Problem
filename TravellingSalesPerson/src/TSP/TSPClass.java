package TSP;

import java.util.ArrayList;

public class TSPClass {
	private String[][] weights;

	private ArrayList<String> route = new ArrayList<>();

	public TSPClass() {
		weights = new String[12][3];

		weights[0][0] = "A";
		weights[0][1] = "B";
		weights[0][2] = "20";

		weights[1][0] = "A";
		weights[1][1] = "C";
		weights[1][2] = "42";

		weights[2][0] = "A";
		weights[2][1] = "D";
		weights[2][2] = "35";
///////////////////////////////////////////////////
		weights[3][0] = "B";
		weights[3][1] = "A";
		weights[3][2] = "20";

		weights[4][0] = "B";
		weights[4][1] = "C";
		weights[4][2] = "30";

		weights[5][0] = "B";
		weights[5][1] = "D";
		weights[5][2] = "34";
////////////////////////////////////////////////////		
		weights[6][0] = "C";
		weights[6][1] = "A";
		weights[6][2] = "42";

		weights[7][0] = "C";
		weights[7][1] = "B";
		weights[7][2] = "30";

		weights[8][0] = "C";
		weights[8][1] = "D";
		weights[8][2] = "12";
/////////////////////////////////////////////////////		
		weights[9][0] = "D";
		weights[9][1] = "A";
		weights[9][2] = "35";

		weights[10][0] = "D";
		weights[10][1] = "B";
		weights[10][2] = "34";

		weights[11][0] = "D";
		weights[11][1] = "C";
		weights[11][2] = "12";

		route.add("ABCD");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TSPClass tsp = new TSPClass();
		tsp.getCostOfRoute("ABCD");

	}

	public int getCostOfRoute(String path) {
		// String[] cit1 = new String[12];
		String[] city = path.split("(?!^)");
		String sCost = "";
		int cost = 0;

		System.out.print(city[0]);
		System.out.print(city[1]);
		System.out.print(city[2]);
		System.out.println(city[3]);

		for (int c = 0; c < city.length; c++) {

			for (int i = 0; i < weights.length; i++) {

				for (int j = 0; j < weights[i].length; j++) {
					if (c == 3) {

						System.out.println("current city : " + city[c]);
						System.out.println("first city : " + city[0]);
						
						
						sCost = weights[i+1][2];
						cost += Integer.parseInt(sCost);
						System.out.println("final cost :" + cost);
						return cost;
					}
					System.out.println(i);
					if (city[c].equals(weights[i][0])) {

						System.out.println("current city : " + city[c]);
						System.out.println("cost:" + sCost);
						System.out.println("index C :" + c);

						/*
						 * if (c == 3) { System.out.println("final total: " + sCost); }
						 */
						if (city[c + 1].equals(weights[i][j]) && c + 1 <= city.length) {

							System.out.println("current City : " + city[c]);
							System.out.println("city to go to :" + weights[i][j]);
							System.out.println("cost from and to " + weights[i][2]);
							sCost = weights[i][2];
							cost += Integer.parseInt(sCost);
						}
					}

				}
			}

		}

		System.out.println(cost);
		return cost;

	}

}
