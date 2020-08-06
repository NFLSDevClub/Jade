package com.zzzyt.jade.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Sound Effect Library Class
 * 
 * @author XGN
 */
public class SE {

	private static ObjectMap<String, Sound> SEs = new ObjectMap<>();
	private static Logger logger = new Logger("SE", U.config().logLevel);

	public static void play(String name) {
//		logger.debug("Playing \"" + name + "\".");
		Sound se = SEs.get(name);
		if (se == null) {
			logger.error("SE with this name doesn't exist!");
		} else {
			long id=se.play();
			se.setVolume(id, U.config().SEVolume);
		}
	}

	/**
	 * 
	 * @param name 
	 * @param music -path
	 * @return the sound file
	 */
	public static Sound register(String name,String snd) {
		logger.debug("Registering sound with internal name:"+name+" path:"+snd);
		Sound sd=Gdx.audio.newSound(Gdx.files.internal(snd));
		
		SEs.put(name, sd);
		return sd;
	}


	public static Logger getLogger() {
		return logger;
	}

}
