package com.zzzyt.jade.game.sequence;

import com.zzzyt.jade.game.Sequence;
import com.zzzyt.jade.game.operator.Operator;
import com.zzzyt.jade.util.J;

public class AddOperator implements Sequence {

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

}
