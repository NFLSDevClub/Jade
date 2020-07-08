package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.util.BGM;

public class SwitchBGM implements Task {

	private String bgmName;

	public SwitchBGM(String bgmName) {
		this.bgmName = bgmName;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void update(int frame) {
		BGM.play(bgmName);
	}

	@Override
	public void init() {
		
	}

}
