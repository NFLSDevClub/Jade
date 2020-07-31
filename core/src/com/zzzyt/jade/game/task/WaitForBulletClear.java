package com.zzzyt.jade.game.task;

import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.game.entity.EnemyBullet;
import com.zzzyt.jade.util.J;

public class WaitForBulletClear implements Task {

	@Override
	public boolean isFinished() {
		Array<Bullet> bullets = J.getBullets();
		for (int i = 0; i < bullets.size; i++) {
			if (bullets.get(i) != null && bullets.get(i) instanceof EnemyBullet) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void update(int t) {

	}

	@Override
	public void init() {

	}

}
