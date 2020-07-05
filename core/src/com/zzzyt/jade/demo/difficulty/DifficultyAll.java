package com.zzzyt.jade.demo.difficulty;

import com.zzzyt.jade.demo.stage1.Stage1;
import com.zzzyt.jade.game.Difficulty;

public class DifficultyAll extends Difficulty {
	public DifficultyAll(String difficultyName) {
		super(difficultyName);
		add(new Stage1());
	}
}
