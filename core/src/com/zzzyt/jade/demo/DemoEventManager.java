package com.zzzyt.jade.demo;

import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.game.BossScene;
import com.zzzyt.jade.game.EventManager;
import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.game.entity.BasicPlayer;
import com.zzzyt.jade.game.entity.Enemy;
import com.zzzyt.jade.game.entity.EnemyBullet;
import com.zzzyt.jade.game.entity.Item;
import com.zzzyt.jade.game.entity.PlayerBullet;
import com.zzzyt.jade.game.task.Spellcard;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.U;

public class DemoEventManager extends EventManager {
	public int playerLife = 3;
	public int playerBomb = 3;
	public long score = 0;
	public long maxPoint = 100000;

	public Logger logger = new Logger("DemoEventManager", U.config().logLevel);

	@Override
	public void onUpdate(int frame) {
		J.getPlayer().canBomb = (playerBomb != 0);
	}

	@Override
	public void onBomb(BasicPlayer basicPlayer) {
		playerBomb--;
	}

	@Override
	public void onRebirthStart(BasicPlayer basicPlayer) {
		playerLife--;
		playerBomb = 3;
	}

	@Override
	public void onItem(Item item, Player player) {
		score += maxPoint;
	}

	@Override
	public void onGraze(BasicPlayer basicPlayer, EnemyBullet eb) {
		maxPoint += 20;
		score += 20;
	}

	@Override
	public void onEnemyDamage(PlayerBullet playerBullet, Enemy e) {
		score += 10 * playerBullet.damage;
	}

	@Override
	public void onSpellcardFinish(Spellcard currentSpellcard, BossScene bossScene) {
		if (!bossScene.getCurrentSpellcard().failBonus) {
			score += currentSpellcard.getBonus();
			logger.info("Get Spellcard Bonus");
		}
	}
}
