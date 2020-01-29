package TSP;

import java.util.*;

public class TSPClass {

	
	private static double totalDistance;
	
	private static ArrayList<City> initialCities = new ArrayList<City>(Arrays.asList(new City("London", 700, 700),
			new City("Worcester", 100, 600), new City("Manchester", 100, 100), new City("Liverpool", 25, 150),
			new City("Malvern", 75, 700), new City("Birmingham", 120, 450), new City("Sheffield", 220, 130),
			new City("Nottingham", 300, 260), new City("Cambridge", 780, 500)

	));

	public TSPClass() {

	}

	public double calcDistance(City cityA, City cityB) {
		// SquareRoot(x2-x1^2/y2-y1^2)
		// Gets Distance

		// Initialise variables needed for calculation
		double distance = 0.0;

		double X1 = 0.0;
		double X2 = 0.0;
		double Y1 = 0.0;
		double Y2 = 0.0;

		// Assign variables
		X1 = cityA.getXpos();
		Y1 = cityA.getYpos();

		X2 = cityB.getXpos();
		Y2 = cityB.getYpos();

		// Print statements to check values
		System.out.print(cityA.getName());
		System.out.print(" " + cityA.getXpos());
		System.out.println("," + cityA.getYpos());

		System.out.print(cityB.getName());
		System.out.print("	" + cityB.getXpos());
		System.out.println("," + cityB.getYpos());

		// Assign distance
		distance = Math.round(Math.sqrt(Math.pow(X2 - X1, 2) + Math.pow(Y2 - Y1, 2)));

		System.out.println("----------------Distance is " + distance);

		return distance;
	}

	public double calculateRoute(ArrayList<City> route) {
		totalDistance = 0.0;

		OUTER_LOOP: for (int i = 0; i < route.size(); i++) {

			// If the index reaches the last value in the route- calculate the distance
			// between start and end of the tour.
			if (i == route.size() - 1) {
				totalDistance += calcDistance(route.get(i), route.get(0));
				break OUTER_LOOP;
			}

			// Increment distance to the distance between every two cities in the tour.
			totalDistance += calcDistance(route.get(i), route.get(i + 1));
		}

		return totalDistance;
	}

	public ArrayList<City> getRoute() {
		return this.initialCities;
	}

	public void setRoute(ArrayList<City> newRoute) {
		this.initialCities = newRoute;
	}
	
	public void printRoute(ArrayList<City> route) {
		for (int i = 0; i < route.size(); i++) {
			System.out.println(route.get(i).getName());
		}
	}

	public double getTotalDistance() {
		return this.totalDistance;
	}

	public void setTotalDistance(double newDistance) {
		this.totalDistance = newDistance;
	}

	public ArrayList<City> generateRandomRoute() {
		ArrayList<City> randoRoute = new ArrayList<City>();
		Collections.shuffle(initialCities);
		randoRoute = (ArrayList<City>) initialCities.clone();
		return randoRoute;
	}


	
}
