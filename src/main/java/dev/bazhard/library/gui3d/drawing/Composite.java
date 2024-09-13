package dev.bazhard.library.gui3d.drawing;

import org.joml.Matrix4fc;

import java.util.List;

public class Composite implements Drawable {
	private final List<Drawable> drawables;
	private final Matrix4fc transformation;

	public Composite(List<Drawable> drawables, Matrix4fc transformation) {
		this.drawables = drawables;
		this.transformation = transformation;
	}

	@Override
	public void draw(DrawingContext context) {
		context.pushTransformation(transformation);
		for (Drawable drawable : drawables)
			drawable.draw(context);
		context.popTransformation();
	}
}
