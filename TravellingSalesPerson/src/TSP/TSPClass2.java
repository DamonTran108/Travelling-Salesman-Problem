package TSP;

import java.util.*;

public class TSPClass2 {

	private Random random = new Random();
	private double totalDistance = 0;
	private boolean isNew = false;

	private ArrayList<City> initialCities = new ArrayList<City>(Arrays.asList(new City("London", 700, 700),
			new City("Worcester", 100, 600), new City("Manchester", 100, 100), new City("Liverpool", 25, 150),
			new City("Malvern", 75, 700), new City("Birmingham", 120, 450), new City("Sheffield", 220, 130),
			new City("Nottingham", 300, 260), new City("Cambridge", 780, 500)

	));

	public TSPClass2() {

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

			//If the index reaches the last value in the route- calculate the distance between start and end of the tour.
			if (i == route.size() - 1) {
				totalDistance += calcDistance(route.get(i), route.get(0));
				break OUTER_LOOP;
			}

			//Increment distance to the distance between every two cities in the tour.
			totalDistance += calcDistance(route.get(i), route.get(i + 1));
		}

		return totalDistance;
	}

	public ArrayList<City> getList() {
		return initialCities;
	}

	public double randomSearch(int iterations) {
		// Define variables
		totalDistance = 0.0;
		double lowestCost = 50000.0;
		double tempCost = 0.0;

		ArrayList<City> newRoute = new ArrayList<City>();
		ArrayList<City> bestRoute = new ArrayList<City>();

		// Outer Loop over iterations input
		for (int i = 0; i < iterations; i++) {

			// Randomise the inital list of cities
			Collections.shuffle(initialCities);
			tempCost = calculateRoute(initialCities); // Store the current Cost of this route in variable
			newRoute = initialCities; // New Route assigned to initialCities

			// Assign new best route based on cost
			if (lowestCost > tempCost) {
				System.out.println("Lowest cost is : " + lowestCost);
				System.out.println("New Low is  : " + tempCost);
				lowestCost = tempCost;
				bestRoute = (ArrayList<City>) newRoute.clone(); // Clone method used as assigning bestRoute to newRoute
																// refers to the location not the actual list.
				System.out.println("NEW LOW FOUND ");
				printRoute(bestRoute);

			}

			// Assign totalDistance to lowestCost
			totalDistance = lowestCost;
			System.out.println("---------------------------Iteration : " + i);
			System.out.println("----------------------------Cost of this tour is " + tempCost);
			System.out.println("---------------------------Current Lowest Distance : " + totalDistance);

		}

		System.out.println("\n" + "-------------------------------Shortest Distance : " + totalDistance);
		initialCities = (ArrayList<City>) bestRoute.clone();
		printRoute(bestRoute);
		return totalDistance;
	}

	public double greedySearch() {
		// Defining variables
		double tempCost = 0;
		double lowestCost = 2000;

		ArrayList<City> greedyPath = new ArrayList<City>();
		// Find two closest cities in the path and make them the start and end of the
		// tour.
		greedyPath = findTwoClosestCities();

		// City object to store the current closest city
		City closestCity = new City(null, 0, 0);

		// Remove the start and end city from the initial tour to avoid repeated
		// comparison.
		initialCities.remove(greedyPath.get(0));
		initialCities.remove(greedyPath.get(1));

		// Length variable to iterate against
		int length = initialCities.size();

		// Outer Loop to compare which city is the closest in the inital list to the
		// current greedy path index.
		for (int i = 0; i < length; i++) {
			// Redefine lowest cost every iteration so comparisons can be made everytime a
			// new city is added to the greedy path
			lowestCost = 2000;

			for (int j = 0; j < initialCities.size(); j++) {

				// Assign temp variable to distance between current greedy path city to every
				// city in the initial list
				tempCost = calcDistance(greedyPath.get(i), initialCities.get(j));

				// Pick the closest city and store the values
				if (lowestCost > tempCost) {
					lowestCost = tempCost;
					closestCity = initialCities.get(j);
				}
			}

			greedyPath.add(closestCity);// Outside the inner loop add the closest city to greedy path
			initialCities.remove(closestCity);// Remove that city from the initial list to reduce comparisons in further
												// checks

			Collections.swap(greedyPath, i + 1, i + 2);// Swap indexes to make sure end of tour city remains at the end
														// of tour.
		}
		initialCities = (ArrayList<City>) greedyPath.clone(); // Reassign initalCities so we can call it and display it
																// on screen
		printRoute(greedyPath);
		totalDistance = calculateRoute(greedyPath);
		return totalDistance;

	}

	public ArrayList<City> findTwoClosestCities() {
		System.out.println("Finding Two Closest Cities in the Tour");
		// Defining variables
		ArrayList<City> newList = new ArrayList<City>();
		double lowestCost = 2000;
		double tempCost = 0;
		City cCity1 = new City(null, 0, 0);
		City cCity2 = new City(null, 0, 0);

		// Outer Loop to compare index i to all the other cities in list
		for (int i = 0; i < initialCities.size(); i++) {

			for (int j = 0; j < initialCities.size(); j++) {

				// If statement to check if the city is the same, if so increment so that i
				// compares to the next city in the list
				if (initialCities.get(i).getName().equals(initialCities.get(j).getName())) {

					j++;

				}
				// If j becomes the size of the arraylist break out of inner loop
				if (j == initialCities.size()) {
					break;
				}

				// Assign tempCost to calculate distance between the two cities
				tempCost = calcDistance(initialCities.get(i), initialCities.get(j));

				// Assign new Two closest cities in the tour
				if (lowestCost > tempCost) {
					lowestCost = tempCost;
					cCity1 = initialCities.get(i);
					cCity2 = initialCities.get(j);
				}
			}
			// Print statements to show which two cities are the current closest
			System.out.println("Current two closest cities are : " + cCity1.getName() + ", " + cCity2.getName());
			System.out.println("With a distance of : " + lowestCost);
			System.out.println("***************************");
		}

		// Print statements to show two closests cities in the entire tour.
		System.out.println("dISTANCE IS : " + lowestCost);
		System.out.println("Two closest Cities in the tour are : " + cCity1.getName() + ", " + cCity2.getName());
		System.out.println();

		// Add the two closest cities to local arrayList and return it via function
		// call.
		newList.add(cCity1);
		newList.add(cCity2);

		// Remove two closest cities from the initial list so set-up for comparison.
		initialCities.remove(cCity1);
		initialCities.remove(cCity2);
		System.out.println("Two Closest Cities Found...");
		return newList;
	}

	public void printRoute(ArrayList<City> route) {
		for (int i = 0; i < route.size(); i++) {
			System.out.println(route.get(i).getName());
		}
	}

	public double getTotalDistance() {
		return totalDistance;
	}
}
