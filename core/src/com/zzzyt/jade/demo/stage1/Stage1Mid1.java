package com.zzzyt.jade.demo.stage1;

import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.game.entity.Enemy;
import com.zzzyt.jade.game.operator.Acceleration;
import com.zzzyt.jade.game.task.BasicTask;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.J;

public class Stage1Mid1 extends BasicTask {

	private float tarX, tarY;
	private float totX, totY;

	@Override
	public void init() {
		super.init();
		setUpdateFunc((frame) -> {
			if (frame == 0) {
				J.add(new Enemy(A.get("th10_sanae.atlas"), "th10_sanae", 10, 5, 0, -100, 1000, 32, 16, true));
			}
			if (frame >= 24 * 60) {
				terminate();
				J.clearOperators();
				return;
			}
			if (frame == 12 * 60) {
				J.addOperator(0, new Acceleration(0.01f, 5));
			}
			Enemy tmp = J.getEnemies().get(0);
			float del = 1 - Math.abs((tmp.getX() - tarX) / totX);
			System.out.println(del);
			if (del < 0.9) {
				del = (float) (Math.cos(Math.PI * (del - 0.5)) * Math.PI / 2) / 100 + 0.01f;
				tmp.setXY(tmp.getX() + totX * del, tmp.getY() + totY * del);
			}

			if (frame % 120 == 0 && frame > 0) {
				if (frame < 18 * 60) {
					tarX = MathUtils.random(-100, 100);
					tarY = MathUtils.random(-300, 0);
				} else {
					tarX = 0;
					tarY = -100;
				}
				totX = tarX - tmp.getX();
				totY = tarY - tmp.getY();
			}
			if (frame % J.diffSelect(60, 45, 30, 15) == 0) {
//				B.create(MathUtils.random(-150, 150), 50, MathUtils.random(-150, -30), 2, MathUtils.random(9, 16), 0);
				TestEnemy te = new TestEnemy(A.get("th10_fairy.atlas"), "th10_fairy_blue", 5, 5, -200, 0, 30, 50, 5,
						false);

				te.task = (t) -> {

					if (t % 120 >= 60 && t % 120 <= 120) {

					} else {
						te.x += 1;
						te.y -= 0.5f;
					}

					if (t % J.diffSelect(30, 30, 10, 5) == 0) {
						B.create(te.x, te.y, MathUtils.random(0, 360), 2, MathUtils.random(9, 16), 0);
					}
				};

				J.add(te);
			}
		});
	}

}
