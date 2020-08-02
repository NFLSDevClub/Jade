package com.zzzyt.jade.demo.player;

import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.game.entity.BasicPlayer;
import com.zzzyt.jade.game.entity.PlayerBullet;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.B;

public class PlayerReimu extends BasicPlayer {
	
	public PlayerReimu() {
		super(A.get("th10_player.atlas"), "th10_reimu", 5, 2, 2, 4.5f, 2);
	}

	private int shotTimer;

	@Override
	public void onShot() {
		shotTimer++;
		if (shotTimer >= 4) {
			shotTimer = 0;

			B.setAngleSpeed(new PlayerBullet(B.get(MathUtils.random(1, 16)), 1024, 1, 3), x - 10, y, 90, 20);
			B.setAngleSpeed(new PlayerBullet(B.get(MathUtils.random(1, 16)), 1024, 1, 3), x + 10, y, 90, 20);
		}
	}
}
