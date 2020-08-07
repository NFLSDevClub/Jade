package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.util.J;

public class AddOperator implements Task {

	public int tag;
	public Operator operator;

	public AddOperator(int tag, Operator operator) {
		this.tag = tag;
		this.operator = operator;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void update(int t) {
		J.addOperator(tag, operator);
	}

	@Override
	public void init() {

	}

}
