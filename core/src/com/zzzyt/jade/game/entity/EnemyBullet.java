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

	public int grazeCount=1;
	
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
		
		if(closeTo(J.getPlayer()) && grazeCount!=0 && J.getPlayer().state==Player.PLOK) {
			J.getPlayer().onGraze(this);
			grazeCount--;
		}
		return false;
	}

	public boolean closeTo(Player player) {
		return Collision.collide(player.getX(), player.getY(), player.getGrazeCollisionData(), x, y, data.collision);
	}
	
	public boolean collide(Player player) {
		return Collision.collide(this, player);
	}
}
