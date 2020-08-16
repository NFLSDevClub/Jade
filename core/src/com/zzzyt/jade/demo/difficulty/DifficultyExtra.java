package com.zzzyt.jade.demo.difficulty;

import com.zzzyt.jade.demo.stageextra.StageExtra;
import com.zzzyt.jade.game.task.Sequence;

public class DifficultyExtra extends Sequence {

	@Override
	public void init() {
		super.init();
		add(new StageExtra());
	}
}
