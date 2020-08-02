package com.zzzyt.jade.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.zzzyt.jade.game.Updatable;

/**
 * Tasked enemy is an enemy which follows an updatable!
 * @author XGN
 *
 */
public class TaskedEnemy extends Enemy {

	public TaskedEnemy(TextureAtlas atlas, String regionName, int frameLength, int transitionFrameLength, float x,
			float y, float hp, float radiusS, float radiusP) {
		super(atlas, regionName, frameLength, transitionFrameLength, x, y, hp, radiusS, radiusP);
	}


	public Updatable task;
	
	public int creationFrame=-1;
	
	/**
	 * A relative timestamp is given to the task<br/>
	 * Please notice that!
	 */
	@Override
	public void update(int t) {
		
		if(creationFrame==-1) {
			creationFrame=t;
		}
		super.update(t);
		
		task.update(t-creationFrame);
	}
}
