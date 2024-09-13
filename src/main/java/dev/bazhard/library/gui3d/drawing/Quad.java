package dev.bazhard.library.gui3d.drawing;

import org.bukkit.Material;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

public class Quad implements Drawable {
	private final Vector3fc center;
	private final Vector3fc extensions;
	private final Quaternionfc rotation;

	public Quad(Vector3fc center, Vector3fc extensions, Quaternionfc rotation) {
		this.center = center;
		this.extensions = extensions;
		this.rotation = rotation;
	}

	@Override
	public void draw(DrawingContext context) {
		context.pushTransformation(center, rotation, extensions);

		context.setMaterial(Material.BLUE_CONCRETE);
		context.drawPoint(-1, -1, 0);
		context.drawPoint(+1, -1, 0);
		context.drawPoint(+1, +1, 0);
		context.drawPoint(-1, +1, 0);
		context.setMaterial(Material.WHITE_CONCRETE);
		context.drawLine(-1, -1, 0, +1, -1, 0);
		context.drawLine(+1, -1, 0, +1, +1, 0);
		context.drawLine(+1, +1, 0, -1, +1, 0);
		context.drawLine(-1, +1, 0, -1, -1, 0);

		context.popTransformation();
	}
}
