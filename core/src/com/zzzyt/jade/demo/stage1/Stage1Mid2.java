package com.zzzyt.jade.demo.stage1;

import com.zzzyt.jade.game.sequence.Single;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.M;

public class Stage1Mid2 extends Single {

	private float tmpf;

	@Override
	public void init() {
		setUpdateFunc((frame) -> {
			if (frame >= 36 * 60) {
				terminate();
				return;
			}
			if (frame % J.diffSelect(16, 10, 5, 2) == 0) {
				for (int i = 0; i < 360; i += 72) {
					B.as(0, -100, i + tmpf, 2, "DS_RICE_S_RED", 0);
					B.as(0, -100, i - tmpf, 2, "DS_RICE_S_BLUE", 0);
				}
				tmpf += M.sin(frame / 2f) * 6;
			}
		});
		this.tmpf = 72;
	}

}
