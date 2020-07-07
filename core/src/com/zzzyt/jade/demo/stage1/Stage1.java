package com.zzzyt.jade.demo.stage1;

import com.zzzyt.jade.game.Stage;
import com.zzzyt.jade.game.sequence.Delay;
import com.zzzyt.jade.game.sequence.SwitchBGM;
import com.zzzyt.jade.game.sequence.WaitForBulletClear;

public class Stage1 extends Stage {

	@Override
	public void init() {
		add(new Delay(120));
		add(new SwitchBGM("mus/Idea12.ogg"));
		add(new Stage1Mid1());
		add(new WaitForBulletClear());
		add(new Delay(300));
		add(new Stage1Mid2());
		add(new WaitForBulletClear());
		add(new Delay(600));
	}

}
