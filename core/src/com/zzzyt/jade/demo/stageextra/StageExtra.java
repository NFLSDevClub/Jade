package com.zzzyt.jade.demo.stageextra;

import com.zzzyt.jade.game.task.Wait;
import com.zzzyt.jade.game.task.Sequence;
import com.zzzyt.jade.game.task.SwitchBGM;
import com.zzzyt.jade.game.task.WaitForBulletClear;

public class StageExtra extends Sequence {

	@Override
	public void init() {
		super.init();
		add(new SwitchBGM("mus/Yet Another Tetris (Piano ver.).ogg"));
		add(new StageExtraMid1());
		add(new WaitForBulletClear());
		add(new Wait(300));
		add(new StageExtraMid2());
		add(new WaitForBulletClear());
		add(new Wait(300));
	}
}
