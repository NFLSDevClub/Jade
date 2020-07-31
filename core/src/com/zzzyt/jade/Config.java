package com.zzzyt.jade;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Scaling;
import com.zzzyt.jade.util.Collision.CollisionMode;

public class Config {

	public static Config config;

	public int logLevel;

	public int fps;
	public boolean vsyncEnabled;
	public float windowWidth;
	public float windowHeight;
	public int startupWindowWidth;
	public int startupWindowHeight;
	public float frameWidth;
	public float frameHeight;
	public float frameOffsetX;
	public float frameOffsetY;
	public boolean allowFullScreen;
	public boolean allowResize;
	public boolean startupFullScreen;
	public Scaling windowScaling;
	public TextureFilter textureMinFilter;
	public TextureFilter textureMagFilter;

	public int w;
	public int h;
	public float originX;
	public float originY;
	public float deleteDistance;
	public float safeDistance;
	public CollisionMode collisionMode;
	public boolean invulnerable;
	public int cleanupBulletCount;
	public int cleanupBlankCount;
	public String defaultShotSheet;
	public boolean allowSpeedUpOutOfReplay;
	public int speedUpMultiplier;
	public float musicVolume;
	/**
	 * Guessed from 0.0-1.0 --XGN
	 */
	public float SEVolume;

	public int[] keyDown;
	public int[] keyUp;
	public int[] keyLeft;
	public int[] keyRight;
	public int[] keySelect;
	public int[] keyCancel;
	public int[] keyShot;
	public int[] keySlow;
	public int[] keyBomb;
	public int[] keyPause;
	public int[] keyCustom;

	public String UIFont;
	public Color UIFontColor;
	public boolean debugActorLayout;

	public Config() {

	}

	public void setDefault() {
		logLevel = Logger.DEBUG;

		fps = 60;
		vsyncEnabled = false;

		windowWidth = 1280;
		windowHeight = 960;
		startupWindowWidth = 640;
		startupWindowHeight = 480;
		frameWidth = 768;
		frameHeight = 896;
		frameOffsetX = 64;
		frameOffsetY = 32;
		allowFullScreen = true;
		allowResize = true;
		startupFullScreen = false;
		windowScaling = Scaling.fit;
		textureMagFilter = TextureFilter.Linear;
		textureMinFilter = TextureFilter.Linear;

		w = 384;
		h = 448;
		originX = w / 2;
		originY = h;
		deleteDistance = 256;
		safeDistance = 16;
		collisionMode = CollisionMode.circleCircleOrtho;
		invulnerable = false;
		cleanupBulletCount = 8192;
		cleanupBlankCount = 512;
		defaultShotSheet = "default_shot.shot";
		allowSpeedUpOutOfReplay = true;
		speedUpMultiplier = 4;
		musicVolume = 1;
		SEVolume = 1;

		keyDown = new int[] { Keys.DOWN };
		keyUp = new int[] { Keys.UP };
		keyLeft = new int[] { Keys.LEFT };
		keyRight = new int[] { Keys.RIGHT };
		keySelect = new int[] { Keys.Z, Keys.ENTER };
		keyCancel = new int[] { Keys.X, Keys.ESCAPE };
		keyShot = new int[] { Keys.Z };
		keySlow = new int[] { Keys.SHIFT_LEFT };
		keyBomb = new int[] { Keys.X };
		keyPause = new int[] { Keys.ESCAPE };
		keyCustom = new int[] { Keys.C };

		UIFont = "font/SongSC.ttf";
		UIFontColor = Color.WHITE;
		debugActorLayout = false;
	}
}
