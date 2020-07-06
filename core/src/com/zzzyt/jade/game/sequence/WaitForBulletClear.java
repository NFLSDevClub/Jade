package com.zzzyt.jade.game.sequence;

import com.zzzyt.jade.game.Sequence;
import com.zzzyt.jade.util.J;

public class WaitForBulletClear implements Sequence {

	@Override
	public boolean isFinished() {
		return J.getSession().getBulletCount() == 0;
	}

	@Override
	public void update(int frame) {

	}

}
