package com.zzzyt.jade.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.JadeApplication;
import com.zzzyt.jade.ui.screen.FadeableScreen;
import com.zzzyt.jade.ui.screen.ScreenState;

public class U {

	public static final Json json = new Json();
	public static JadeApplication game;

	public static Config config() {
		if (Config.config == null) {
			Config.config = new Config();
			Config.config.setDefault();
		}
		return Config.config;
	}

	public static void glClear() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

	}

	public static float safeDeltaTime() {
		return M.clamp(Gdx.graphics.getDeltaTime(), 0f, 0.1f);
	}

	public static boolean matchKey(int keycode, int[] key) {
		if (U.game.blocker.isBlocking())
			return false;
		for (int i = 0; i < key.length; i++) {
			if (keycode == key[i])
				return true;
		}
		return false;
	}

	public static boolean checkKey(int[] key) {
		if (U.game.blocker.isBlocking())
			return false;
		for (int i = 0; i < key.length; i++) {
			if (Gdx.input.isKeyPressed(key[i]))
				return true;
		}
		return false;
	}

	public static float getAlpha(Sprite sprite) {
		return sprite.getColor().a;
	}

	public static Sprite addAlpha(Sprite sprite, float delta) {
		sprite.setAlpha(M.clamp(sprite.getColor().a + delta, 0, 1));
		return sprite;
	}

	public static TextureRegion setToCoverWorld(TextureRegion region) {
		region.setRegionWidth(U.config().w);
		region.setRegionHeight(U.config().h*2);
		return region;
	}

	public static float screenToWorldY(float y) {
		return y - U.config().originY;
	}

	public static float screenToWorldX(float x) {
		return x - U.config().originX;
	}

	public static Json getJson() {
		return json;
	}

	public static <T> T fromJson(FileHandle file, Class<T> type) {
		return json.fromJson(type, file);
	}

	public static boolean outOfWorld(float x, float y, float rx, float ry) {
		if (x + rx < -U.config().originX - U.config().deleteDistance)
			return true;
		if (x - rx > U.config().w + U.config().deleteDistance - U.config().originX)
			return true;
		if (y + ry < -U.config().originY - U.config().deleteDistance)
			return true;
		if (y - ry > U.config().h + U.config().deleteDistance - U.config().originY)
			return true;
		return false;
	}

	public static boolean outOfFrame(float x, float y, float rx, float ry) {
		if (x + rx < -U.config().originX)
			return true;
		if (x - rx > U.config().w - U.config().originX)
			return true;
		if (y + ry < -U.config().originY)
			return true;
		if (y - ry > U.config().h - U.config().originY)
			return true;
		return false;
	}

	public static Rectangle getWorldRectangle() {
		return new Rectangle(-U.config().originX, -U.config().originY, U.config().w, U.config().h);
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

	public static void quit() {
		switchScreen("", 0.5f);
	}

	public static InputProcessor addProcessor(InputProcessor processor) {
		game.input.addProcessor(processor);
		return processor;
	}

	public static InputProcessor removeProcessor(InputProcessor processor) {
		game.input.removeProcessor(processor);
		return processor;
	}

	public static void switchScreen(String name) {
		Array<FadeableScreen> screens = game.screens;
		FadeableScreen scr = null;
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getName().equals(name)) {
				scr = screens.get(i);
				break;
			}
		}
		game.blocker.enable();
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).hide();
				screens.get(i).setState(ScreenState.HIDDEN);
			}
		}
		if (scr != null) {
			game.logger.info("Switching to screen \"" + name + "\".");
			scr.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			scr.show();
			scr.setState(ScreenState.SHOWN);
		} else {
			game.logger.info("Switching to no screen.");
		}
	}

	public static void switchScreen(String name, float fadeTime) {
		Array<FadeableScreen> screens = game.screens;
		FadeableScreen scr = null;
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getName().equals(name)) {
				scr = screens.get(i);
				break;
			}
		}
		game.blocker.enable();
		for (int i = 0; i < screens.size; i++) {
			if (screens.get(i).getState().isRendered()) {
				screens.get(i).fadeOut(fadeTime);
			}
		}
		if (scr != null) {
			game.logger.info("Switching to screen \"" + name + "\".");
			scr.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			scr.fadeIn(fadeTime);
		} else {
			game.logger.info("Switching to no screen.");
		}
	}

}
