package com.zzzyt.jade.util;

import com.badlogic.gdx.math.MathUtils;

public class M {

	public static final float SQRT2 = 1.41421356237309505f;

	public static int clamp(int value, int min, int max) {
		return MathUtils.clamp(value, min, max);
	}

	public static long clamp(long value, long min, long max) {
		return MathUtils.clamp(value, min, max);
	}

	public static float clamp(float value, float min, float max) {
		return MathUtils.clamp(value, min, max);
	}

	public static double clamp(double value, double min, double max) {
		return MathUtils.clamp(value, min, max);
	}

	public static int safeMod(int x, int mod) {
		if (x >= 0) {
			return x % mod;
		} else {
			return (x % mod + mod) % mod;
		}
	}

	public static float safeMod(float x, float mod) {
		if (x >= 0) {
			return x % mod;
		} else {
			return (x % mod + mod) % mod;
		}
	}

	public static float normalizeAngle(float angle) {
		if (angle > 0) {
			angle = angle - MathUtils.round(angle / 360) * 360;
		} else {
			angle = angle + MathUtils.round(-angle / 360) * 360;
		}
		if (angle == -180) {
			angle = 180;
		}
		return angle;
	}

	public static float max(float a, float b) {
		return Math.max(a, b);
	}

	public static float min(float a, float b) {
		return Math.min(a, b);
	}

	public static int max(int a, int b) {
		return Math.max(a, b);
	}

	public static int min(int a, int b) {
		return Math.min(a, b);
	}

	public static int round(float a) {
		return MathUtils.round(a);
	}

	public static int floor(float a) {
		return MathUtils.floor(a);
	}

	public static int ceil(float a) {
		return MathUtils.ceil(a);
	}

	public static float dist2(float x1, float y1, float x2, float y2) {
		return M.sqr(x1 - x2) + M.sqr(y1 - y2);
	}

	public static float dist(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(M.sqr(x1 - x2) + M.sqr(y1 - y2));
	}

	public static float atan2(float x, float y) {
		return MathUtils.atan2(y, x) * MathUtils.radiansToDegrees;
	}

	public static float atan2(float x1, float y1, float x2, float y2) {
		return MathUtils.atan2(y2 - y1, x2 - x1) * MathUtils.radiansToDegrees;
	}

	public static float sin(float degrees) {
		return MathUtils.sinDeg(degrees);
	}

	public static float cos(float degrees) {
		return MathUtils.cosDeg(degrees);
	}

	public static float tan(float degrees) {
		return (float) (MathUtils.radiansToDegrees * Math.tan(degrees * MathUtils.degreesToRadians));
	}

	public static float sqr(float x) {
		return x * x;
	}

}
