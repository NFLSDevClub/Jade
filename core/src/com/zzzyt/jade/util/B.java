package com.zzzyt.jade.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.game.entity.EnemyBullet;
import com.zzzyt.jade.game.entity.PlayerBullet;
import com.zzzyt.jade.game.shot.BulletData;
import com.zzzyt.jade.game.shot.ShotSheet;

public class B {

	public static ShotSheet sheet;

	public static ShotSheet setSheet(ShotSheet sheet) {
		B.sheet = sheet;
		return sheet;
	}

	public static void setSheet(String sheet) {
		B.sheet = A.get(sheet);
	}

	public static Bullet newBullet() {
		return new Bullet();
	}

	/**
	 * You probably want to use {@link #newEnemyBullet(TextureRegion, int, float)} :)
	 */
	public static Bullet newBullet(TextureRegion region, int tag, float radius) {
		return newEnemyBullet(region, tag, radius);
	}
	
	/**
	 *Returns the enemy bullet by the basic argument
	 */
	public static EnemyBullet newEnemyBullet(TextureRegion region, int tag, float radius) {
		EnemyBullet tmp = new EnemyBullet();
		tmp.sprite = new Sprite(region);
		tmp.tag = tag;
		tmp.radius = radius;
		tmp.boundingRadius = Math.max(tmp.sprite.getHeight() * tmp.sprite.getScaleX(),
				tmp.sprite.getWidth() * tmp.sprite.getScaleY());
		return tmp;
	}
	
	public static PlayerBullet newPlayerBullet(TextureRegion region, int tag,float radius, int penetration, int dmg) {
		PlayerBullet tmp = new PlayerBullet();
		tmp.sprite = new Sprite(region);
		tmp.tag = tag;
		tmp.radius = radius;
		tmp.boundingRadius = Math.max(tmp.sprite.getHeight() * tmp.sprite.getScaleX(),
				tmp.sprite.getWidth() * tmp.sprite.getScaleY());
		tmp.penetration=penetration;
		tmp.damage=dmg;
		return tmp;
	}
	
	/**
	 * Create an enemy shot with angle and speed
	 * @param x
	 * @param y
	 * @param angle
	 * @param speed
	 * @param id
	 * @param tag
	 * @return
	 */
	public static Bullet as(float x, float y, float angle, float speed, int id, int tag) {
		return as(x, y, angle, speed, id, tag,false,-1,-1);
	}
	
	/**
	 * Create shot with angle and speed
	 * @param x
	 * @param y
	 * @param angle
	 * @param speed
	 * @param id
	 * @param tag
	 * @param isPlayerShot
	 * @param penetration - anything if not player shot
	 * @param dmg -anything if not player shot
	 * @return
	 */
	public static Bullet as(float x, float y, float angle, float speed, int id, int tag,boolean isPlayerShot,int penetration,int dmg) {
		angle = M.normalizeAngle(angle);
		BulletData data = sheet.findBullet(id);
		Bullet bullet;
		if(isPlayerShot) {
			bullet=newPlayerBullet(data.texture,tag,data.radius,penetration,dmg);
		}else {
			bullet = newBullet(data.texture, tag, data.radius);
		}
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
	
	/**
	 * {@link #as(float, float, float, float, int, int)}
	 * @param x
	 * @param y
	 * @param angle
	 * @param speed
	 * @param name
	 * @param tag
	 * @return
	 */
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
