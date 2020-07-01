package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.Config;

public class PlayerBuilder {

	private BasicPlayer player;

	public PlayerBuilder() {
		this.player = new BasicPlayer();
		player.x = 0;
		player.y = -Config.h + 64;
	}

	public PlayerBuilder left(Array<? extends TextureRegion> texture) {
		player.setLeft(texture);
		return this;
	}

	public PlayerBuilder center(Array<? extends TextureRegion> texture) {
		player.setCenter(texture);
		return this;
	}

	public PlayerBuilder right(Array<? extends TextureRegion> texture) {
		player.setRight(texture);
		return this;
	}

	public PlayerBuilder toLeft(Array<? extends TextureRegion> texture) {
		player.setToLeft(texture);
		return this;
	}

	public PlayerBuilder toRight(Array<? extends TextureRegion> texture) {
		player.setToRight(texture);
		return this;
	}

	public PlayerBuilder hitbox(Sprite texture) {
		texture.setSize(texture.getRegionWidth(), texture.getRegionWidth());
		player.setHitbox(texture);
		return this;
	}
	
	public PlayerBuilder speed(float speedHigh, float speedLow) {
		player.speedHigh = speedHigh;
		player.speedLow = speedLow;
		return this;
	}

	public PlayerBuilder frameLength(int frameLength) {
		player.frameLength = frameLength;
		return this;
	}

	public PlayerBuilder radius(float radius) {
		player.radius = radius;
		return this;
	}

	public PlayerBuilder data(float radius, float speedHigh, float speedLow) {
		player.radius = radius;
		player.speedHigh = speedHigh;
		player.speedLow = speedLow;
		return this;
	}

	public PlayerBuilder startPosition(float x, float y) {
		player.x = x;
		player.y = y;
		return this;
	}

	public PlayerBuilder fromAtlas(TextureAtlas atlas, String regionName, int frameLength) {
		player.setLeft(atlas.findRegions(regionName + "_left"));
		player.setCenter(atlas.findRegions(regionName + "_center"));
		player.setRight(atlas.findRegions(regionName + "_right"));
		player.setToLeft(atlas.findRegions(regionName + "_toLeft"));
		player.setToRight(atlas.findRegions(regionName + "_toRight"));
		player.frameLength = frameLength;
		return this;
	}

	public PlayerBuilder staticTexture(TextureRegion region) {
		Array<TextureRegion> tmp = new Array<TextureRegion>();
		tmp.add(region);
		player.setLeft(tmp);
		player.setCenter(tmp);
		player.setRight(tmp);
		player.setToLeft(tmp);
		player.setToRight(tmp);
		return this;
	}

	public BasicPlayer build() {
		return new BasicPlayer(player);
	}

}
