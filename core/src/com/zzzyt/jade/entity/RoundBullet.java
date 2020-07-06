package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.J;

public class RoundBullet extends Bullet implements Poolable {

	public float radius;

	public RoundBullet() {
		super();
	}

	public RoundBullet(TextureRegion region, int tag, float radius) {
		super(region, tag);
		this.radius = radius;
	}

	public RoundBullet(TextureRegion region, int tag, float radius, float x, float y) {
		super(region, tag, x, y);
		this.radius = radius;
	}

	public RoundBullet(TextureRegion region, int tag, float radius, float x, float y, float speed, float dir) {
		super(region, tag, x, y, speed, dir);
		this.radius = radius;
	}

	@Override
	public boolean collide(Player player) {
		return Collision.circleCircleOrtho(player.getX(), player.getY(), player.getRadius(), x, y, radius);
	}

	@Override
	public void onHit() {
		J.onHit();
	}

	@Override
	public void reset() {
		id = -1;
		type = 0;
		tag = 0;
		x = 0;
		y = 0;
		t = 0;
		sprite = null;
		speed = 0;
		angle = 0;
		spriteRotationVelocity = 0;
		boundingRadius = 0;
		animated = false;
		texture = null;
		radius = -1;
	}
}
