package com.zzzyt.jade.game.operator;

import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.entity.Bullet;

public class AngularVelocity implements Operator {

	private float delta, duration;

	public AngularVelocity(float delta, int duration) {
		this.delta = delta;
		this.duration = duration;
	}

	@Override
	public void apply(Bullet bullet, int t) {
		if (t < duration) {
			bullet.angle += delta;
		}
	}

}
