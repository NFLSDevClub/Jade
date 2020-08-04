package com.zzzyt.jade.game;

import com.zzzyt.jade.util.Collision.CollisionData;
import com.zzzyt.jade.game.entity.EnemyBullet;

public abstract class Player extends Entity {
	
	/**
	 * Player state: PLOK, PLDB, PLRB <br/>
	 */
	public int state;
	
	/**
	 * allow to trigger {@link #onShot()} {@link #onBomb()}?
	 */
	public boolean canBomb, canShot;

	/**
	 * Normal player state
	 */
	public static final int PLOK = 0;
	/**
	 * Waiting for deadbombing state
	 */
	public static final int PLDB = 1;
	/**
	 * Waiting for respawn state
	 */
	public static final int PLRB = 2;
	
	/**
	 * Event shot
	 */
	public abstract void onShot();

	/**
	 * Event bomb
	 */
	public abstract void onBomb();

	/**
	 * Event hit <br/>
	 * Player can still deathbomb
	 */
	public abstract void onHit();

	/**
	 * Event rebirth start <br/>
	 * Player fails to deathbomb and now waiting for rebirth frame
	 */
	public abstract void onRebirthStart();
	
	/**
	 * Event rebirth end <br/>
	 * Player has just rebirthed
	 */
	public abstract void onRebirthEnd();

	/**
	 * The item collection line height.
	 * @return
	 */
	public float getItemCollectionLineHeight() {
		return 0;
	}

	public abstract void onGraze(EnemyBullet eb);

	public abstract CollisionData getGrazeCollisionData();

}
