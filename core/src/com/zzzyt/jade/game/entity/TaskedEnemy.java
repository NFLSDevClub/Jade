package com.zzzyt.jade.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.util.J;

/**
 * Tasked enemy is an enemy which follows a task!
 * @author XGN
 *
 */
public class TaskedEnemy extends Enemy {

	public Task task;
	
	public TaskedEnemy(TextureAtlas atlas, String regionName, int frameLength, int transitionFrameLength, float x,
			float y, float hp, float radiusS, float radiusP,Task task) {
		super(atlas, regionName, frameLength, transitionFrameLength, x, y, hp, radiusS, radiusP);
		
		this.task=task;
	}

	@Override
	public void update(int t) {
		super.update(t);
		if(task.isFinished()) {
			J.remove(this);
		}else {
			task.update(t);
		}
	}
}
