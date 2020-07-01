package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.util.Collision;

public class SquareBullet extends Bullet {

	public float side;

	public SquareBullet(TextureRegion region, int tag, float side) {
		super(region, tag);
		this.side = side;
	}

	public SquareBullet(TextureRegion region, int tag, float side, float x, float y) {
		super(region, tag, x, y);
		this.side = side;
	}

	public SquareBullet(TextureRegion region, int tag, float side, float x, float y, float speed, float dir) {
		super(region, tag, x, y, speed, dir);
		this.side = side;
	}

	@Override
	public boolean collide(BasicPlayer player) {
		return Collision.circleSquare(player.x, player.y, player.radius, x, y, side);
	}

	@Override
	public void onHit() {
		Jade.session.onHit();
	}
}
