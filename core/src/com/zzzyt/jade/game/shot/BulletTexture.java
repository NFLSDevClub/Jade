package com.zzzyt.jade.game.shot;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BulletTexture {
	public TextureRegion texture;
	public Array<? extends TextureRegion> frames;
	public Array<Integer> frameTime;

	public BulletTexture(TextureAtlas atlas, String name) {
		this.texture = atlas.findRegion(name);
		this.frames = null;
		this.frameTime = null;
	}

	public BulletTexture(TextureAtlas atlas,String name,int[] frames) {
		this.texture=null;
		this.frames = atlas.findRegions(name);
		this.frameTime = new Array<Integer>();
		for (int i = 0; i < frames.length; i++) {
			if (i == 0) {
				this.frameTime.add(frames[0]);
			} else {
				this.frameTime.add(this.frameTime.get(i - 1) + frames[0]);
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
