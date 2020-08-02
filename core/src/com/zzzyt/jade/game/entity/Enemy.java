package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.Entity;
import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;
import com.zzzyt.jade.util.PlayerAnimation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Base class for enemies
 * @author XGN
 *
 */
public class Enemy extends Entity {

	public transient PlayerAnimation was;
	public float x, y;
	/**
	 * The health point of the enemy
	 */
	public float hp;
	/**
	 * Hitbox radius for shot
	 */
	public float radiusS;
	/**
	 * Hitbox radius for players
	 */
	public float radiusP;
	
	
	public Enemy(TextureAtlas atlas, String regionName,int frameLength, int transitionFrameLength,float x, float y, float hp, float radiusS, float radiusP) {
		super();
		was=new PlayerAnimation(atlas.findRegions(regionName + "_left"),
				atlas.findRegions(regionName + "_center"),
				atlas.findRegions(regionName + "_right"),
				atlas.findRegions(regionName + "_toLeft"),
				atlas.findRegions(regionName + "_toRight"),
				frameLength,
				transitionFrameLength);
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.radiusS = radiusS;
		this.radiusP = radiusP;
	}


	public boolean collide(Player player) {
		return Collision.defaultCollision(player.getX(), player.getY(), player.getRadius(), x, y, radiusP);
	}
	

	private float lastx;
	
	/**
	 * When overriding this, please call <code>super.update()</code> first!!! <br/>
	 * VERY IMPORTANT
	 */
	@Override
	public void update(int t) {
		//check collision
		if(collide(J.getPlayer())) {
			J.onHit();
		}
		
		was.update(t, x-lastx);
		
		lastx=x;
	}
	
	public void draw(Batch batch) {
		TextureRegion tmp = was.getTexture();
		batch.draw(tmp, x - tmp.getRegionWidth() / 2, y - tmp.getRegionHeight() / 2);
	}

	@Override
	public Enemy setX(float x) {
		this.x = x;
		return this;
	}

	@Override
	public Enemy setY(float y) {
		this.y = y;
		return this;
	}

	@Override
	public Enemy setXY(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Called when the enemy is on hit
	 * @param damage
	 */
	public void onHit(float damage) {
		hp-=damage;
		if(hp<=0) {
			J.remove(this);
		}
	}
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
	
	@Override
	public int getZIndex() {
		return -512;
	}


	
}
