package com.zzzyt.jade.game;

public interface Player extends Entity {
	
	public float getRadius();

	/**
	 * Event shot
	 */
	public void onShot();

	/**
	 * Event bomb
	 */
	public void onBomb();
	
}
