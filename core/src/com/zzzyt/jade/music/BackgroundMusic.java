package com.zzzyt.jade.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.U;

public class BackgroundMusic {
	public String name;
	public Music music;

	public boolean isPlaying, isLooping;
	public float loopStart, loopEnd;

	public BackgroundMusic(String name) {
		this.name = name;
		this.isLooping = false;
		this.isPlaying = false;
	}

	public BackgroundMusic(String name, float loopStart, float loopEnd) {
		this.name = name;
		this.isLooping = true;
		this.isPlaying = false;
		this.loopStart = loopStart;
		this.loopEnd = loopEnd;
	}

	public String getName() {
		return name;
	}

	public Music getMusic() {
		return music;
	}

	public void stop() {
		isPlaying = false;
		music.stop();
	}

	public void load() {
		BGM.getLogger().debug("Loading music file \"" + name + "\".");
		this.music = Gdx.audio.newMusic(Gdx.files.internal(name));
		music.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				if (isLooping) {
					music.setVolume(U.config().musicVolume);
					music.play();
					music.setPosition(loopStart);
				}
			}
		});
	}

	public void dispose() {
		BGM.getLogger().debug("Disposing music file \"" + name + "\".");
		music.dispose();
		music = null;
	}

	public void play() {
		load();
		isPlaying = true;
		music.setLooping(false);
		music.setVolume(U.config().musicVolume);
		music.play();
	}

	public void pause() {
		isPlaying = false;
		music.pause();
	}

	public void resume() {
		isPlaying = true;
		music.setVolume(U.config().musicVolume);
		music.play();
	}

	public void update() {
		if (music != null && isPlaying && isLooping) {
			if (!music.isPlaying()) {
				music.setPosition(loopStart);
				music.setVolume(U.config().musicVolume);
				music.play();
			} else if (music.getPosition() >= loopEnd) {
				music.setPosition(loopStart + (music.getPosition() - loopEnd));
			}
		}
	}

	public void setVolume(float volume) {
		music.setVolume(volume);
	}
}
