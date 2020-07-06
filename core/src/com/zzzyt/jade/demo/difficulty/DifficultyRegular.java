package com.zzzyt.jade.demo.difficulty;

import com.zzzyt.jade.demo.stage1.Stage1;
import com.zzzyt.jade.game.Difficulty;

public class DifficultyRegular extends Difficulty {
	public DifficultyRegular(int difficulty) {
		super(difficulty);
		add(new Stage1());
	}
}
