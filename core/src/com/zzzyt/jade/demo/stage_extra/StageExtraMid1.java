package com.zzzyt.jade.demo.stage_extra;

import com.zzzyt.jade.game.operator.AngularVelocityOperator;
import com.zzzyt.jade.game.sequence.Single;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.M;

public class StageExtraMid1 extends Single {

	private float tmpf;

	public StageExtraMid1() {
		super();
		setUpdateFunc((frame) -> {
			if (frame >= 16 * 60) {
				terminate();
				return;
			}
			if (frame == 8 * 60) {
				J.addOperator(new AngularVelocityOperator(0, 0.5f, 180));
			}
			if (frame % 3 == 0) {
				for (int i = 0; i < 7; i++) {
					B.as(0, -100, i * 360f / 7f + tmpf, M.min(frame % 60, 60 - frame % 60) / 60f + 1, i + 9, 0);
				}
			}
			tmpf += (float) (frame / 30) / 8 + 1;
		});
		this.tmpf = 0;
	}
}
