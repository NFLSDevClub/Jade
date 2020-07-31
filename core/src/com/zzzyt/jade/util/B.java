package com.zzzyt.jade.util;

import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.game.entity.EnemyBullet;
import com.zzzyt.jade.game.shot.BulletData;
import com.zzzyt.jade.game.shot.ShotSheet;

public class B {

	public static ShotSheet defaultSheet;

	public static ShotSheet setSheet(ShotSheet sheet) {
		B.defaultSheet = sheet;
		return sheet;
	}

	public static void setSheet(String sheet) {
		B.defaultSheet = A.get(sheet);
	}

	public static BulletData get(int id) {
		return defaultSheet.findBullet(id);
	}

	public static BulletData get(String name) {
		return defaultSheet.findBullet(name);
	}

	public static Bullet setAngleSpeed(Bullet bullet, float x, float y, float angle, float speed) {
		angle = M.normalizeAngle(angle);
		bullet.sprite.setRotation(angle - bullet.data.rotation);
		bullet.setXY(x, y);
		bullet.setSpeed(speed);
		bullet.setAngle(angle);
		J.add(bullet);
		return bullet;
	}

	public static Bullet create(float x, float y, float angle, float speed, int id, int tag) {
		return setAngleSpeed(new EnemyBullet(get(id), tag), x, y, angle, speed);
	}

	public static Bullet towards(float x, float y, float angle, float speed, String name, int tag) {
		return create(x, y, angle, speed, defaultSheet.getId(name), tag);
	}

	public static Bullet towards(float x, float y, float targetX, float targetY, float speed, int id, int tag) {
		return create(x, y, M.atan2(x, y, targetX, targetY), speed, id, tag);
	}

	public static Bullet towards(float x, float y, float targetX, float targetY, float speed, String name, int tag) {
		return towards(x, y, targetX, targetY, speed, defaultSheet.getId(name), tag);
	}

	public static Bullet towards(float x, float y, float speed, int id, int tag) {
		return towards(x, y, J.playerX(), J.playerY(), speed, id, tag);
	}

	public static Bullet towards(float x, float y, float speed, String name, int tag) {
		return towards(x, y, speed, defaultSheet.getId(name), tag);
	}
}
