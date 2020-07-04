package com.zzzyt.jade.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.zzzyt.jade.Config;

public class U {

	public static final Json json = new Json();
	public static final float SQRT2 = (float) Math.sqrt(2);

	public static void glClear() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
	}

	public static float safeDeltaTime() {
		return MathUtils.clamp(Gdx.graphics.getDeltaTime(), 0f, 0.1f);
	}

	public static boolean matchKey(int keycode, int[] key) {
		if (Game.game.blocker.isBlocking())
			return false;
		for (int i = 0; i < key.length; i++) {
			if (keycode == key[i])
				return true;
		}
		return false;
	}

	public static boolean checkKey(int[] key) {
		if (Game.game.blocker.isBlocking())
			return false;
		for (int i = 0; i < key.length; i++) {
			if (Gdx.input.isKeyPressed(key[i]))
				return true;
		}
		return false;
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

	public static float dist2(float x1, float y1, float x2, float y2) {
		return U.sqr(x1 - x2) + U.sqr(y1 - y2);
	}

	public static float sqr(float x) {
		return x * x;
	}

	public static float screenToWorldY(float y) {
		return y - Config.originY;
	}

	public static float screenToWorldX(float x) {
		return x - Config.originX;
	}

	public static int difficultyToInt(String difficulty) {
		switch (difficulty) {
		case "easy":
			return 1;
		case "normal":
			return 2;
		case "hard":
			return 3;
		case "lunatic":
			return 4;
		case "extra":
			return 5;
		case "phantasm":
			return 6;
		default:
			return 0;
		}
	}

	public static int difficultyToInt() {
		return difficultyToInt((String) G.get("_difficulty"));
	}

	public static Json getJson() {
		return json;
	}

	public static <T> T fromJson(FileHandle file, Class<T> type) {
		return json.fromJson(type, file);
	}
}
