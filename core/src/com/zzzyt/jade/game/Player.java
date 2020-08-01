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
	
}
