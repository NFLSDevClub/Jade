package com.zzzyt.jade.game.operator;

import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.entity.Bullet;

public class Acceleration implements Operator {

	private int tag;
	private float acceleration, target;

	public Acceleration(int tag, float acceleration, int target) {
		this.tag = tag;
		this.acceleration = acceleration;
		this.target = target;
	}

	@Override
	public int getTag() {
		return tag;
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
