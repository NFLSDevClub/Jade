package com.zzzyt.jade.demo.difficulty;

import com.zzzyt.jade.demo.stage1.Stage1;
import com.zzzyt.jade.game.task.Plural;
import com.zzzyt.jade.util.J;

public class DifficultyRegular extends Plural {

	private int difficulty;

	public DifficultyRegular(int difficulty) {
		super();
		this.difficulty = difficulty;
	}

	@Override
	public void init() {
		super.init();
		add(J.diffSelect(difficulty, new Stage1(), new Stage1(), new Stage1(), new Stage1()));
	}
}
