package dev.bazhard.library.gui3d.drawing;

import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Sphere implements Drawable {
	private final Vector3fc center;
	private final Vector3fc radius;
	private final Quaternionfc rotation;
	private final int nSegmentsTheta;
	private final int nSegmentsPhi;

	public Sphere(Vector3fc center, Vector3fc radius, Quaternionfc rotation, int nSegmentsTheta, int nSegmentsPhi) {
		this.center = center;
		this.radius = radius;
		this.rotation = rotation;
		this.nSegmentsTheta = nSegmentsTheta;
		this.nSegmentsPhi = nSegmentsPhi;
	}

	private Vector3fc sphericalToCartesian(float rho, float theta, float phi) {
		return new Vector3f(
				rho * MathUtils.sin(phi) * MathUtils.cos(theta),
				rho * MathUtils.cos(phi),
				rho * MathUtils.sin(phi) * MathUtils.sin(theta)
		);
	}

	@Override
	public void draw(DrawingContext context) {
		context.pushTransformation(center, rotation, radius);

		float deltaPhi = MathUtils.PI / nSegmentsPhi;
		float deltaTheta = 2 * MathUtils.PI / nSegmentsTheta;

		Vector3fc[] vertexBufferTheta = new Vector3fc[nSegmentsTheta + 1];
		for (int n = 0; n <= nSegmentsTheta; n++)
			vertexBufferTheta[n] = sphericalToCartesian(1, n * deltaTheta, 0);

		float phi = 0;
		for (int i = 0; i < nSegmentsPhi; i++) {
			float nextPhi = phi + deltaPhi;
			float theta = 0;

			for (int j = 0; j < nSegmentsTheta; j++) {
				Vector3fc topLeft = vertexBufferTheta[j];
				Vector3fc bottomLeft = sphericalToCartesian(1, theta, nextPhi);
				Vector3fc topRight = vertexBufferTheta[j + 1];

				context.drawLine(topLeft, topRight, topLeft);
				context.drawLine(topLeft, bottomLeft);

				theta += deltaTheta;
				vertexBufferTheta[j] = bottomLeft;
			}

			Vector3fc topRight = vertexBufferTheta[nSegmentsTheta];
			Vector3fc bottomRight = sphericalToCartesian(1, 2 * MathUtils.PI, nextPhi);

			context.drawLine(topRight, bottomRight);

			vertexBufferTheta[nSegmentsTheta] = bottomRight;
			phi = nextPhi;
		}

		for (int n = 0; n < nSegmentsTheta; n++) {
			Vector3fc bottomLeft = vertexBufferTheta[n];
			Vector3fc bottomRight = vertexBufferTheta[n + 1];

			context.drawLine(bottomLeft, bottomRight, bottomLeft);
		}

		context.popTransformation();
	}
}
