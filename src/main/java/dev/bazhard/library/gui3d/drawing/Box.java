package dev.bazhard.library.gui3d.drawing;

import org.bukkit.Material;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

public class Box implements Drawable {
	private final Vector3fc center;
	private final Vector3fc extensions;
	private final Quaternionfc rotation;

	public Box(Vector3fc center, Vector3fc extensions, Quaternionfc rotation) {
		this.center = center;
		this.extensions = extensions;
		this.rotation = rotation;
	}

	@Override
	public void draw(DrawingContext context) {
		context.pushTransformation(center, rotation, extensions);

		context.setMaterial(Material.BLUE_CONCRETE);
		context.drawPoint(-1, -1, -1);
		context.drawPoint(+1, -1, -1);
		context.drawPoint(+1, +1, -1);
		context.drawPoint(-1, +1, -1);

		context.drawPoint(-1, -1, +1);
		context.drawPoint(+1, -1, +1);
		context.drawPoint(+1, +1, +1);
		context.drawPoint(-1, +1, +1);
		context.setMaterial(Material.WHITE_CONCRETE);

		context.drawLine(-1, -1, -1, +1, -1, -1);
		context.drawLine(+1, -1, -1, +1, +1, -1);
		context.drawLine(+1, +1, -1, -1, +1, -1);
		context.drawLine(-1, +1, -1, -1, -1, -1);

		context.drawLine(-1, -1, +1, +1, -1, +1);
		context.drawLine(+1, -1, +1, +1, +1, +1);
		context.drawLine(+1, +1, +1, -1, +1, +1);
		context.drawLine(-1, +1, +1, -1, -1, +1);

		context.drawLine(-1, -1, -1, -1, -1, +1);
		context.drawLine(+1, -1, -1, +1, -1, +1);
		context.drawLine(+1, +1, -1, +1, +1, +1);
		context.drawLine(-1, +1, -1, -1, +1, +1);

		context.popTransformation();
	}
}
