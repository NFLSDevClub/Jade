package com.zzzyt.jade.game.task;

import com.zzzyt.jade.game.Task;
import com.zzzyt.jade.game.operator.Operator;
import com.zzzyt.jade.util.J;

public class AddOperator implements Task {

	public Operator operator;

	public AddOperator(Operator operator) {
		this.operator = operator;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void update(int frame) {
		J.addOperator(operator);
	}

	@Override
	public void init() {
		
	}

}
