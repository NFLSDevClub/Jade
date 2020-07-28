package com.zzzyt.jade.util;

import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import com.zzzyt.jade.music.BackgroundMusic;

public class BGM {

	public static BackgroundMusic bgm;

	private static ObjectMap<String, BackgroundMusic> bgms = new ObjectMap<String, BackgroundMusic>();
	private static Logger logger = new Logger("BGM", U.config().logLevel);

	public static void play(String name) {
		if (name == null) {
			stop();
			return;
		}
		if ("".equals(name)) {
			logger.debug("Keep original BGM.");
			return;
		}
		if (BGM.bgm != null) {
			if (BGM.bgm.getName().equals(name)) {
				logger.debug("Same BGM as before. No changing.");
				return;
			}
			stop();
		}
		logger.debug("Playing \"" + name + "\".");
		BGM.bgm = bgms.get(name);
		if (BGM.bgm == null) {
			logger.error("BGM with this name doesn't exist!");
		} else {
			BGM.bgm.play();
		}
	}

	public static BackgroundMusic getBGM() {
		return BGM.bgm;
	}

	public static BackgroundMusic getBGM(String name) {
		return BGM.bgms.get(name);
	}

	public static BackgroundMusic register(BackgroundMusic music) {
		bgms.put(music.getName(), music);
		return music;
	}

	public static void update() {
		if (BGM.bgm != null) {
			BGM.bgm.update();
		}
	}

	public static void stop() {
		if (BGM.bgm != null) {
			logger.debug("Stopping \"" + BGM.bgm.getName() + "\".");
			BGM.bgm.stop();
			BGM.bgm = null;
		}
	}

	public static void dispose() {
		if (BGM.bgm != null) {
			logger.debug("\"" + BGM.bgm.getName() + "\" is stopped and disposed.");
			BGM.bgm.stop();
			BGM.bgm.dispose();
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void pause() {
		if (bgm != null) {
			bgm.pause();
		}
	}

	public static void resume() {
		if (bgm != null) {
			bgm.resume();
		}
	}

	public static void setVolume(float volume) {
		if (bgm != null) {
			bgm.setVolume(volume);
		}
	}

}
