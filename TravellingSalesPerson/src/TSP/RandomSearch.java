package TSP;

import java.util.ArrayList;
import java.util.Collections;

public class RandomSearch implements Searchable {
	private TSPClass tsp;
	private int iterations;

	public RandomSearch(int iterations) {
		tsp = new TSPClass();
		ArrayList<City> route = new ArrayList<City>();
		this.iterations = iterations;
	}

	@Override
	public void search() {
		
		// Define variables
		double distance = 0;
		double lowestCost = 50000.0;
		double tempCost = 0.0;

		ArrayList<City> newRoute = new ArrayList<City>();
		ArrayList<City> bestRoute = new ArrayList<City>();

		// Outer Loop over iterations input
		for (int i = 0; i < iterations; i++) {

			// Randomise the inital list of cities

			newRoute = tsp.generateRandomRoute();
			tempCost = tsp.calculateRoute(newRoute); // Store the current Cost of this route in variable
			// New Route assigned to initialCities

			// Assign new best route based on cost
			if (lowestCost > tempCost) {
				System.out.println("Lowest cost is : " + lowestCost);
				System.out.println("New Low is  : " + tempCost);
				lowestCost = tempCost;
				bestRoute = (ArrayList<City>) newRoute.clone(); // Clone method used as assigning bestRoute to newRoute
																// refers to the location not the actual list.
				System.out.println("NEW LOW FOUND ");
				tsp.printRoute(bestRoute);

			}

			// Assign totalDistance to lowestCost
			distance = lowestCost;
			tsp.setTotalDistance(distance);
			System.out.println("---------------------------Iteration : " + i);
			System.out.println("----------------------------Cost of this tour is " + tempCost);
			System.out.println("---------------------------Current Lowest Distance : " + distance);

		}

		System.out.println("\n" + "-------------------------------Shortest Distance : " + distance);
		newRoute = (ArrayList<City>) bestRoute.clone();
		tsp.setTotalDistance(distance);
		tsp.printRoute(bestRoute);
		tsp.setRoute(newRoute);
		

	}
}
