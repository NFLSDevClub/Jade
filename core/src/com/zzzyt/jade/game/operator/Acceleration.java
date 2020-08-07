package com.zzzyt.jade.game.operator;

import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.entity.Bullet;

public class Acceleration implements Operator {

	private float acceleration, target;

	public Acceleration(float acceleration, float target) {
		this.acceleration = acceleration;
		this.target = target;
	}

	@Override
	public void apply(Bullet bullet, int t) {
		if (acceleration > 0) {
			if (bullet.speed < target) {
				bullet.speed += acceleration;
			}
		} else {
			if (bullet.speed > target) {
				bullet.speed += acceleration;
			}
		}
	}

}
