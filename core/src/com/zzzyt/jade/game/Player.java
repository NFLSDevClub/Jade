package com.zzzyt.jade.game;

public abstract class Player extends Entity {
	
	public abstract float getRadius();

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
}
