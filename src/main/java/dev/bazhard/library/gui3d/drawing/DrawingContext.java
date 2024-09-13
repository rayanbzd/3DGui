package dev.bazhard.library.gui3d.drawing;

import dev.bazhard.library.gui3d.element.BlockDisplayElement;
import dev.bazhard.library.gui3d.element.DisplayElement;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DrawingContext {
	private final Set<DisplayElement> elements = new HashSet<>();
	private final Stack<Matrix4fc> transformations = new Stack<>();
	private Material material = Material.WHITE_CONCRETE;
	private Player viewer;
	private float lineThickness = 0.05f;
	private float pointThickness = 0.051f;

	public void pushTransformation(Matrix4fc transformation) {
		if (transformations.isEmpty())
			transformations.push(transformation);
		else
			transformations.push(transformations.peek().mul(transformation, new Matrix4f()));
	}

	public void pushTransformation(Vector3fc translation, Quaternionfc rotation, Vector3fc scale) {
		pushTransformation(new Matrix4f().translationRotateScale(translation, rotation, scale));
	}

	public void popTransformation() {
		transformations.pop();
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setViewer(Player viewer) {
		this.viewer = viewer;
	}

	public void setLineThickness(float lineThickness) {
		this.lineThickness = lineThickness;
	}

	public void setPointThickness(float pointThickness) {
		this.pointThickness = pointThickness;
	}

	public Matrix4fc currentTransformation() {
		return transformations.isEmpty() ? MathUtils.IDENTITY : transformations.peek();
	}

	public void drawLine(Vector3fc pos1, Vector3fc pos2) {
		drawLine(pos1.x(), pos1.y(), pos1.z(), pos2.x(), pos2.y(), pos2.z());
	}

	public void drawLine(float x1, float y1, float z1, float x2, float y2, float z2) {
		drawLine(x1, y1, z1, x2, y2, z2, (x1 == x2 && z1 == z2) ? MathUtils.X : MathUtils.Y);
	}

	public void drawLine(Vector3fc pos1, Vector3fc pos2, Vector3fc pseudoNormal) {
		drawLine(pos1.x(), pos1.y(), pos1.z(), pos2.x(), pos2.y(), pos2.z(), pseudoNormal);
	}

	public void drawLine(float x1, float y1, float z1, float x2, float y2, float z2, Vector3fc pseudoNormal) {
		Matrix4fc matrix = currentTransformation();
		Vector3f origin = matrix.transformPosition(new Vector3f(x1, y1, z1));
		Vector3f vector = matrix.transformPosition(new Vector3f(x2, y2, z2)).sub(origin);
		if (MathUtils.isZero(vector)) {
			drawPoint(x1, y1, z1);
			return;
		}

		Vector3f scale = new Vector3f(vector.length() + lineThickness, lineThickness, lineThickness);
		Vector3f up = matrix.transformDirection(pseudoNormal, new Vector3f());
		Quaternionf rotation = new Quaternionf().lookAlong(vector, up).conjugate().rotateY(MathUtils.PI / 2);
		Vector3f translation = new Vector3f(-lineThickness / 2).rotate(rotation);

		BlockDisplayElement element = new BlockDisplayElement(viewer, Vector.fromJOML(origin).toLocation(viewer.getWorld()), material);

		element.setTranslation(translation);
		element.setRotationLeft(rotation);
		element.setScale(scale);
		element.setGlowing(true);
		element.setGlowColor(Color.WHITE);
		element.setBrightnessOverride(15, 15);
		elements.add(element);
	}

	public void drawPoint(Vector3fc pos) {
		drawPoint(pos.x(), pos.y(), pos.z());
	}

	public void drawPoint(float x, float y, float z) {
		drawPoint(x, y, z, MathUtils.ZERO);
	}

	public void drawPoint(Vector3fc pos, Vector3fc pseudoNormal) {
		drawPoint(pos.x(), pos.y(), pos.z(), pseudoNormal);
	}

	public void drawPoint(float x, float y, float z, Vector3fc pseudoNormal) {
		Matrix4fc matrix = currentTransformation();
		Vector3f pos = matrix.transformPosition(new Vector3f(x, y, z));

		Vector3f scale = new Vector3f(pointThickness, pointThickness, pointThickness);
		Vector3f up = matrix.transformDirection(MathUtils.Y, new Vector3f());
		Vector3f target = matrix.transformDirection(pseudoNormal, new Vector3f());
		Quaternionf rotation = MathUtils.isZero(target) ? matrix.getUnnormalizedRotation(new Quaternionf()) : new Quaternionf().lookAlong(target, up).conjugate();
		Vector3f translation = new Vector3f(-pointThickness / 2).rotate(rotation);

		BlockDisplayElement element = new BlockDisplayElement(viewer, Vector.fromJOML(pos).toLocation(viewer.getWorld()), material);

		element.setTranslation(translation);
		element.setRotationLeft(rotation);
		element.setScale(scale);
		element.setGlowing(true);
		element.setGlowColor(Color.WHITE);
		element.setBrightnessOverride(15, 15);
		elements.add(element);
	}

	public void show() {
		elements.forEach(DisplayElement::show);
	}

	public void hide() {
		elements.forEach(DisplayElement::destroy);
	}

	public void clear() {
		hide();
		elements.clear();
	}
}
