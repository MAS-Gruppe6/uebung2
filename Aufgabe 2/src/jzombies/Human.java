package jzombies;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class Human {

	private ContinuousSpace<Object> space;

	private Grid<Object> grid;

	private boolean moved;

	private int index;

	// Route die der Bote zurücklegt
	private static final GridPoint[] route = { new GridPoint(10, 15), new GridPoint(10, 35), new GridPoint(30, 45),
			new GridPoint(5, 5) };

	public Human(ContinuousSpace<Object> space, Grid<Object> grid) {
		this.index = 0;
		this.space = space;
		this.grid = grid;
		this.moved = false;
	}

	@ScheduledMethod(start = 1, interval = 1)
	public void run() {
		moved = false;
		moveTowards(route[index]);
	}

	public void moveTowards(GridPoint pt) {
		NdPoint myPoint = space.getLocation(this);
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
		// Ein Feld pro Tick bewegen
		space.moveByVector(this, 1, angle, 0);
		myPoint = space.getLocation(this);
		grid.moveTo(this, (int) myPoint.getX(), (int) myPoint.getY());

		// Distanz zwischen jetztigem und gewolltem Punkt berechnen, wenn erreicht zähle
		// index hoch
		if (grid.getDistance(grid.getLocation(this), pt) <= 1) {
			if (index == route.length - 1) {
				index = 0;
			} else {
				index++;
			}
		}

		moved = true;
	}

	public GridPoint getLocation() {
		return grid.getLocation(this);
	}
}
