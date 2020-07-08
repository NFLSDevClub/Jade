package com.zzzyt.jade.game.operator;

import com.zzzyt.jade.game.entity.Bullet;

public class AngularVelocity implements Operator {

	private int tag;
	private float delta, duration;

	public AngularVelocity(int tag, float delta, int duration) {
		this.tag = tag;
		this.delta = delta;
		this.duration = duration;
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void apply(Bullet bullet, int t) {
		if(t<duration) {
			bullet.angle += delta;			
		}
	}

}
