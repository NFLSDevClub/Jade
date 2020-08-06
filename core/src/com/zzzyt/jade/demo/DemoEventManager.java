package com.zzzyt.jade.demo;

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

public class DemoEventManager extends EventManager {
	public int playerLife=3;
	public int playerBomb=2;
	public long score=0;
	public long maxPoint=100000;
	
	
	@Override
	public void onUpdate(int frame) {
		J.getPlayer().canBomb=(playerBomb!=0);
	}
	
	@Override
	public void onBomb(BasicPlayer basicPlayer) {
		playerBomb--;
	}
	
	@Override
	public void onRebirthStart(BasicPlayer basicPlayer) {
		playerLife--;
	}
	
	@Override
	public void onItem(Item item, Player player) {
		score+=maxPoint;
	}
	
	@Override
	public void onGraze(BasicPlayer basicPlayer, EnemyBullet eb) {
		maxPoint+=20;
		score+=20;
	}
	
	@Override
	public void onEnemyDamage(PlayerBullet playerBullet, Enemy e) {
		score+=10*playerBullet.damage;
	}
	
	@Override
	public void onSpellcardFinish(Spellcard currentSpellcard, BossScene bossScene) {
		if(!bossScene.failBonus) {
			score+=currentSpellcard.getBonus();
			System.out.println("Bonus!!!");
		}
	}
}
