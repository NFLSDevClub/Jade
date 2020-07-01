package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.util.Collision;

public class RoundBullet extends Bullet {

	public float radius;

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
	public boolean collide(BasicPlayer player) {
		return Collision.circleCircleOrtho(player.x, player.y, player.radius, x, y, radius);
	}

	@Override
	public void onHit() {
		Jade.session.onHit();
	}
}
