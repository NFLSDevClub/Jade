package com.zzzyt.jade.game.shot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zzzyt.jade.game.shot.RawShotSheet.RawBulletData;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.Collision.CollisionMethod;

public class BulletData {

	public int id;
	public String name;
	public String render;
	public float originX, originY;
	public BulletTexture texture;
	public TextureRegion delayTexture;
	public Color delayColor;
	public float spinVelocity;
	public CollisionMethod collision;
	public float rotation;

	public BulletData(ShotSheet parent, RawBulletData raw) {
		this.id = raw.id;
		this.name = raw.name;
		this.render = raw.render;
		this.spinVelocity = raw.spinVelocity;
		this.rotation = raw.rotation;
		this.delayColor = Color.valueOf(raw.delayColor);
		this.delayTexture = parent.atlas.findRegion(raw.delaySrc);
		this.originX = raw.originX;
		this.originY = raw.originY;
		if (raw.frames == null) {
			this.texture = new BulletTexture(parent.atlas, name);
		} else {
			this.texture = new BulletTexture(parent.atlas, name, raw.frames);
		}
		if ("Circle".equals(raw.collisionMethod)) {
			this.collision = new Collision.Circle(raw.collisionData[0], raw.collisionData[1], raw.collisionData[2]);
		} else if ("Rectangle".equals(raw.collisionMethod)) {
			this.collision = new Collision.Rectangle(raw.collisionData[0], raw.collisionData[1], raw.collisionData[2],
					raw.collisionData[3]);
		} else {
			this.collision = new Collision.Circle(raw.collisionData[0], raw.collisionData[1], raw.collisionData[2]);
		}
	}
}