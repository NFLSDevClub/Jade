package com.zzzyt.jade.entity;

import com.zzzyt.jade.Config;
import com.zzzyt.jade.util.U;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class BasicPlayer implements Player {

	public transient Array<? extends TextureRegion> left, center, right, toLeft, toRight;
	public transient Sprite hitbox;
	public float radius, speedHigh, speedLow;
	public float x, y;
	public int frameLength, transitionFrameLength;

	private int dx, dy;
	private int timer1, timer2, pos;

	public BasicPlayer() {

	}

	public BasicPlayer(Array<? extends TextureRegion> left, Array<? extends TextureRegion> center,
			Array<? extends TextureRegion> right, Array<? extends TextureRegion> toLeft,
			Array<? extends TextureRegion> toRight, Sprite hitbox, int frameLength, int transitionFrameLength,
			float radius, float speedHigh, float speedLow) {
		this.left = left;
		this.center = center;
		this.right = right;
		this.toLeft = toLeft;
		this.toRight = toRight;
		this.hitbox = hitbox;
		this.radius = radius;
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(Config.w / 2);
		this.y = U.screenToWorldY(32);
		this.frameLength = frameLength;
		this.transitionFrameLength = transitionFrameLength;
		this.timer1 = 0;
		this.timer2 = 0;
		this.pos = 0;
	}

	public BasicPlayer(TextureAtlas atlas, String regionName, int frameLength, int transitionFrameLength, float radius,
			float speedHigh, float speedLow) {
		this.left = atlas.findRegions(regionName + "_left");
		this.center = atlas.findRegions(regionName + "_center");
		this.right = atlas.findRegions(regionName + "_right");
		this.toLeft = atlas.findRegions(regionName + "_toLeft");
		this.toRight = atlas.findRegions(regionName + "_toRight");
		this.hitbox = new Sprite(atlas.findRegion(regionName + "_hitbox"));
		this.frameLength = frameLength;
		this.transitionFrameLength = transitionFrameLength;
		this.radius = radius;
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(Config.w / 2);
		this.y = U.screenToWorldY(32);
		this.timer1 = 0;
		this.timer2 = 0;
		this.pos = 0;
	}

	public BasicPlayer(TextureRegion region, float radius, float speedHigh, float speedLow) {
		Array<TextureRegion> tmp = new Array<TextureRegion>();
		tmp.add(region);
		this.left = tmp;
		this.center = tmp;
		this.right = tmp;
		this.toLeft = tmp;
		this.toRight = tmp;
		this.frameLength = 1;
		this.transitionFrameLength = 1;
		this.radius = radius;
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(Config.w / 2);
		this.y = U.screenToWorldY(32);
		this.timer1 = 0;
		this.timer2 = 0;
		this.pos = 0;
	}

	public void draw(Batch batch) {
		TextureRegion tmp = getTexture();
		batch.draw(tmp, x - tmp.getRegionWidth() / 2, y - tmp.getRegionHeight() / 2);
		if (U.checkKey(Config.keySlow)) {
			hitbox.setAlpha(MathUtils.clamp(hitbox.getColor().a + 0.1f, 0, 1));
		} else {
			hitbox.setAlpha(MathUtils.clamp(hitbox.getColor().a - 0.1f, 0, 1));
		}
		if (hitbox.getColor().a > 0) {
			hitbox.draw(batch);
			hitbox.setRotation(-hitbox.getRotation());
			hitbox.draw(batch);
			hitbox.setRotation(-hitbox.getRotation());
		}
	}

	public TextureRegion getTexture() {
		timer1 = (timer1 + 1) % frameLength;
		if (dx < 0) {
			if (pos > -toLeft.size * transitionFrameLength) {
				pos--;
				timer2 = 0;
			} else {
				if (timer1 == 0) {
					timer2 = (timer2 + 1) % left.size;
				}
			}
		} else if (dx > 0) {
			if (pos < toRight.size * transitionFrameLength) {
				pos++;
				timer2 = 0;
			} else {
				if (timer1 == 0) {
					timer2 = (timer2 + 1) % right.size;
				}
			}
		} else {
			if (pos > 0) {
				pos--;
				timer2 = 0;
			} else if (pos < 0) {
				pos++;
				timer2 = 0;
			} else {
				if (timer1 == 0) {
					timer2 = (timer2 + 1) % center.size;
				}
			}
		}
		if (pos == -toLeft.size * transitionFrameLength) {
			return left.get(timer2);
		}
		if (pos < 0) {
			return toLeft.get((-pos + transitionFrameLength - 1) / transitionFrameLength - 1);
		}
		if (pos == 0) {
			return center.get(timer2);
		}
		if (pos == toRight.size * transitionFrameLength) {
			return right.get(timer2);
		}
		if (pos > 0) {
			return toRight.get((pos + transitionFrameLength - 1) / transitionFrameLength - 1);
		}
		return center.get(timer2);
	}

	@Override
	public BasicPlayer setX(float x) {
		this.x = x;
		return this;
	}

	@Override
	public BasicPlayer setY(float y) {
		this.y = y;
		return this;
	}

	@Override
	public BasicPlayer setXY(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public void update() {
		dx = 0;
		dy = 0;
		float speed = speedHigh;
		if (U.checkKey(Config.keySlow)) {
			speed = speedLow;
		}
		if (U.checkKey(Config.keyUp)) {
			dy++;
		}
		if (U.checkKey(Config.keyDown)) {
			dy--;
		}
		if (U.checkKey(Config.keyLeft)) {
			dx--;
		}
		if (U.checkKey(Config.keyRight)) {
			dx++;
		}
		if (Math.abs(dx) > 0 && Math.abs(dy) == 0) {
			x += speed * dx;
		} else if (Math.abs(dx) == 0 && Math.abs(dy) > 0) {
			y += speed * dy;
		} else if (Math.abs(dx) > 0 && Math.abs(dy) > 0) {
			x += speed * dx / U.SQRT2;
			y += speed * dy / U.SQRT2;
		}
		x = MathUtils.clamp(x, -Config.originX, Config.w - Config.originX);
		y = MathUtils.clamp(y, -Config.originY, Config.h - Config.originY);

		hitbox.setPosition(x - hitbox.getWidth() / 2, y - hitbox.getHeight() / 2);
		hitbox.setRotation(U.normalizeAngle(hitbox.getRotation() + 4f));
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
	public float getRadius() {
		return radius;
	}
}
