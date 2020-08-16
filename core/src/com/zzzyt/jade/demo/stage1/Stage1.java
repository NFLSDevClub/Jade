package com.zzzyt.jade.demo.stage1;

import com.zzzyt.jade.game.task.Wait;
import com.zzzyt.jade.game.task.WaitForBossScene;
import com.zzzyt.jade.game.task.Sequence;
import com.zzzyt.jade.game.drawable.TiledScrollingBackground;
import com.zzzyt.jade.game.task.AddBossScene;
import com.zzzyt.jade.game.task.AddDrawable;
import com.zzzyt.jade.game.task.SwitchBGM;
import com.zzzyt.jade.util.A;

public class Stage1 extends Sequence {

	@Override
	public void init() {
		super.init();
		add(new AddDrawable(new TiledScrollingBackground(A.getRegion("bg/st3_water.png"), 1, 1, -2048)));
		add(new AddDrawable((new TiledScrollingBackground(A.getRegion("bg/st3_water_overlay.png"), 1.2f, 0, -2047)
				.setAlpha(0.5f))));
		add(new AddDrawable(new TiledScrollingBackground(A.getRegion("bg/st3_shore_left.png"), 1, 1, -2046, 0, 128)));
		add(new AddDrawable(
				new TiledScrollingBackground(A.getRegion("bg/st3_shore_right.png"), 1, 1, -2046, 256, 128)));
		add(new Wait(120));
		add(new SwitchBGM("mus/Idea12.ogg"));
//		add(new Stage1Mid1());
//		add(new WaitForBulletClear());
//		add(new Wait(300));
//		add(new Stage1Mid2());
//		add(new WaitForBulletClear());
//		add(new Wait(600));
		add(new AddBossScene(new Stage1BossScene()));
		add(new WaitForBossScene());
	}

}
