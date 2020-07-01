package com.zzzyt.jade.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.Config;

public class BGM {

	public static Music bgm;
	public static String name = "";

	private static Logger logger = new Logger("BGM", Config.logLevel);

	public static void play(String name) {
		play(name, true);
	}

	public static void play(String name, boolean isLooping) {
		if (name == null) {
			throw new IllegalArgumentException("BGM name can't be null");
		}
		if (BGM.name.equals(name)) {
			logger.debug("Same BGM as before. No changing.");
			return;
		}
		if (BGM.bgm != null) {
			logger.debug("\"" + BGM.name + "\" is stopped and disposed.");
			bgm.stop();
			bgm.dispose();
		}
		logger.debug("Loading and playing \"" + name + "\".");
		BGM.name = name;
		BGM.bgm = Gdx.audio.newMusic(Gdx.files.internal(name));
		BGM.bgm.setLooping(isLooping);
		BGM.bgm.play();
	}

	public static void stop() {
		if (BGM.bgm != null) {
			logger.debug("Stopping \"" + BGM.name + "\".");
			BGM.bgm.stop();
		}
	}

	public static void dispose() {
		if (BGM.bgm != null) {
			logger.debug("\"" + BGM.name + "\" is stopped and disposed.");
			BGM.bgm.stop();
			BGM.bgm.dispose();
		}
	}

}
