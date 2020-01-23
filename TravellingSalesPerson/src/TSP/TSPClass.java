package TSP;

import java.util.*;

public class TSPClass {

	private Random random = new Random();
	private double totalDistance = 0;
	

	private ArrayList<City> initialCities = new ArrayList<City>(Arrays.asList(new City("London", 700, 700),
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

	public ArrayList<City> generateRandomRoute() {
		ArrayList<City> randoRoute = new ArrayList<City>();
		Collections.shuffle(initialCities);
		randoRoute = (ArrayList<City>) initialCities.clone();
		return randoRoute;
	}

	public double gaSearch() {
		double bestCostEver = 5000;
		double endTempCost = 0;
		int iter_max = 100;
		int population = 10;
		int k = 3;
		int parents = 6;
		Random rng = new Random();
		ArrayList<City> bestPath = new ArrayList<City>();
		ArrayList<City>[] solutions = new ArrayList[population];

		ArrayList<City>[] parentSolutions = new ArrayList[parents];
		ArrayList<City>[] childrenSolutions = new ArrayList[parents];
		ArrayList<City>[] kSolutions = new ArrayList[k];

		// Initialise Population solutions
		for (int i = 0; i < population; i++) {
			ArrayList<City> randomRoute = new ArrayList<City>();
			randomRoute = generateRandomRoute();
			solutions[i] = randomRoute;

		}

		for (int iterCounter = 0; iterCounter < iter_max; iterCounter++) {

			// Select 3 random solutions from population
			for (int parentCounter = 0; parentCounter < parents; parentCounter++) {
				for (int i = 0; i < k; i++) {

					int random = rng.nextInt(solutions.length);

					kSolutions[i] = solutions[random];
				}

				// Select the best solution
				double lowestCost = 5000;
				double tempCost = 0;

				for (int pCounter = 0; pCounter < kSolutions.length; pCounter++) {

					tempCost = calculateRoute(kSolutions[pCounter]);

					if (lowestCost > tempCost) {
						lowestCost = tempCost;
						parentSolutions[parentCounter] = kSolutions[pCounter];
					}
				}

			}

			// Loop to create Children from every consecutive pair of parent routes
			for (int i = 0; i < childrenSolutions.length; i++) {

				// Condition to exit loop when i hits the boundary of the array
				if (i == childrenSolutions.length - 1) {
					break;
				}

				// Assign the new children tours to the childrenSolutions Array
				childrenSolutions[i] = createChild(parentSolutions[i], parentSolutions[i + 1]);
			}

			// Mutate the children via two opt swap function.
			for (int i = 0; i < childrenSolutions.length - 1; i++) {

				// Local doubles to check mutations costs (Can delete if want)
				double oldcost = 0;
				oldcost = calculateRoute(childrenSolutions[i]);

				// Assign the mutated tours to the children solution Array
				childrenSolutions[i] = mutateRoute(childrenSolutions[i]);
				double newcost = 0;

				newcost = calculateRoute(childrenSolutions[i]);
				System.out.println("Mutation cost before mutation " + oldcost);
				System.out.println("Mutation cost at mutation " + newcost);
			}

			// Evaluate the children and pick the best to become the next generation of
			// parents to repeat the whole process
			double lowestChildrenCost = 50000;
			ArrayList<City> bestChild = new ArrayList<City>();
			for (int i = 0; i < childrenSolutions.length - 1; i++) {
				double tempChildrenCost = calculateRoute(childrenSolutions[i]);
				if (lowestChildrenCost > tempChildrenCost) {
					lowestChildrenCost = tempChildrenCost;
					bestChild = childrenSolutions[i];
					System.out.println("Lowest mutation cost at time : " + lowestChildrenCost);
				}
			}

			// Replace Population Array with the better mutated offspring and repeat process
			for (int i = 0; i < childrenSolutions.length - 1; i++) {
				System.out.println("size of children +" + childrenSolutions.length);
				if (calculateRoute(solutions[i]) > calculateRoute(childrenSolutions[i])) {
					solutions[i] = childrenSolutions[i];
				}
			}

			// Loop to obtain the best Path and distance at the end of the entire iteration
			for (int i = 0; i < solutions.length; i++) {
				endTempCost = calculateRoute(solutions[i]);
				if (bestCostEver > endTempCost) {
					bestCostEver = endTempCost;
					bestPath = solutions[i];

				}
			}

			// Loop to see the best path and its cities. (can DELETE if you want)
			for (int i = 0; i < bestPath.size(); i++) {
				System.out.println("BEST PATH HERE ");
				System.out.println(bestPath.get(i).getName());
			}
			System.out.println("Best cost at the end is " + bestCostEver);
			System.out.println("Size of parents " + parentSolutions.length);
		}

		// This Loop is just checking that parent solutions have been produced...
		int o = 0;
		for (ArrayList<City> list : parentSolutions) {
			System.out.println("List : " + o);
			System.out.println("Distance is " + calculateRoute(list));

			for (City city : list) {

				System.out.println(city.getName());
			}
			o++;
		}
		// Re-assign InitialCities tour to the best Path to output onto screen
		initialCities = (ArrayList<City>) bestPath.clone();
		totalDistance = bestCostEver;
		return totalDistance;
	}

	public ArrayList<City> createChild(ArrayList<City> parent1, ArrayList<City> parent2) {

		// Create a blank city object to store into the child arraylist
		City closestCity = new City("", 0, 0);

		// Child arraylist to store crossover from parent1/parent2
		ArrayList<City> child = new ArrayList<City>(parent1.size());

		// TempParent arraylist to remove indexes without removing actual indexes from
		// the parent2 Tour
		ArrayList<City> tempParent2 = new ArrayList<City>(parent1.size());

		// Assign tempParent
		tempParent2 = (ArrayList<City>) parent2.clone();

		// Add the blank cities to the child so we can insert at specific indexes w/o
		// Out of bounds error
		for (int i = 0; i < parent1.size(); i++) {
			child.add(closestCity);

		}

		// Initalise start and end variables
		int half = (parent1.size() - 1) / 2;
		int start = 0;
		start = random.nextInt(half);
		int end = start + half;

		System.out.println("Start is " + start);
		System.out.println("end is " + end);

		// Outer Loop start at start index -> end
		for (int i = start; i < end; i++) {

			// ADD the random slice from first parent to child
			child.set(i, parent1.get(i));

			// Remove that slice from second parent as we aren't going to use them again.
			tempParent2.remove(parent1.get(i));

		}

		// Loop to add the cities from the second parent into the child
		for (int i = 0; i < tempParent2.size(); i++) {

			// Use modulo in-case it exceeds bounds and stores it in appropriate place
			child.set((end + i) % parent1.size(), tempParent2.get(i));
		}

		// Print statements just checking if the one order crossover succeeded.
		for (

				int i = 0; i < parent1.size(); i++) {
			System.out.print("Parent1: " + parent1.get(i).getName() + " " + "\n");
		}
		for (int i = 0; i < parent2.size(); i++) {
			System.out.print("Parent2: " + parent2.get(i).getName() + " " + "\n");
		}
		for (int i = 0; i < child.size(); i++) {

			System.out.println("Child index : " + i);
			System.out.println(" City: " + child.get(i).getName() + " ");
		}

		return child;
	}

	public ArrayList<City> mutateRoute(ArrayList<City> route) {

		// ArrayList to store mutated Tour.
		ArrayList<City> mutatedRoute = new ArrayList<City>();

		// Initialising random int variables
		int i = random.nextInt(route.size());
		int j = random.nextInt(route.size());

		// condition to ensure i and j do not ==
		if (i == j) {
			do {
				i = random.nextInt(route.size());
				j = random.nextInt(route.size());
			} while (i != j);
		}
		// Set local variable to the arraylist passed in via parameter
		mutatedRoute = (ArrayList<City>) route.clone();
		System.out.println("Swapping Indexes " + i + "and " + j);
		// Swap the indexes in the arraylist
		Collections.swap(mutatedRoute, i, j);
		return mutatedRoute;
	}

}
