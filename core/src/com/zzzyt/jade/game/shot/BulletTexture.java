package com.zzzyt.jade.game.shot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BulletTexture {
	public TextureRegion texture;
	public Array<TextureRegion> frames;
	public Array<Integer> frameTime;

	public BulletTexture(Texture texture, int[] rect) {
		this.texture = new TextureRegion(texture, rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
		this.frames = null;
		this.frameTime = null;
	}

	public BulletTexture(Texture texture, int[][] animation) {
		this.texture = null;
		this.frames = new Array<TextureRegion>();
		this.frameTime = new Array<Integer>();
		for (int i = 0; i < animation.length; i++) {
			this.frames.add(new TextureRegion(texture, animation[i][1], animation[i][2],
					animation[i][3] - animation[i][1], animation[i][4] - animation[i][2]));
			if (i == 0) {
				this.frameTime.add(animation[i][0]);
			} else {
				this.frameTime.add(this.frameTime.get(i - 1) + animation[i][0]);
			}
		}
	}

	public TextureRegion getFrame(int frame) {
		if (!isAnimated()) {
			return texture;
		}
		int tmp = frame % frameTime.get(frameTime.size - 1);
		for (int i = 0; i < frameTime.size; i++) {
			if (tmp < frameTime.get(i)) {
				return frames.get(i);
			}
		}
		return frames.get(0);
	}

	public boolean isAnimated() {
		return frames != null;
	}

	public int getMaxWidth() {
		if (!isAnimated()) {
			return texture.getRegionWidth();
		}
		int ret = 0;
		for (int i = 0; i < frames.size; i++) {
			ret = Math.max(ret, frames.get(i).getRegionWidth());
		}
		return ret;
	}

	public int getMaxHeight() {
		if (!isAnimated()) {
			return texture.getRegionHeight();
		}
		int ret = 0;
		for (int i = 0; i < frames.size; i++) {
			ret = Math.max(ret, frames.get(i).getRegionHeight());
		}
		return ret;
	}
	
}
