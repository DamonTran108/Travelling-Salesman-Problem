package TSP;

public class City {

	private String name;
	private double xPos;
	private double yPos;

	public City(String name, double xPos, double yPos) {
		this.name = name;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public double getXpos() {
		return xPos;
	}

	public double getYpos() {
		return yPos;
	}

	public String getName() {
		return name;
	}

	public double setXpos(double newXPos) {
		this.xPos = newXPos;
		return xPos;
	}

	public double setYpos(double newYPos) {
		this.yPos = newYPos;
		return yPos;
	}
}
