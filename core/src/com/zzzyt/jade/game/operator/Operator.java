package com.zzzyt.jade.game.operator;

import com.zzzyt.jade.entity.Bullet;

public interface Operator {
	
	public int getTag();
	public void apply(Bullet bullet,int t);
	
}
