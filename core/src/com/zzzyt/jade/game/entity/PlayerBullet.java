package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.shot.BulletData;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.J;

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
		
		boolean hit=false;
		
		for(int i=0;i<J.getEnemies().size();i++) {
			Enemy e=J.getEnemies().get(i);
			if(e==null) {
				continue;
			}
			if(penetration==0) { //can't go forward anymore
				J.remove(this);
				return hit;
			}
			if(collide(e)) {
				hit=true;
				e.onHit(damage);
				penetration--;
			}
		}
		
		return hit;
	}

	public boolean collide(Enemy e) {
		return Collision.defaultCollision(x, y, data.radius, e.x, e.y, e.radiusS);
	}
}
