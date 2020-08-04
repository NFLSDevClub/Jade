package com.zzzyt.jade.util;

import com.zzzyt.jade.game.Entity;

public class Collision {

	public static abstract class CollisionMethod {
		public abstract float getHalfHeight();

		public abstract float getHalfWidth();
	}

	public static class Circle extends CollisionMethod {
		public float offsetX, offsetY;
		public float radius;

		public Circle() {

		}

		public Circle(float offsetX, float offsetY, float radius) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.radius = radius;
		}

		@Override
		public float getHalfWidth() {
			return radius;
		}

		@Override
		public float getHalfHeight() {
			return radius;
		}
	}

	public static class Rectangle extends CollisionMethod {
		public float offsetX, offsetY;
		public float width, height;

		public Rectangle() {

		}

		public Rectangle(float offsetX, float offsetY, float width, float height) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.width = width;
			this.height = height;
		}

		@Override
		public float getHalfWidth() {
			return width / 2;
		}

		@Override
		public float getHalfHeight() {
			return height / 2;
		}
	}

	public static boolean collide(float x1, float y1, CollisionMethod m1, float x2, float y2, CollisionMethod m2) {
		if (m1 instanceof Circle) {
			Circle tmp1 = (Circle) m1;
			if (m2 instanceof Circle) {
				Circle tmp2 = (Circle) m2;
				if (U.config().orthoCircleCollision) {
					return circleCircleOrtho(x1 + tmp1.offsetX, y1 + tmp1.offsetY, tmp1.radius, x2 + tmp2.offsetX,
							y2 + tmp2.offsetY, tmp2.radius);
				} else {
					return circleCircle(x1 + tmp1.offsetX, y1 + tmp1.offsetY, tmp1.radius, x2 + tmp2.offsetX,
							y2 + tmp2.offsetY, tmp2.radius);
				}
			} else if (m2 instanceof Rectangle) {
				Rectangle tmp2 = (Rectangle) m2;
				return circleRect(x1 + tmp1.offsetX, y1 + tmp1.offsetY, tmp1.radius, x2 + tmp2.offsetX,
						y2 + tmp2.offsetY, tmp2.width, tmp2.height);
			}
		} else if (m1 instanceof Rectangle) {
			Rectangle tmp1 = (Rectangle) m1;
			if (m2 instanceof Circle) {
				Circle tmp2 = (Circle) m2;
				return circleRect(x2 + tmp2.offsetX, y2 + tmp2.offsetY, tmp2.radius, x1 + tmp1.offsetX,
						y1 + tmp1.offsetY, tmp1.width, tmp1.height);
			} else if (m2 instanceof Rectangle) {
				return rectRect(x1, y1, ((Rectangle) m1).width, ((Rectangle) m1).height, x2, y2, ((Rectangle) m2).width,
						((Rectangle) m2).height);
			}
		}
		return false;
	}

	public static boolean collide(Entity e1, Entity e2) {
		return collide(e1.getX(), e1.getY(), e1.getCollisionMethod(e2), e2.getX(), e2.getY(),
				e2.getCollisionMethod(e1));
	}

	public static boolean circleCircle(float x1, float y1, float r1, float x2, float y2, float r2) {
		if (M.dist2(x1, y1, x2, y2) <= M.sqr(r1 + r2)) {
			return true;
		}
		return false;
	}

	public static boolean circleCircleOrtho(float x1, float y1, float r1, float x2, float y2, float r2) {
		if (M.dist2(x1, y1, x2, y2) <= M.sqr(r1) + M.sqr(r2)) {
			return true;
		}
		return false;
	}

	public static boolean circleRect(float x1, float y1, float r, float x2, float y2, float w, float h) {
		x2 = M.clamp(x1, x2 - w / 2, x2 + w / 2);
		y2 = M.clamp(y1, y2 - h / 2, y2 + h / 2);

		return M.dist2(x1, y1, x2, y2) <= M.sqr(r);
	}

	public static boolean circleSquare(float x1, float y1, float r, float x2, float y2, float s) {
		x2 = M.clamp(x1, x2 - s / 2, x2 + s / 2);
		y2 = M.clamp(y1, y2 - s / 2, y2 + s / 2);

		return M.dist2(x1, y1, x2, y2) <= M.sqr(r);
	}

	public static boolean rectRect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
		return Math.abs(x1 - x2) <= (w1 + w2) / 2 && Math.abs(y1 - y2) <= (h1 + h2) / 2;
	}

	public static boolean squareSquare(float x1, float y1, float s1, float x2, float y2, float s2) {
		return rectRect(x1, y1, s1, s1, x2, y2, s2, s2);
	}

}
