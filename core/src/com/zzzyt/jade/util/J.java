package com.zzzyt.jade.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.entity.Bullet;
import com.zzzyt.jade.entity.Player;
import com.zzzyt.jade.game.Jade;

public class J {

	public static final int EASY = 1;
	public static final int NORMAL = 2;
	public static final int HARD = 3;
	public static final int LUNATIC = 4;
	public static final int EXTRA = 5;
	public static final int PHANTASM = 6;

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

	public static Array<Bullet> getBullets() {
		return Jade.session.getBullets();
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

	public static int difficulty() {
		return (int) Global.get("_difficulty");
	}

	public static int diffSelect(int easy, int normal, int hard, int lunatic) {
		switch (difficulty()) {
		case EASY:
			return easy;
		case NORMAL:
			return normal;
		case HARD:
			return hard;
		case LUNATIC:
			return lunatic;
		default:
			getLogger().error("diffSelect: difficulty id \"" + difficulty() + "\" doesn't exist");
			return -1;
		}
	}

	public static float diffSelectFloat(float easy, float normal, float hard, float lunatic) {
		switch (difficulty()) {
		case EASY:
			return easy;
		case NORMAL:
			return normal;
		case HARD:
			return hard;
		case LUNATIC:
			return lunatic;
		default:
			getLogger().error("diffSelect: difficulty id \"" + difficulty() + "\" doesn't exist");
			return -1;
		}
	}
}
