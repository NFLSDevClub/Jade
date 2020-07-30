package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.J;

/**
 * Enemy Bullet base class
 * @author XGN
 *
 */
public class EnemyBullet extends Bullet {

	@Override
	public boolean checkCollision() {
		if (collide(J.getPlayer())) {
			J.onHit();
			return true;
		}
		
		return false;
	}
	
	public boolean collide(Player player) {
		return Collision.defaultCollision(player.getX(), player.getY(), player.getRadius(), x, y, data.radius);
	}
}
