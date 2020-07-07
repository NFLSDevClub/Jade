package com.zzzyt.jade.game.shot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BulletTexture extends TextureRegion {
	public TextureRegion texture;
	public Array<TextureRegion> animation;
	public Array<Integer> frameLength;

	public BulletTexture(Texture texture, int[] rect) {
		this.texture = new TextureRegion(texture, rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
		this.animation = null;
		this.frameLength = null;
		setRegion(this.texture);
	}

	public BulletTexture(Texture texture, int[][] animation) {
		this.texture = null;
		this.animation = new Array<TextureRegion>();
		this.frameLength = new Array<Integer>();
		for (int i = 0; i < animation.length; i++) {
			this.animation.add(new TextureRegion(texture, animation[i][1], animation[i][2],
					animation[i][3] - animation[i][1], animation[i][4] - animation[i][2]));
			if (i == 0) {
				this.frameLength.add(animation[i][0]);
			} else {
				this.frameLength.add(this.frameLength.get(i - 1) + animation[i][0]);
			}
		}
		setRegion(this.animation.get(0));
	}

	public TextureRegion getRegion(int frame) {
		if (animation != null) {
			int tmp = frame % frameLength.get(frameLength.size - 1);
			for(int i=0;i<frameLength.size;i++) {
				if(tmp<frameLength.get(i)) {
					return animation.get(i);
				}
			}
		}
		return animation.get(0);
	}

	public boolean isAnimated() {
		return animation!=null;
	}
}
