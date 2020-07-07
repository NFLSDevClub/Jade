package com.zzzyt.jade.game.sequence;

import com.zzzyt.jade.game.Sequence;
import com.zzzyt.jade.util.BGM;

public class SwitchBGM implements Sequence {

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
