package com.zzzyt.jade;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Logger;

public class Config {

	public static int logLevel = Logger.DEBUG;

	public static int fps = 60;
	public static boolean vsyncEnabled = false;
	public static int windowWidth = 640;
	public static int windowHeight = 480;
	public static int w = 384;
	public static int h = 448;
	public static float offsetX = 32;
	public static float offsetY = 16;
	public static float originX = w / 2;
	public static float originY = h;
	public static float deleteDistance = 32;
	public static float safeDistance = 8;

	public static int cleanupBulletCount = 8192;
	public static int cleanupBlankCount = 512;
	public static String defaultShotSheet = "default_shot.shot";

	public static int[] keyDown = { Keys.DOWN };
	public static int[] keyUp = { Keys.UP };
	public static int[] keyLeft = { Keys.LEFT };
	public static int[] keyRight = { Keys.RIGHT };
	public static int[] keySelect = { Keys.Z, Keys.ENTER };
	public static int[] keyCancel = { Keys.X, Keys.ESCAPE };
	public static int[] keySlow = { Keys.SHIFT_LEFT };
	public static int[] keyBomb = { Keys.X };
	public static int[] keyCustom1 = { Keys.C };
	public static int[] keyCustom2 = { Keys.V };

	public static String UIFont = "font/SongSC.ttf";
	public static Color UIFontColor = Color.WHITE;
}
