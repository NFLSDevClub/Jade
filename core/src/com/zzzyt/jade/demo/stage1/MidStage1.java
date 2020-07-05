package com.zzzyt.jade.demo.stage1;

import com.zzzyt.jade.game.BasicSequence;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.M;

public class MidStage1 extends BasicSequence {

	private float tmpf;

	public MidStage1() {
		super();
		setUpdateFunc((frame) -> {
			if (frame >= 20 * 60) {
				terminate();
				return;
			}
			if (frame % diffToChance((String) G.get("_difficulty")) == 0) {
				tmpf += M.sin(frame) * 6;
				for (int i = 0; i < 360; i += 72) {
					B.as(0, -100, i + tmpf, 2, "DS_RICE_S_RED", 0);
					B.as(0, -100, i - tmpf, 2, "DS_RICE_S_BLUE", 0);
				}
			}
		});
		this.tmpf = 72;
	}

	private static int diffToChance(String str) {
		switch (str) {
		case "easy":
			return 16;
		case "normal":
			return 10;
		case "hard":
			return 5;
		case "lunatic":
			return 2;
		default:
			return 0;
		}
	}
}
