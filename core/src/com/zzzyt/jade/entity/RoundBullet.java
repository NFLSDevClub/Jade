package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.util.Collision;

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
		Jade.session.onHit();
	}

	@Override
	public void reset() {
		this.radius = -1;
	}
}
