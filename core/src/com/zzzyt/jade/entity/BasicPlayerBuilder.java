package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BasicPlayerBuilder {

	Array<? extends TextureRegion> left, center, right, toLeft, toRight;
	Sprite hitbox;
	public float radius, speedHigh, speedLow;
	public int frameLength, transitionFrameLength;

	public BasicPlayerBuilder() {

	}

	public BasicPlayerBuilder left(Array<? extends TextureRegion> texture) {
		this.left = texture;
		return this;
	}

	public BasicPlayerBuilder center(Array<? extends TextureRegion> texture) {
		this.center = texture;
		return this;
	}

	public BasicPlayerBuilder right(Array<? extends TextureRegion> texture) {
		this.right = texture;
		return this;
	}

	public BasicPlayerBuilder toLeft(Array<? extends TextureRegion> texture) {
		this.toLeft = texture;
		return this;
	}

	public BasicPlayerBuilder toRight(Array<? extends TextureRegion> texture) {
		this.toRight = texture;
		return this;
	}

	public BasicPlayerBuilder hitbox(Sprite texture) {
		this.hitbox = texture;
		return this;
	}

	public BasicPlayerBuilder speed(float speedHigh, float speedLow) {
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		return this;
	}

	public BasicPlayerBuilder frameLength(int frameLength, int transitionFrameLength) {
		this.frameLength = frameLength;
		this.transitionFrameLength = transitionFrameLength;
		return this;
	}

	public BasicPlayerBuilder radius(float radius) {
		this.radius = radius;
		return this;
	}

	public BasicPlayerBuilder data(float radius, float speedHigh, float speedLow) {
		this.radius = radius;
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		return this;
	}

	public BasicPlayerBuilder fromAtlas(TextureAtlas atlas, String regionName, int frameLength,
			int transitionFrameLength) {
		this.left = atlas.findRegions(regionName + "_left");
		this.center = atlas.findRegions(regionName + "_center");
		this.right = atlas.findRegions(regionName + "_right");
		this.toLeft = atlas.findRegions(regionName + "_toLeft");
		this.toRight = atlas.findRegions(regionName + "_toRight");
		this.frameLength = frameLength;
		this.transitionFrameLength = transitionFrameLength;
		return this;
	}

	public BasicPlayerBuilder fromStaticTexture(TextureRegion region) {
		Array<TextureRegion> tmp = new Array<TextureRegion>();
		tmp.add(region);
		this.left = tmp;
		this.center = tmp;
		this.right = tmp;
		this.toLeft = tmp;
		this.toRight = tmp;
		this.frameLength = 1;
		this.transitionFrameLength = 1;
		return this;
	}

	public BasicPlayer build() {
		return new BasicPlayer(left, center, right, toLeft, toRight, hitbox, frameLength, transitionFrameLength, radius,
				speedHigh, speedLow);
	}

}
