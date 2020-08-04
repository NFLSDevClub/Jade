package com.zzzyt.jade.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.game.Drawable;
import com.zzzyt.jade.game.EntityArray;
import com.zzzyt.jade.game.Jade;
import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.game.entity.Enemy;
import com.zzzyt.jade.game.entity.Item;

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

	public static EntityArray<Bullet> getBullets() {
		return Jade.session.getBullets();
	}

	public static EntityArray<Enemy> getEnemies() {
		return Jade.session.getEnemies();
	}
	
	public static Operator addOperator(Operator operator) {
		Jade.session.addOperator(operator);
		return operator;
	}

	public static void removeOperator(Operator operator) {
		Jade.session.removeOperator(operator);
	}

	public static Array<Operator> getOperators(int tag) {
		return Jade.session.getOperators(tag);
	}

	public static void clearOperators(int tag) {
		Jade.session.getOperators(tag).clear();
	}

	public static void clearOperators() {
		Jade.session.operators.clear();
	}

	public static Task addTask(Task task) {
		Jade.session.addTask(task);
		task.init();
		return task;
	}

	public static void removeTask(Task task) {
		Jade.session.removeTask(task);
	}

	public static Array<Task> getTasks() {
		return Jade.session.getTasks();
	}

	public static void clearTasks() {
		Task tmp = Jade.session.getTask(0);
		Jade.session.getTasks().clear();
		Jade.session.addTask(tmp);
	}

	public static int frame() {
		return Jade.session.frame();
	}

	public static Bullet add(Bullet bullet) {
		Jade.session.add(bullet);
		return bullet;
	}

	public static Enemy add(Enemy e) {
		Jade.session.add(e);
		return e;
	}
	
	public static Item add(Item e) {
		Jade.session.add(e);
		return e;
	}
	
	public static void remove(Bullet bullet) {
		Jade.session.remove(bullet);
	}

	public static void remove(Enemy enemy) {
		Jade.session.remove(enemy);
	}
	
	public static void remove(Item enemy) {
		Jade.session.remove(enemy);
	}
	
	public static void clearBullets() {
		EntityArray<Bullet> tmp = J.getBullets();
		for (int i = tmp.size() - 1; i >= 0; i--) {
			if (tmp.get(i) != null)
				J.remove(tmp.get(i));
		}
	}

	public static void clearEnemies(boolean clearBoss) {
		EntityArray<Enemy> tmp = J.getEnemies();
		for (int i = tmp.size() - 1; i >= 0; i--) {
			if (tmp.get(i) != null) {
				if(!tmp.get(i).isBoss || clearBoss) {
					J.remove(tmp.get(i));
				}
			}
				
		}
	}
	
	public static void addDrawable(Drawable drawable) {
		Jade.session.addDrawable(drawable);
	}

	public static void onHit() {
		Jade.session.onHit();
	}

	public static boolean isRunning() {
		return Jade.session.isRunning();
	}

	public static boolean isKeyPressed(int[] keycode) {
		return U.checkKey(keycode);
	}

	public static String gameMode() {
		return (String) Global.get("_gameMode");
	}

	public static boolean isGameModeRegular() {
		return "regular".equals(Global.get("_gameMode"));
	}

	public static boolean isGameModeExtra() {
		return "extra".equals(Global.get("_gameMode"));
	}

	public static boolean isGameModeSpellPractice() {
		return "spellPractice".equals(Global.get("_gameMode"));
	}

	public static boolean isGameModeStagePractice() {
		return "stagePractice".equals(Global.get("_gameMode"));
	}

	public static boolean isGameModeReplay() {
		return "replay".equals(Global.get("_gameMode"));
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

	public static int diffSelect(int difficulty, int easy, int normal, int hard, int lunatic) {
		switch (difficulty) {
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

	public static float diffSelect(float easy, float normal, float hard, float lunatic) {
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

	public static float diffSelect(int difficulty, float easy, float normal, float hard, float lunatic) {
		switch (difficulty) {
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

	public static <T> T diffSelect(T easy, T normal, T hard, T lunatic) {
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
			return null;
		}
	}

	public static <T> T diffSelect(int difficulty, T easy, T normal, T hard, T lunatic) {
		switch (difficulty) {
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
			return null;
		}
	}

}
