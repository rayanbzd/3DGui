package dev.bazhard.library.gui3d.drawing;

import org.bukkit.Location;
import org.bukkit.Material;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Line implements Drawable {
	private final Vector3fc start;
	private final Vector3fc end;

	public Line(Vector3fc start, Vector3fc end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public void draw(DrawingContext context) {
		Vector3fc vector = start.sub(end, new Vector3f());
		context.setMaterial(Material.BLUE_CONCRETE);
		context.drawPoint(start, vector);
		context.drawPoint(end, vector);
		context.setMaterial(Material.WHITE_CONCRETE);
		context.drawLine(start, end);
	}

	public static Line between(Location start, Location end) {
		return new Line(start.toVector().toVector3f(), end.toVector().toVector3f());
	}

	public static Line from(Location location, double length) {
		Vector3fc origin = location.toVector().toVector3f();
		return new Line(origin, location.getDirection().normalize().multiply(length).toVector3f().add(origin));
	}
}
