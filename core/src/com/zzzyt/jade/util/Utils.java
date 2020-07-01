package com.zzzyt.jade.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;

public class Utils {

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

	public static float clampAngle(float angle) {
		if(angle>0) {
			return angle-MathUtils.round(angle/360)*360;
		}
		return angle+MathUtils.round(-angle/360)*360;
	}
	
	public static float dist2(float x1, float y1, float x2, float y2) {
		return Utils.sqr(x1 - x2) + Utils.sqr(y1 - y2);
	}

	public static float sqr(float x) {
		return x * x;
	}
}
