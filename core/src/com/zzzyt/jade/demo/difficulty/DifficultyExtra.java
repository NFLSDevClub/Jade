package com.zzzyt.jade.demo.difficulty;

import com.zzzyt.jade.demo.stage_extra.StageExtra;
import com.zzzyt.jade.game.Difficulty;
import com.zzzyt.jade.util.J;

public class DifficultyExtra extends Difficulty {

	public DifficultyExtra() {
		super(J.EXTRA);
	}

	@Override
	public void init() {
		add(new StageExtra());
	}
}
