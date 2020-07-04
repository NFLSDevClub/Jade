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

	public static RoundBullet as(int id, int tag, float radius, float x, float y, float speed, float angle) {
		BulletData data = sheet.findBullet(id);
		RoundBullet bullet = Jade.session.newRoundBullet(data.texture, tag, data.radius);
		if (data.texture.isAnimated()) {
			bullet.animated = true;
			bullet.texture = data.texture;
		}
		bullet.type = id;
		bullet.setXY(x, y);
		bullet.setSpeed(speed);
		bullet.setAngle(angle);
		Jade.session.add(bullet);
		return bullet;
	}
}
