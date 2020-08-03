package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.util.J;

public class WaitForBossScene implements Task {

	@Override
	public boolean isFinished() {
		return J.getSession().bossScene==null;
	}

	@Override
	public void update(int t) {

	}

	@Override
	public void init() {

	}

}
