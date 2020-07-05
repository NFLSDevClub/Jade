package com.zzzyt.jade.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.entity.Bullet;
import com.zzzyt.jade.entity.Player;
import com.zzzyt.jade.entity.RoundBullet;

public class J {

	public static Jade getSession() {
		return Jade.session;
	}

	public static Logger getLogger() {
		return Jade.session.getLogger();
	}

	public static Player getPlayer() {
		return Jade.session.getPlayer();
	}

	public static float playerX() {
		return Jade.session.getPlayer().getX();
	}

	public static float playerY() {
		return Jade.session.getPlayer().getY();
	}

	public static int frame() {
		return Jade.session.frame();
	}

	public static Bullet add(Bullet bullet) {
		return Jade.session.add(bullet);
	}

	public static Bullet remove(Bullet bullet) {
		return Jade.session.remove(bullet);
	}

	public static void onHit() {
		Jade.session.onHit();
	}

	public static boolean isRunning() {
		return Jade.session.isRunning();
	}

	public static RoundBullet newRoundBullet() {
		return Jade.session.newRoundBullet();
	}

	public static RoundBullet newRoundBullet(TextureRegion region, int tag, float radius) {
		return Jade.session.newRoundBullet(region, tag, radius);
	}

	public static String difficulty() {
		return (String) G.get("_difficulty");
	}

	public static int difficultyInt() {
		return J.difficultyInt(difficulty());
	}

	public static int difficultyInt(String difficulty) {
		switch (difficulty) {
		case "easy":
			return 1;
		case "normal":
			return 2;
		case "hard":
			return 3;
		case "lunatic":
			return 4;
		case "extra":
			return 5;
		case "phantasm":
			return 6;
		default:
			return 0;
		}
	}

}
