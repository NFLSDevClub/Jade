package com.zzzyt.jade.game.operator;

import com.zzzyt.jade.game.entity.Bullet;

public class BasicOperator implements Operator {

	private int tag;
	private Appliable appliable;

	public BasicOperator(int tag, Appliable appliable) {
		this.tag = tag;
		this.appliable = appliable;
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void apply(Bullet bullet, int t) {
		appliable.apply(bullet, t);
	}

	@FunctionalInterface
	public static interface Appliable {
		public void apply(Bullet bullet, int t);
	}

}
