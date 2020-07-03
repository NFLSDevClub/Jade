package com.zzzyt.jade.util;

import com.badlogic.gdx.math.MathUtils;

public class Collision {

	public static boolean circleCircle(float x1, float y1, float r1, float x2, float y2, float r2) {
		if (Utils.dist2(x1, y1, x2, y2) <= Utils.sqr(r1 + r2)) {
			return true;
		}
		return false;
	}

	public static boolean circleCircleOrtho(float x1, float y1, float r1, float x2, float y2, float r2) {
		if (Utils.dist2(x1, y1, x2, y2) <= Utils.sqr(r1) + Utils.sqr(r2)) {
			return true;
		}
		return false;
	}

	public static boolean circleRect(float x1, float y1, float r, float x2, float y2, float w, float h) {
		x2 = MathUtils.clamp(x1, x2, x2 + w);
		y2 = MathUtils.clamp(y1, y2, y2 + h);

		if (Utils.dist2(x1, y1, x2, y2) <= Utils.sqr(r)) {
			return true;
		}
		return false;
	}

	public static boolean circleSquare(float x1, float y1, float r, float x2, float y2, float s) {
		x2 = MathUtils.clamp(x1, x2 - s / 2, x2 + s / 2);
		y2 = MathUtils.clamp(y1, y2 - s / 2, y2 + s / 2);

		if (Utils.dist2(x1, y1, x2, y2) <= Utils.sqr(r)) {
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
		if (Math.max(x1 - x2, y1 - y2) <= (s1 + s2) / 2) {
			return true;
		}
		return false;
	}

}
