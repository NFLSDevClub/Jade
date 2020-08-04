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

	/**
	 * The item collection line height.
	 * @return
	 */
	public float getItemCollectionLineHeight() {
		return 0;
	}

	/**
	 * The item collection radius <br/>
	 * Defaults to be the hitbox radius*10
	 * @return
	 */
	public float getItemRadius() {
		return getRadius()*10;
	}
}
