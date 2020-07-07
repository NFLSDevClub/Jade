package com.zzzyt.jade.demo.player;

import com.zzzyt.jade.game.entity.BasicPlayer;
import com.zzzyt.jade.util.A;

public class PlayerMarisa extends BasicPlayer {
	public PlayerMarisa() {
		super(A.get("th10_player.atlas"), "th10_marisa", 5, 2, 3.5f, 5, 2);
	}
}
