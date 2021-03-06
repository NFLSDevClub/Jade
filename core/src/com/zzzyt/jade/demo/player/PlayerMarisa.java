package com.zzzyt.jade.demo.player;

import com.zzzyt.jade.game.entity.BasicPlayer;
import com.zzzyt.jade.game.entity.PlayerBullet;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.SE;

public class PlayerMarisa extends BasicPlayer {
	
	public PlayerMarisa() {
		super(A.get("th10_player.atlas"), "th10_marisa", 5, 2, 3.5f, 5, 2,30,300);
	}
	
	private int shotTimer;

	@Override
	public void onShot() {
		super.onShot();
		
		shotTimer++;
		if (shotTimer >= 2) {

			SE.play("shoot");
			shotTimer = 0;

			B.setAngleSpeed(new PlayerBullet(B.get(153), 1024, 1000, 0.5f), x - 10, y, 90, 25);
			B.setAngleSpeed(new PlayerBullet(B.get(156), 1024, 1000, 0.5f), x + 10, y, 90, 25);
		}
	}
	
	@Override
	public float getItemCollectionLineHeight() {
		return -50;
	}
	
	@Override
	public void onBomb() {
		super.onBomb();
		
		J.clearBullets(true);
	}
}
