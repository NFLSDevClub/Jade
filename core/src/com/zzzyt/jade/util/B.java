package com.zzzyt.jade.util;

import com.zzzyt.jade.Jade;
import com.zzzyt.jade.entity.RoundBullet;
import com.zzzyt.jade.util.shot.BulletData;
import com.zzzyt.jade.util.shot.ShotSheet;

public class B {
	public static ShotSheet sheet;

	public static ShotSheet setSheet(ShotSheet sheet) {
		B.sheet = sheet;
		return sheet;
	}

	public static void setSheet(String sheet) {
		B.sheet = A.get(sheet);
	}

	public static RoundBullet as(float x, float y, float angle, float speed, int id, int tag) {
		angle = M.normalizeAngle(angle);
		BulletData data = sheet.findBullet(id);
		RoundBullet bullet = Jade.session.newRoundBullet(data.texture, tag, data.radius);
		if (data.texture.isAnimated()) {
			bullet.animated = true;
			bullet.texture = data.texture;
		}
		bullet.sprite.setRotation(angle - data.rotation);
		bullet.angularVelocity = data.angularVelocity;
		bullet.type = id;
		bullet.setXY(x, y);
		bullet.setSpeed(speed);
		bullet.setAngle(angle);
		Jade.session.add(bullet);
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
		return at(x, y, Jade.getPlayer().getX(), Jade.getPlayer().getY(), speed, id, tag);
	}

	public static RoundBullet at(float x, float y, float speed, String name, int tag) {
		return at(x, y, speed, sheet.getId(name), tag);
	}
}
