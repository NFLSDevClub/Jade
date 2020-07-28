package com.zzzyt.jade.ui;

import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.game.shot.ShotSheet;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.U;

public class BackgroundLoader extends Thread {

	private Array<String> tasks;

	private Logger logger;

	public BackgroundLoader() {
		this.tasks = new Array<String>();
		this.logger = new Logger("BackgroundLoader", U.config().logLevel);
	}

	public void addTask(String folder) {
		tasks.add(folder);
	}

	@Override
	public void run() {
		loadInternal();
		for (String s : tasks) {
			logger.debug("Reading resources for task \"" + s + "\"");
			loadRes(Gdx.files.internal(s), 0);
		}
		logger.debug("Reading complete");
	}

	private void loadInternal() {
		// this is too violent.....
		URL tmp = getClass().getResource("/com/");
		if (tmp.getPath().contains(".jar!")) {
			logger.debug("Reading resources inside a jar file");
			try {
				URL jar = new URL(tmp.getPath().split("!")[0]);
				ZipInputStream zip = new ZipInputStream(jar.openStream());
				while (true) {
					ZipEntry e = zip.getNextEntry();
					if (e == null)
						break;
					if (e.isDirectory())
						continue;
					String name = e.getName();
					if (name.startsWith("META-INF"))
						continue;
					if (name.startsWith("com"))
						continue;
					if (name.startsWith("net"))
						continue;
					if (name.startsWith("org"))
						continue;
					if (name.startsWith("javazoom"))
						continue;
					if (name.endsWith(".dll"))
						continue;
					if (name.endsWith(".dylib"))
						continue;
					if (name.endsWith(".jnilib"))
						continue;
					if (name.endsWith(".so"))
						continue;
					if (name.endsWith(".ser"))
						continue;
					loadSafe(Gdx.files.internal(name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			logger.debug("Reading resources in normal folders");
			URL path = getClass().getResource("/");
			loadRes(Gdx.files.absolute(path.getPath()), path.getPath().toString().length() - 1);
		}
	}

	private void loadRes(FileHandle fh, int rootLength) {
		if (fh.isDirectory()) {
			if ("com".equals(fh.name())) {
				return;
			}
			for (FileHandle s : fh.list()) {
				loadRes(s, rootLength);
			}
		} else {
			loadSafe(Gdx.files.internal(fh.toString().substring(rootLength)));
		}
	}

	public static void loadSafe(FileHandle fh) {
		String extension = fh.extension();
		if ("png".equals(extension))
			A.load(fh.path(), Texture.class, A.defaultTextureParameter());
		else if ("jpg".equals(extension))
			A.load(fh.path(), Texture.class, A.defaultTextureParameter());
		else if ("jpeg".equals(extension))
			A.load(fh.path(), Texture.class, A.defaultTextureParameter());
		else if ("bmp".equals(extension))
			A.load(fh.path(), Texture.class, A.defaultTextureParameter());
		else if ("gif".equals(extension))
			A.load(fh.path(), Texture.class, A.defaultTextureParameter());
		else if ("atlas".equals(extension))
			A.load(fh.path(), TextureAtlas.class);
		else if ("fnt".equals(extension))
			A.load(fh.path(), BitmapFont.class, A.defaultBitmapFontParameter());
		else if ("ttf".equals(extension))
			A.load(fh.path(), FreeTypeFontGenerator.class);
		else if ("shot".equals(extension))
			A.load(fh.path(), ShotSheet.class);
		else if ("p".equals(extension))
			A.load(fh.path(), ParticleEffect.class);
	}
}
