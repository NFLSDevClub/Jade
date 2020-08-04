package com.zzzyt.jade.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.game.Entity;
import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.shot.BulletData;
import com.zzzyt.jade.util.Collision.CollisionData;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.M;
import com.zzzyt.jade.util.U;

/**
 * Base class for bullets
 *
 */
public class Bullet extends Entity {

	public int tag;
	public int t;
	public float x, y;
	public Sprite sprite;
	public float speed, angle;
	public float boundingWidth, boundingHeight;
	public BulletData data;

	public Bullet() {

	}

	public Bullet(BulletData data, int tag) {
		this.data = data;
		this.tag = tag;
		this.boundingWidth = data.texture.getMaxWidth();
		this.boundingHeight = data.texture.getMaxHeight();
		this.sprite = new Sprite(data.texture.getFrame(0));
	}

	public BulletData getData() {
		return data;
	}

	public float getBoundingWidth() {
		return boundingWidth;
	}

	public float getBoundingHeight() {
		return boundingHeight;
	}

	public Bullet setSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	public Bullet setAngle(float angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public Bullet setX(float nx) {
		x = nx;
		updateSpritePosition();
		return this;
	}

	@Override
	public Bullet setY(float ny) {
		y = ny;
		updateSpritePosition();
		return this;
	}

	@Override
	public Bullet setXY(float nx, float ny) {
		x = nx;
		y = ny;
		updateSpritePosition();
		return this;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	public Bullet updateSpritePosition() {
		sprite.setPosition(x - data.originX * sprite.getScaleX(), y - data.originY * sprite.getScaleY() / 2);
		return this;
	}

	public Bullet setRotaion(float degrees) {
		sprite.setRotation(degrees);
		return this;
	}

	public Bullet setScale(float scaleXY) {
		sprite.setScale(scaleXY);
		boundingWidth = data.texture.getMaxWidth() * sprite.getScaleX();
		boundingHeight = data.texture.getMaxHeight() * sprite.getScaleY();
		updateSpritePosition();
		return this;
	}

	public Bullet setColor(Color tint) {
		sprite.setColor(tint);
		return this;
	}

	public Bullet setAlpha(float a) {
		sprite.setAlpha(a);
		return this;
	}

	public float dist2(float x2, float y2) {
		return M.sqr(x - x2) + M.sqr(y - y2);
	}

	public void draw(Batch batch) {
		if (!U.outOfFrame(x, y, boundingWidth, boundingHeight)) {
			sprite.draw(batch);
		}
	}

	public void update(int frame) {
		checkCollision();

		t++;
		Array<Operator> tmp = J.getOperators(tag);
		if (tmp != null) {
			for (int i = 0; i < tmp.size; i++) {
				if (tmp.get(i) != null) {
					tmp.get(i).apply(this, t);
					if (internalId == -1)
						return;
				}
			}
			if (internalId == -1)
				return;
		}
		sprite.setRegion(data.texture.getFrame(t));
		sprite.setRotation(M.normalizeAngle(sprite.getRotation() + data.spinVelocity));
		x += speed * MathUtils.cosDeg(angle);
		y += speed * MathUtils.sinDeg(angle);
		if (U.outOfWorld(x, y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY())) {
			J.remove(this);
			return;
		}
		updateSpritePosition();
	}

	/**
	 * Implement this function to check collision with others. <br/>
	 * This is called at the beginning of update function <br/>
	 * 
	 * @return if a collision has happened
	 */
	public boolean checkCollision() {
		return false;
	}

	/**
	 * Unknown. Not used --XGN
	 */
	public void onHit() {
		J.onHit();
	}

	@Override
	public int getZIndex() {
		return 0;
	}

	@Override
	public CollisionData getCollisionData(Entity other) {
		return data.collision;
	}

}
