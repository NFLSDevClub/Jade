package com.zzzyt.jade.game.operator;

import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.entity.Bullet;

public class BasicOperator implements Operator {

	private Appliable appliable;

	public BasicOperator(Appliable appliable) {
		this.appliable = appliable;
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
