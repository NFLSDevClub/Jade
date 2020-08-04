package com.zzzyt.jade.game.shot;

import com.badlogic.gdx.files.FileHandle;
import com.zzzyt.jade.util.U;

public class RawShotSheet {
	public String atlas;
	public RawBulletData[] data;

	public RawShotSheet() {

	}

	public static RawShotSheet fromJson(FileHandle file) {
		return U.fromJson(file, RawShotSheet.class);
	}

	public static class RawBulletData {
		public int id;
		public String name;
		public int[] frames;
		public String render;
		public String delaySrc;
		public String delayColor;
		public String collisionMethod;
		public float[] collisionData;
		public float rotation;
		public float spinVelocity;
		public float originX, originY;

		public RawBulletData() {

		}
	}
}
