package com.zzzyt.jade.game;

import com.zzzyt.jade.game.entity.BasicPlayer;
import com.zzzyt.jade.game.entity.EnemyBullet;
import com.zzzyt.jade.game.entity.Item;

/**
 * An event manager receives event and you can do custom things! Hooray <br/>
 * This class implements an empty event manager
 * @author XGN
 *
 */
public class EventManager {

	public void onBomb(BasicPlayer basicPlayer) {
		// TODO Auto-generated method stub
		
	}

	public void onRebirthStart(BasicPlayer basicPlayer) {
		// TODO Auto-generated method stub
		
	}

	public void onRebirthEnd(BasicPlayer basicPlayer) {
		// TODO Auto-generated method stub
		
	}

	public void onPlayerHit(BasicPlayer basicPlayer) {
		// TODO Auto-generated method stub
		
	}

	public void onPlayerShoot(BasicPlayer basicPlayer) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Please note that onBomb will also be fired along with this event
	 * @param basicPlayer
	 */
	public void onPlayerDeathbomb(BasicPlayer basicPlayer) {
		// TODO Auto-generated method stub
		
	}

	public void onUpdate(int frame) {
		// TODO Auto-generated method stub
		
	}

	public void onDraw() {
		// TODO Auto-generated method stub
		
	}

	public void onItem(Item item, Player player) {
		// TODO Auto-generated method stub
		
	}

	public void onGraze(BasicPlayer basicPlayer, EnemyBullet eb) {
		// TODO Auto-generated method stub
		
	}

}
