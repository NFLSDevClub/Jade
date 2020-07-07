package com.zzzyt.jade.util;

import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.Config;

public class Collision {

	public static enum CollisionMode {
		squareSquare, squareCircle, circleSquare, circleCircle, circleCircleOrtho;
	}

	public static boolean collide(float x1, float y1, float r1, float x2, float y2, float r2) {
		switch (Config.collisionMode) {
		case circleCircleOrtho:
			return circleCircleOrtho(x1, y1, r1, x2, y2, r2);
		case squareSquare:
			return squareSquare(x1, y1, r1, x2, y2, r2);
		case circleCircle:
			return circleCircle(x1, y1, r1, x2, y2, r2);
		case circleSquare:
			return circleSquare(x1, y1, r1, x2, y2, r2);
		case squareCircle:
			return circleSquare(x2, y2, r2, x1, y1, r1);
		default:
			return circleCircleOrtho(x1, y1, r1, x2, y2, r2);
		}
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
		x2 = MathUtils.clamp(x1, x2, x2 + w);
		y2 = MathUtils.clamp(y1, y2, y2 + h);

		if (M.dist2(x1, y1, x2, y2) <= M.sqr(r)) {
			return true;
		}
		return false;
	}

	public static boolean circleSquare(float x1, float y1, float r, float x2, float y2, float s) {
		x2 = MathUtils.clamp(x1, x2 - s / 2, x2 + s / 2);
		y2 = MathUtils.clamp(y1, y2 - s / 2, y2 + s / 2);

		if (M.dist2(x1, y1, x2, y2) <= M.sqr(r)) {
			return true;
		}
		return false;
	}

	public static boolean rectRect(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
		if (x1 + w1 >= x2 && x1 <= x2 + w2 && y1 + h1 >= y2 && y1 <= y2 + h2) {
			return true;
		}
		return false;
	}

	public static boolean squareSquare(float x1, float y1, float s1, float x2, float y2, float s2) {
		if (Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)) <= (s1 + s2) / 2) {
			return true;
		}
		return false;
	}

}
