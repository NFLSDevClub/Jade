package com.zzzyt.jade.game.sequence;

import com.zzzyt.jade.game.Sequence;
import com.zzzyt.jade.util.J;

public class ClearBullets implements Sequence {

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void update(int frame) {
		J.clearBullets();
	}

	@Override
	public void init() {
		
	}

}
