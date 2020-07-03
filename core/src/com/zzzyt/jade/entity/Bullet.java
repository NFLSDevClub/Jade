package com.zzzyt.jade.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.util.Utils;

public class Bullet implements Entity{

	public int id;
	public int type;
	public int tag;

	public float x, y;
	public transient Sprite sprite;
	public float speed, dir;

	private float boundingRadius;

	public Bullet() {
		
	}
	
	public Bullet(TextureRegion region, int tag) {
		this.sprite = new Sprite(region);
		this.tag = tag;
		this.boundingRadius = Math.max(sprite.getHeight() * sprite.getScaleX(), sprite.getWidth() * sprite.getScaleY());
		this.speed = 0;
		this.dir = 0;
		this.x = 0;
		this.y = 0;
	}

	public Bullet(TextureRegion region, int tag, float x, float y) {
		this.sprite = new Sprite(region);
		this.tag = tag;
		this.boundingRadius = Math.max(sprite.getHeight() * sprite.getScaleX(), sprite.getWidth() * sprite.getScaleY());
		this.speed = 0;
		this.dir = 0;
		this.x = x;
		this.y = y;
	}

	public Bullet(TextureRegion region, int tag, float x, float y, float speed, float dir) {
		this.sprite = new Sprite(region);
		this.tag = tag;
		this.boundingRadius = Math.max(sprite.getHeight() * sprite.getScaleX(), sprite.getWidth() * sprite.getScaleY());
		this.speed = speed;
		this.dir = dir;
		this.x = x;
		this.y = y;
	}

	public float getBoundingRadius() {
		return boundingRadius;
	}

	public Bullet setSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	public Bullet setDir(float dir) {
		this.dir = dir;
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
		sprite.setPosition(x - sprite.getWidth() * sprite.getScaleX() / 2,
				y - sprite.getHeight() * sprite.getScaleY() / 2);
		return this;
	}

	public Bullet setRotaion(float degrees) {
		sprite.setRotation(degrees);
		return this;
	}

	public Bullet setScale(float scaleXY) {
		sprite.setScale(scaleXY);
		this.boundingRadius = Math.max(sprite.getHeight() * sprite.getScaleX(), sprite.getWidth() * sprite.getScaleY());
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
		return Utils.sqr(x - x2) + Utils.sqr(y - y2);
	}

	public void draw(Batch batch) {
		if (!Jade.outOfFrame(x, y, boundingRadius, boundingRadius)) {
			sprite.draw(batch);
		}
	}

	public void update() {
		x += speed * MathUtils.cosDeg(dir);
		y += speed * MathUtils.sinDeg(dir);
		if (Jade.outOfWorld(x, y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY())) {
			Jade.session.remove(this);
			return;
		}
		sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
	}

	public boolean collide(BasicPlayer player) {
		return false;
	}

	public void onHit() {

	}

}
