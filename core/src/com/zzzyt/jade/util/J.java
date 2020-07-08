package com.zzzyt.jade.util;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.game.Jade;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.game.entity.Player;
import com.zzzyt.jade.game.operator.Operator;

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

	public static Jade addOperator(Operator operator) {
		return Jade.session.addOperator(operator);
	}

	public static Jade removeOperator(Operator operator) {
		return Jade.session.removeOperator(operator);
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

	public static Jade addTask(Task Task) {
		return Jade.session.addTask(Task);
	}

	public static Jade removeTask(Task task) {
		return Jade.session.removeTask(task);
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

	public static Jade add(Bullet bullet) {
		return Jade.session.add(bullet);
	}

	public static Jade remove(Bullet bullet) {
		return Jade.session.remove(bullet);
	}

	public static void clearBullets() {
		Array<Bullet> tmp = J.getBullets();
		for (int i = tmp.size - 1; i >= 0; i--) {
			if (tmp.get(i) != null)
				J.remove(tmp.get(i));
		}
	}

	public static void onHit() {
		Jade.session.onHit();
	}

	public static boolean isRunning() {
		return Jade.session.isRunning();
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
