package com.zzzyt.jade.demo.difficulty;

import com.zzzyt.jade.demo.stageextra.StageExtra;
import com.zzzyt.jade.game.task.Plural;

public class DifficultyExtra extends Plural {

	@Override
	public void init() {
		super.init();
		add(new StageExtra());
	}
}
