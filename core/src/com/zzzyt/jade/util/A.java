package com.zzzyt.jade.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.zzzyt.jade.Config;

public class A {

	public static AssetManager am;

	public static TextureParameter textureParam;

	private static Map<String, BitmapFont> fontCache;

	public static void init() {
		A.am = new AssetManager();
		A.am.getLogger().setLevel(Config.logLevel);
		A.am.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
		A.textureParam = new TextureParameter();
		A.textureParam.minFilter = TextureFilter.Linear;
		A.textureParam.magFilter = TextureFilter.Linear;
		A.fontCache = new HashMap<String, BitmapFont>();
	}

	public static <T> T get(String fileName) {
		return am.get(fileName);
	}

	public static <T> T get(String fileName, Class<T> type) {
		return am.get(fileName, type);
	}

	public static void load(String fileName) {
		String extension = am.getFileHandleResolver().resolve(fileName).extension();
		if ("png".equals(extension))
			load(fileName, Texture.class, textureParam);
		else if ("jpg".equals(extension))
			load(fileName, Texture.class, textureParam);
		else if ("jpeg".equals(extension))
			load(fileName, Texture.class, textureParam);
		else if ("bmp".equals(extension))
			load(fileName, Texture.class, textureParam);
		else if ("gif".equals(extension))
			load(fileName, Texture.class, textureParam);
		else if ("mp3".equals(extension))
			load(fileName, Music.class);
		else if ("ogg".equals(extension))
			load(fileName, Music.class);
		else if ("wav".equals(extension))
			load(fileName, Sound.class);
		else if ("atlas".equals(extension))
			load(fileName, TextureAtlas.class);
		else if ("fnt".equals(extension))
			load(fileName, BitmapFont.class);
		else if ("ttf".equals(extension))
			load(fileName, FreeTypeFontGenerator.class);
		else if ("json".equals(extension))
			load(fileName, Skin.class);
		else if ("p".equals(extension))
			load(fileName, ParticleEffect.class);
	}

	public static <T> void load(String fileName, Class<T> type) {
		if (am.isLoaded(fileName, type)) {
			am.getLogger().debug("[A] " + fileName + " Already loaded, aborting.");
		}
		am.load(fileName, type);
	}

	public static <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
		if (am.isLoaded(fileName, type)) {
			am.getLogger().debug("[A] " + fileName + " Already loaded, aborting.");
			return;
		}
		am.load(fileName, type, parameter);
	}

	public static boolean isLoaded(String fileName) {
		return am.isLoaded(fileName);
	}

	public static <T> boolean isLoaded(String fileName, Class<T> type) {
		return am.isLoaded(fileName, type);
	}

	public static void finishLoading() {
		am.finishLoading();
	}

	public static void update() {
		am.update();
	}

	public static void update(int times) {
		for (int i = 0; i < times; i++) {
			am.update();
		}
	}

	public static void dispose() {
		A.am.dispose();
		for (BitmapFont font : A.fontCache.values()) {
			font.dispose();
		}
	}

	public static BitmapFont getFont(String name, int size, float borderWidth, Color borderColor) {
		StringBuilder tmp = new StringBuilder(64);
		tmp.append(name).append(':').append(size).append(':');
		if (borderWidth != 0) {
			tmp.append(borderWidth).append(':').append(borderColor.toString());
		}
		String key = tmp.toString();
		if (fontCache.containsKey(key)) {
			return fontCache.get(key);
		}
		FreeTypeFontGenerator generator = get(name);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		if (borderWidth != 0) {
			parameter.borderWidth = borderWidth;
			parameter.borderColor = borderColor;
		}
		BitmapFont font = generator.generateFont(parameter);
		fontCache.put(key, font);
		return font;
	}

	public static BitmapFont getFont(String name, int size) {
		return getFont(name, size, 0, null);
	}

}
