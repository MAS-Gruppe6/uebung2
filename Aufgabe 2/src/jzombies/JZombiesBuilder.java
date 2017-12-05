package jzombies;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;

public class JZombiesBuilder implements ContextBuilder<Object> {

	@Override
	public Context<Object> build(Context<Object> context) {
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<>("infection network", context, true);
		netBuilder.buildNetwork();
		context.setId("jzombies");
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		// Border wurde nun auf strict gesetzt
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,
				new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.StrictBorders(), 50, 50);
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);

		// ebenfalls StrictBorder
		Grid<Object> grid = gridFactory.createGrid("grid", context,
				new GridBuilderParameters<Object>(new StrictBorders(), new SimpleGridAdder<Object>(), true, 50, 50));

		Human messenger = new Human(space, grid);
		context.add(messenger);

		Zombie robot = new Zombie(space, grid, messenger);
		context.add(robot);

		// Punkt wird nun fest vergeben
		NdPoint pt = new NdPoint(5, 5);
		space.moveTo(messenger, (int) pt.getX(), (int) pt.getY());
		pt = new NdPoint(4, 4);
		space.moveTo(robot, (int) pt.getX(), (int) pt.getY());

		pt = space.getLocation(messenger);
		grid.moveTo(messenger, (int) pt.getX(), (int) pt.getY());
		pt = space.getLocation(robot);
		grid.moveTo(robot, (int) pt.getX(), (int) pt.getY());

		return context;
	}

}
