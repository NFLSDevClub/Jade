package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.BossScene;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.util.J;

/**
 * A task that initalize the boss scene and add it to Jade
 * @author think
 *
 */
public class AddBossScene implements Task {

	public BossScene scene;

	public AddBossScene(BossScene scene) {
		this.scene=scene;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void update(int t) {
		if(J.getSession().bossScene!=null) {
			throw new RuntimeException("Boss Scene Already Exists!");
		}
		scene.init();
		J.getSession().bossScene=scene;
	}

	@Override
	public void init() {
		
	}

}
