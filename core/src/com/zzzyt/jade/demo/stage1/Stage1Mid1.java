package com.zzzyt.jade.demo.stage1;

import com.badlogic.gdx.math.MathUtils;
import com.zzzyt.jade.game.entity.TaskedEnemy;
import com.zzzyt.jade.game.operator.Acceleration;
import com.zzzyt.jade.game.task.Single;
import com.zzzyt.jade.util.A;
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
			if (frame % J.diffSelect(180,120,60,45) == 0) {
//				B.create(MathUtils.random(-150, 150), 50, MathUtils.random(-150, -30), 2, MathUtils.random(9, 16), 0);
				TaskedEnemy te=new TaskedEnemy(A.get("th10_player.atlas"), "th10_reimu", 5, 2, -200, 0, 300, 10, 1);
				
				te.task=(t)->{
					te.x+=1;
					te.y-=0.5f;
					
					if(t%J.diffSelect(60,50,40,30)==0) {
						B.create(te.x, te.y, MathUtils.random(0,360), 2, MathUtils.random(9, 16), 0);
					}
				};
				
				J.add(te);
			}
		});
	}

}
