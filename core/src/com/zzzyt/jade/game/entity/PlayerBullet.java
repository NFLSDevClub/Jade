package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.shot.BulletData;

/**
 * Player Bullet base class
 * 
 * @author XGN
 *
 */
public class PlayerBullet extends Bullet {

	/**
	 * The number of times the bullet can damage enemies. <br/>
	 * Calculated each frame. <br/>
	 */
	public int penetration;

	/**
	 * The damage <b>each</b> penetration.
	 */
	public float damage;

	public PlayerBullet() {
		super();
	}

	public PlayerBullet(BulletData data, int tag, int penetration, float damage) {
		super(data, tag);
		this.penetration = penetration;
		this.damage = damage;
	}

	@Override
	public boolean checkCollision() {
		// TODO check collision with enemy
		return false;
	}

}
