package TSP;

import java.util.ArrayList;
import java.util.Collections;

public class greedySearch implements Searchable{
	private TSPClass tsp;
	private ArrayList<City> route;

	public greedySearch() {
		tsp = new TSPClass();
		route = new ArrayList<City>();
	}
	
	public ArrayList<City> findTwoClosestCities() {
		System.out.println("Finding Two Closest Cities in the Tour");
		// Defining variables
		ArrayList<City> newList = new ArrayList<City>();
		route = tsp.getRoute();
		double lowestCost = 2000;
		double tempCost = 0;
		City cCity1 = new City(null, 0, 0);
		City cCity2 = new City(null, 0, 0);

		// Outer Loop to compare index i to all the other cities in list
		for (int i = 0; i < route.size(); i++) {

			for (int j = 0; j < route.size(); j++) {

				// If statement to check if the city is the same, if so increment so that i
				// compares to the next city in the list
				if (route.get(i).getName().equals(route.get(j).getName())) {

					j++;

				}
				// If j becomes the size of the arraylist break out of inner loop
				if (j == route.size()) {
					break;
				}

				// Assign tempCost to calculate distance between the two cities
				tempCost = tsp.calcDistance(route.get(i), route.get(j));

				// Assign new Two closest cities in the tour
				if (lowestCost > tempCost) {
					lowestCost = tempCost;
					cCity1 = route.get(i);
					cCity2 = route.get(j);
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
		route.remove(cCity1);
		route.remove(cCity2);
		System.out.println("Two Closest Cities Found...");
		return newList;
	}


	@Override
	public void search() {
		// Defining variables
				double distance = 0;
				double tempCost = 0;
				double lowestCost = 2000;
				
				route = tsp.getRoute();
				
				ArrayList<City> greedyPath = new ArrayList<City>();
				// Find two closest cities in the path and make them the start and end of the
				// tour.
				greedyPath = findTwoClosestCities();

				// City object to store the current closest city
				City closestCity = new City(null, 0, 0);

				// Remove the start and end city from the initial tour to avoid repeated
				// comparison.
				route.remove(greedyPath.get(0));
				route.remove(greedyPath.get(1));

				// Length variable to iterate against
				int length = route.size();

				// Outer Loop to compare which city is the closest in the inital list to the
				// current greedy path index.
				for (int i = 0; i < length; i++) {
					// Redefine lowest cost every iteration so comparisons can be made everytime a
					// new city is added to the greedy path
					lowestCost = 2000;

					for (int j = 0; j < route.size(); j++) {

						// Assign temp variable to distance between current greedy path city to every
						// city in the initial list
						tempCost = tsp.calcDistance(greedyPath.get(i), route.get(j));

						// Pick the closest city and store the values
						if (lowestCost > tempCost) {
							lowestCost = tempCost;
							closestCity = route.get(j);
						}
					}

					greedyPath.add(closestCity);// Outside the inner loop add the closest city to greedy path
					route.remove(closestCity);// Remove that city from the initial list to reduce comparisons in further
												// checks

					Collections.swap(greedyPath, i + 1, i + 2);// Swap indexes to make sure end of tour city remains at the end
																// of tour.
				}
				route = (ArrayList<City>) greedyPath.clone(); // Reassign initalCities so we can call it and display it
																// on screen
				tsp.printRoute(greedyPath);
				distance = tsp.calculateRoute(greedyPath);
				tsp.setRoute(greedyPath);	
	}
}
