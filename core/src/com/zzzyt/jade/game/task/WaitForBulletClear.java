package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.util.J;

public class WaitForBulletClear implements Task {

	@Override
	public boolean isFinished() {
		return J.getSession().getBulletCount() == 0;
	}

	@Override
	public void update(int frame) {

	}

	@Override
	public void init() {
		
	}

}
