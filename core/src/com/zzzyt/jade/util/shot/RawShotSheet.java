package com.zzzyt.jade.util.shot;

import com.badlogic.gdx.files.FileHandle;
import com.zzzyt.jade.util.U;

public class RawShotSheet {
	public String image;
	public String name;
	public int[] delayRect;
	public RawBulletData[] data;

	public RawShotSheet() {

	}

	public static RawShotSheet fromJson(FileHandle file) {
		return U.fromJson(file, RawShotSheet.class);
	}

	public static class RawBulletData {
		public int id;
		public String name;
		public String render;
		public int[] rect;
		public int[] delayColor;
		public int[][] animation;
		public float radius;
		public float rotation;
		public float spinSpeed;

		public RawBulletData() {

		}
	}
}
