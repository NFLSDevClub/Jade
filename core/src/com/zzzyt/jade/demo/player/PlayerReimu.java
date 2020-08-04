package com.zzzyt.jade.demo.player;

import com.zzzyt.jade.game.entity.BasicPlayer;
import com.zzzyt.jade.game.entity.PlayerBullet;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.J;

public class PlayerReimu extends BasicPlayer {
	
	public PlayerReimu() {
		super(A.get("th10_player.atlas"), "th10_reimu", 5, 2, 2, 4.5f, 2,60,120);
	}

	private int shotTimer;

	@Override
	public void onShot() {
		super.onShot();
		
		shotTimer++;
		if (shotTimer >= 4) {
			shotTimer = 0;

			B.setAngleSpeed(new PlayerBullet(B.get("PLAYER_REIMU_STANDARD"), 1024, 1, 3), x - 10, y, 90, 20);
			B.setAngleSpeed(new PlayerBullet(B.get("PLAYER_REIMU_STANDARD"), 1024, 1, 3), x + 10, y, 90, 20);
		}
	}
	
	@Override
	public float getItemCollectionLineHeight() {
		return -100;
	}
	
	@Override
	public void onBomb() {
		super.onBomb();
		
		J.clearBullets(true);
	}
}
