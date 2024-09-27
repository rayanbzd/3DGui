package dev.bazhard.library.gui3d.drawing;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class MathUtils {
	public static final Vector3fc ZERO = new Vector3f();
	public static final Vector3fc X = new Vector3f(1, 0, 0);
	public static final Vector3fc Y = new Vector3f(0, 1, 0);
	public static final Vector3fc Z = new Vector3f(0, 0, 1);
	public static final Matrix4fc IDENTITY = new Matrix4f();

	public static final float PI = (float) Math.PI;

	public static boolean isZero(Vector3fc vector) {
		return vector.lengthSquared() == 0;
	}

	public static float sin(float v) {
		return (float) Math.sin(v);
	}

	public static float cos(float v) {
		return (float) Math.cos(v);
	}
}
