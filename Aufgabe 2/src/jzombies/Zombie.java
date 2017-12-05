package jzombies;

import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class Zombie {

	private ContinuousSpace<Object> space;

	private Grid<Object> grid;

	private boolean moved;

	private Human human;

	public Zombie(ContinuousSpace<Object> space, Grid<Object> grid, Human human) {

		this.space = space;

		this.grid = grid;
		this.human = human;

	}

	// Der Roboter überwacht nun den Boten und bewegt sich wenn dieser sich auch
	// bewegt
	@Watch(watcheeClassName = "jzombies.Human", watcheeFieldNames = "moved", whenToTrigger = WatcherTriggerSchedule.IMMEDIATE)
	public void step() {

		moveTowards(human.getLocation());
	}

	// keine Änderung zu Tutorial
	public void moveTowards(GridPoint pt) {

		// only move if we are not already in this grid location

		if (!pt.equals(grid.getLocation(this))) {

			NdPoint myPoint = space.getLocation(this);

			NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());

			double angle = SpatialMath.calcAngleFor2DMovement(space,

					myPoint, otherPoint);

			space.moveByVector(this, 1, angle, 0);

			myPoint = space.getLocation(this);

			grid.moveTo(this, (int) myPoint.getX(), (int) myPoint.getY());

			moved = true;
		}

	}
}
