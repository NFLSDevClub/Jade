package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.J;

/**
 * Player Bullet base class
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
	public int damage;

	@Override
	public boolean checkCollision() {
		//TODO check collision with enemy
		return false;
	}
	
	
}
