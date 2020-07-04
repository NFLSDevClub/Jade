package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.U;

public class SquareBullet extends Bullet implements Poolable {

	public float sideLength;

	public SquareBullet() {
		super();
	}

	public SquareBullet(TextureRegion region, int tag, float sideLength) {
		super(region, tag);
		this.sideLength = sideLength;
	}

	public SquareBullet(TextureRegion region, int tag, float sideLength, float x, float y) {
		super(region, tag, x, y);
		this.sideLength = sideLength;
	}

	public SquareBullet(TextureRegion region, int tag, float sideLength, float x, float y, float speed, float dir) {
		super(region, tag, x, y, speed, dir);
		this.sideLength = sideLength;
	}

	@Override
	public boolean collide(Player player) {
		// I'm not sure if this is appropriate...
		return Collision.squareSquare(player.getX(), player.getY(), player.getRadius() * U.SQRT2, x, y, sideLength);
	}

	@Override
	public void onHit() {
		Jade.session.onHit();
	}

	@Override
	public void reset() {
		this.sideLength = -1;
	}
}
