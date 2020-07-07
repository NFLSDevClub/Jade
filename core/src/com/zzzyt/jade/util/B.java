package com.zzzyt.jade.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.game.shot.BulletData;
import com.zzzyt.jade.game.shot.ShotSheet;

public class B {

	public static Pool<Bullet> BulletPool = new Pool<Bullet>() {
		@Override
		protected Bullet newObject() {
			return new Bullet();
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

	public static Bullet newBullet() {
		return BulletPool.obtain();
	}

	public static Bullet newBullet(TextureRegion region, int tag, float radius) {
		Bullet tmp = BulletPool.obtain();
		tmp.sprite = new Sprite(region);
		tmp.tag = tag;
		tmp.radius = radius;
		tmp.boundingRadius = Math.max(tmp.sprite.getHeight() * tmp.sprite.getScaleX(),
				tmp.sprite.getWidth() * tmp.sprite.getScaleY());
		return tmp;
	}

	public static Bullet freeBullet(Bullet bullet) {
		BulletPool.free(bullet);
		return bullet;
	}

	public static Bullet as(float x, float y, float angle, float speed, int id, int tag) {
		angle = M.normalizeAngle(angle);
		BulletData data = sheet.findBullet(id);
		Bullet bullet = newBullet(data.texture, tag, data.radius);
		if (data.texture.isAnimated()) {
			bullet.animated = true;
			bullet.texture = data.texture;
		}
		bullet.sprite.setRotation(angle - data.rotation);
		bullet.spinSpeed = data.spinSpeed;
		bullet.type = id;
		bullet.setXY(x, y);
		bullet.setSpeed(speed);
		bullet.setAngle(angle);
		J.add(bullet);
		return bullet;
	}

	public static Bullet as(float x, float y, float angle, float speed, String name, int tag) {
		return as(x, y, angle, speed, sheet.getId(name), tag);
	}

	public static Bullet at(float x, float y, float targetX, float targetY, float speed, int id, int tag) {
		return as(x, y, M.atan2(x, y, targetX, targetY), speed, id, tag);
	}

	public static Bullet at(float x, float y, float targetX, float targetY, float speed, String name, int tag) {
		return at(x, y, targetX, targetY, speed, sheet.getId(name), tag);
	}

	public static Bullet at(float x, float y, float speed, int id, int tag) {
		return at(x, y, J.playerX(), J.playerY(), speed, id, tag);
	}

	public static Bullet at(float x, float y, float speed, String name, int tag) {
		return at(x, y, speed, sheet.getId(name), tag);
	}
}
