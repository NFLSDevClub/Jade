package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.game.shot.BulletData;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.J;

/**
 * Enemy Bullet base class
 * 
 * @author XGN
 *
 */
public class EnemyBullet extends Bullet {

	public EnemyBullet() {
		super();
	}

	public EnemyBullet(BulletData data, int tag) {
		super(data, tag);
	}

	@Override
	public boolean checkCollision() {
		if (collide(J.getPlayer())) {
			J.onHit();
			return true;
		}

		return false;
	}

	public boolean collide(Player player) {
		return Collision.collide(this, player);
	}
}
