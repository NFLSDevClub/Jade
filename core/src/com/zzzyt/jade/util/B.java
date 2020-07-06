package com.zzzyt.jade.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.zzzyt.jade.entity.RoundBullet;
import com.zzzyt.jade.util.shot.BulletData;
import com.zzzyt.jade.util.shot.ShotSheet;

public class B {

	public static Pool<RoundBullet> roundBulletPool = new Pool<RoundBullet>() {
		@Override
		protected RoundBullet newObject() {
			return new RoundBullet();
		}
	};
	public static ShotSheet sheet;

	public static ShotSheet setSheet(ShotSheet sheet) {
		B.sheet = sheet;
		return sheet;
	}

	public static void setSheet(String sheet) {
		B.sheet = A.get(sheet);
	}

	public static RoundBullet newRoundBullet() {
		return roundBulletPool.obtain();
	}

	public static RoundBullet newRoundBullet(TextureRegion region, int tag, float radius) {
		RoundBullet tmp = roundBulletPool.obtain();
		tmp.sprite = new Sprite(region);
		tmp.tag = tag;
		tmp.radius = radius;
		tmp.boundingRadius = Math.max(tmp.sprite.getHeight() * tmp.sprite.getScaleX(),
				tmp.sprite.getWidth() * tmp.sprite.getScaleY());
		return tmp;
	}

	public static RoundBullet freeRoundBullet(RoundBullet bullet) {
		roundBulletPool.free(bullet);
		return bullet;
	}

	public static RoundBullet as(float x, float y, float angle, float speed, int id, int tag) {
		angle = M.normalizeAngle(angle);
		BulletData data = sheet.findBullet(id);
		RoundBullet bullet = newRoundBullet(data.texture, tag, data.radius);
		if (data.texture.isAnimated()) {
			bullet.animated = true;
			bullet.texture = data.texture;
		}
		bullet.sprite.setRotation(angle - data.rotation);
		bullet.spriteRotationVelocity = data.spinSpeed;
		bullet.type = id;
		bullet.setXY(x, y);
		bullet.setSpeed(speed);
		bullet.setAngle(angle);
		J.add(bullet);
		return bullet;
	}

	public static RoundBullet as(float x, float y, float angle, float speed, String name, int tag) {
		return as(x, y, angle, speed, sheet.getId(name), tag);
	}

	public static RoundBullet at(float x, float y, float targetX, float targetY, float speed, int id, int tag) {
		return as(x, y, M.atan2(x, y, targetX, targetY), speed, id, tag);
	}

	public static RoundBullet at(float x, float y, float targetX, float targetY, float speed, String name, int tag) {
		return at(x, y, targetX, targetY, speed, sheet.getId(name), tag);
	}

	public static RoundBullet at(float x, float y, float speed, int id, int tag) {
		return at(x, y, J.playerX(), J.playerY(), speed, id, tag);
	}

	public static RoundBullet at(float x, float y, float speed, String name, int tag) {
		return at(x, y, speed, sheet.getId(name), tag);
	}
}
