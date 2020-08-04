package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.BossScene;
import com.zzzyt.jade.game.Entity;
import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.Collision.CollisionData;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;
import com.zzzyt.jade.util.FlyingAnimation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Base class for enemies
 * 
 * @author XGN
 *
 */
public class Enemy extends Entity {

	public transient FlyingAnimation was;
	public float x, y;
	/**
	 * The health point of the enemy
	 */
	public double hp;
	/**
	 * Hitbox radius for shot
	 */
	public CollisionData collisionS;
	/**
	 * Hitbox radius for players
	 */
	public CollisionData collisionP;

	private float lastx;
	public boolean isBoss;

	/**
	 * Auto remove when out of bound for too far
	 */
	public boolean enableOOBCheck = true;

	public Enemy(TextureAtlas atlas, String regionName, int frameLength, int transitionFrameLength, float x, float y,
			float hp, float radiusS, float radiusP, boolean isBoss) {
		super();
		was = new FlyingAnimation(atlas.findRegions(regionName + "_left"), atlas.findRegions(regionName + "_center"),
				atlas.findRegions(regionName + "_right"), atlas.findRegions(regionName + "_toLeft"),
				atlas.findRegions(regionName + "_toRight"), frameLength, transitionFrameLength);
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.collisionS = new Collision.Circle(0, 0, radiusS);
		this.collisionP = new Collision.Circle(0, 0, radiusP);
		if (isBoss) {
			this.hp = BossScene.MAXHP;
		}
		this.isBoss = isBoss;
	}

	public boolean collide(Player player) {
		return Collision.collide(this, player);
	}

	/**
	 * When overriding this, please call <code>super.update()</code> first!!! <br/>
	 * VERY IMPORTANT
	 */
	@Override
	public void update(int t) {
		// check collision
		if (collide(J.getPlayer())) {
			J.onHit();
		}

		// OOB Check
		if (enableOOBCheck && x >= U.config().frameWidth + 300 || x <= -300 || y >= U.config().frameHeight + 300
				|| y <= -300) {
			J.remove(this);
		} else {

			was.update(t, x - lastx);

			lastx = x;
		}
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
	 * 
	 * @param damage
	 */
	public void onHit(float damage) {
		hp -= damage;
		if (hp <= 0) {
			onDie();
			J.remove(this);
		}
	}

	public void onDie() {

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

	@Override
	public CollisionData getCollisionData(Entity other) {
		if (other instanceof Player) {
			return collisionP;
		}
		return collisionS;
	}

}
