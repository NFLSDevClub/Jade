package com.zzzyt.jade.demo.stageextra;

import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.game.Operator;
import com.zzzyt.jade.game.operator.Acceleration;
import com.zzzyt.jade.game.operator.AngularVelocity;
import com.zzzyt.jade.game.task.Single;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.J;

public class StageExtraMid2 extends Single {

	@Override
	public void init() {
		super.init();
		J.addOperator(new AngularVelocity(0, 0.5f, 720));
		Operator tmp = J.addOperator(new Acceleration(0, -0.03f, 0.3f));
		setUpdateFunc((frame) -> {
			if (frame >= 15 * 60) {
				terminate();
				J.clearOperators();
				return;
			}
			if (frame == 8 * 60) {
				J.removeOperator(tmp);
				J.addOperator(new Acceleration(0, 0.03f, 2f));
			}
			if (frame == 12 * 60) {
				J.clearOperators();
			}
			if (frame <= 5 * 60 && frame % 40 == 0) {
				genCircle(-50, -50, 4, MathUtils.random(-50, 50), 60, 9 + frame % 7);
				genCircle(50, -50, 4, MathUtils.random(-50, 50), 60, 9 + frame % 7);
			}
		});
	}

	private void genCircle(float x, float y, float speed, float off, int cnt, int bullet) {
		for (int i = 0; i < cnt; i++) {
			B.as(x, y, i * 360f / cnt + off, speed, bullet, 0);
		}
	}

}
