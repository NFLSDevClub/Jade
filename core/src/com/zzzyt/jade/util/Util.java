package com.zzzyt.jade.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.zzzyt.jade.Config;

public class Util {

	public static final Json json = new Json();

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

	public static float screenToWorldY(float y) {
		return y - Config.originY;
	}

	public static float screenToWorldX(float x) {
		return x - Config.originX;
	}

	public static Json getJson() {
		return json;
	}

	public static <T> T fromJson(FileHandle file, Class<T> type) {
		return json.fromJson(type, file);
	}

	public static boolean outOfWorld(float x, float y, float rx, float ry) {
		if (x + rx < -Config.originX - Config.deleteDistance)
			return true;
		if (x - rx > Config.w + Config.deleteDistance - Config.originX)
			return true;
		if (y + ry < -Config.originY - Config.deleteDistance)
			return true;
		if (y - ry > Config.h + Config.deleteDistance - Config.originY)
			return true;
		return false;
	}

	public static boolean outOfFrame(float x, float y, float rx, float ry) {
		if (x + rx < -Config.originX)
			return true;
		if (x - rx > Config.w - Config.originX)
			return true;
		if (y + ry < -Config.originY)
			return true;
		if (y - ry > Config.h - Config.originY)
			return true;
		return false;
	}

	public static Rectangle getWorldRectangle() {
		return new Rectangle(-Config.originX, -Config.originY, Config.w, Config.h);
	}
	
	public static <T> void cleanupArray(Array<T> array) {
		int j = 0;
		for (int i = 0; i < array.size; i++) {
			if (array.get(i) != null) {
				array.set(j, array.get(i));
				j++;
			}
		}
		array.truncate(j);
	}
}
