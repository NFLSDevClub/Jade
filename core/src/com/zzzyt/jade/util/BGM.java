package com.zzzyt.jade.util;

import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.music.BackgroundMusic;

public class BGM {

	public static BackgroundMusic bgm;

	private static ObjectMap<String, BackgroundMusic> bgms = new ObjectMap<String, BackgroundMusic>();
	private static Logger logger = new Logger("BGM", Config.logLevel);

	public static void play(String name) {
		BackgroundMusic bgm = BGM.bgm;
		if (name == null) {
			throw new IllegalArgumentException("BGM name can't be null");
		}
		if (bgm != null) {
			if (bgm.getName().equals(name)) {
				logger.debug("Same BGM as before. No changing.");
				return;
			}
			logger.debug("Stopping \"" + bgm.getName() + "\".");
			bgm.stop();
			bgm.dispose();
		}
		logger.debug("Playing \"" + name + "\".");
		BGM.bgm = bgms.get(name);
		if (BGM.bgm == null) {
			logger.error("BGM with this name doesn't exist!");
		} else {
			BGM.bgm.play();
		}
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

}
