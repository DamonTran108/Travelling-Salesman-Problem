package TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class geneticSearch implements Searchable {
	private TSPClass tsp;
	private ArrayList<City> route;
	private Random random;
	private int iterations;
	private int population;
	private int k;
	private int parents;

	public geneticSearch(int iterations, int population, int k, int parents) {
		tsp = new TSPClass();
		route = new ArrayList<City>();
		random = new Random();
		this.iterations = iterations;
		this.population = population;
		this.k = k;
		this.parents = parents;
		
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

	@Override
	public void search() {
		double distance = 0;
		double bestCostEver = 5000;
		double endTempCost = 0;
	

		Random rng = new Random();
		ArrayList<City> bestPath = new ArrayList<City>();
		ArrayList<City>[] solutions = new ArrayList[population];

		ArrayList<City>[] parentSolutions = new ArrayList[parents];
		ArrayList<City>[] childrenSolutions = new ArrayList[parents];
		ArrayList<City>[] kSolutions = new ArrayList[k];

		// Initialise Population solutions
		for (int i = 0; i < population; i++) {
			ArrayList<City> randomRoute = new ArrayList<City>();
			randomRoute = tsp.generateRandomRoute();
			solutions[i] = randomRoute;

		}

		for (int iterCounter = 0; iterCounter < iterations; iterCounter++) {

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

					tempCost = tsp.calculateRoute(kSolutions[pCounter]);

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
				oldcost = tsp.calculateRoute(childrenSolutions[i]);

				// Assign the mutated tours to the children solution Array
				childrenSolutions[i] = mutateRoute(childrenSolutions[i]);
				double newcost = 0;

				newcost = tsp.calculateRoute(childrenSolutions[i]);
				System.out.println("Mutation cost before mutation " + oldcost);
				System.out.println("Mutation cost at mutation " + newcost);
			}

			// Evaluate the children and pick the best to become the next generation of
			// parents to repeat the whole process
			double lowestChildrenCost = 50000;
			ArrayList<City> bestChild = new ArrayList<City>();
			for (int i = 0; i < childrenSolutions.length - 1; i++) {
				double tempChildrenCost = tsp.calculateRoute(childrenSolutions[i]);
				if (lowestChildrenCost > tempChildrenCost) {
					lowestChildrenCost = tempChildrenCost;
					bestChild = childrenSolutions[i];
					System.out.println("Lowest mutation cost at time : " + lowestChildrenCost);
				}
			}

			// Replace Population Array with the better mutated offspring and repeat process
			for (int i = 0; i < childrenSolutions.length - 1; i++) {
				System.out.println("size of children +" + childrenSolutions.length);
				if (tsp.calculateRoute(solutions[i]) > tsp.calculateRoute(childrenSolutions[i])) {
					solutions[i] = childrenSolutions[i];
				}
			}

			// Loop to obtain the best Path and distance at the end of the entire iteration
			for (int i = 0; i < solutions.length; i++) {
				endTempCost = tsp.calculateRoute(solutions[i]);
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
			System.out.println("Distance is " + tsp.calculateRoute(list));

			for (City city : list) {

				System.out.println(city.getName());
			}
			o++;
		}
		// Re-assign InitialCities tour to the best Path to output onto screen
		route = (ArrayList<City>) bestPath.clone();
		distance = bestCostEver;
		tsp.setRoute(bestPath);
		tsp.setTotalDistance(bestCostEver);
	}

}
