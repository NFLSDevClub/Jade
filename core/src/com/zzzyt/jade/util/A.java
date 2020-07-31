package com.zzzyt.jade.util;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.zzzyt.jade.game.shot.ShotSheet;
import com.zzzyt.jade.game.shot.ShotSheetLoader;

public class A {

	public static AssetManager am;

	private static ObjectMap<Texture, String> textureReflect;
	private static ObjectMap<String, BitmapFont> fontCache;

	public static void init() {
		A.am = new AssetManager();
		A.am.getLogger().setLevel(U.config().logLevel);
		A.am.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
		A.am.setLoader(ShotSheet.class, new ShotSheetLoader(new InternalFileHandleResolver()));

		A.fontCache = new ObjectMap<String, BitmapFont>();
		A.textureReflect = new ObjectMap<Texture, String>();
	}

	public static <T> T get(String fileName) {
		return am.get(fileName);
	}

	public static <T> T get(String fileName, Class<T> type) {
		return am.get(fileName, type);
	}

	public static Texture getTexture(String fileName) {
		return am.get(fileName, Texture.class);
	}

	public static <T> TextureRegion getRegion(String fileName) {
		T tmp = am.get(fileName);
		if (tmp instanceof TextureRegion) {
			return (TextureRegion) tmp;
		} else if (tmp instanceof Texture) {
			return new TextureRegion((Texture) tmp);
		} else {
			A.am.getLogger().error("[A] getRegion() requires a Texture-like asset!");
			return (TextureRegion) tmp;
		}
	}

	public static void load(String fileName) {
		String extension = am.getFileHandleResolver().resolve(fileName).extension();
		if ("png".equals(extension))
			load(fileName, Texture.class, defaultTextureParameter());
		else if ("jpg".equals(extension))
			load(fileName, Texture.class, defaultTextureParameter());
		else if ("jpeg".equals(extension))
			load(fileName, Texture.class, defaultTextureParameter());
		else if ("bmp".equals(extension))
			load(fileName, Texture.class, defaultTextureParameter());
		else if ("gif".equals(extension))
			load(fileName, Texture.class, defaultTextureParameter());
		else if ("mp3".equals(extension))
			load(fileName, Music.class);
		else if ("ogg".equals(extension))
			load(fileName, Music.class);
		else if ("atlas".equals(extension))
			load(fileName, TextureAtlas.class);
		else if ("fnt".equals(extension))
			load(fileName, BitmapFont.class, defaultBitmapFontParameter());
		else if ("ttf".equals(extension))
			load(fileName, FreeTypeFontGenerator.class);
		else if ("otf".equals(extension))
			load(fileName, FreeTypeFontGenerator.class);
		else if ("shot".equals(extension))
			load(fileName, ShotSheet.class);
		else if ("p".equals(extension))
			load(fileName, ParticleEffect.class);
	}

	public static <T> void load(String fileName, Class<T> type) {
		if (am.isLoaded(fileName, type)) {
			am.getLogger().debug("[A] " + fileName + " Already loaded, aborting.");
		}
		if (type == Texture.class) {
			AssetLoaderParameters<T> parameter = new AssetLoaderParameters<T>();
			parameter.loadedCallback = new TextureReflectCallback();
			am.load(fileName, type, parameter);
		} else {
			am.load(fileName, type);
		}
	}

	public static <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
		if (am.isLoaded(fileName, type)) {
			am.getLogger().debug("[A] " + fileName + " Already loaded, aborting.");
			return;
		}
		if (type == Texture.class) {
			LoadedCallback tmp = parameter.loadedCallback;
			parameter.loadedCallback = new TextureReflectCallback(tmp);
			am.load(fileName, type, parameter);
		} else {
			am.load(fileName, type, parameter);
		}
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

	public static BitmapFont getFont(String name, int size, Color color, float borderWidth, Color borderColor) {
		StringBuilder tmp = new StringBuilder(64);
		tmp.append(name).append(':').append(size).append(':').append(color).append(':');
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
		parameter.color = color;
		if (borderWidth != 0) {
			parameter.borderWidth = borderWidth;
			parameter.borderColor = borderColor;
		}
		parameter.minFilter = U.config().textureMinFilter;
		parameter.magFilter = U.config().textureMagFilter;
		BitmapFont font = generator.generateFont(parameter);
		fontCache.put(key, font);
		return font;
	}

	public static BitmapFont getFont(String name, int size) {
		return getFont(name, size, Color.WHITE, 0, null);
	}

	public static LabelStyle getUILabelStyle(int fontSize) {
		return new LabelStyle(A.getFont(U.config().UIFont, fontSize, U.config().UIFontColor,
				U.config().UIFontBorderWidth, U.config().UIFontBorderColor), Color.WHITE);
	}

	public static TextureParameter defaultTextureParameter() {
		TextureParameter tmp = new TextureParameter();
		tmp.minFilter = U.config().textureMinFilter;
		tmp.magFilter = U.config().textureMagFilter;
		return tmp;
	}

	public static BitmapFontParameter defaultBitmapFontParameter() {
		BitmapFontParameter tmp = new BitmapFontParameter();
		tmp.minFilter = U.config().textureMinFilter;
		tmp.magFilter = U.config().textureMagFilter;
		return tmp;
	}

	public static AtlasRegion findRegion(String atlasName, String regionName) {
		return ((TextureAtlas) A.get(atlasName)).findRegion(regionName);
	}

	public static Array<AtlasRegion> findRegions(String atlasName, String regionName) {
		return ((TextureAtlas) A.get(atlasName)).findRegions(regionName);
	}

	public static AtlasRegion findRegion(String atlasName, String regionName, int index) {
		return ((TextureAtlas) A.get(atlasName)).findRegion(regionName, index);
	}

	public static void putTextureReflect(Texture texture, String fileName) {
		am.getLogger().debug("[A] Texture reflect info: " + texture.hashCode() + " <- " + fileName);
		A.textureReflect.put(texture, fileName);
	}

	private static class TextureReflectCallback implements LoadedCallback {
		private LoadedCallback original;

		public TextureReflectCallback() {

		}

		public TextureReflectCallback(LoadedCallback original) {
			this.original = original;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
			A.putTextureReflect(assetManager.get(fileName), fileName);
			if (original != null) {
				original.finishedLoading(assetManager, fileName, type);
			}
		}
	}
}
