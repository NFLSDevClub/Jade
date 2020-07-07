package com.zzzyt.jade.demo.stage_extra;

import com.zzzyt.jade.game.Stage;
import com.zzzyt.jade.game.sequence.Delay;
import com.zzzyt.jade.game.sequence.SwitchBGM;
import com.zzzyt.jade.game.sequence.WaitForBulletClear;

public class StageExtra extends Stage {

	@Override
	public void init() {
		add(new SwitchBGM("mus/Yet Another Tetris (Piano ver.).ogg"));
		add(new StageExtraMid1());
		add(new WaitForBulletClear());
		add(new Delay(300));
	}
}
