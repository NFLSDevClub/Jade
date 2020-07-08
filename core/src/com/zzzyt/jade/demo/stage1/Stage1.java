package com.zzzyt.jade.demo.stage1;

import com.zzzyt.jade.game.task.Wait;
import com.zzzyt.jade.game.task.Plural;
import com.zzzyt.jade.game.task.SwitchBGM;
import com.zzzyt.jade.game.task.WaitForBulletClear;

public class Stage1 extends Plural {

	@Override
	public void init() {
		super.init();
		add(new Wait(120));
		add(new SwitchBGM("mus/Idea12.ogg"));
		add(new Stage1Mid1());
		add(new WaitForBulletClear());
		add(new Wait(300));
		add(new Stage1Mid2());
		add(new WaitForBulletClear());
		add(new Wait(600));
	}

}
