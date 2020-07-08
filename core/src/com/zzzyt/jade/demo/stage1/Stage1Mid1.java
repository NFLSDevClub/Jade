package com.zzzyt.jade.demo.stage1;

import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.game.operator.Acceleration;
import com.zzzyt.jade.game.task.Single;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.J;

public class Stage1Mid1 extends Single {

	@Override
	public void init() {
		super.init();
		setUpdateFunc((frame) -> {
			if (frame >= 24 * 60) {
				terminate();
				J.clearOperators();
				return;
			}
			if (frame == 12 * 60) {
				J.addOperator(new Acceleration(0, 0.01f, 5));
			}
			if (frame % J.diffSelect(16, 10, 5, 2) == 0) {
				B.as(MathUtils.random(-150, 150), 50, MathUtils.random(-150, -30), 2, MathUtils.random(9, 16), 0);
			}
		});
	}

}
